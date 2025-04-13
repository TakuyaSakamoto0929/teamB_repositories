package com.example.demo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller

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
	
	@GetMapping("/insert")
    public String showRegisterForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "insert";
    }

    @PostMapping("/confirm")
    public String confirm( @ModelAttribute Employee employee, BindingResult result, Model model) {
        if (result.hasErrors() || !employee.getPassword().equals(employee.getPasswordConfirm())) {
            model.addAttribute("error", "パスワードが一致しません");
            return "insertConfirm";
        }
        model.addAttribute("employee", employee);
        return "insertConfirm";
    }

    @PostMapping("/complete")
    public String complete(@ModelAttribute Employee employee, Model model) {
    	if (employee.getStart() == null) {
            employee.setStart(LocalDateTime.now());  // デフォルトの日付をセット
        }
        
        employeeService.insertEmployee(employee);
        model.addAttribute("employee", employee);
        return "insertComplete";
    }
    
 // 1. 更新画面の表示
    @GetMapping("/employee/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            model.addAttribute("employee", employeeOpt.get());
            return "updateForm";
        } else {
            model.addAttribute("msg", "社員が見つかりません");
            return "errorPage";
        }
    }

    // 2. 更新処理の確認
    @PostMapping("/employee/updateConfirm")
    public String UpdateConfirm(@Validated @ModelAttribute("employee") Employee employee, 
    		                    BindingResult result, Model model) {
        if (result.hasErrors() || !employee.getPassword().equals(employee.getPasswordConfirm())) {
            model.addAttribute("error", "パスワードが一致しません");
            model.addAttribute("employee", employee);
            return "errorPage";
        }
        model.addAttribute("employee", employee);
        return "updateConfirm";
    }

    // 3. 更新処理の完了
    @PostMapping("/employee/updateComplete")
    public String completeUpdate(@ModelAttribute Employee employee, Model model) {
    	employee.setPasswordConfirm(null);
    	employeeService.updateEmployee(employee);
        return "updateComplete";
    }
	
	
}
