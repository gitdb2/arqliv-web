package uy.edu.ort.arqliv.obligatorio.web.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uy.edu.ort.arqliv.obligatorio.common.ShipService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;
import uy.edu.ort.arqliv.obligatorio.web.controllers.models.ShipEditWrapper;
import uy.edu.ort.arqliv.obligatorio.web.pdf.PDFRenderer;

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
	public String list( Model model, HttpSession session) {
		List<Ship> ships = new ArrayList<>();
		try {
			ships = shipService.list((String)session.getAttribute("user"));
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		model.addAttribute("ships", ships );
		
		return "ships/list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String setupCreate( Model model) {
		model.addAttribute("ship", new Ship());
		return "ships/create";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String submitCreate(@Valid Ship newShip, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
            return "ships/create";
        }
		try {
			shipService.store((String)session.getAttribute("user"), newShip);
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		return "redirect:/ships/list.html";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String setupDelete( Model model, @RequestParam("id") int id) {
		model.addAttribute("shipId", id);
		return "ships/delete";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String submitDelete(@Valid int id, HttpSession session) {
		try {
			shipService.delete((String)session.getAttribute("user"), id);
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		return "redirect:/ships/list.html";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String setupEdit( Model model, @RequestParam("id") int id, HttpSession session) {
		Ship ship = null;
		boolean serviceError = false;
		try {
			ship = shipService.find((String)session.getAttribute("user"), id);
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
	public String submitEdit(@Valid ShipEditWrapper shipEditWrapper, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
            return "ships/edit";
        }
		try {
			Date arrivalDate = sdf.parse(shipEditWrapper.getArrivalDate());
			shipService.update((String)session.getAttribute("user"), shipEditWrapper.getShip(), arrivalDate);
		} catch (CustomServiceException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "redirect:/ships/list.html";
	}
	

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String containerHome( Model model, HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		return "ships/menu";
	}
	

	
	@RequestMapping(value = "/getPdfList", method =  { RequestMethod.GET, RequestMethod.POST} )
	public ResponseEntity<byte[]> postPDF( Model model, HttpSession session) {
		List<Ship> ships = new ArrayList<>();
		try {
			ships = shipService.list((String)session.getAttribute("user"));
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		String filename = "ships_"+ System.currentTimeMillis();
		String fileExtension = ".pdf";
		PDFRenderer renderer = new PDFRenderer(filename, "Listado de Barcos", getPdfTitles(), getPdfLines(ships), "");
		byte[] contents = renderer.render();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    headers.setContentDispositionFormData(filename + fileExtension, filename + fileExtension);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
	    return response;
	}
	
	private String getPdfTitles() {
		return String.format
				("%10s  " // ID
						+ "%-30s " // "Nombre",
						+ "%-15s " // "Bandera",
						+ "%15s " // "Codigo",
						+ "%20s " // "Año Manufactura",
						+ "%20s " // "Cant. Tripulacion",
						+ "%15s" // "Capacidad"
						, "Id", "Nombre", "Bandera", "Codigo", "Año Manufactura",
						"Cant. Tripulacion", "Capacidad");
	}
	
	private List<String> getPdfLines(List<Ship> ships) {
		List<String> lines = new ArrayList<>();
		for (int i = 0; i < ships.size(); i++) {
			Ship ship = ships.get(i);
			lines.add(String.format("%10d  " // ID
					+ "%-30s " // "Nombre",
					+ "%-15s " // "Bandera",
					+ "%15d " // "Codigo",
					+ "%20d " // "Año Manufactura",
					+ "%20d " // "Cant. Tripulacion",
					+ "%15.2f" // "Capacidad"
			, ship.getId(), ship.getName(), ship.getFlag(), ship.getCode(),
					ship.getManufactoringYear(), ship.getCrewQuantity(),
					ship.getCapacity()));
		}
		return lines;
	}
	
}
