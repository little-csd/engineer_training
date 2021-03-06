import torch
import torchvision
import torch.nn as nn
from PIL import Image
import numpy as np
import torchvision.transforms as transforms
import argparse
import os
import io


def load_image(image_bytes, transform=None, max_size=None, shape=None):
    """Load an image and convert it to a torch tensor"""
    image = Image.open(io.BytesIO(image_bytes))
    if max_size:
        scale = max_size / max(image.size)
        size = np.array(image.size) * scale
        image = image.resize(size.astype(int), Image.ANTIALIAS)

    if shape:
        image = image.resize(shape, Image.LANCZOS)

    if transform:
        image = transform(image).unsqueeze(0)

    return image


class VGGNet(nn.Module):
    def __init__(self):
        """Select  conv1_1 ~ conv5_1  activation maps"""
        super(VGGNet, self).__init__()
        self.select = ['0', '5', '10', '19', '28']
        self.vgg: nn.Module = torchvision.models.vgg19(pretrained=True).features

    def forward(self, x):
        """Extract  multiple convolutional feature maps"""
        features = []
        for name, layer in self.vgg.named_children():
            x = layer(x)
            if name in self.select:
                features.append(x)
        return features


def image_to_byte_array(image:Image):
  imgByteArr = io.BytesIO()
  image.save(imgByteArr, format=image.format)
  imgByteArr = imgByteArr.getvalue()
  return imgByteArr


def main(content_bytes, style_bytes):
    gpu_id = 0
    max_size = 400
    total_step = 2000
    log_step = 10
    sample_step = 500
    style_weight = 100
    lr = 0.003
    device = torch.device('cuda:' + str(gpu_id) if torch.cuda.is_available() else 'cpu')

    # Image preprocessing
    # use the same normalization statistics like ImageNet here
    transform = transforms.Compose([
        transforms.ToTensor(),
        transforms.Normalize(mean=(0.485, 0.456, 0.406),
                             std=(0.229, 0.224, 0.225))])

    # Load content and style images
    # Make the style image same size as the content image
    content = load_image(content_bytes, transform, max_size)
    style = load_image(style_bytes, transform, shape=[content.size(2), content.size(3)])
    content = content.to(device)
    style = style.to(device)
    # Initialize a target image with the content image
    target = content.clone().requires_grad_(True)

    optimizer = torch.optim.Adam([target], lr=lr)

    vgg = VGGNet().to(device).eval()

    for step in range(total_step):
        # Extract multiple(5) conv feature vectors
        target_features = vgg(target)
        content_features = vgg(content)
        style_features = vgg(style)

        content_loss = 0
        style_loss = 0

        for f1, f2, f3 in zip(target_features, content_features, style_features):
            content_loss += torch.mean((f1 - f2) ** 2)
            _, c, h, w = f1.size()
            f1 = f1.reshape(c, h * w)
            f3 = f3.reshape(c, h * w)

            # Compute gram matrix
            f1 = torch.mm(f1, f1.t())
            f3 = torch.mm(f3, f3.t())

            style_loss += torch.mean((f1 - f3) ** 2) / (c * h * w)

        loss = content_loss + style_weight * style_loss
        optimizer.zero_grad()
        loss.backward()
        optimizer.step()

        if (step + 1) % log_step == 0:
            print('Step [{}/{}], Content Loss: {:.4f}, Style Loss: {:.4f}'
                  .format(step + 1, total_step, content_loss.item(), style_loss.item()))


        # maybe need to modify here
        if (step + 1) % sample_step == 0:
            # Save generated image
            denorm = transforms.Normalize(mean=(-2.12, -2.04, -1.8),
                                          std=(4.37, 4.46, 4.44))
            img = target.clone().squeeze()
            img = denorm(img).clamp_(0, 1)

            imgByteArr = image_to_byte_array(img)
            return imgByteArr

            if not os.path.exists('outputImage'):
                os.makedirs('outputImage')
                torchvision.utils.save_image(img, 'outputImage/' + 'output-{}.png'.format(step + 1))
            else:
                torchvision.utils.save_image(img, 'outputImage/' + 'output-{}.png'.format(step + 1))

