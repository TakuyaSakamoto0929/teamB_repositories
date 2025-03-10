package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
@SpringBootApplication
@Service

public class KusunokiController01 {

	@RequestMapping("/mainMenu")
	public String Mein(Model m){
		return "mainMenu";
	}
	@RequestMapping("/header")
	public String Header(Model m){
		return "header";
	}
	@RequestMapping("/delete")
	public String Delete(Model m){
		return "delete";
	}
	
	@Autowired
	private EmployeeServeice employeeService;
	
	@RequestMapping("/deleteRear")
	public String deleteRear(Model m,@RequestParam(value = "id",required = false )Long id){
				if(id == null||id == 0){
			m.addAttribute("msg","IDを入力してください");
			return "delete";
		}
		if (!employeeRepository.existsById(id)) {
		        m.addAttribute("msg", "指定されたIDの従業員は存在しません");
		        return "delete";
		}
		
		{
			employeeService.deleteEmployee(id);
		      m.addAttribute("msg","削除完了");
		return "deleteRear";
		}
	}

	
	
	@Autowired
	private EmployeeRepository employeeRepository;
	@RequestMapping("/test")//DB確認用all表示
	public String testpage(Model m){
		List<Employee>employee=employeeRepository.findAll();
		m.addAttribute("employee",employee);
		return "testpage";
	}
	
	public static void main(String[] args) {
        SpringApplication.run(KusunokiController01.class, args);
    }


	
}
