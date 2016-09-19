package org.mm.hf;

import org.mm.hf.dao.HouseMapper;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext cp=new ClassPathXmlApplicationContext("classpath:spring-context.xml");
		HouseMapper houseMapper=cp.getBean(HouseMapper.class);
		houseMapper.selectByAutoID(1);
	}
}
