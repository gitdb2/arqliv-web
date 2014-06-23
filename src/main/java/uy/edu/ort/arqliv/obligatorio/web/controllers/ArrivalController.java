package uy.edu.ort.arqliv.obligatorio.web.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;
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

import uy.edu.ort.arqliv.obligatorio.common.ArrivalService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;
import uy.edu.ort.arqliv.obligatorio.web.controllers.models.ArrivalModel;

/**
 * Controller para atender las paginas que esten relacionadas con los barcos
 * 
 * @author rodrigo
 * 
 */
@Controller
@RequestMapping(value = "/arrivals")
public class ArrivalController {

	private static final Logger logger = LoggerFactory.getLogger(ArrivalController.class);

	@Autowired
	private ArrivalService arrivalService;

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String menu(HttpSession session, Model model) {
		model.addAttribute("user", session.getAttribute("user"));
		return "arrivals/menu";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HttpSession session) {
		List<Arrival> arrivals = new ArrayList<>();
		String user = (String) (session.getAttribute("user") == null ? "dummy" : session.getAttribute("user"));
		model.addAttribute("user", user);

		try {
			arrivals = arrivalService.list(user);
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		model.addAttribute("arrivals", arrivals);
		return "arrivals/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String setupCreate(Model model, HttpSession session) {
		String user = (String) (session.getAttribute("user") == null ? "dummy" : session.getAttribute("user"));
		model.addAttribute("user", user);

		ArrivalModel arrivalModel = new ArrivalModel();
		arrivalModel.setArrivalDate(new Date());

		model.addAttribute("arrivalModel", arrivalModel);
		return "arrivals/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String submitCreate(@Valid ArrivalModel arrivalModel, BindingResult result, Model model,
			HttpSession session) {
		String user = (String) (session.getAttribute("user") == null ? "dummy" : session.getAttribute("user"));
		model.addAttribute("user", user);

		if (result.hasErrors()) {
			return "arrivals/create";
		}
		try {
			Set<Long> containerSet 		= new HashSet<>(arrivalModel.getContainers());
			
			Arrival arrival = new Arrival();
			arrival.setArrivalDate(arrivalModel.getArrivalDate());
			arrival.setShipOrigin(arrivalModel.getShipOrigin());	
			arrival.setContainersDescriptions(arrivalModel.getContainersDescriptions());
			
			
			
			Long id = arrivalService.store(
							user, 
							arrival, 
							arrivalModel.getShipId(),
							new ArrayList<>(containerSet));
			
			logger.info("Arrival id: " + id + " creado");
		} catch (CustomServiceException e) {
		//	e.printStackTrace();
			result.reject("error", e.getMessage());
			return "arrivals/create";
		}
		return "redirect:/arrivals/list.html";
	}

	/**
	 * show form para eliminar
	 * 
	 * @param model
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String setupDelete(Model model, @RequestParam("id") int id, HttpSession session) {
		String user = (String) (session.getAttribute("user") == null ? "dummy" : session.getAttribute("user"));
		model.addAttribute("user", user);
		model.addAttribute("arrivalId", id);
		return "arrivals/delete";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String submitDelete(@Valid int id, HttpSession session, Model model, BindingResult result) {
		String user = (String) (session.getAttribute("user") == null ? "dummy" : session.getAttribute("user"));

		try {
			arrivalService.delete(user, id);
		} catch (CustomServiceException e) {
			model.addAttribute("error", e.getMessage());
			result.reject("error", e.getMessage());
			return "arrivals/delete";
		}
		return "redirect:/arrivals/list.html";
	}

	//
	// @RequestMapping(value = "/edit", method = RequestMethod.GET)
	// public String setupEdit(Locale locale, Model model, @RequestParam("id")
	// int id) {
	// logger.info("Welcome home! The client locale is {}.", locale);
	// Container container = null;
	// boolean serviceError = false;
	// try {
	// container = containerService.find("rodrigo", id);
	// } catch (CustomServiceException e) {
	// e.printStackTrace();
	// serviceError = true;
	// }
	// if (container == null || serviceError) {
	// return "redirect:/containers/list.html";
	// }
	// model.addAttribute("container", container);
	// return "containers/edit";
	// }
	//
	// @RequestMapping(value = "/edit", method = RequestMethod.POST)
	// public String submitEdit(@Valid Container container, BindingResult
	// result) {
	// if(result.hasErrors()) {
	// return "containers/edit";
	// }
	// try {
	// containerService.update("rodrigo", container);
	// } catch (CustomServiceException e) {
	// e.printStackTrace();
	// }
	// return "redirect:/containers/list.html";
	// }

	

}
