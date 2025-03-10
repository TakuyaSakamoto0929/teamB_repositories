package com.example.demo;

import lombok.Data;

@Data
public class Employee {
	
	    private Long id;          // 社員ID
	    private String name;      // 社員名
	    private Integer age;      // 年齢
	    private String password;  // パスワード

	    // コンストラクタ
	    public Employee() {}

	    public Employee(String name, Integer age, String password) {
	        this.name = name;
	        this.age = age;
	        this.password = password;
	    }

	    // Getter & Setter
	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }

	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }

	    public Integer getAge() { return age; }
	    public void setAge(Integer age) { this.age = age; }

	    public String getPassword() { return password; }
	    public void setPassword(String password) { this.password = password; }
	}



