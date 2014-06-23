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

import uy.edu.ort.arqliv.obligatorio.common.ArrivalService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;
import uy.edu.ort.arqliv.obligatorio.web.controllers.models.ArrivalModel;
import uy.edu.ort.arqliv.obligatorio.web.controllers.models.Error;

/**
 * Controller para atender las paginas que esten relacionadas con los arribos
 * @author rodrigo
 * 
 */
@Controller
@RequestMapping(value = "/arrivals")
public class ArrivalController {

	private static final Logger logger = LoggerFactory.getLogger(ArrivalController.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	
	@Autowired
	private ArrivalService arrivalService;

	/**
	 * Menu principal
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/menu", method = { RequestMethod.GET, RequestMethod.POST })
	public String menu(HttpSession session, Model model) {
		model.addAttribute("user", session.getAttribute("user"));
		return "arrivals/menu";
	}

	/**
	 * Listade de todas las entidades en la db
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HttpSession session) {
		List<Arrival> arrivals = new ArrayList<>();
		String user = (String) (session.getAttribute("user") == null ? "dummy" : session.getAttribute("user"));
		model.addAttribute("user", user);

		try {
			arrivals = arrivalService.list(user);
		} catch (CustomServiceException e) {
			logger.error(e.getMessage(), e);
		}
		model.addAttribute("arrivals", arrivals);
		return "arrivals/list";
	}

	/**
	 * Show form para crear una entidad, carga un modelo vacio
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String setupCreate(Model model, HttpSession session) {
		String user = (String) (session.getAttribute("user") == null ? "dummy" : session.getAttribute("user"));
		model.addAttribute("user", user);

		ArrivalModel arrivalModel = new ArrivalModel();
		arrivalModel.setArrivalDate(new Date());

		model.addAttribute("arrivalModel", arrivalModel);
		return "arrivals/create";
	}

	/**
	 * Recibe el modelo con los datos para crear la entidad y persistirla.
	 * @param arrivalModel
	 * @param result
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String submitCreate(@Valid ArrivalModel arrivalModel, BindingResult result, Model model, HttpSession session) {
		String user = (String) (session.getAttribute("user") == null ? "dummy" : session.getAttribute("user"));
		model.addAttribute("user", user);

		if (result.hasErrors()) {
			return "arrivals/create";
		}
		try {
			Set<Long> containerSet = new HashSet<>(arrivalModel.getContainers());

			Arrival arrival = new Arrival();
			arrival.setArrivalDate(arrivalModel.getArrivalDate());
			arrival.setShipOrigin(arrivalModel.getShipOrigin());
			arrival.setContainersDescriptions(arrivalModel.getContainersDescriptions());

			Long id = arrivalService.store(user, arrival, arrivalModel.getShipId(), new ArrayList<>(containerSet));

			logger.info("Arrival id: " + id + " creado");
		} catch (CustomServiceException e) {
			logger.error(e.getMessage(), e);
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

	/**
	 * On submit de confirmacion de borrado, elimina de la db
	 * @param id
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String submitDelete(@RequestParam(value = "id", required = true) Integer id, Model model, HttpSession session

	) {
		String user = (String) (session.getAttribute("user") == null ? "dummy" : session.getAttribute("user"));

		try {
			arrivalService.delete(user, id);
		} catch (CustomServiceException e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("error", new Error(e.getMessage(), "arrivals"));
			return "error";
		}
		return "redirect:/arrivals/list.html";
	}

	/**
	 * Show form para edicion, a partir del id se carga el modelo desde la DB para editarlo
	 * @param id
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String setupEdit(@RequestParam(value = "id", required = true) Integer id, Model model, HttpSession session) {

		String user = (String) (session.getAttribute("user") == null ? "dummy" : session.getAttribute("user"));
		model.addAttribute("user", user);

		ArrivalModel arrivalModel = new ArrivalModel();

		try {
			Arrival arrival = arrivalService.find(user, id);
			
			arrivalModel.setArrivalId(arrival.getId());
			arrivalModel.setArrivalDate(arrival.getArrivalDate());

			arrivalModel.setContainers(arrival.getContainersIdList());
			arrivalModel.setContainersDescriptions(arrival.getContainersDescriptions());
			arrivalModel.setShipId(arrival.getShip().getId());
			arrivalModel.setShipOrigin(arrival.getShipOrigin());

		} catch (CustomServiceException e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("error", new Error(e.getMessage(), "arrivals"));
			return "error";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("error", new Error(e.getMessage(), "arrivals"));
			return "error";
		}

		model.addAttribute("arrivalModel", arrivalModel);
		return "arrivals/edit";
	}

		/**
		 * Submit de edicion de entidad, controla que haya cambios y si los hay aplica los cambios en la db
		 * @param arrivalModel
		 * @param result
		 * @param model
		 * @param session
		 * @return
		 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String submitEdit(@Valid ArrivalModel arrivalModel, BindingResult result, Model model, HttpSession session) {
		String user = (String) (session.getAttribute("user") == null ? "dummy" : session.getAttribute("user"));
		model.addAttribute("user", user);

		
		
		if (result.hasErrors()) {
			return "arrivals/edit";
		}
		try {
			Set<Long> containerSet = new HashSet<>(arrivalModel.getContainers());

			Arrival arrival = arrivalService.find(user, arrivalModel.getArrivalId());
			
			///CONTROL DE CAMBIOS 
			boolean hayCambios = false;
			
			if (arrivalModel.getArrivalDate() != null) {
				if (!sdf.format(arrival.getArrivalDate()).equals(sdf.format(arrival.getArrivalDate())) ){
					arrival.setArrivalDate(arrivalModel.getArrivalDate());
					hayCambios = true;
				}
			}
			if (arrivalModel.getShipOrigin() != null
					&& !arrivalModel.getShipOrigin().isEmpty()
					&& !arrival.getShipOrigin().trim()
							.equals(arrivalModel.getShipOrigin().trim())) {
				arrival.setShipOrigin(arrivalModel.getShipOrigin());
				hayCambios = true;
			}
			if (arrivalModel.getContainersDescriptions() != null
					&& !arrivalModel.getContainersDescriptions().trim().isEmpty()
					&& !arrival.getContainersDescriptions().trim()
							.equals(arrivalModel.getContainersDescriptions().trim())) {
				arrival.setContainersDescriptions(arrivalModel.getContainersDescriptions());
				hayCambios = true;
			}
			Long shipid = 0L;
			if (arrivalModel.getShipId() != null && arrival.getShip().getId() != (long) arrivalModel.getShipId()) {
				hayCambios = true;
				shipid = (long) arrivalModel.getShipId();
			} else {
				shipid = arrival.getShip().getId();
			}
			
			//cambios en los contenedores??
			List<Long> containers = arrival.getContainersIdList();
			boolean changeCont =  !(containerSet.size() == containers.size()
								  && containers.containsAll(containerSet));
			
			if(changeCont){
				containers = new ArrayList<>(containerSet);
			}			
				
			//FIN CONTROL DE CAMBIOS 
		
			if(hayCambios || changeCont){
				Long id  = arrivalService.update(user, arrival, shipid, containers);
				logger.info("Arribo Modificado correctamente, id: "+ id);
			}else{
				logger.info("No hay Cambios, id: "+ arrivalModel.getArrivalId());
				result.reject("error", "No se realizaron cambios, no se permite la edición.");
				return "arrivals/edit";
			}
			
		} catch (CustomServiceException e) {
			result.reject("error", e.getMessage());
			return "arrivals/edit";
		}catch (Exception e) {
			result.reject("error", e.getMessage());
			return "arrivals/edit";
		}
		return "redirect:/arrivals/list.html";
	}

}
