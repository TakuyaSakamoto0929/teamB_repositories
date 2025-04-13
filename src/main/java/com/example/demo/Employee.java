package com.example.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.hibernate.annotations.processing.Pattern;

@Entity
@Table(name="employee")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // ID を自動生成
	private Long id;//Longはintより長い数が入る
	@Column(name = "name", nullable = false)
	@NotBlank(message = "社員名は必須です")
	private String name;
	@Column(name = "age", nullable = false)
	@Min(value = 0, message = "年齢は0以上で入力してください")
	private int age;
	@Column(name = "password", nullable = false)
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "パスワードは英大文字・数字を含む8文字以上の半角英数字")
	private String password;
	@Column(name = "start", nullable = false)
	private LocalDateTime start = LocalDateTime.now();
	@Column(name = "updated", nullable = false)
	private LocalDateTime updated = LocalDateTime.now();
	@Column(name = "end_date")
	private LocalDate endDate;

	
	
	//パスワード確認
	@Transient  // データベースに保存しない
	private String passwordConfirm;
	
	public Employee() {}
	public  Employee(Long id,String name,int age,
			String password,LocalDateTime start, LocalDateTime updated, LocalDate endDate) {
		this.id=id;
		this.name=name;
		this.age=age;
		this.password=password;
		this.start = (start != null) ? start : LocalDateTime.now();  // ★ null の場合デフォルト値を設定
		this.updated = (updated != null) ? updated : LocalDateTime.now();
		this.endDate=endDate;
	}
	public Long getId() {return id;}
	public void setId(Long id) {this.id=id;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name=name;}
	
	public int getAge() {return age;}
	public void setAge(int age) {this.age=age;}
	
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password=password;}

	public LocalDateTime getStart() {return start;}
	public void setStart(LocalDateTime start) {this.start=start;}
	
	
	public LocalDateTime getUpdated() {return updated;}
	public void setUpdated(LocalDateTime updated) {this.updated=updated;}
	
	public LocalDate getEndDate() {return endDate;}
	public void setEndDate(LocalDate endDate) {this.endDate = endDate;}
	
	//パスワード確認
	public String getPasswordConfirm() {return passwordConfirm;}
	public void setPasswordConfirm(String passwordConfirm) {this.passwordConfirm = passwordConfirm;}
}
