package com.explorer2;

import com.explorer2.crawler.JobSprider;
import com.explorer2.setting.BEANS;
import com.explorer2.setting.IPPOOL;


public class App 
{
    public static void main( String[] args )
    {
//    	IPPOOL.addFromWeb();
//    	IPPOOL.printAll();
    	JobSprider text=(JobSprider)BEANS.CRAWLER.getBean("liepin");
    	JobSprider text1=(JobSprider)BEANS.CRAWLER.getBean("chinahr");
    	text.setPageCount(100);
    	//text.setUseProxy(true);
    	text1.setPageCount(100);
    	//text1.setUseProxy(true);
    	text.start();
    	text1.start();
    	System.out.println("final");
    }
}
