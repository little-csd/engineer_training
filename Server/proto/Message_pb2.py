# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: Message.proto

from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor.FileDescriptor(
  name='Message.proto',
  package='aiins',
  syntax='proto3',
  serialized_options=b'\n\027com.example.aiins.proto',
  create_key=_descriptor._internal_create_key,
  serialized_pb=b'\n\rMessage.proto\x12\x05\x61iins\"\'\n\nMessageReq\x12\x0b\n\x03uid\x18\x01 \x01(\x05\x12\x0c\n\x04time\x18\x02 \x01(\x05\"*\n\nMessageRsp\x12\x1c\n\x04msgs\x18\x01 \x03(\x0b\x32\x0e.aiins.Message\"N\n\x07Message\x12\x0b\n\x03src\x18\x01 \x01(\x05\x12\x0b\n\x03\x64st\x18\x02 \x01(\x05\x12\x0c\n\x04time\x18\x03 \x01(\x05\x12\x0c\n\x04text\x18\x04 \x01(\t\x12\r\n\x05image\x18\x05 \x01(\x0c\"\\\n\x07PostReq\x12\x0c\n\x04type\x18\x01 \x01(\x05\x12\x0b\n\x03uid\x18\x02 \x01(\x05\x12\x0c\n\x04time\x18\x03 \x01(\x05\x12\x0c\n\x04text\x18\x04 \x01(\t\x12\x0c\n\x04img1\x18\x05 \x01(\x0c\x12\x0c\n\x04img2\x18\x06 \x01(\x0c\"%\n\x07PostRsp\x12\x1a\n\x05posts\x18\x01 \x03(\x0b\x32\x0b.aiins.Post\"~\n\x04Post\x12\x0b\n\x03uid\x18\x01 \x01(\x05\x12\x0c\n\x04time\x18\x04 \x01(\x05\x12\x0c\n\x04text\x18\x02 \x01(\t\x12\r\n\x05image\x18\x03 \x01(\x0c\x12\x0c\n\x04\x64\x65sc\x18\x05 \x01(\t\x12\x10\n\x08username\x18\x06 \x01(\t\x12\x10\n\x08nickname\x18\x07 \x01(\t\x12\x0c\n\x04icon\x18\x08 \x01(\x0c\x42\x19\n\x17\x63om.example.aiins.protob\x06proto3'
)




_MESSAGEREQ = _descriptor.Descriptor(
  name='MessageReq',
  full_name='aiins.MessageReq',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='uid', full_name='aiins.MessageReq.uid', index=0,
      number=1, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='time', full_name='aiins.MessageReq.time', index=1,
      number=2, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=24,
  serialized_end=63,
)


_MESSAGERSP = _descriptor.Descriptor(
  name='MessageRsp',
  full_name='aiins.MessageRsp',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='msgs', full_name='aiins.MessageRsp.msgs', index=0,
      number=1, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=65,
  serialized_end=107,
)


_MESSAGE = _descriptor.Descriptor(
  name='Message',
  full_name='aiins.Message',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='src', full_name='aiins.Message.src', index=0,
      number=1, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='dst', full_name='aiins.Message.dst', index=1,
      number=2, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='time', full_name='aiins.Message.time', index=2,
      number=3, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='text', full_name='aiins.Message.text', index=3,
      number=4, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='image', full_name='aiins.Message.image', index=4,
      number=5, type=12, cpp_type=9, label=1,
      has_default_value=False, default_value=b"",
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=109,
  serialized_end=187,
)


_POSTREQ = _descriptor.Descriptor(
  name='PostReq',
  full_name='aiins.PostReq',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='type', full_name='aiins.PostReq.type', index=0,
      number=1, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='uid', full_name='aiins.PostReq.uid', index=1,
      number=2, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='time', full_name='aiins.PostReq.time', index=2,
      number=3, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='text', full_name='aiins.PostReq.text', index=3,
      number=4, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='img1', full_name='aiins.PostReq.img1', index=4,
      number=5, type=12, cpp_type=9, label=1,
      has_default_value=False, default_value=b"",
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='img2', full_name='aiins.PostReq.img2', index=5,
      number=6, type=12, cpp_type=9, label=1,
      has_default_value=False, default_value=b"",
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=189,
  serialized_end=281,
)


_POSTRSP = _descriptor.Descriptor(
  name='PostRsp',
  full_name='aiins.PostRsp',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='posts', full_name='aiins.PostRsp.posts', index=0,
      number=1, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=283,
  serialized_end=320,
)


_POST = _descriptor.Descriptor(
  name='Post',
  full_name='aiins.Post',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='uid', full_name='aiins.Post.uid', index=0,
      number=1, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='time', full_name='aiins.Post.time', index=1,
      number=4, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='text', full_name='aiins.Post.text', index=2,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='image', full_name='aiins.Post.image', index=3,
      number=3, type=12, cpp_type=9, label=1,
      has_default_value=False, default_value=b"",
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='desc', full_name='aiins.Post.desc', index=4,
      number=5, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='username', full_name='aiins.Post.username', index=5,
      number=6, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='nickname', full_name='aiins.Post.nickname', index=6,
      number=7, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='icon', full_name='aiins.Post.icon', index=7,
      number=8, type=12, cpp_type=9, label=1,
      has_default_value=False, default_value=b"",
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=322,
  serialized_end=448,
)

_MESSAGERSP.fields_by_name['msgs'].message_type = _MESSAGE
_POSTRSP.fields_by_name['posts'].message_type = _POST
DESCRIPTOR.message_types_by_name['MessageReq'] = _MESSAGEREQ
DESCRIPTOR.message_types_by_name['MessageRsp'] = _MESSAGERSP
DESCRIPTOR.message_types_by_name['Message'] = _MESSAGE
DESCRIPTOR.message_types_by_name['PostReq'] = _POSTREQ
DESCRIPTOR.message_types_by_name['PostRsp'] = _POSTRSP
DESCRIPTOR.message_types_by_name['Post'] = _POST
_sym_db.RegisterFileDescriptor(DESCRIPTOR)

MessageReq = _reflection.GeneratedProtocolMessageType('MessageReq', (_message.Message,), {
  'DESCRIPTOR' : _MESSAGEREQ,
  '__module__' : 'Message_pb2'
  # @@protoc_insertion_point(class_scope:aiins.MessageReq)
  })
_sym_db.RegisterMessage(MessageReq)

MessageRsp = _reflection.GeneratedProtocolMessageType('MessageRsp', (_message.Message,), {
  'DESCRIPTOR' : _MESSAGERSP,
  '__module__' : 'Message_pb2'
  # @@protoc_insertion_point(class_scope:aiins.MessageRsp)
  })
_sym_db.RegisterMessage(MessageRsp)

Message = _reflection.GeneratedProtocolMessageType('Message', (_message.Message,), {
  'DESCRIPTOR' : _MESSAGE,
  '__module__' : 'Message_pb2'
  # @@protoc_insertion_point(class_scope:aiins.Message)
  })
_sym_db.RegisterMessage(Message)

PostReq = _reflection.GeneratedProtocolMessageType('PostReq', (_message.Message,), {
  'DESCRIPTOR' : _POSTREQ,
  '__module__' : 'Message_pb2'
  # @@protoc_insertion_point(class_scope:aiins.PostReq)
  })
_sym_db.RegisterMessage(PostReq)

PostRsp = _reflection.GeneratedProtocolMessageType('PostRsp', (_message.Message,), {
  'DESCRIPTOR' : _POSTRSP,
  '__module__' : 'Message_pb2'
  # @@protoc_insertion_point(class_scope:aiins.PostRsp)
  })
_sym_db.RegisterMessage(PostRsp)

Post = _reflection.GeneratedProtocolMessageType('Post', (_message.Message,), {
  'DESCRIPTOR' : _POST,
  '__module__' : 'Message_pb2'
  # @@protoc_insertion_point(class_scope:aiins.Post)
  })
_sym_db.RegisterMessage(Post)


DESCRIPTOR._options = None
# @@protoc_insertion_point(module_scope)
