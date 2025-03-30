package com.example.demo;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
//@SpringBootApplication
@Service

public class KusunokiController01 {
	  @Autowired
	    private LoginRepository loginRepository;
	  
	    @Autowired
	    private HttpSession session;
	    
	    // すべてのリクエストで共通の情報をモデルに追加
	    @ModelAttribute
	    public void addAttributes(Model model) {
	        // セッションから 'username' を取得
	        String username = (String) session.getAttribute("username");

	        // 'username' があればモデルに追加
	        if (username != null) {
	            model.addAttribute("username", username);
	        }
	    }

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
	 public String bay(Model m) {
		 session.invalidate();
		 m.addAttribute("msg","ログアウトしました");  
		return "login";
	}
	  
	@RequestMapping("/mainMenu")
	public String Mein(Model m, HttpSession session){
		String username = (String) session.getAttribute("username");

    // 'username' があればモデルに追加
    if (username != null) {
    	return "mainMenu";
    }
		
			 m.addAttribute("msg","ログインしてください");  
				return "login";
		
		
		
	}
	@RequestMapping("/header")
	public String Header(Model m){
		return "header";
	}

	@RequestMapping("/delete")
	public String Delete(@RequestParam(value = "id", required = false) Long id, Model model) {
	    if (id != null) {
	        model.addAttribute("id", id); // idを追加
	    }
	    return "delete";
	}

	
	@RequestMapping("/deleteCheck")
	public String DeleteCheck(Model m,@RequestParam(value = "id",required = false )Long id){
		if(id == null||id == 0){
			m.addAttribute("msg","IDを入力してください");
			return "delete";//入力画面にもどる
		}
		
		if (!employeeRepository.existsById(id)) {
        m.addAttribute("msg", "指定されたIDの従業員は存在しません");
        return "delete";//入力画面にもどる
		}
		
		// IDが存在する場合、確認画面を表示
        m.addAttribute("id", id);
        m.addAttribute("employee", employeeRepository.findById(id).get());
        return "deleteCheck"; // deleteCheck.html に遷移
		
	}
	@Autowired
	private EmployeeServeice employeeService;
	
	
	@PostMapping("/deleteRear")
	public String deleteRear(Model m,@RequestParam("id")Long id){
				
			employeeService.deleteEmployee(id);
		      m.addAttribute("msg","削除完了");
		return "deleteRear";
		
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
