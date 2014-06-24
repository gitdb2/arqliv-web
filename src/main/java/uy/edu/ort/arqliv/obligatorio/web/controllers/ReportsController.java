package uy.edu.ort.arqliv.obligatorio.web.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uy.edu.ort.arqliv.obligatorio.common.ReportsService;
import uy.edu.ort.arqliv.obligatorio.common.ShipService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;
import uy.edu.ort.arqliv.obligatorio.dominio.Departure;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;
import uy.edu.ort.arqliv.obligatorio.web.controllers.models.ReportsWrapper;
import uy.edu.ort.arqliv.obligatorio.web.pdf.PDFRenderer;

/**
 * Controller para atender las paginas que esten relacionadas con los barcos
 * @author rodrigo
 *
 */
@Controller
@RequestMapping(value = "/reports")
public class ReportsController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);
	
	private SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private ReportsService reportsService;
	
	@Autowired
	private ShipService shipService;
	
	@RequestMapping(value = "/arrivalsbymonth", method = RequestMethod.GET)
	public String arrivalsByMonth(Model model, HttpSession session, @RequestParam(value="month", required=false) Integer month) {
		List<Arrival> arrivals = new ArrayList<Arrival>();
		ReportsWrapper reportsWrapper = new ReportsWrapper();
		if (month != null) {
			try {
				arrivals = reportsService.arrivalsByMonth((String)session.getAttribute("user"), month);
				reportsWrapper.setMonth(month);
			} catch (CustomServiceException e) {
				logger.error("Error al consultar al servicio", e);
			}
		} else {
			reportsWrapper.setMonth(1);
		}
		reportsWrapper.setArrivals(arrivals);
		model.addAttribute("reportsWrapper", reportsWrapper);
		model.addAttribute("months", getMonths());
		return "reports/arrivalsbymonth";
	}
	
	@RequestMapping(value = "/getPdfArrivalsByMonth", method =  { RequestMethod.GET, RequestMethod.POST } )
	public ResponseEntity<byte[]> postPDFArrivalsByMonth(Model model, HttpSession session, @RequestParam(value="month", required=false) Integer month) {
		List<Arrival> arrivals = new ArrayList<Arrival>();
		if (month != null) {
			try {
				arrivals = reportsService.arrivalsByMonth((String)session.getAttribute("user"), month);
			} catch (CustomServiceException e) {
				logger.error("Error al consultar al servicio", e);
			}
			String filename = "reports_arrival_by_month_"+ System.currentTimeMillis();
			String fileExtension = ".pdf";
			PDFRenderer renderer = new PDFRenderer(filename, "Arribos por mes", getPdfTitlesArrival(), getPdfLinesArrival(arrivals), "");
			byte[] contents = renderer.render();
		    ResponseEntity<byte[]> response = createResponse(filename, fileExtension, contents);
		    return response;
		}
		return null;
	}
	
	@RequestMapping(value = "/arrivalsbymonthbyship", method = RequestMethod.GET)
	public String arrivalsByMonthByShip(Model model, HttpSession session, 
			@RequestParam(value="month", required=false) Integer month,
			@RequestParam(value="ship", required=false) Long ship) {
		List<Arrival> arrivals = new ArrayList<Arrival>();
		ReportsWrapper reportsWrapper = new ReportsWrapper();
		if (month != null && ship != null) {
			try {
				arrivals = reportsService.arrivalsByMonthByShip((String)session.getAttribute("user"), month, ship);
				reportsWrapper.setMonth(month);
				reportsWrapper.setShip(ship);
			} catch (CustomServiceException e) {
				logger.error("Error al consultar al servicio", e);
			}
		} else {
			reportsWrapper.setMonth(1);
		}
		reportsWrapper.setArrivals(arrivals);
		model.addAttribute("reportsWrapper", reportsWrapper);
		model.addAttribute("months", getMonths());
		model.addAttribute("ships", getShips((String)session.getAttribute("user")));
		return "reports/arrivalsbymonthbyship";
	}
	
	@RequestMapping(value = "/getPdfArrivalsByMonthByShip", method =  { RequestMethod.GET, RequestMethod.POST } )
	public ResponseEntity<byte[]> postPDFArrivalsByMonthByShip(Model model, HttpSession session, 
			@RequestParam(value="month", required=false) Integer month, 
			@RequestParam(value="ship", required=false) Long ship) {
		List<Arrival> arrivals = new ArrayList<Arrival>();
		if (month != null) {
			try {
				arrivals = reportsService.arrivalsByMonthByShip((String)session.getAttribute("user"), month, ship);
			} catch (CustomServiceException e) {
				logger.error("Error al consultar al servicio", e);
			}
			String filename = "reports_arrival_by_month_by_ship_"+ System.currentTimeMillis();
			String fileExtension = ".pdf";
			PDFRenderer renderer = new PDFRenderer(filename, "Arribos por mes por Barco", getPdfTitlesArrival(), getPdfLinesArrival(arrivals), "");
			byte[] contents = renderer.render();
		    ResponseEntity<byte[]> response = createResponse(filename, fileExtension, contents);
		    return response;
		}
		return null;
	}
	
	@RequestMapping(value = "/departuresbymonth", method = RequestMethod.GET)
	public String departuresByMonth(Model model, HttpSession session, @RequestParam(value="month", required=false) Integer month) {
		List<Departure> departures = new ArrayList<Departure>();
		ReportsWrapper reportsWrapper = new ReportsWrapper();
		if (month != null) {
			try {
				departures = reportsService.departuresByMonth((String)session.getAttribute("user"), month);
				reportsWrapper.setMonth(month);
			} catch (CustomServiceException e) {
				logger.error("Error al consultar al servicio", e);
			}
		} else {
			reportsWrapper.setMonth(1);
		}
		reportsWrapper.setDepartures(departures);
		model.addAttribute("reportsWrapper", reportsWrapper);
		model.addAttribute("months", getMonths());
		return "reports/departuresbymonth";
	}
	
	@RequestMapping(value = "/departuresbymonthbyship", method = RequestMethod.GET)
	public String departuresByMonthByShip(Model model, HttpSession session, 
			@RequestParam(value="month", required=false) Integer month,
			@RequestParam(value="ship", required=false) Long ship) {
		List<Departure> departures = new ArrayList<Departure>();
		ReportsWrapper reportsWrapper = new ReportsWrapper();
		String user = (String)session.getAttribute("user");
		if (month != null && ship != null) {
			try {
				departures = reportsService.departuresByMonthByShip(user, month, ship);
				reportsWrapper.setMonth(month);
				reportsWrapper.setShip(ship);
			} catch (CustomServiceException e) {
				logger.error("Error al consultar al servicio", e);
			}
		} else {
			reportsWrapper.setMonth(1);
		}
		reportsWrapper.setDepartures(departures);
		model.addAttribute("reportsWrapper", reportsWrapper);
		model.addAttribute("months", getMonths());
		model.addAttribute("ships", getShips(user));
		return "reports/departuresbymonthbyship";
	}
	
	@RequestMapping(value = "/getPdfDeparturesByMonth", method =  { RequestMethod.GET, RequestMethod.POST } )
	public ResponseEntity<byte[]> postPDFDeparturesByMonth(Model model, HttpSession session, 
			@RequestParam(value="month", required=false) Integer month) {
		List<Departure> departures = new ArrayList<Departure>();
		if (month != null) {
			try {
				departures = reportsService.departuresByMonth((String)session.getAttribute("user"), month);
			} catch (CustomServiceException e) {
				logger.error("Error al consultar al servicio", e);
			}
			String filename = "reports_departures_by_month_"+ System.currentTimeMillis();
			String fileExtension = ".pdf";
			PDFRenderer renderer = new PDFRenderer(filename, "Partidas por mes", getPdfTitlesDeparture(), getPdfLinesDeparture(departures), "");
			byte[] contents = renderer.render();
		    ResponseEntity<byte[]> response = createResponse(filename, fileExtension, contents);
		    return response;
		}
		return null;
	}
	
	@RequestMapping(value = "/getPdfDeparturesByMonthByShip", method =  { RequestMethod.GET, RequestMethod.POST } )
	public ResponseEntity<byte[]> postPDFDeparturesByMonthByShip(Model model, HttpSession session, 
			@RequestParam(value="month", required=false) Integer month, 
			@RequestParam(value="ship", required=false) Long ship) {
		List<Departure> departures = new ArrayList<Departure>();
		if (month != null) {
			try {
				departures = reportsService.departuresByMonthByShip((String)session.getAttribute("user"), month, ship);
			} catch (CustomServiceException e) {
				logger.error("Error al consultar al servicio", e);
			}
			String filename = "reports_departures_by_month_by_ship_"+ System.currentTimeMillis();
			String fileExtension = ".pdf";
			PDFRenderer renderer = new PDFRenderer(filename, "Partidas por mes por Barco", getPdfTitlesDeparture(), getPdfLinesDeparture(departures), "");
			byte[] contents = renderer.render();
		    ResponseEntity<byte[]> response = createResponse(filename, fileExtension, contents);
		    return response;
		}
		return null;
	}
	
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String containerHome(Model model, HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		return "reports/menu";
	}
	
	private Map<Long, String> getShips(String user) {
		List<Ship> ships = new ArrayList<Ship>();
		Map<Long, String> result = new HashMap<Long, String>();
		try {
			ships = shipService.list(user);
			if (ships != null) {
				for (Ship s : ships) {
					result.put(s.getId(), "Id: " + s.getId() + ", Nombre:" + s.getName());
				}
			}
		} catch (CustomServiceException e) {
			logger.error("Error al consultar al servicio", e);
		}
		return result;
	}
	
	private ResponseEntity<byte[]> createResponse(String filename, String fileExtension, byte[] contents) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    headers.setContentDispositionFormData(filename + fileExtension, filename + fileExtension);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
		return response;
	}
	
	private String getPdfTitlesArrival() {
		return String.format("%10s  " // ID
				+ "%-15s  " // "Fecha de arribo",
				+ "%15s  " // "Id de barco",
				+ "%-15s  " // "Pais de Origen",
				+ "%-20s  " // "Ids contenedores"
				+ "%-20s " // "Desc. Contenedores"
				, "Id", "Fecha de arribo", "Id de barco"
				, "Pais de Origen",	"Ids contenedores", "Desc. Contenedores");
	}
	
	private String getPdfTitlesDeparture() {
		return String.format("%10s  " // ID
				+ "%-15s  " // "Fecha de partida",
				+ "%15s  " // "Id de barco",
				+ "%-15s  " // "Pais de Destino",
				+ "%-20s  " // "Contenedores"
				+ "%-20s " // "Descripcion de Contenedores"
				, "Id", "Fecha de partida", "Id de barco"
				, "Pais de Destino", "Ids contenedores", "Desc. Contenedores");
	}
	
	private List<String> getPdfLinesArrival(List<Arrival> arrivals) {
		List<String> lines = new ArrayList<>();
		for (Arrival arr : arrivals) {
			lines.add(String.format("%10d  " // ID
					+ "%-15s  " // "Fecha de arribo",
					+ "%15d  " // "Id de barco",
					+ "%-15s  " // "Pais de Origen",
					+ "%-20s  " // "Ids contenedores"
					+ "%-20s" // "Desc. Contenedores"
					, arr.getId()
					, sdfOut.format(arr.getArrivalDate())
					, arr.getShip().getId()
					, arr.getShipOrigin()
					, generateContainerList(arr.getContainers()).toString()
					, arr.getContainersDescriptions()));
		}
		return lines;
	}
	
	private List<String> getPdfLinesDeparture(List<Departure> departures) {
		List<String> lines = new ArrayList<>();
		for (Departure dep : departures) {
			lines.add(String.format("%10d  " // ID
					+ "%-15s  " // "Fecha de partida",
					+ "%15d  " // "Id de barco",
					+ "%-15s  " // "Destino del barco",
					+ "%-20s  " // "Contenedores"
					+ "%-20s" // "Descripcion de Contenedores"
					, dep.getId()
					, sdfOut.format(dep.getDepartureDate())
					, dep.getShip().getId()
					, dep.getShipDestination()
					, generateContainerList(dep.getContainers()).toString()
					, dep.getContainersDescriptions()));
		}
		return lines;
	}
	
	private List<Long> generateContainerList(List<Container> containers) {
		List<Long> ret = new ArrayList<>();
		if (containers != null) {
			for (Container container : containers) {
				ret.add(container.getId());
			}
		}
		return ret;
	}
	
	private Map<Integer, String> getMonths() {
		Map<Integer, String> months = new HashMap<Integer, String>();
		months.put(1, "Enero");
		months.put(2, "Febrero");
		months.put(3, "Marzo");
		months.put(4, "Abril");
		months.put(5, "Mayo");
		months.put(6, "Junio");
		months.put(7, "Julio");
		months.put(8, "Agosto");
		months.put(9, "Setiembre");
		months.put(10, "Octubre");
		months.put(11, "Noviembre");
		months.put(12, "Diciembre");
		return months;
	}
	
}
