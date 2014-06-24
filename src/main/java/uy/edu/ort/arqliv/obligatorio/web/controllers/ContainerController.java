package uy.edu.ort.arqliv.obligatorio.web.controllers;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

import uy.edu.ort.arqliv.obligatorio.common.ContainerService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;
import uy.edu.ort.arqliv.obligatorio.web.pdf.PDFRenderer;

/**
 * Controller para atender las paginas que esten relacionadas con los barcos
 * @author rodrigo
 *
 */
@Controller
@RequestMapping(value = "/containers")
public class ContainerController {
	
	private static final Logger logger = LoggerFactory.getLogger(ContainerController.class);

	@Autowired
	private ContainerService containerService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list( Model model, HttpSession session) {
		List<Container> containers = new ArrayList<>();
		try {
			containers = containerService.list((String)session.getAttribute("user"));
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		model.addAttribute("containers", containers);
		return "containers/list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String setupCreate( Model model) {
		model.addAttribute("container", new Container());
		return "containers/create";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String submitCreate(@Valid Container container, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
            return "containers/create";
        }
		try {
			containerService.store((String)session.getAttribute("user"), container);
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		return "redirect:/containers/list.html";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String setupDelete( Model model, @RequestParam("id") int id) {
		model.addAttribute("contId", id);
		return "containers/delete";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String submitDelete(@Valid int id, HttpSession session) {
		try {
			containerService.delete((String)session.getAttribute("user"), id);
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		return "redirect:/containers/list.html";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String setupEdit( Model model, @RequestParam("id") int id, HttpSession session) {
		Container container = null;
		boolean serviceError = false;
		try {
			container = containerService.find((String)session.getAttribute("user"), id);
		} catch (CustomServiceException e) {
			e.printStackTrace();
			serviceError = true;
		}
		if (container == null || serviceError) {
			return "redirect:/containers/list.html";
		}
		model.addAttribute("container", container);
		return "containers/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String submitEdit(@Valid Container container, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
            return "containers/edit";
        }
		try {
			containerService.update((String)session.getAttribute("user"), container);
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		return "redirect:/containers/list.html";
	}
	
	@RequestMapping(value = "/getPdfList", method =  { RequestMethod.GET, RequestMethod.POST} )
	public ResponseEntity<byte[]> postPDF( Model model, HttpSession session) {
		List<Container> containers = new ArrayList<>();
		try {
			containers = containerService.list((String)session.getAttribute("user"));
		} catch (CustomServiceException e) {
			e.printStackTrace();
		}
		String filename = "containers_"+ System.currentTimeMillis();
		String fileExtension = ".pdf";
		PDFRenderer renderer = new PDFRenderer(filename, "Listado de Contenedores", getPdfTitles(), getPdfLines(containers), "");
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
				+ "%15s  " // "Codigo",
				+ "%-30s " // "Marca",
				+ "%-30s " // "Modelo",
				+ "%15s" // "Capacidad"
		, "Id", "Codigo", "Marca", "Modelo", "Capacidad");
	}
	
	private List<String> getPdfLines(List<Container> containers) {
		List<String> lines = new ArrayList<>();
		for (int i = 0; i < containers.size(); i++) {
			Container container = containers.get(i);
			lines.add(String.format
					 ("%10d  " // ID
					+ "%15d  " // "Codigo",
					+ "%-30s " // "Marca",
					+ "%-30s " // "Modelo",
					+ "%15.2f" // "Capacidad"
			,
			container.getId(), container.getCode(), container.getBrand(), container.getModel(), container.getCapacity()));
		}
		return lines;
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String containerHome(Model model, HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		return "containers/menu";
	}
}
