# # a = ['1','2','3','1sds']
# # # b = ' '.join(a)
# # # print(b)
#
# # # print(a)
# # a = 'dadasd'
# # print(list(a))
# # import torch
# # a = torch.tensor([1,2,3], requires_grad=True, dtype=float)
# # b = a.sum()
# # b.backward()
# # print(a.grad)
# # q = torch.tensor([1,2,3], requires_grad=True, dtype=float)
#
#
# # with torch.no_grad():
# # 	c = a.sum()
#
#
# # d = c*3 + q.sum()
# # d.backward()
# # print(a.grad)
# # print(c.requires_grad)
# # a = torch.randn(3,5)
# # print(a.size())
# # a = a.unsqueeze(1)
# # print(a.size())
# # print(a)
# # a = torch.randn(3)
# # print(a)
# # print(a.item())
#
#
# # a = torch.randn(6,7)
# # b = torch.randn(6,7)
# # print(a)
# # print(b)
# # c = torch.stack((a, b), 2)
# # print(c)
# # print(c.size())
# # a = torch.tensor([3])
# # b = torch.tensor([4])
# # c = torch.tensor([5])
# # d = torch.stack([a,b,c] ,1)
# # print(d)
#
# # e = torch.cat((a,b,c), dim=0)
# # print(e)
#
# # a = [4,6,3,4,6]
# # a.sort(key=lambda x: x)
# # print(a)
#
# # a, b = zip([1,2],[3,4],[4,5],[3,4])
# # print(a)
# # a = torch.randn(3, 256, 256)
# # b = torch.randn(3, 256, 256)
# # c = torch.randn(3, 256, 256)
# # d = torch.stack((a,b,c), dim=0)
# # # print(d.size())
# # criterion = torch.nn.CrossEntropyLoss()
# # a = torch.randn(3,5)
# # b = torch.zeros(3).long()
# # print(criterion(a, b))
# # a = [3,4,5,1,2,3,45]
# # a = torch.FloatTensor([3,4,5])
# # print(type(a))
# # a = torch.randn(2,4,5)
# # print(len(a))
# # import torch.nn as nn
# # class testNet(nn.Module):
# # 	def __init__(self):
# # 		super(testNet, self).__init__()
# # 		self.embedding = nn.Embedding(5, 4)
# # 		self.conv1 = nn.Conv2d(3, 2, 3)
# # 		self.linear = nn.Linear(3, 2)
#
#
# # a = testNet()
# # c = list(a.parameters())
#
# # # print(type(b))
#
# # # a = [torch.tensor([3,4,5]), torch.tensor([1,2,32,3,32,32,3])]
# # # c = iter(a)
# # print(type(c[0]))
# import numpy as np
#
# # a = torch.from_numpy(np.array([1,2,3,4]))
# # b = torch.autograd.Variable(a, )
# # print(b.requires_grad)
#
#
# # a = torch.linspace(1, 12, steps=12).view(3, 4)
# # print(a)
# # b = torch.index_select(a, 0, torch.tensor([2, 0]))
# # print(b)
# # print(a.index_select(0, torch.tensor([0, 2])))
# # c = torch.index_select(a, 1, torch.tensor([1, 3]))
# # print(c)
#
#
#
#
# import torch
# from torch import nn
#
#
# def rnn_forwarder(rnn, inputs, seq_lengths):
#     """
#     :param rnn: RNN instance
#     :param inputs: FloatTensor, shape [batch, time, dim] if rnn.batch_first else [time, batch, dim]
#     :param seq_lengths: LongTensor shape [batch]
#     :return: the result of rnn layer,
#     """
#     batch_first = rnn.batch_first
#
#
#
#     # assume seq_lengths = [3, 5, 2]
#     # 对序列长度进行排序(降序), sorted_seq_lengths = [5, 3, 2]
#     # indices 为 [1, 0, 2], indices 的值可以这么用语言表述
#     # 原来 batch 中在 0 位置的值, 现在在位置 1 上.
#     # 原来 batch 中在 1 位置的值, 现在在位置 0 上.
#     # 原来 batch 中在 2 位置的值, 现在在位置 2 上.
#     sorted_seq_lengths, indices = torch.sort(seq_lengths, descending=True)
#
#
#
#     # 如果我们想要将计算的结果恢复排序前的顺序的话,
#     # 只需要对 indices 再次排序(升序),会得到 [0, 1, 2],
#     # desorted_indices 的结果就是 [1, 0, 2]
#     # 使用 desorted_indices 对计算结果进行索引就可以了.
#     _, desorted_indices = torch.sort(indices, descending=False)
#
#     # 对原始序列进行排序
#     if batch_first:
#         inputs = inputs[indices]
#     else:
#         inputs = inputs[:, indices]
#
#     packed_inputs = nn.utils.rnn.pack_padded_sequence(inputs,
#                                                       sorted_seq_lengths,
#                                                       batch_first=batch_first)
#
#     # print(packed_inputs[0])
#     # print(packed_inputs[0].size())
#     # print(packed_inputs[0])
#     # print(packed_inputs)
#     # res, state = rnn(packed_inputs)
#     # # print(res[0])
#     # padded_res, _ = nn.utils.rnn.pad_packed_sequence(res, batch_first=batch_first)
#
#     # # print(padded_res)
#
#     # # 恢复排序前的样本顺序
#     # if batch_first:
#     #     desorted_res = padded_res[desorted_indices]
#     #     print(desorted_res)
#     # else:
#     #     desorted_res = padded_res[:, desorted_indices]
#     # return desorted_res
#
#
# if __name__ == "__main__":
#     bs = 3
#     max_time_step = 5
#     feat_size = 15
#     hidden_size = 2
#
#     seq_lengths = torch.tensor([3, 2, 4], dtype=torch.long)
#
#     rnn = nn.GRU(input_size=feat_size,
#                  hidden_size=hidden_size, batch_first=True, bidirectional=True)
#     x = torch.arange(3*5).reshape(bs, max_time_step).float()
#     # print(x)
#
#     using_packed_res = rnn_forwarder(rnn, x, seq_lengths)
#     # print(using_packed_res)
#
#     # # 不使用 pack_paded, 用来和上面的结果对比一下.
#     # not_packed_res, _ = rnn(x)
#     # print(not_packed_res)
#
# # a = torch.tensor([[3, 5, 2], [4,0,9]])
# # b = a[:,[0,2,1]]
# # print(b)
#
#
# # a = torch.tensor([[1,2,3,4],[2,3,4,5]])
# # b = a+1
# # print(b)
# # print(np.exp(1))
#
# # a = torch.tensor([1,2,3],dtype=float, requires_grad=True)
#
# import torch
# import torch.nn as nn
# import torch.nn.functional as F
#
#
# class Net(nn.Module):
#
#     def __init__(self):
#         super(Net, self).__init__()
#         # 1 input image channel, 6 output channels, 5x5 square convolution
#         # kernel
#         self.conv1 = nn.Conv2d(1, 6, 5)
#         self.pool1 = nn.MaxPool2d(3)
#         # an affine operation: y = Wx + b
#         self.fc1 = nn.Linear(16 * 5 * 5, 120)
#         a = self.fc1.parameters()
#         for i in a:
#             i.requires_grad = False
#
#     def forward(self, x):
#         # Max pooling over a (2, 2) window
#         x = nn.max_pool2d(nn.ReLU(self.conv1(x)), (2, 2))
#         # If the size is a square you can only specify a single number
#         x = nn.max_pool2d(nn.ReLU(self.conv2(x)), 2)
#         x = x.view(-1, self.num_flat_features(x))
#         x = F.relu(self.fc1(x))
#         x = F.relu(self.fc2(x))
#         x = self.fc3(x)
#         return x
#
#     def num_flat_features(self, x):
#         size = x.size()[1:]  # all dimensions except the batch dimension
#         num_features = 1
#         for s in size:
#             num_features *= s
#         return num_features
#
#
# # net = Net()
# # a = list(net.parameters())
# # # print(len(a))
# # # print(a)
# # for param in a:
# #     print(param.requires_grad)
# # lr=1
# # optimizer = torch.optim.Adam( filter(lambda p: p.requires_grad, net.parameters()) ,lr)
# from torchvision import transforms
# from torch.utils.data import Dataset, DataLoader
#
# # Parameters and DataLoaders
# input_size = 5
# output_size = 2
#
# batch_size = 30
# data_size = 100
# class RandomDataset(Dataset):
#
#     def __init__(self, size, length):
#         self.len = length
#         self.data = torch.randn(100,5)
#
#     def __getitem__(self, index):
#         return self.data[index]
#
#     def __len__(self):
#         return self.len
#
# rand_loader = DataLoader(dataset=RandomDataset(input_size, data_size),
#                          batch_size=batch_size, shuffle=True)
# # itera = iter(rand_loader)
# # a = itera.next()
# # print(a.size())
#
# # a = torch.empty(3,5).fill_(1)
# # print(a)
#
# # a = torch.randn(3,5,2)
# # _, idx = torch.max(a, dim=1)
# # print(idx.size())
# # a = torch.tensor([3,4,5], requires_grad=True, dtype=float)
# # b = 3
# # c = a.sum()*b
# # # c.backward(retain_graph=True)
# # s = c*4+1*a.sum()
#
# # p = 9*s
# # p.backward()
#
# # print(a.grad)
#
# # y = torch.arange(0,10).reshape(1,10).float()
# # print(y.requires_grad)
# # out = torch.randn(1, 10, requires_grad=True)
# # criterion = nn.MSELoss(reduction='sum')
# # loss = criterion(out, y)
# # #loss是个scalar，我们可以直接用item获取到他的python类型的数值
# # print(loss)
# # loss.backward()
# # a = torch.rand(64, 500000)
# # b = torch.ones_like(a)
# # print(b.size())
# # c = nn.BCELoss()
# # loss = c(a, b)
# # print(loss.item())
#
# # a = nn.RNN(30, 3, batch_first=True)
#
# # train = torch.randn(32, 3, 30)
# # output, s = a(train)
# # print(output[0])
# # print(s[0][0])
#
#
#
# import torch
# # x = torch.FloatTensor([[1., 2.]])
# # w1 = torch.FloatTensor([[2.], [1.]])
# # w2 = torch.FloatTensor([3.])
# # w1.requires_grad = True
# # w2.requires_grad = True
#
# # d = torch.matmul(x, w1)
#
# # d_ = d.detach() # 换成 .detach(), 就可以看到 程序报错了...
#
# # f = torch.matmul(d, w2)
# # d_[:] = 1
# # f.backward()
#
# # a = torch.randn(3,5,4)
# # print(a.flatten().size())
#
# # b = np.array([['ad', 'asdasd'],['asdasd',['asda']]])
# # # print(b.shape)
# # import torchvision
#
# # model = torchvision.models.vgg19(pretrained=True)
#
# # print(model)
# # for param in model.parameters():
# #     param.requires_grad = False
#
# # optimizer = torch.optim.SGD(model.parameters(), lr=1)
# # for param in model.parameters():
# #     print(param.requires_grad)
#
#
# import torch
#
# import torch.nn as nn
#
# rnn = nn.LSTM(3, 2, bidirectional=True, num_layers=2, batch_first=True)
#
# inputs = torch.randn(3,2,3)
# outputs, (h, c) = rnn(inputs)
# #
# # print(outputs) # batch, seq, hidden_size*bidirectional. 3, 2, 2*2
# #
# #
# # print(h) # num_layers*bidirectional,batch, hidden_size       2*2,3,2
# # print(c) # same
# #
# # print(outputs.size())
# # print(h.size())
# # print(c.size())
# # import torch
# #
# #
# # [[-0.1220,  0.0668,  0.1265,  0.2125],
# #          [-0.1618,  0.1402,  0.1061,  0.1355]]
# #
# #  [[-0.0986,  0.0931,  0.1022,  0.2285],
# #          [-0.1502,  0.1081,  0.0578,  0.1405]],
# # [[-0.1212,  0.0839,  0.1367,  0.2228],
# #          [-0.1459,  0.1507,  0.1008,  0.1425]]
# #
# # 9(9（（)
#
# # a = torch.stack([torch.tensor([1]), torch.tensor([3])])
# # print(a)
# a = [torch.tensor([1]), torch.tensor([3])]
# print(type(a))

from PIL import Image


def load_image(image_pth, transform=None):
    image = Image.open(image_pth).convert('RGB')
    print(type(image))
    image = image.resize((224, 224), Image.LANCZOS)
    if transform is not None:
        image = transform(image).unsqueeze(0)
    return image


load_image('images/content2.png')
