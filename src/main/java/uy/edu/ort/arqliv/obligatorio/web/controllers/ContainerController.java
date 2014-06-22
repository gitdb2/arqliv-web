package uy.edu.ort.arqliv.obligatorio.web.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uy.edu.ort.arqliv.obligatorio.common.ContainerService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;

/**
 * Controller para atender las paginas que esten relacionadas con los barcos
 * @author rodrigo
 *
 */
@Controller
@RequestMapping(value = "/containers")
public class ContainerController {
	
	private static final Logger logger = LoggerFactory.getLogger(ContainerController.class);

	@Autowired
	private ContainerService containerService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		List<Container> containers = new ArrayList<>();
		try {
			containers = containerService.list("rodrigo");
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		model.addAttribute("containers", containers);
		return "containers/list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String setupCreate(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		model.addAttribute("container", new Container());
		return "containers/create";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String submitCreate(@Valid Container container, BindingResult result) {
		if(result.hasErrors()) {
            return "containers/create";
        }
		try {
			containerService.store("rodrigo", container);
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		return "redirect:/containers/list.html";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String setupDelete(Locale locale, Model model, @RequestParam("id") int id) {
		logger.info("Welcome home! The client locale is {}.", locale);
		model.addAttribute("contId", id);
		return "containers/delete";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String submitDelete(@Valid int id) {
		try {
			containerService.delete("rodrigo", id);
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		return "redirect:/containers/list.html";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String setupEdit(Locale locale, Model model, @RequestParam("id") int id) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Container container = null;
		boolean serviceError = false;
		try {
			container = containerService.find("rodrigo", id);
		} catch (CustomServiceException e) {
			e.printStackTrace();
			serviceError = true;
		}
		if (container == null || serviceError) {
			return "redirect:/containers/list.html";
		}
		model.addAttribute("container", container);
		return "containers/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String submitEdit(@Valid Container container, BindingResult result) {
		if(result.hasErrors()) {
            return "containers/edit";
        }
		try {
			containerService.update("rodrigo", container);
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		return "redirect:/containers/list.html";
	}
	
}
