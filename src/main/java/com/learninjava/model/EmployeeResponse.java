package com.learninjava.model;

import java.util.List;

public class EmployeeResponse {

	/*
	 * int status; String statusDesc;
	 */
	List<Employee> employees;
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	/*
	 * public int getStatus() { return status; } public void setStatus(int status) {
	 * this.status = status; } public String getStatusDesc() { return statusDesc; }
	 * public void setStatusDesc(String statusDesc) { this.statusDesc = statusDesc;
	 * }
	 */
}
