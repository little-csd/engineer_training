import torch
import torch.nn as nn
import argparse
import os
from torchvision import transforms
import pickle
from build_vocab import Vocabulary
from data_loader import get_loader
from model import EncoderCNN, DecoderRNN
from torch.nn.utils.rnn import pack_padded_sequence
import numpy as np


parser = argparse.ArgumentParser()
parser.add_argument('--model_path', type=str, default='models/')
parser.add_argument('--crop_size', type=int, default=224)
parser.add_argument('--vocab_path', type=str, default='data/vocab.pkl')
parser.add_argument('--image_dir', type=str, default='data/resized2014')
parser.add_argument('--caption_path', type=str, default='data/annotations/captions_train2014.json')
parser.add_argument('--log_step', type=int, default=10)
parser.add_argument('--save_step', type=int, default=1000)

parser.add_argument('--embed_size', type=int, default=256)
parser.add_argument('--hidden_size', type=int, default=512)
parser.add_argument('--num_layers', type=int, default=1)

parser.add_argument('--num_epochs', type=int, default=5)
parser.add_argument('--batch_size', type=int, default=128)
parser.add_argument('--num_workers', type=int, default=2)
parser.add_argument('--learning_rate', type=float, default=1e-3)

parser.add_argument('--gpu_id', type=int, default=0)

args = parser.parse_args()
print(args)





device = torch.device('cuda:' + str(args.gpu_id))


# Create model directory
if not os.path.exists(args.model_path):
    os.makedirs(args.model_path)


# Image preprocessing, normalization for the pretrained resnet
transform = transforms.Compose([
    transforms.RandomCrop(args.crop_size),
    transforms.RandomHorizontalFlip(),
    transforms.ToTensor(),
    transforms.Normalize(mean=(0.485, 0.456, 0.406),
                        std=(0.229, 0.224, 0.225))])

# Load vocab wrapper
with open(args.vocab_path, 'rb') as f:
    vocab: Vocabulary = pickle.load(f)

data_loader = get_loader(args.image_dir, args.caption_path, vocab, transform, args.batch_size, True, num_workers=args.num_workers)

# Build the model
encoder = EncoderCNN(args.embed_size).to(device)
decoder = DecoderRNN(args.embed_size, args.hidden_size, len(vocab), args.num_layers).to(device)

# Loss and optimizer
criterion = nn.CrossEntropyLoss()
params = list(decoder.parameters()) + list(encoder.linear.parameters()) + list(encoder.bn.parameters())
optimizer = torch.optim.Adam(params, lr=args.learning_rate)


# Train
total_step = len(data_loader)
for epoch in range(args.num_epochs):
    for i, (images, captions, lengths) in enumerate(data_loader):

        # Set mini-batch dataset

        images = images.to(device)
        captions = captions.to(device) 
        targets = pack_padded_sequence(captions, lengths, batch_first=True)[0] # [lengths[i].sum]

        features = encoder(images) # batch, embed_size
        outputs = decoder(features, captions, lengths)
        loss = criterion(outputs, targets)

        decoder.zero_grad()
        encoder.zero_grad()
        loss.backward()
        optimizer.step()


        if i % args.log_step == 0:
            print('Epoch [{}/{}], Step [{}/{}], Loss: {:.4f}, ppl:{:5.4f}'
                .format(epoch+1, args.num_epochs, i+1, total_step, loss.item(), np.exp(loss.item())))

        # Save the model checkpoints
        if (i+1) % args.save_step == 0:
            torch.save(decoder.state_dict(), os.path.join(
                args.model_path, 'decoder-{}-{}.ckpt'.format(epoch+1, i+1)))
            torch.save(encoder.state_dict(), os.path.join(
                args.model_path, 'encoder-{}-{}.ckpt'.format(epoch+1, i+1)))

