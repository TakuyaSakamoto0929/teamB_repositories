package com.example.demo;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller

public class KusunokiController01 {
	  @Autowired
		private EmployeeRepository employeeRepository;
	  @Autowired
	    private HttpSession session;
	  @Autowired
	    private KusunokiService01 kusunokiService01;
	    
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

	  @GetMapping("/login") // login.html を表示
	    public String showLoginPage() {
	        return "login";
	    }
	  
	  @PostMapping("/login")// ユーザー名 passwordで検索
	  public String login(@RequestParam("name") String name, 
	                      @RequestParam("password") String password,
	                      Model model,HttpSession session) {
		  
	//kusunokiService01で情報確認する
		  Employee user =kusunokiService01.authenticate (name,password);
	     
	       if (user == null ) {
	            model.addAttribute("msg", "ユーザー名またはパスワードが間違っています");
	            return "login";  // ログインページに戻す
	        }
	       
	        session.setAttribute("username", name); // セッションにユーザー名を保存
	        return "redirect:/mainMenu";  // メインメニューにリダイレクト
	  }
	    
	 @PostMapping("/bay")//ログアウト後login画面に戻る
	 @Transactional
	 public String bay(Model m) {
		 session.invalidate();
		 m.addAttribute("msg","ログアウトしました");  
		return "login";
	}
	  
	@RequestMapping("/mainMenu")//メインメニューと未ログインアクセス防止
	public String Mein(Model m, HttpSession session){
		String username = (String) session.getAttribute("username");
    if (username != null) {
    	return "mainMenu";
    }	
			 m.addAttribute("msg","ログインしてください");  
				return "login";	
	}

	
	@RequestMapping("/delete")
	public String Delete(@RequestParam(value = "id", required = false) Long id, Model model) {
	    if (id != null) {
	        model.addAttribute("id", id); // idを追加
	    }
	    return "delete";
	}

//	正しいか確認
	@RequestMapping("/deleteCheck")
	public String deleteCheck(Model m,@RequestParam(value = "id",required = false )Long id){
		return kusunokiService01.checkEmployeeForDelete(id, m);
	}

	
//	削除処理
	@PostMapping("/deleteRear")
	public String deleteRear(Model m,@RequestParam("id")Long id){
			  kusunokiService01.deleteEmployee(id);
		      m.addAttribute("msg","削除完了");
		      return "deleteRear";
		
	}

	
	

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
