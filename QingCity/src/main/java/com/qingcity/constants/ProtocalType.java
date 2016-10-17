package com.qingcity.constants;

/**
 * 协议类型
 * @author leehot
 *
 */
public interface ProtocalType {
	
	byte //二进制
	     BINARY = 0, 
		 //protobuf  lua	
	     PB_LUA = 1, 
	     //protobuf C#
	     PBC = 2, 
	     
	     SPROTO = 3;
}
