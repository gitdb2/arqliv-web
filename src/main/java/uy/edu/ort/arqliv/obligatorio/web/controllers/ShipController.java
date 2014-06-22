package uy.edu.ort.arqliv.obligatorio.web.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
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
		return "redirect:/ships/list.html";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String setupDelete(Locale locale, Model model, @RequestParam("id") int id) {
		logger.info("Welcome home! The client locale is {}.", locale);
		model.addAttribute("shipId", id);
		return "ships/delete";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String submitDelete(@Valid int id) {
		try {
			shipService.delete("rodrigo", id);
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		return "redirect:/ships/list.html";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String setupEdit(Locale locale, Model model, @RequestParam("id") int id) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Ship ship = null;
		boolean serviceError = false;
		try {
			ship = shipService.find("rodrigo", id);
		} catch (CustomServiceException e) {
			e.printStackTrace();
			serviceError = true;
		}
		if (ship == null || serviceError) {
			return "redirect:/ships/list.html";
		}
		ShipEditWrapper shipEditWrapper = new ShipEditWrapper();
		shipEditWrapper.setShip(ship);
		shipEditWrapper.setArrivalDate(sdf.format(new Date()));
		model.addAttribute("shipEditWrapper", shipEditWrapper);
		return "ships/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String submitEdit(@Valid ShipEditWrapper shipEditWrapper, BindingResult result) {
		if(result.hasErrors()) {
            return "ships/edit";
        }
		try {
			Date arrivalDate = sdf.parse(shipEditWrapper.getArrivalDate());
			shipService.update("rodrigo", shipEditWrapper.getShip(), arrivalDate);
		} catch (CustomServiceException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "redirect:/ships/list.html";
	}
	
	public static class ShipEditWrapper {
		
		private Ship ship;
		
		@NotNull
		@NotEmpty
		private String arrivalDate;
		
		public ShipEditWrapper() {
			super();
		}

		public Ship getShip() {
			return ship;
		}
		
		public void setShip(Ship ship) {
			this.ship = ship;
		}
		
		public String getArrivalDate() {
			return arrivalDate;
		}

		public void setArrivalDate(String arrivalDate) {
			this.arrivalDate = arrivalDate;
		}

	}
	
}
