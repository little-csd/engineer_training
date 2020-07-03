import nltk
from collections import Counter
from pycocotools.coco import COCO
import argparse
import pickle

class Vocabulary():
    """Simple vocabulary wrapper"""
    def __init__(self):
        self.word2idx = {}
        self.idx2word = []
        self.idx = 0

    def add_word(self, word):
        if not word in self.word2idx:
            self.word2idx[word] = self.idx
            self.idx2word.append(word)
            self.idx += 1

    def __call__(self, word):
        if not word in self.word2idx:
            return self.word2idx['<unk>']
        return self.word2idx[word]

    def __len__(self):
        return len(self.word2idx)


def build_vocab(json, threshold):
    """Build a simple vocab wrapper"""
    coco = COCO(json)
    counter = Counter()
    ids = coco.anns.keys()

    for i, id in enumerate(ids):
        caption = str(coco.anns[id]['caption'])
        tokens = nltk.tokenize.word_tokenize(caption.lower())
        counter.update(tokens)

        if (i+1) % 1000 == 0:
            print("[{}/{}] Tokenized the captions".format(i+1, len(ids)))

    # If the word freq is less than threshold, the word is discarded
    words = [word for word, cnt in counter.items() if cnt >= threshold]

    # Create a vocab wrapper and add some special tokens
    vocab = Vocabulary()
    vocab.add_word('<pad>')
    vocab.add_word('<start>')
    vocab.add_word('<end>')
    vocab.add_word('<unk>')
    for i, word in enumerate(words):
        vocab.add_word(word)
    return vocab

def main(args):
    vocab = build_vocab(args.caption_path, args.threshold)
    vocab_path = args.vocab_path
    with open(vocab_path, 'wb') as f:
        pickle.dump(vocab, f)
    print("Total vocabulary size: {}".format(len(vocab)))
    print('Saved the vocabulary wrapper to {}'.format(vocab_path))



if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--threshold', type=int, default=4)
    parser.add_argument('--caption_path', type=str, default='./data/annotations/captions_train2014.json')
    parser.add_argument('--vocab_path', type=str, default='./data/vocab.pkl')
    args = parser.parse_args()
    main(args)

