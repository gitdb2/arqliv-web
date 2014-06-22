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
	
	@RequestMapping(value = "/getPdfList", method =  { RequestMethod.GET, RequestMethod.POST} )
	public ResponseEntity<byte[]> postPDF(Locale locale, Model model) {
		List<Ship> ships = new ArrayList<>();
		try {
			ships = shipService.list("rodrigo");
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
