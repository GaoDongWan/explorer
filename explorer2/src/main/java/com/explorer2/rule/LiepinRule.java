package com.explorer2.rule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.jsoup.nodes.Element;

import com.explorer2.entity.JobInfo;
import com.explorer2.strategy.Rules;
import com.explorer2.strategy.TimeFormat;

public class LiepinRule extends Rules{
	public void infoStore(JobInfo jobInfo,String path){
		System.out.println("write to liepin");
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream(new File("E:/liepin1.txt"), true));
		} catch (FileNotFoundException e) {
			System.out.println("file write error");
		}
		pw.println(jobInfo.toStringAll());
		pw.println("===");
		pw.flush();
		pw.close();
	}
	public void infoExtract(JobInfo jobInfo,Element jobDoc){
		jobInfo.setPosition(jobDoc.getElementsByTag("h1").first().text());
		jobInfo.setSalary(jobDoc.getElementsByClass("job-item-title").first().ownText());
		jobInfo.setRequest(jobDoc.getElementsByClass("job-qualifications").first().text());
		jobInfo.setSite(jobDoc.getElementsByClass("basic-infor").first().child(0).text());
		jobInfo.setTime(TimeFormat.toTime(jobDoc.getElementsByClass("basic-infor").first().child(1).text()));
		jobInfo.setCompany(jobDoc.getElementsByTag("h3").first().text());
		jobInfo.setDetails("");
	}
}
