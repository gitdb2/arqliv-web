package uy.edu.ort.arqliv.obligatorio.web.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import uy.edu.ort.arqliv.obligatorio.common.DepartureService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Departure;

/**
 * Controller para atender las paginas que esten relacionadas con los barcos
 * @author rodrigo
 *
 */
@Controller
@RequestMapping(value = "/departures")
public class DepartureController {
	
	private static final Logger logger = LoggerFactory.getLogger(DepartureController.class);

	@Autowired
	private DepartureService departureService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		List<Departure> departures = new ArrayList<>();
		try {
			departures = departureService.list("rodrigo");
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		model.addAttribute("departures", departures);
		return "departures/list";
	}
	
}
