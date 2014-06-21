package uy.edu.ort.arqliv.obligatorio.rest.controllers.ships;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import uy.edu.ort.arqliv.obligatorio.common.ShipService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomInUseServiceException;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.RestServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;

@Controller
@RequestMapping(value = "/ships")
public class ShipsRestController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(ShipsRestController.class);

	@Autowired
	ShipService shipClient;

	@RequestMapping(value = "/list", method = RequestMethod.GET,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Ship> list(Locale locale, HttpSession session) {
//		logger.info("Welcome home! The client locale is {}.", locale);

		System.out.println(session ==null);
		
		session.setAttribute("test", "test");
		List<Ship> ships = new ArrayList<>();
		try {
			ships = shipClient.list("rodrigo");

		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		org.springframework.http.converter.json.MappingJackson2HttpMessageConverter tmp;
		
		return ships;
	}

	
	@RequestMapping(value = "/error", method = RequestMethod.GET,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Ship> error(Locale locale, HttpSession session) {
		System.out.println(session.getAttribute("test"));
		throw new CustomInUseServiceException("esta en uso");
//		throw new RestServiceException("Forzada", 777);
	}

}
