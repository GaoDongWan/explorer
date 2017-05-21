package com.explorer2.entity;

public class JobInfo {
	private String position;
	private String salary;
	private String site;
	private String request;
	private String company;
	private String time;
	private String details;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String toString() {
		return position + "-" + salary + "-" + site + "-" + request + "-" + company + "-" + time;
	}

	public String toStringAll() {
		return position + "-" + salary + "-" + site + "-" + request + "-" + company + "-" + time + "-" + details;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
