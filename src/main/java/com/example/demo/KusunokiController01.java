package com.example.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

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


@Controller

public class KusunokiController01 {
	
	@Autowired
	    private LoginRepository loginRepository;
	  
	@Autowired
	    private HttpSession session;
	
	 // すべてのリクエストで共通の情報をモデルに追加
	    @ModelAttribute
	    public void addAttributes(Model model, HttpSession session) {
	        // セッションから 'username' を取得
	        String username = (String) session.getAttribute("username");

	        // 'username' があればモデルに追加
	        if (username != null) {
	            model.addAttribute("username", username);
	        }
	    }

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
	
	
	 @GetMapping("/login")
	    public String showLoginPage() {
	        return "login"; // login.html を表示
	    }
	  
	  @PostMapping("/login")
	  public String login(@RequestParam("name") String name, 
	                        @RequestParam("password") String password,
	                        Model model,HttpSession session) {
	        // ユーザー名 passwordで検索
	        Login user = loginRepository.findByNameAndPassword(name,password);
	        // ユーザーが存在しない、またはパスワードが一致しない場合
	        if (user == null ) {
	            model.addAttribute("msg", "ユーザー名またはパスワードが間違っています");
	            return "login";  // ログインページに戻す
	        }
	        // ログイン成功
	        session.setAttribute("username", name); // セッションにユーザー名を保存
	        return "redirect:/mainMenu";  // メインメニューにリダイレクト
	  }
	    
	 @PostMapping("/bay")//ログアウトごlogin画面に戻る
	 @Transactional
	 public String bay(Model m, HttpSession session) {
		 session.invalidate();
		 m.addAttribute("msg","ログアウトしました");  
		return "login";
	}
	
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
            employee.setStart(LocalDate.now());  // デフォルトの日付をセット
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
        if (emp.getEndDate() != null) {
        	if (emp.getEndDate().isBefore(emp.getStart())) {
                return "終了日は開始日以降の日付にしてください。";
            }
        }

        return null; // 問題なし
    }

    
    
 // 1. 更新画面の表示
    @GetMapping("/updateForm")
    public String showUpdateForm(@RequestParam("id") Long id, Model model) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
        	Employee emp = employee.get();
            emp.setPasswordConfirm(emp.getPassword()); // 初期状態で一致させておく
            model.addAttribute("employee", emp);
            return "updateForm";
        } else {
            return "redirect:/errorPage"; // 見つからないとき用
        }
    }



    // 2. 更新処理の確認
    @PostMapping("/employee/updateConfirm")
    public String UpdateConfirm(@ModelAttribute Employee employee, 
    		                     Model model) {
    	String errorMessage = validateEmployee(employee);
    	 if (errorMessage != null){
    	        // エラーメッセージをFlashに入れて入力画面に戻す
    		    model.addAttribute("popupError", errorMessage);
    	        model.addAttribute("employee", employee); // 入力値を戻す
    	        return "updateForm";  // リダイレクトではなく、直接遷移
    	    }
        model.addAttribute("employee", employee);
        return "updateConfirm";
    }

    // 3. 更新処理の完了
    @PostMapping("/employee/updateComplete")
    public String completeUpdate(@ModelAttribute Employee employee, Model model) {
    	employee.setPasswordConfirm(null);
        employee.setUpdated(LocalDateTime.now()); // 更新日を今に
        employeeService.updateEmployee(employee);
        return "updateComplete";
    }
	
	
}
