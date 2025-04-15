package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class KusunokiService01 {
	
	  @Autowired
	    private  EmployeeRepository loginRepository;
//	　ログイン機能
	  public  Employee authenticate(String name, String password) {
	        return loginRepository.findByNameAndPassword(name, password);
	    }
	  
//	  削除
	    @Autowired
	    private EmployeeRepository employeeRepository;

	    public String checkEmployeeForDelete(Long id, Model m) {
	        if (id == null || id == 0) {
	            m.addAttribute("msg", "IDを入力してください");
	            return "delete"; // 入力画面にもどる
	        }

	        if (!employeeRepository.existsById(id)) {
	            m.addAttribute("msg", "指定されたIDの従業員は存在しません");
	            return "delete"; // 入力画面にもどる
	        }
	        
	        m.addAttribute("id", id);
	        m.addAttribute("employee", employeeRepository.findById(id).get());
	        return "deleteCheck"; // 確認画面に遷移 deleteCheck.html
	    }
	    
	   
	    public void deleteEmployee(Long id) {
	        employeeRepository.deleteById(id); // IDを指定して削除
	    }
}
