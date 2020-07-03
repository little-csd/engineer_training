import torch
from torchvision import transforms
import argparse
from PIL import Image
import pickle
from model import EncoderCNN, DecoderRNN
import numpy as np
import matplotlib.pyplot as plt
from build_vocab import Vocabulary
import io


def load_image(bytes_stream, transform=None):
	image =Image.open(io.BytesIO(bytes_stream))
	image = image.resize((224, 224), Image.LANCZOS)
	if transform is not None:
		image = transform(image).unsqueeze(0)
	return image




def main(bytestream):
	encoder_path = 'models/encoder-5-3000.ckpt'
	decoder_path = 'models/decoder-5-3000.ckpt'
	vocab_path = './data/vocab.pkl'
	embed_size = 256
	hidden_size = 512
	num_layers = 1
	device = torch.device('cpu')
	# Image preprocessing
	transform = transforms.Compose([
		transforms.ToTensor(),
		transforms.Normalize((0.485, 0.456, 0.406),
							 (0.229, 0.224, 0.225))])

	# Load vocab wrapper
	with open(vocab_path, 'rb') as f:
		vocab = pickle.load(f)

	encoder = EncoderCNN(embed_size).eval().to(device)
	decoder = DecoderRNN(embed_size, hidden_size, len(vocab), num_layers).to(device)

	# Load trained model params
	encoder.load_state_dict(torch.load(encoder_path, map_location='cpu'))
	decoder.load_state_dict(torch.load(decoder_path, map_location='cpu'))

	# Prepare an image
	image = load_image(bytestream, transform)
	image_tensor = image.to(device)

	# Generate an caption from the image
	feature = encoder(image_tensor)

	sampled_ids = decoder.sample(feature)
	# sampled_ids = sampled_ids[0] # (max_seq_length)

	# Convert word_ids to words
	sampled_caption = []
	for word_id in sampled_ids:
		word = vocab.idx2word[word_id]
		sampled_caption.append(word)
		if word == '<end>':
			break
	sentence = ' '.join(sampled_caption)
	return sentence


