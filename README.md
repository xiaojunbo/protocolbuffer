# protocolbuffer
1.什么是protocolbuffer?
  protocolbuffer(以下简称PB)是google 的一种数据交换的格式，它独立于语言，独立于平台。google 提供了多种语言的实现：java、c#、c++、go 和 python，每一种实现都包含了相应语言的编译器以及库文件。由于它是一种二进制的格式，比使用xml进行数据交换快许多。可以把它用于分布式应用之间的数据通信或者异构环境下的数据交换。作为一种效率和兼容性都很优秀的二进制数据传输格式，可以用于诸如网络传输、配置文件、数据存储等诸多领域。

2.protobuf对应的数据类型

  http://img.blog.csdn.net/20141123230823074?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYW5vbnltYWxpYXM=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast

3.protobuf 实例（maven下）
根目录下创建proto/msg.protoc文件
option java_package = "com.zhangjb.zt"; //存放生成java类的路径
option java_outer_classname = "ZT_OperatingParameter"; //生成java类的名称
 message ZT_Operatingparameter{//生成相关builder类的名称
            message DataHead{    //报文头
optional bytes DeviceCode = 1;   optional uint64 CollectTime = 2;    optional uint32 RowLength = 3;  
  }	 message DataBody{ 	  //报文内容	 	  
optional uint32 LinkStatus = 1;   optional uint32 WorkModel = 2;   optional sint32 ElectricQuantity = 3; 
optional sint32 FuelGauge = 4;   repeated sint32 Temperature = 5;   repeated sint32 Humidity = 6;  
	  }	
required DataHead Datahead = 1; required DataBody Databody = 2;                              	    
}

    正如上述文件格式，在消息定义中，每个字段都有唯一的一个标识符。这些标识符是用来在消息的二进制格式中识别各个字段的，一旦开始使用就不能够再改 变。注：[1,15]之内的标识号在编码的时候会占用一个字节。[16,2047]之内的标识号则占用2个字节。所以应该为那些频繁出现的消息元素保留 [1,15]之内的标识号。切记：要为将来有可能添加的、频繁出现的标识号预留一些标识号。
    最小的标识号可以从1开始，最大到229 - 1, or 536,870,911。不可以使用其中的[19000－19999]的标识号， Protobuf协议实现中对这些进行了预留。如果非要在.proto文件中使用这些预留标识号，编译时就会报警。
    
下面介绍如何用将.proto的文件转换成.java的文件
1.http://code.google.com/p/protobuf/downloads/list 下载 protoc-2.6.1-win32.zip 并解压放于根目录下
  添加依赖：
      <dependency>
		<groupId>com.google.protobuf</groupId>
		<artifactId>protobuf-java</artifactId>
		<version>2.6.1</version>
	</dependency>
	Tip:编译器要和protobuf-java.jar版本一致
2.通过命令行将.proto的文件生成为.java的文件官网上是这样写的
protoc -I=$SRC_DIR --java_out=$DST_DIR $SRC_DIR/addressbook.proto

3.在目录com\zhangjb\zt 目录下将生成一个ZT_OperatingParameter.java源文件，并将其引入

4.生成java类之后的运用	
public static void main(String[] args) {
	ZT_Operatingparameter.Builder builderleida = ZT_Operatingparameter.newBuilder();//获取总的builer
	ZT_Operatingparameter.DataHead.Builder HEAD = ZT_Operatingparameter.DataHead.newBuilder();//获取报文头的builder
	ZT_Operatingparameter.DataBody.Builder Databody = ZT_Operatingparameter.DataBody.newBuilder();	//获取报文内容的builder
	builderleida.setDatahead(HEAD.setCollectTime(333));//利用builder填充报文头数据
	builderleida.setDatabody(Databody.addTemperature(1).addTemperature(2).addTemperature(3));
		ZT_Operatingparameter leida = builderleida.build();
		byte[] buf = leida.toByteArray();//将报文进行序列化
	try {
		ZT_Operatingparameter leida1 = ZT_Operatingparameter.parseFrom(buf);//进行反序列化
		System.out.println(leida1.getDatabody().getTemperature(0));//打印报文内容中所填充数据
	} catch (InvalidProtocolBufferException e) {e.printStackTrace();}}}




