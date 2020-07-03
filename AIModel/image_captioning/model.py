import torch
import torch.nn as nn
import torchvision
from torch.nn.utils.rnn import pack_padded_sequence

class EncoderCNN(nn.Module):
    def __init__(self, embed_size):
        super(EncoderCNN, self).__init__()
        resnet = torchvision.models.resnet152(pretrained=True)
        modules = list(resnet.children())[:-1] # delete the last fc layer
        self.resnet = nn.Sequential(*modules)
        self.linear = nn.Linear(resnet.fc.in_features, embed_size)
        self.bn = nn.BatchNorm1d(embed_size, momentum=0.01)

    def forward(self, images):
        """Extract feature vectors from input images"""
        with torch.no_grad():
            features = self.resnet(images) # batch, channels, 1, 1
        features = features.reshape(features.size(0), -1) # batch, in_features(channels)
        features = self.linear(features) # batch, embed_size
        features = self.bn(features)
        return features

        

class DecoderRNN(nn.Module):

    def __init__(self, embed_size, hidden_size, vocab_size, num_layers, max_seq_length=20):
        super(DecoderRNN, self).__init__()
        self.embed = nn.Embedding(vocab_size, embed_size)
        self.lstm = nn.LSTM(input_size=embed_size, 
            hidden_size=hidden_size, 
            num_layers=num_layers, 
            batch_first=True)
        self.linear = nn.Linear(hidden_size, vocab_size)
        self.max_seq_length = max_seq_length

    def forward(self, features, captions, lengths):
        """Decode image feature vectors and generates captions"""
        embeddings = self.embed(captions) # batch, seq_len -> batch, seq_len, embed_size

        embeddings = torch.cat((features.unsqueeze(1), embeddings), 1) # batch, seq_len+1, embed_size

        packed = pack_padded_sequence(embeddings, lengths, batch_first=True) # lengths[i].sum, embed_size

        outputs, _ = self.lstm(packed)  
        outputs = self.linear(outputs[0]) # lengths[i].sum, vocab_size
        return outputs

    def sample(self, features, states=None):
        """Generate captions for given image features using greedy search"""
        # batch = 1
        sample_ids = []
        inputs = features.unsqueeze(1)
        for i in range(self.max_seq_length):
            hiddens, states = self.lstm(inputs, states) # hiddens:batch, 1, hidden_size
            outputs = self.linear(hiddens.squeeze(1)) # batch, vocab_size
            _, predicted = outputs.max(1) # batch
            sample_ids.append(predicted.item())

            inputs = self.embed(predicted) # batch, embed_size
            inputs = inputs.unsqueeze(1)

        # sampled_ids = torch.stack(sample_ids) # batch, max_seq_length
        return sample_ids



