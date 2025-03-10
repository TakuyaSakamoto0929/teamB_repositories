package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="employee")
public class Employee {
	@Id
	private Long id;//Longはintより長い数が入る
	private String name;
	private int age;
	private String password;
	private String start;
	private String updated;
	private String end;
	
	public Employee() {}
	public  Employee(Long id,String name,int age,
			String password,String start,String updated,String end) {
		this.id=id;
		this.name=name;
		this.age=age;
		this.password=password;
		this.start=start;
		this.updated=updated;
		this.end=end;
	}
	public Long getId() {return id;}
	public void setId(Long id) {this.id=id;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name=name;}
	
	public int getAge() {return age;}
	public void setAge(int age) {this.age=age;}
	
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password=password;}

	public String getStart() {return start;}
	public void setStart(String start) {this.start=start;}
	
	public String getUpdated() {return updated;}
	public void setUpdated(String updated) {this.updated=updated;}
	
	public String getEnd() {return end;}
	public void setEnd(String end) {this.end=end;}
}
