package com.example.demo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
	private EmployeeService employeeService;
	
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
    public String confirm( @ModelAttribute Employee employee,
    		               BindingResult result, Model model) {
        if (result.hasErrors() || !employee.getPassword().equals(employee.getPasswordConfirm())) {
            model.addAttribute("error", "パスワードが一致しません");
            return "insertConfirm";
        }
        model.addAttribute("employee", employee);
        return "insertConfirm";
    }

    @PostMapping("/complete")
    public <employeeService> String complete(@ModelAttribute Employee employee, Model model) {
    	if (employee.getStart() == null) {
            employee.setStart(LocalDateTime.now());  // デフォルトの日付をセット
        }
        
        employeeService.insertEmployee(employee);
        model.addAttribute("employee", employee);
        return "insertComplete";
    }
    
    //更新画面のバリデーション
    private String validateEmployee(Employee emp) {
        if (emp.getName() == null || emp.getName().isEmpty()) {
            return "名前は必須です。";
        }

        if (emp.getAge() <= 0) {
            return "年齢は必須です。";
        }

        String password = emp.getPassword();
        if (password == null || !password.matches("^(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            return "パスワードは8文字以上の英数字で、大文字を1文字以上含めてください。";
        }

        if (!password.equals(emp.getPasswordConfirm())) {
            return "パスワードと確認用パスワードが一致しません。";
        }

        // 開始日は必須
        if (emp.getStart() == null) {
            return "開始日は必須です。";
        }

        // 終了日がある場合は、開始日以降であることを確認（任意）
        if (emp.getStart() != null && emp.getEndDate() != null) {
            if (emp.getEndDate().isBefore(emp.getStart().toLocalDate())) {
                return "終了日は開始日以降の日付にしてください。";
            }
        }

        return null; // 問題なし
    }

    
    
 // 1. 更新画面の表示
    @GetMapping("/employee/updateForm")
    public String showUpdateForm(@ModelAttribute("employee") Employee employee, Model model) {
        // FlashAttributeからemployeeが来ていない場合は新規で空をセット（直接アクセス対策）
        if (!model.containsAttribute("employee")) {
            model.addAttribute("employee", new Employee());
        }
        return "updateForm";
    }


    // 2. 更新処理の確認
    @PostMapping("/employee/updateConfirm")
    public String UpdateConfirm(@ModelAttribute Employee employee, 
    		                     BindingResult result, Model model, 
    		                     RedirectAttributes redirectAttributes) {
    	String errorMessage = validateEmployee(employee);
    	 if (result.hasErrors()){
    	        // エラーメッセージをFlashに入れて入力画面に戻す
    	        redirectAttributes.addFlashAttribute("popupError", "日付の形式が正しくありません（例：2024/04/01）");
    	        redirectAttributes.addFlashAttribute("employee", employee); // 入力値も戻す
    	        return "redirect:/employee/updateForm";  // GETメソッドで再表示
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
