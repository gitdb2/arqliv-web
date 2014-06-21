package uy.edu.ort.arqliv.obligatorio.web.rest.controllers.ships;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;

@Controller
@RequestMapping(value = "/ships")
public class ShipsRestController {

	private static final Logger logger = LoggerFactory
			.getLogger(ShipsRestController.class);

	@Autowired
	ShipService shipClient;

	@RequestMapping(value = "/list", method = RequestMethod.GET)//, headers = "Accept=application/json, application/xml")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Ship> list(Locale locale) {
//		logger.info("Welcome home! The client locale is {}.", locale);

		List<Ship> ships = new ArrayList<>();
		
		
//		ships.add(new Ship(33, 2, 3, "dddddd", 999, "pepe"));
		try {
			ships = shipClient.list("rodrigo");

		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		return ships;
	}

	// /**
	// * Simply selects the home view to render by returning its name.
	// */
	// @RequestMapping(value = "/list", method = RequestMethod.GET)
	// public String list(Locale locale, Model model) {
	// logger.info("Welcome home! The client locale is {}.", locale);
	//
	// List<Ship> ships = new ArrayList<>();
	// try {
	// ships = shipClient.list("rodrigo");
	// } catch (CustomServiceException e) {
	// e.printStackTrace();
	// }
	// model.addAttribute("ships", ships );
	//
	// return "ships/list";
	// }
}

// public class RestUserController {
//
// @Autowired
// private UserService userService;
//
// @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET,
// headers = "Accept=application/json, application/xml")
// @ResponseBody
// @ResponseStatus(value = HttpStatus.OK)
// public User getUser(@PathVariable("userId") long id) {
// User user = this.userService.getUser(id);
//
// return user;
// }
//
// @RequestMapping(value = "/users.json", method = RequestMethod.GET,
// headers = "Accept=application/json, application/xml")
// @ResponseBody
// @ResponseStatus(value = HttpStatus.OK)
// public List<User> getUsers() {
// List<User> users = this.userService.listUsers();
// return users;
// }
//
// }
