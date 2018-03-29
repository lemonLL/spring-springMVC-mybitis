package cn.itcast.ssm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@RequestMapping("/login")
	public String loginSubmit(HttpSession session,String username,String userpass) throws Exception{
		session.setAttribute("username", username);
		return "redirect:/items/queryItemsQuery.action";
	}
	
	@RequestMapping("/logout")
	public String loginout(HttpSession session) throws Exception{
		session.invalidate();
		return "redirect:/items/queryItemsQuery.action";
	}
}
