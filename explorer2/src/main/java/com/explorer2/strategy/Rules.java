package com.explorer2.strategy;

import java.util.Queue;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.explorer2.entity.JobInfo;
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

	public boolean analyzeDoc(Document doc,Queue waitUrl,UrlRBF URBF,JobRBF JRBF){
		if(doc==null)
			return true;
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
			Element jobDoc=jobInfos.first();
			JobInfo jobInfo=new JobInfo();
			infoExtract(jobInfo,jobDoc);
			if(!JRBF.contains(jobInfo.toString())){
				JRBF.add(jobInfo.toString());
				infoStore(jobInfo,"");
			}
		}
		return (urls.first()==null);
	}
	public String getJobRegex() {
		return jobRegex;
	}
	public String getUrlRegex() {
		return urlRegex;
	}
	public void infoExtract(JobInfo jobInfo,Element jobDoc){
		System.out.println("未重写详细抓取规则方法");
	}
	public void infoStore(JobInfo jobInfo,String path){
		System.out.println("未设置文件存放路径");
	}
}
