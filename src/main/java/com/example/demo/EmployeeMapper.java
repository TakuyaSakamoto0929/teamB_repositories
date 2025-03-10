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
	    @Select("SELECT * FROM employee")
	    List<Employee> findAll();

	    // IDで検索
	    @Select("SELECT * FROM employee WHERE id = #{id}")
	    Employee findById(Long id);

	    // 新規登録
	    @Insert("INSERT INTO employee (name, age, password) VALUES (#{name}, #{age}, #{password})")
	    @Options(useGeneratedKeys = true, keyProperty = "id")
	    void insert(Employee employee);

	    // 更新
	    @Update("UPDATE employee SET name = #{name}, age = #{age}, password = #{password} WHERE id = #{id}")
	    void update(Employee employee);

	    // 削除
	    @Delete("DELETE FROM employee WHERE id = #{id}")
	    void delete(Long id);


}
