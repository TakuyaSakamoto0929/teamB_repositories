package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class EmployeeServeice {
	 @Autowired
	    private EmployeeRepository employeeRepository; // Repositoryを使う

	    public void deleteEmployee(Long id) {
	        employeeRepository.deleteById(id); // IDを指定して削除
	    }
}
