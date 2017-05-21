package com.explorer2.crawler;

import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.nodes.Document;

import com.explorer2.entity.Seed;
import com.explorer2.setting.JobRBF;
import com.explorer2.setting.UrlRBF;
import com.explorer2.strategy.Rules;
import com.explorer2.strategy.Visit;

public class JobSprider extends Thread {
	private UrlRBF URBF;
	private JobRBF JRBF;
	private Seed seed;
	private int pageCount;
	private Rules rules;
	private Queue<String> waitUrl = new LinkedList<String>();
	public String getText(){
		return rules.getUrlRegex();
	}
	public void setURBF(String key) {
		URBF = new UrlRBF(key);
	}

	public void setJRBF(JobRBF JRBF) {
		this.JRBF = JRBF;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public Seed getSeed() {
		return seed;
	}

	public void setSeed(Seed seed) {
		this.seed = seed;
		waitUrl.add(seed.getUrl());
	}

	public void setRules(Rules rules) {
		this.rules = rules;
	}

	public void run() {
		Document doc = null;
		while ((pageCount--) > 0) {
			String url = waitUrl.poll();
			do {
				doc = Visit.getDoc(url, seed.getFlag(), false);
			} while (doc == null);
			rules.analyzeDoc(doc, waitUrl, URBF, JRBF);
		}
	}
}
