package com.zhangjb.protobf.my_app_simple;

import java.util.List;
import org.junit.Test;

import com.google.protobuf.InvalidProtocolBufferException;
import com.taiji.zhangjb.zt.AddressBookProtos;
import com.taiji.zhangjb.zt.AddressBookProtos.Person;
import com.taiji.zhangjb.zt.AddressBookProtos.Person.PhoneNumber;

public class ProtobfTest{

	
	@Test
	public void testProtocolBuffer(){
		AddressBookProtos.Person.Builder builder = AddressBookProtos.Person.newBuilder();
		builder.setEmail("904545149@qq.com");
		builder.setId(1);
		builder.setName("zhangjb");
		builder.addPhone(AddressBookProtos.Person.PhoneNumber.newBuilder().setNumber("18612596995").setType(AddressBookProtos.Person.PhoneType.MOBILE));
		builder.addPhone(AddressBookProtos.Person.PhoneNumber.newBuilder().setNumber("28889626").setType(AddressBookProtos.Person.PhoneType.HOME));
		
		Person person = builder.build();
		byte[] buf = person.toByteArray();
		
		try{
			Person ps = AddressBookProtos.Person.parseFrom(buf);
			System.out.println(ps.getName() +" , "+ ps.getEmail());
			List<PhoneNumber> lstPhones = ps.getPhoneList();
			for(PhoneNumber phoneNumber : lstPhones){
				System.out.println(phoneNumber);
			}
			
		}catch(InvalidProtocolBufferException  e){
			e.printStackTrace();
		}
		System.out.println(buf);
	}
}
