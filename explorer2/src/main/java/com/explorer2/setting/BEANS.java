package com.explorer2.setting;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class BEANS {
	public static ApplicationContext SEEDS = new FileSystemXmlApplicationContext(
			"E:/Java_Code/explorer2/src/main/resources/com/explorer2/SEEDS.xml");
	public static ApplicationContext CRAWLER = new FileSystemXmlApplicationContext(
			"E:/Java_Code/explorer2/src/main/resources/com/explorer2/CRAWLER.xml");
	public static ApplicationContext ALL = new FileSystemXmlApplicationContext(
			"E:/Java_Code/explorer2/src/main/resources/com/explorer2/CRAWLER.xml",
			"E:/Java_Code/explorer2/src/main/resources/com/explorer2/SEEDS.xml");
}
