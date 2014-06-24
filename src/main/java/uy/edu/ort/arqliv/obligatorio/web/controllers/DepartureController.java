package uy.edu.ort.arqliv.obligatorio.web.controllers;

import java.text.SimpleDateFormat;
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

import uy.edu.ort.arqliv.obligatorio.common.DepartureService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Departure;
import uy.edu.ort.arqliv.obligatorio.web.controllers.models.DepartureModel;
import uy.edu.ort.arqliv.obligatorio.web.controllers.models.Error;

/**
 * Controller para atender las paginas que esten relacionadas partidas
 * 
 * @author rodrigo
 * 
 */
@Controller
@RequestMapping(value = "/departures")
public class DepartureController {

	private static final Logger logger = LoggerFactory.getLogger(DepartureController.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Autowired
	private DepartureService departureService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/menu", method = { RequestMethod.GET, RequestMethod.POST })
	public String containerHome(Model model, HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		return "departures/menu";
	}

	/**
	 * Listado de todas las partidas
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HttpSession session) {
		String user = (String) (session.getAttribute("user") == null ? "dummy" : session.getAttribute("user"));
		model.addAttribute("user", user);

		List<Departure> departures = new ArrayList<>();
		try {
			departures = departureService.list((String)session.getAttribute("user"));
		} catch (CustomServiceException e) {
			logger.error(e.getMessage(), e);
		}
		model.addAttribute("departures", departures);
		return "departures/list";
	}

	/**
	 * Carga del modelo para mostrar el formulario de crear una partida
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String setupCreate(Model model, HttpSession session) {
		String user = (String) (session.getAttribute("user") == null ? "dummy" : session.getAttribute("user"));
		model.addAttribute("user", user);

		DepartureModel departureModel = new DepartureModel();
		departureModel.setDepartureDate(new Date());

		model.addAttribute("departureModel", departureModel);
		return "departures/create";
	}

	/**
	 * On submit del formulario de crear, toma los datos del modelo, crea la entidad y la persiste
	 * @param departureModel
	 * @param result
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String submitCreate(@Valid DepartureModel departureModel, BindingResult result, Model model,
			HttpSession session) {
		String user = (String) (session.getAttribute("user") == null ? "dummy" : session.getAttribute("user"));
		model.addAttribute("user", user);

		if (result.hasErrors()) {
			return "departures/create";
		}
		try {
			Set<Long> containerSet = new HashSet<>(departureModel.getContainers());

			Departure departure = new Departure();
			departure.setDepartureDate(departureModel.getDepartureDate());
			departure.setShipDestination(departureModel.getShipDestination());
			departure.setContainersDescriptions(departureModel.getContainersDescriptions());

			Long id = departureService.store(user, departure, departureModel.getShipId(), new ArrayList<>(containerSet));

			logger.info("Departure id: " + id + " creado");
		} catch (CustomServiceException e) {
			logger.error(e.getMessage(), e);
			result.reject("error", e.getMessage());
			return "departures/create";
		}
		return "redirect:/departures/list.html";
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
		model.addAttribute("departureId", id);
		return "departures/delete";
	}

	/**
	 * Submit de la confirmacion de eliminar, da de baja en la db
	 * @param id
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String submitDelete(@RequestParam(value = "id", required = true) Integer id, 
			Model model, HttpSession session

	) {
		String user = (String) (session.getAttribute("user") == null ? "dummy" : session.getAttribute("user"));

		try {
			departureService.delete(user, id);
		} catch (CustomServiceException e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("error", new Error(e.getMessage(), "departures"));
			return "error";
		}
		return "redirect:/departures/list.html";
	}

	/**
	 * Show form para el formulario de edicion
	 * carga el modelo con los datos de la base de datos a partir del id
	 * @param id
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String setupEdit(@RequestParam(value = "id", required = true) Integer id, Model model, HttpSession session) {

		String user = (String) (session.getAttribute("user") == null ? "dummy" : session.getAttribute("user"));
		model.addAttribute("user", user);

		DepartureModel departureModel = new DepartureModel();

		try {
			Departure departure = departureService.find(user, id);

			departureModel.setDepartureId(departure.getId());
			departureModel.setDepartureDate(departure.getDepartureDate());

			departureModel.setContainers(departure.getContainersIdList());
			departureModel.setContainersDescriptions(departure.getContainersDescriptions());
			departureModel.setShipId(departure.getShip().getId());
			departureModel.setShipDestination(departure.getShipDestination());

		} catch (CustomServiceException e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("error", new Error(e.getMessage(), "departures"));
			return "error";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("error", new Error(e.getMessage(), "departures"));
			return "error";
		}

		model.addAttribute("departureModel", departureModel);
		return "departures/edit";
	}

	/***
	 * Submit para el formulario de edicion.
	 * si hay cambios efectua el update en la db
	 * @param departureModel
	 * @param result
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String submitEdit(@Valid DepartureModel departureModel, BindingResult result, Model model, HttpSession session) {
		String user = (String) (session.getAttribute("user") == null ? "dummy" : session.getAttribute("user"));
		model.addAttribute("user", user);

		if (result.hasErrors()) {
			return "departures/edit";
		}
		try {
			Set<Long> containerSet = new HashSet<>(departureModel.getContainers());

			Departure departure = departureService.find(user, departureModel.getDepartureId());

			// /CONTROL DE CAMBIOS
			boolean hayCambios = false;

			if (departureModel.getDepartureDate() != null) {
				if (!sdf.format(departure.getDepartureDate()).equals(sdf.format(departure.getDepartureDate()))) {
					departure.setDepartureDate(departureModel.getDepartureDate());
					hayCambios = true;
				}
			}
			if (departureModel.getShipDestination() != null && !departureModel.getShipDestination().isEmpty()
					&& !departure.getShipDestination().trim().equals(departureModel.getShipDestination().trim())) {
				departure.setShipDestination(departureModel.getShipDestination());
				hayCambios = true;
			}
			if (departureModel.getContainersDescriptions() != null
					&& !departureModel.getContainersDescriptions().trim().isEmpty()
					&& !departure.getContainersDescriptions().trim()
							.equals(departureModel.getContainersDescriptions().trim())) {
				departure.setContainersDescriptions(departureModel.getContainersDescriptions());
				hayCambios = true;
			}
			Long shipid = 0L;
			if (departureModel.getShipId() != null && departure.getShip().getId() != (long) departureModel.getShipId()) {
				hayCambios = true;
				shipid = (long) departureModel.getShipId();
			} else {
				shipid = departure.getShip().getId();
			}

			// cambios en los contenedores??
			List<Long> containers = departure.getContainersIdList();
			boolean changeCont = !(containerSet.size() == containers.size() && containers.containsAll(containerSet));

			if (changeCont) {
				containers = new ArrayList<>(containerSet);
			}

			// FIN CONTROL DE CAMBIOS

			if (hayCambios || changeCont) {
				Long id = departureService.update(user, departure, shipid, containers);
				logger.info("PArtida Modificado correctamente, id: " + id);
			} else {
				logger.info("No hay Cambios, id: " + departureModel.getDepartureId());
				result.reject("error", "No se realizaron cambios, no se permite la edición.");
				return "departures/edit";
			}

		} catch (CustomServiceException e) {
			result.reject("error", e.getMessage());
			return "departures/edit";
		} catch (Exception e) {
			result.reject("error", e.getMessage());
			return "departures/edit";
		}
		return "redirect:/departures/list.html";
	}

}
