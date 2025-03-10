package com.example.demo;

import java.util.List;  // Listをインポート

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EmployeeService {
	
	@Autowired
    private EmployeeMapper employeeMapper;
	
    // 全社員取得
    public List<Employee> getAllEmployees() {
        return employeeMapper.findAll();
    }

    // ID検索
    public Employee getEmployeeById(Long id) {
        return employeeMapper.findById(id);
    }

    // 新規登録
    public void createEmployee(Employee employee) {
        employeeMapper.insert(employee);
    }

    // 更新
    public void updateEmployee(Employee employee) {
        employeeMapper.update(employee);
    }

    // 削除
    public void deleteEmployee(Long id) {
        employeeMapper.delete(id);
    }

}
