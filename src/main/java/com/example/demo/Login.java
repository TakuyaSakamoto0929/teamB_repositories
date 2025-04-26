package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="employee")
public class Login {
	@Id
    private String name; // ユーザー名（ログインID）

    private String password; // パスワード

    public Login() {}

    public Login(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {return name;}
	public void setName(String name) {this.name=name;}
	
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password=password;}

    

}
