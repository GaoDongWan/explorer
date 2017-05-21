package com.explorer2;

import com.explorer2.crawler.JobSprider;
import com.explorer2.setting.BEANS;


public class App 
{
    public static void main( String[] args )
    {
    	//IPPOOL.addFromWeb();
//    	UrlRBF te=new UrlRBF("initial");
//    	System.out.println(te.contains("aaa"));
//    	te.add("aaa");
//    	System.out.println(te.contains("aaa"));
    	JobSprider text=(JobSprider)BEANS.CRAWLER.getBean("liepin");
    	JobSprider text1=(JobSprider)BEANS.CRAWLER.getBean("chinahr");
    	text.setPageCount(100);
    	text1.setPageCount(100);
    	text.start();
    	text1.start();
    	System.out.println("final");
    	//System.exit(0);
    }
}
