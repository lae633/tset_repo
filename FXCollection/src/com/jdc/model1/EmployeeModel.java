package com.jdc.model1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jdc.model.Employee;

public class EmployeeModel {
	
	private static EmployeeModel instance;
	
	private List<Employee> model;
	
	private static final String FILE="emp.ser";
	

		

		
		@SuppressWarnings("unchecked")
		private EmployeeModel() {
			model = new ArrayList<>();
			try(ObjectInputStream in =new ObjectInputStream(new FileInputStream(FILE)))
			{
				model=(List<Employee>) in.readObject();
			} catch (Exception e) {
			e.printStackTrace();
			}
		}
		
		public static EmployeeModel getInstance() {
			if(null == instance)
				instance = new EmployeeModel();
			return instance;
		}
		
		public void save(Employee emp) {
			boolean isNew = true;
			
			for(Employee e : model) {
				if(e.getId() == emp.getId()) {
					isNew = false;
					break;
				}
			}
			
			if(isNew) {
				add(emp);
			} else {
				update(emp);
			}
			
		}
		
		private void add(Employee emp) {
			model.add(emp);
		}
		
		public void update(Employee updateValue) {
			for(int i = 0; i < model.size(); i++) {
				if(model.get(i).getId() == updateValue.getId()) {
					model.set(i, updateValue);
					break;
				}
			}
		}
		
		public List<Employee> getAll() {
			List<Employee> result = new ArrayList<Employee>(model);
			return result;
		}
		
		public List<Employee> find(String name, String email, String phone, int salary) {
			List<Employee> result = new ArrayList<>();
			
			if(name.isEmpty() && email.isEmpty() && phone.isEmpty() && salary <= 0) {
				result.addAll(model);
			} else {
				Iterator<Employee> itr = model.iterator();
				
				while(itr.hasNext()) {
					Employee emp = itr.next();
					
					if(!name.isEmpty()) {
						if(emp.getName().equals(name))
							result.add(emp);
					}
					
					else if(!email.isEmpty()) {
						if(emp.getMail().equals(email))
							result.add(emp);
					}
					
					else if(!phone.isEmpty()) {
						if(emp.getPhone().equals(phone))
							result.add(emp);
					}
					
					else if(salary > 0) {
						if(emp.getSalary() >= salary)
							result.add(emp);
					}
					
				}
			}
			
			return result;
		}	public void delete(List<Employee> params) {
			
			Iterator<Employee> itr = model.iterator();
			
			while(itr.hasNext()) {
				Employee emp = itr.next();
				
				for(Employee e : params) {
					if(e.getId() == emp.getId()) {
						itr.remove();
						break;
					}
				}
				
			}
			
		}
		
		public void saveToFile() {
			try (ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(FILE)))
			{
				out.writeObject(model);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		public void upload(File file) {
			
			try(BufferedReader br=new BufferedReader(new FileReader(file))) 
			{
				String line=null;
				while(null != (line = br.readLine())) {
		//			line=br.readLine();
					
					String[] arr=line.split("\t");
					if (arr.length==4) {
						Employee emp=new Employee();
						emp.setName(arr[0]);
						emp.setPhone(arr[1]);
						emp.setMail(arr[2]);
						emp.setSalary(Integer.parseInt(arr[3]));
						
						this.save(emp);
						
						
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

	

		
}