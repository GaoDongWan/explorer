package com.explorer2.rule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.jsoup.nodes.Element;

import com.explorer2.strategy.Rules;

public class LiepinRule extends Rules{
	public void infoStore(Element jobInfo){
		System.out.println("liepin");
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream(new File("E:/liepin1.txt"), true));
		} catch (FileNotFoundException e) {
			System.out.println("file write error");
		}
		pw.println(jobInfo.text());
		pw.println("===");
		pw.flush();
		pw.close();
	}
}
