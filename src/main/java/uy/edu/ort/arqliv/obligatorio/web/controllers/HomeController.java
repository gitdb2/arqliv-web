package uy.edu.ort.arqliv.obligatorio.web.controllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@RequestMapping(value = "/")
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST} )
	public String login(@RequestParam(value="user", required=false) String user, Model model, HttpSession session){
		
		
		boolean logged =false;
		if(user == null || user.trim().isEmpty()){
			model.addAttribute("user", "");
			session.setAttribute("user", "dummy");
			return "login";
		}else{
			session.setAttribute("user", user);
			return "redirect:/home/menu.html";
		}
		
	}
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home/menu", method = RequestMethod.GET)
	public String home(Model model, HttpSession session) {
		if(session.getAttribute("user")== null){
			session.setAttribute("user", "dummy");
		}
		
		
		model.addAttribute("user", session.getAttribute("user"));
		return "menu";
	}
	
}
