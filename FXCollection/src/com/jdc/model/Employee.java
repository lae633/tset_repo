package com.jdc.model;

import java.io.Serializable;
import java.util.List;

import com.jdc.model1.EmployeeModel;

public class Employee implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String phone;
	private String mail;
	private int salary;
	private static int count;
	
	public Employee() {
		
		List<Employee> list=EmployeeModel.getInstance().getAll();
		if (!list.isEmpty()) {
			count=list.size();
		}
		
		count++;
		id=count;
	}
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	

}
