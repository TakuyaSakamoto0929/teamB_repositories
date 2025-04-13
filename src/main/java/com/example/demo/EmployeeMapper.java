//package com.example.demo;


//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Options;
//import org.apache.ibatis.annotations.Select;

//@Mapper
//public interface EmployeeMapper {
//	@Insert("INSERT INTO employee (name, age, password, start_date, end_date) VALUES (#{name}, #{age}, #{password}, #{start}, #{end})")
//	@Options(useGeneratedKeys = true, keyProperty = "id")
//	@Select("SELECT * FROM employee WHERE id = #{id}")
//	Employee findById(Long id);

 //   void insert(Employee employee);

//}
