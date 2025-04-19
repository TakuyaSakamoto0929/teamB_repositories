package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	 @Autowired
	    private EmployeeRepository employeeRepository; // Repositoryを使う

	    public void deleteEmployee(Long id) {
	        employeeRepository.deleteById(id); // IDを指定して削除
	    }
	    
	    public void addEmployee(Employee employee) {
	    	employeeRepository.save(employee);
	    }
	    
	    // IDが存在するか確認
	    public boolean existsById(Long id) {
	        return employeeRepository.existsById(id);
	    }
	    
	    // 社員情報をIDで検索
	    public Optional<Employee> findEmployeeById(Long id) {
	        return employeeRepository.findById(id);
	    }

	    // 社員情報の更新
	    public void updateEmployee(Employee employee) {
	        if (employeeRepository.existsById(employee.getId())) {
	            employeeRepository.save(employee);
	        }
	    }

		public void insertEmployee(Employee employee) {
			employeeRepository.save(employee);	
		}
		

}
