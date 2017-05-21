package com.explorer2.rule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.jsoup.nodes.Element;

import com.explorer2.entity.JobInfo;
import com.explorer2.strategy.Rules;

public class ChinahrRule extends Rules{
	public void infoStore(JobInfo jobInfo,String path){
		System.out.println("write to chinahr");
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream(new File("E:/chinahr1.txt"), true));
		} catch (FileNotFoundException e) {
			System.out.println("file write error");
		}
		pw.println(jobInfo.toStringAll());
		pw.println("===");
		pw.flush();
		pw.close();
	}
	public void infoExtract(JobInfo jobInfo,Element jobDoc){
		jobInfo.setPosition(jobDoc.getElementsByClass("job_name").first().text());
		jobInfo.setSalary(jobDoc.getElementsByClass("job_price").first().text());
		jobInfo.setRequest("");
		jobInfo.setSite(jobDoc.getElementsByClass("job_loc").first().text());
		jobInfo.setTime(jobDoc.getElementsByClass("updatetime").first().text());
		jobInfo.setCompany("");
		jobInfo.setDetails("");
	}
}
