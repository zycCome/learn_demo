// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: message.proto

package com.zyc.protocol;

public final class Message {
  private Message() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_protocol_User_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_protocol_User_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_protocol_User_HobbysEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_protocol_User_HobbysEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rmessage.proto\022\010protocol\032\nfile.proto\"\233\002" +
      "\n\004User\022\016\n\006userId\030\001 \001(\005\022\020\n\010username\030\002 \001(\t" +
      "\022\017\n\005error\030\003 \001(\tH\000\022\016\n\004code\030\004 \001(\005H\000\022\014\n\004nam" +
      "e\030\010 \001(\t\022$\n\010userType\030\t \001(\0162\022.protocol.Use" +
      "rType\022\r\n\005roles\030\n \003(\005\022\034\n\004file\030\013 \001(\0132\016.pro" +
      "tocol.File\022*\n\006hobbys\030\014 \003(\0132\032.protocol.Us" +
      "er.HobbysEntry\032-\n\013HobbysEntry\022\013\n\003key\030\001 \001" +
      "(\t\022\r\n\005value\030\002 \001(\t:\0028\001B\005\n\003msgJ\004\010\006\020\010R\007user" +
      "Id2*4\n\010UserType\022\n\n\006UNKNOW\020\000\022\t\n\005ADMIN\020\001\022\021" +
      "\n\rBUSINESS_USER\020\0022\233\001\n\013UserService\022+\n\007get" +
      "User\022\016.protocol.User\032\016.protocol.User\"\000\022." +
      "\n\010getUsers\022\016.protocol.User\032\016.protocol.Us" +
      "er\"\0000\001\022/\n\tsaveUsers\022\016.protocol.User\032\016.pr" +
      "otocol.User\"\000(\0012:\n\013FileService\022+\n\007getFil" +
      "e\022\016.protocol.User\032\016.protocol.File\"\000B\036\n\020c" +
      "om.zyc.protocolP\001Z\010protocolb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.zyc.protocol.FileOuterClass.getDescriptor(),
        });
    internal_static_protocol_User_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_protocol_User_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_protocol_User_descriptor,
        new java.lang.String[] { "UserId", "Username", "Error", "Code", "Name", "UserType", "Roles", "File", "Hobbys", "Msg", });
    internal_static_protocol_User_HobbysEntry_descriptor =
      internal_static_protocol_User_descriptor.getNestedTypes().get(0);
    internal_static_protocol_User_HobbysEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_protocol_User_HobbysEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
    com.zyc.protocol.FileOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
