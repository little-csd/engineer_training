from torch.utils.data import Dataset, DataLoader
from pycocotools.coco import COCO
from nltk.tokenize import word_tokenize
from build_vocab import Vocabulary
from PIL import Image
import torch
import os
from torchvision import transforms



class CocoDataset(Dataset):
    """Coco custom dataset compatible with torch.utils.data.DataLoader"""
    def __init__(self, root, json, vocab, transform=None):
        """Set the path for images, captions, and vocabulary wrapper.
        Args:
            root: image directory
            json: coco annotation file path
            vocab: vocabulary wrapper
            transform: image transformer
        """

        self.root = root
        self.coco = COCO(json)
        self.ids = list(self.coco.anns.keys())
        self.transform = transform
        self.vocab = vocab

    def __getitem__(self, index):
        """Returns one data pair (image and caption)"""
        coco = self.coco 
        vocab =  self.vocab
        ann_id = self.ids[index]
        caption = coco.anns[ann_id]['caption']
        img_id = coco.anns[ann_id]['image_id']
        path = coco.loadImgs(img_id)[0]['file_name']
        image = Image.open(os.path.join(self.root, path)).convert('RGB')

        if self.transform is not None:
            image = self.transform(image)

        tokens = word_tokenize(str(caption).lower())
        caption = []
        caption.append(vocab('<start>'))
        caption.extend([vocab(token) for token in tokens])
        caption.append(vocab('<end>'))
        target = torch.Tensor(caption)

        return image, target


    def __len__(self):
        return len(self.ids)



def collate_fn(data: list):
    """Creates mini_batch tensors from the list of tuples (image, caption)
    # build custom collate_fn because merging caption including padding is not supported in default
    Args:
        data: list of tuple(image, caption) length of list is the batch_size
            - image: torch tensor of shape(3, 256, 256)
            - caption: torch tensor of shape (?); variable length

    Returns:
        images: torch tensor of shape (batch_size, 3, 256, 256)
        targets: torch tensor of shape(batch_size, padded_length)
        lengths: list; valid length for each padded caption
    """
    # Sort a data list by caption length (descending order)
    data.sort(key=lambda x: len(x[1]), reverse=True)
    images, captions = zip(*data)

    # Merge images from tuple of 3D tensor to 4D tensor
    images = torch.stack(images, 0) # batch_size, 3, 256 ,256

    # Merge captions from tuple of 1D tensor to 2D tensor
    lengths = [len(cap) for cap in captions]
    targets = torch.zeros(len(captions), max(lengths)).long()
    for i, cap in enumerate(captions):
        end = lengths[i]
        targets[i, :end] = cap[:end]
    return images, targets, lengths




def get_loader(root, json, vocab, transform, batch_size, shuffle, num_workers):
    """Return torch.utils.data.DataLoader for custom coco dataset"""
    # COCO caption dataset
    coco = CocoDataset(root, json, vocab, transform=transform)

    # Data loader for COCO dataset
    # return (images, captions, lengths) for each iteration
    # images: a tensor of shape (batch_size, 3, 224, 224)
    # captions: a tensor of shape (batch_size, padded_length)
    # lengths: a list indicating valid length for each caption, len(lengths) = batch_size

    data_loader = DataLoader(dataset=coco, batch_size=batch_size, shuffle=shuffle, num_workers=num_workers, collate_fn=collate_fn)
    return data_loader

