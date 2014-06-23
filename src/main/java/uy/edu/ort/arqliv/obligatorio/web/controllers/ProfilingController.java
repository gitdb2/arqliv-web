package uy.edu.ort.arqliv.obligatorio.web.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uy.edu.ort.arqliv.obligatorio.common.ProfilingService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Pair;
import uy.edu.ort.arqliv.obligatorio.web.pdf.PDFRenderer;

/**
 * Controller para atender las paginas que esten relacionadas con los barcos
 * @author rodrigo
 *
 */
@Controller
@RequestMapping(value = "/profiling")
public class ProfilingController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProfilingController.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Autowired
	private ProfilingService profilingService;
	
	@RequestMapping(value = "/avg", method = RequestMethod.GET)
	public String avg(Model model, HttpSession session, @RequestParam(value="date", required=false) String dateString) {
		List<Pair<String, Double>> averages = new ArrayList<Pair<String, Double>>();
		Date forDate = tryParseDate(dateString);
		ProfilingWrapper profilingWrapper = new ProfilingWrapper();
		if (forDate != null) {
			try {
				averages = profilingService.avgServiceTime("rodrigo", forDate);
				profilingWrapper.setDate(dateString);
			} catch (CustomServiceException e) {
				logger.error("Error al consultar al servicio", e);
			}
		} else {
			profilingWrapper.setDate(sdf.format(new Date()));
		}
		profilingWrapper.setProfilingInfoAvg(averages);
		model.addAttribute("profilingWrapper", profilingWrapper);
		return "profiling/avg";
	}
	
	@RequestMapping(value = "/min", method = RequestMethod.GET)
	public String min(Model model, HttpSession session, @RequestParam(value="date", required=false) String dateString) {
		List<Pair<String, Long>> mins = new ArrayList<Pair<String, Long>>();
		Date forDate = tryParseDate(dateString);
		ProfilingWrapper profilingWrapper = new ProfilingWrapper();
		if (forDate != null) {
			try {
				mins = profilingService.minServiceTime("rodrigo", forDate);
				profilingWrapper.setDate(dateString);
			} catch (CustomServiceException e) {
				logger.error("Error al consultar al servicio", e);
			}
		} else {
			profilingWrapper.setDate(sdf.format(new Date()));
		}
		profilingWrapper.setProfilingInfoMinMax(mins);
		model.addAttribute("profilingWrapper", profilingWrapper);
		return "profiling/min";
	}
	
	@RequestMapping(value = "/max", method = RequestMethod.GET)
	public String max(Model model, HttpSession session, @RequestParam(value="date", required=false) String dateString) {
		List<Pair<String, Long>> maxs = new ArrayList<Pair<String, Long>>();
		Date forDate = tryParseDate(dateString);
		ProfilingWrapper profilingWrapper = new ProfilingWrapper();
		if (forDate != null) {
			try {
				maxs = profilingService.maxServiceTime("rodrigo", forDate);
				profilingWrapper.setDate(dateString);
			} catch (CustomServiceException e) {
				logger.error("Error al consultar al servicio", e);
			}
		} else {
			profilingWrapper.setDate(sdf.format(new Date()));
		}
		profilingWrapper.setProfilingInfoMinMax(maxs);
		model.addAttribute("profilingWrapper", profilingWrapper);
		return "profiling/max";
	}
	
	private Date tryParseDate(String toParse) {
		Date result;
		if (toParse == null)
			return null;
		try {
			result = sdf.parse(toParse);
		} catch (ParseException e) {
			result = null;
		} 
		return result;
	}
	
	@RequestMapping(value = "/getPdfAvg", method =  { RequestMethod.GET, RequestMethod.POST } )
	public ResponseEntity<byte[]> postPDFAvg(Model model, HttpSession session, @RequestParam(value="date", required=false) String dateString) {
		List<Pair<String, Double>> averages = new ArrayList<Pair<String, Double>>();
		Date forDate = tryParseDate(dateString);
		if (forDate != null) {
			try {
				averages = profilingService.avgServiceTime("rodrigo", forDate);
			} catch (CustomServiceException e) {
				e.printStackTrace();
			}
			String filename = "avg_"+ System.currentTimeMillis();
			String fileExtension = ".pdf";
			PDFRenderer renderer = new PDFRenderer(filename, "Tiempo promedio de servicios", getPdfTitlesAvg(), getPdfLinesAvg(averages), "");
			byte[] contents = renderer.render();
		    ResponseEntity<byte[]> response = createResponse(filename, fileExtension, contents);
		    return response;	
		}
		return null;
	}
	
	@RequestMapping(value = "/getPdfMin", method =  { RequestMethod.GET, RequestMethod.POST} )
	public ResponseEntity<byte[]> postPDFMin(Model model, HttpSession session, @RequestParam(value="date", required=false) String dateString) {
		List<Pair<String, Long>> mins = new ArrayList<Pair<String, Long>>();
		Date forDate = tryParseDate(dateString);
		if (forDate != null) {
			try {
				mins = profilingService.minServiceTime("rodrigo", new Date());
			} catch (CustomServiceException e) {
				e.printStackTrace();
			}
			String filename = "min_"+ System.currentTimeMillis();
			String fileExtension = ".pdf";
			PDFRenderer renderer = new PDFRenderer(filename, "Mínimo tiempo de servicios", getPdfTitlesMin(), getPdfLinesMinMax(mins), "");
			byte[] contents = renderer.render();
		    ResponseEntity<byte[]> response = createResponse(filename, fileExtension, contents);
		    return response;
		}
	    return null;
	}
	
	@RequestMapping(value = "/getPdfMax", method =  { RequestMethod.GET, RequestMethod.POST} )
	public ResponseEntity<byte[]> postPDFMax(Model model, HttpSession session, @RequestParam(value="date", required=false) String dateString) {
		List<Pair<String, Long>> maxs = new ArrayList<Pair<String, Long>>();
		Date forDate = tryParseDate(dateString);
		if (forDate != null) {
			try {
				maxs = profilingService.maxServiceTime("rodrigo", new Date());
			} catch (CustomServiceException e) {
				e.printStackTrace();
			}
			String filename = "max_"+ System.currentTimeMillis();
			String fileExtension = ".pdf";
			PDFRenderer renderer = new PDFRenderer(filename, "Máximo tiempo de servicios", getPdfTitlesMax(), getPdfLinesMinMax(maxs), "");
			byte[] contents = renderer.render();
		    ResponseEntity<byte[]> response = createResponse(filename, fileExtension, contents);
		    return response;
		}
		return null;
	}

	private ResponseEntity<byte[]> createResponse(String filename, String fileExtension, byte[] contents) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    headers.setContentDispositionFormData(filename + fileExtension, filename + fileExtension);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
		return response;
	}
	
	private String getPdfTitlesAvg() {
		return String.format("%-30s %-30s %-30s", "Servicio", "Tiempo promedio (nanosec)", "Tiempo promedio (milisec)");
	}
	
	private String getPdfTitlesMin() {
		return String.format("%-30s %-30s %-30s", "Servicio", "Tiempo minimo (nanosec)", "Tiempo minimo (milisec)");
	}
	
	private String getPdfTitlesMax() {
		return String.format("%-30s %-30s %-30s", "Servicio", "Tiempo maximo (nanosec)", "Tiempo maximo (milisec)");
	}
	
	private List<String> getPdfLinesAvg(List<Pair<String, Double>> averages) {
		List<String> lines = new ArrayList<>();
		for (Pair<String, Double> pair : averages) {
			lines.add(String.format("%-30s %-30.2f %-30.2f", pair.getKey(), pair.getValue(), pair.getValue() / 1000000.0));
		}
		return lines;
	}
	
	private List<String> getPdfLinesMinMax(List<Pair<String, Long>> minMax) {
		List<String> lines = new ArrayList<>();
		for (Pair<String, Long> pair : minMax) {
			lines.add(String.format("%-30s %-30d %-30d", pair.getKey(), pair.getValue(), pair.getValue() / 1000000));
		}
		return lines;
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String containerHome( Model model, HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		return "profiling/menu";
	}
	
	public static class ProfilingWrapper {
		
		@NotNull
		@NotEmpty
		private String date;
		
		List<Pair<String, Double>> profilingInfoAvg;
		
		List<Pair<String, Long>> profilingInfoMinMax;

		public ProfilingWrapper() {
			super();
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public List<Pair<String, Double>> getProfilingInfoAvg() {
			return profilingInfoAvg;
		}

		public void setProfilingInfoAvg(List<Pair<String, Double>> profilingInfoAvg) {
			this.profilingInfoAvg = profilingInfoAvg;
		}

		public List<Pair<String, Long>> getProfilingInfoMinMax() {
			return profilingInfoMinMax;
		}

		public void setProfilingInfoMinMax(List<Pair<String, Long>> profilingInfoMinMax) {
			this.profilingInfoMinMax = profilingInfoMinMax;
		}
		
	}
	
}
