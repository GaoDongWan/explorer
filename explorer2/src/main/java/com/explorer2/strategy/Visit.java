package com.explorer2.strategy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.explorer2.entity.Ip;
import com.explorer2.setting.IPPOOL;

public class Visit {
	public static Document getDoc(String url, long flag, boolean useProxy) {
		Document doc = null;
		Response response = null;
		Ip ip = null;
		Connection conn = Jsoup.connect(url);
		conn.timeout(30000);
		conn.header("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
		if (useProxy) {
			ip = IPPOOL.getIp(flag);
			System.out.println("using proxy->" + ip.getHost() + ":" + ip.getPort());
			InetSocketAddress addr = new InetSocketAddress(ip.getHost(), ip.getPort());
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
			conn.proxy(proxy);
		}
		try {
			System.out.println("Connecting : " + url);
			response = conn.execute();
		} catch (IOException e1) {
			System.out.println("get Document visit error URL");
			ip.setCount(ip.getCount() + 1);
			return null;
		}
		//System.out.println(response.statusCode());
		try {
			doc = response.parse();
		} catch (IOException e2) {
			System.out.println("response parse error");
		}

		try {
			Thread.sleep(1000);
			System.out.println("——————休眠1秒——————");
		} catch (InterruptedException e) {
			System.out.println("sleep error");
		}
		return doc;
	}
}
