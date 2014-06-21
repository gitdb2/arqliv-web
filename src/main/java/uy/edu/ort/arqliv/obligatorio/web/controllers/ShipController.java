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

import uy.edu.ort.arqliv.obligatorio.common.ShipService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;

/**
 * Controller para atender las paginas que esten relacionadas con los barcos
 * @author rodrigo
 *
 */
@Controller
@RequestMapping(value = "/ships")
public class ShipController {
	
	private static final Logger logger = LoggerFactory.getLogger(ShipController.class);
	
	@Autowired
	private ShipService shipService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		List<Ship> ships = new ArrayList<>();
		try {
			ships = shipService.list("rodrigo");
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		model.addAttribute("ships", ships );
		
		return "ships/list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET) 
	public String setupCreate(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		model.addAttribute("ship", new Ship());
		return "ships/create"; 
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String submitCreate(@Valid Ship newShip, BindingResult result) {
		if(result.hasErrors()) {
            return "ships/create";
        }
		try {
			shipService.store("rodrigo", newShip);
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		return "ships/list";
	}
	
}
