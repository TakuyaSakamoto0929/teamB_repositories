package com.example.demo;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface EmployeeMapper {
	

	    // 全社員を取得
	    @Select("SELECT * FROM employees")
	    List<Employee> findAll();

	    // IDで検索
	    @Select("SELECT * FROM employees WHERE id = #{id}")
	    Employee findById(Long id);

	    // 新規登録
	    @Insert("INSERT INTO employees (name, age, password) VALUES (#{name}, #{age}, #{password})")
	    @Options(useGeneratedKeys = true, keyProperty = "id")
	    void insert(Employee employee);

	    // 更新
	    @Update("UPDATE employees SET name = #{name}, age = #{age}, password = #{password} WHERE id = #{id}")
	    void update(Employee employee);

	    // 削除
	    @Delete("DELETE FROM employees WHERE id = #{id}")
	    void delete(Long id);


}
