package com.enset.TP2springMCV.security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SecurityController {
	@GetMapping("/notAuthorized")
	public String error()
	{
		return "notAuthorized";
	}
	
	@GetMapping("/login")
	public String loginPage()
	{
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request,RedirectAttributes red) throws ServletException
	{
		request.logout();
		  red.addFlashAttribute("success", "Vous êtes déconnecter");
		return "redirect:/login";
	}
	
	
	@GetMapping("/profil")
	public String profil()
	{		
		return "profil";
	}
	
}
