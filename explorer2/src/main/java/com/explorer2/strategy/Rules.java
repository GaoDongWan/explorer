package com.explorer2.strategy;

import java.util.Queue;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.explorer2.setting.JobRBF;
import com.explorer2.setting.UrlRBF;

public abstract class Rules {
	private String jobRegex;
	private String urlRegex;
	public void setjobRegex(String jobRegex) {
		this.jobRegex = jobRegex;
	}
	public void seturlRegex(String urlRegex) {
		this.urlRegex = urlRegex;
	}

	public void analyzeDoc(Document doc,Queue waitUrl,UrlRBF URBF,JobRBF JRBF){
		Elements urls=doc.select(urlRegex);
		if(urls.first()!=null){
			for(Element url:urls){
				if(!URBF.contains(url.attr("abs:href"))){
					waitUrl.add(url.attr("abs:href"));
					URBF.add(url.attr("abs:href"));
				}
			}
		}
		Elements jobInfos=doc.select(jobRegex);
		if(jobInfos.first()!=null){
			Element jobInfo=jobInfos.first();
			if(!JRBF.contains(jobInfo.text())){
			JRBF.add(jobInfo.text());
			infoStore(jobInfo);
			}
		}
	}
	public String getJobRegex() {
		return jobRegex;
	}
	public String getUrlRegex() {
		return urlRegex;
	}
	public void infoStore(Element jobInfo){
		System.out.println("未重写详细抓取规则方法");
	}
}
