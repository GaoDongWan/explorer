package com.explorer2.setting;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import com.explorer2.App;
import com.explorer2.entity.Ip;
import com.explorer2.entity.Seed;
import com.explorer2.entity.UrlList;

public class IPPOOL {
	private static List<Ip> ipPool = new ArrayList<Ip>();
	private static UrlList urlList;

	public static void addIp(Ip ip) {
		ipPool.add(ip);
	}

	public static Ip getIp(long flag) {
		Ip ip;
		do {
			int index = (int) (Math.random() * (ipPool.size() - 1));
			ip = ipPool.get(index);
			if (ip.getCount() >= 10) {
				ipPool.remove(index);
				if (ipPool.size() == 0) {
					IPPOOL.addFromWeb();// ????????
				}
				continue;
			}
			if ((flag & ip.getFlag()) != 0)
				break;
		} while (true);
		return ip;
	}

	public static int size() {
		return ipPool.size();
	}

	public static void printAll() {
		for (Ip it : ipPool) {
			System.out.println(it.getHost() + ":" + it.getPort() + " " + Long.toBinaryString(it.getFlag()));
		}
	}

	public static void textAdd() {
		Ip ip = new Ip();
		ip.setHost("111.155.116.202");
		ip.setPort(8123);
		IPPOOL.addIp(ip);
	}

	public static void addFromFile(String path) {
		;
	}

	public static void addFromWeb() {
		urlList = (UrlList) BEANS.SEEDS.getBean("seedlist");
		for (int i = 1; i < 3; i++) {
			String url = "http://www.xicidaili.com/nn/" + i;
			Document ipdoc = null;
			Elements ips = null;
			try {
				ipdoc = Jsoup.connect(url).header("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0").get();
				ips = ipdoc.select("tr[class]");
			} catch (IOException e) {
				System.out.println("ip池初始化错误");
				System.exit(0);
			}
			for (Element ip : ips) {
				Ip ipItem = new Ip();
				ipItem.setHost(ip.child(1).text());
				ipItem.setPort(Integer.parseInt(ip.child(2).text()));
				if (ipItem.getHost().equals("180.76.154.5"))
					continue;/////////////////////////////////////////
				System.out.printf("checking proxy->" + ipItem.getHost() + ":" + ipItem.getPort());
				Iterator goalUrls = urlList.getUrlList().iterator();
				while (goalUrls.hasNext()) {
					Seed seed = (Seed) goalUrls.next();
					if (check(ipItem, seed.getUrl())) {
						ipItem.setFlag(ipItem.getFlag() + seed.getFlag());
					}
				}
				System.out.println((ipItem.getFlag() > 0) ? (" good proxy " + Long.toBinaryString(ipItem.getFlag()))
						: (" bad proxy"));
			}
		}
		
	}

	private static boolean check(Ip ipItem, String url) {
		Connection conn = Jsoup.connect(url);
		InetSocketAddress addr = new InetSocketAddress(ipItem.getHost(), ipItem.getPort());
		Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
		conn.proxy(proxy);
		conn.header("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
		conn.timeout(3000);
		Response response = null;
		try {
			response = conn.execute();
		} catch (IOException e) {
			return false;
		}
		return (response.statusCode() == 200) ? true : false;
	}
}
