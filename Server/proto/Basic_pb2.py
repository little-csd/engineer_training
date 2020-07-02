# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: Basic.proto

from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor.FileDescriptor(
  name='Basic.proto',
  package='aiins',
  syntax='proto3',
  serialized_options=b'\n\027com.example.aiins.proto',
  create_key=_descriptor._internal_create_key,
  serialized_pb=b'\n\x0b\x42\x61sic.proto\x12\x05\x61iins\"\x1a\n\x0bUserDataReq\x12\x0b\n\x03uid\x18\x01 \x03(\x05\"I\n\x08UserData\x12\x10\n\x08nickname\x18\x01 \x01(\t\x12\x10\n\x08username\x18\x02 \x01(\t\x12\x0c\n\x04icon\x18\x03 \x01(\x0c\x12\x0b\n\x03uid\x18\x04 \x01(\x05\"0\n\x0bUserDataRsp\x12!\n\x08userData\x18\x01 \x03(\x0b\x32\x0f.aiins.UserData\"R\n\rBasicUserData\x12\x10\n\x08nickname\x18\x01 \x01(\t\x12\x10\n\x08username\x18\x02 \x01(\t\x12\x10\n\x08password\x18\x03 \x01(\t\x12\x0b\n\x03uid\x18\x04 \x01(\x05\x42\x19\n\x17\x63om.example.aiins.protob\x06proto3'
)




_USERDATAREQ = _descriptor.Descriptor(
  name='UserDataReq',
  full_name='aiins.UserDataReq',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='uid', full_name='aiins.UserDataReq.uid', index=0,
      number=1, type=5, cpp_type=1, label=3,
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
  serialized_start=22,
  serialized_end=48,
)


_USERDATA = _descriptor.Descriptor(
  name='UserData',
  full_name='aiins.UserData',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='nickname', full_name='aiins.UserData.nickname', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='username', full_name='aiins.UserData.username', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='icon', full_name='aiins.UserData.icon', index=2,
      number=3, type=12, cpp_type=9, label=1,
      has_default_value=False, default_value=b"",
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='uid', full_name='aiins.UserData.uid', index=3,
      number=4, type=5, cpp_type=1, label=1,
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
  serialized_start=50,
  serialized_end=123,
)


_USERDATARSP = _descriptor.Descriptor(
  name='UserDataRsp',
  full_name='aiins.UserDataRsp',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='userData', full_name='aiins.UserDataRsp.userData', index=0,
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
  serialized_start=125,
  serialized_end=173,
)


_BASICUSERDATA = _descriptor.Descriptor(
  name='BasicUserData',
  full_name='aiins.BasicUserData',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='nickname', full_name='aiins.BasicUserData.nickname', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='username', full_name='aiins.BasicUserData.username', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='password', full_name='aiins.BasicUserData.password', index=2,
      number=3, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='uid', full_name='aiins.BasicUserData.uid', index=3,
      number=4, type=5, cpp_type=1, label=1,
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
  serialized_start=175,
  serialized_end=257,
)

_USERDATARSP.fields_by_name['userData'].message_type = _USERDATA
DESCRIPTOR.message_types_by_name['UserDataReq'] = _USERDATAREQ
DESCRIPTOR.message_types_by_name['UserData'] = _USERDATA
DESCRIPTOR.message_types_by_name['UserDataRsp'] = _USERDATARSP
DESCRIPTOR.message_types_by_name['BasicUserData'] = _BASICUSERDATA
_sym_db.RegisterFileDescriptor(DESCRIPTOR)

UserDataReq = _reflection.GeneratedProtocolMessageType('UserDataReq', (_message.Message,), {
  'DESCRIPTOR' : _USERDATAREQ,
  '__module__' : 'Basic_pb2'
  # @@protoc_insertion_point(class_scope:aiins.UserDataReq)
  })
_sym_db.RegisterMessage(UserDataReq)

UserData = _reflection.GeneratedProtocolMessageType('UserData', (_message.Message,), {
  'DESCRIPTOR' : _USERDATA,
  '__module__' : 'Basic_pb2'
  # @@protoc_insertion_point(class_scope:aiins.UserData)
  })
_sym_db.RegisterMessage(UserData)

UserDataRsp = _reflection.GeneratedProtocolMessageType('UserDataRsp', (_message.Message,), {
  'DESCRIPTOR' : _USERDATARSP,
  '__module__' : 'Basic_pb2'
  # @@protoc_insertion_point(class_scope:aiins.UserDataRsp)
  })
_sym_db.RegisterMessage(UserDataRsp)

BasicUserData = _reflection.GeneratedProtocolMessageType('BasicUserData', (_message.Message,), {
  'DESCRIPTOR' : _BASICUSERDATA,
  '__module__' : 'Basic_pb2'
  # @@protoc_insertion_point(class_scope:aiins.BasicUserData)
  })
_sym_db.RegisterMessage(BasicUserData)


DESCRIPTOR._options = None
# @@protoc_insertion_point(module_scope)
