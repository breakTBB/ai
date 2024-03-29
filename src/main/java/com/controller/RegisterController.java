package com.controller;

import javax.servlet.http.HttpServletRequest;

import com.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 注册接口
 *
 */
@Controller
public class RegisterController {

	@Autowired
	RegisterService registerService;

	@RequestMapping(path = "/register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public View register(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("introduction") String introduction, HttpServletRequest request) {
		
		String contextPath = request.getContextPath();
		String action = registerService.reg(username, password, introduction);
		return new RedirectView(contextPath + action);
	}
}
