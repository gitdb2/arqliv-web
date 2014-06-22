package uy.edu.ort.arqliv.obligatorio.web.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

/**
 * Clase encargada de generar los pdf y dejarlos en la ruta especificada.
 * 
 * @author rodrigo
 * 
 */
public class PDFRenderer {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	String outputfile;
	Phrase pageHeader;
	Phrase dataHeader;
	List<String> lines;
	Phrase footer;
	Font font;
	final float FONT_SIZE = (float) 7.2;

	public PDFRenderer(String outputfile, String pageHeader, String dataHeader, List<String> lines, String footer) {
		super();
		font = new Font();
		font.setSize(FONT_SIZE);
		try {
			BaseFont bf = BaseFont.createFont("resources/fonts/CONSOLA.TTF", BaseFont.WINANSI, BaseFont.EMBEDDED);
			font = new Font(bf, FONT_SIZE);
		} catch (IOException | DocumentException e) {	
			log.error("Problema al cargar la fuente, usando la por defecto",  e);
		}
		this.outputfile = outputfile;
		this.pageHeader = new Phrase(pageHeader, font);
		this.dataHeader = new Phrase(dataHeader, font);
		this.lines = lines;
		this.footer = new Phrase(footer, font);
	}

	public byte[] render() {
		try {
			File tempFile = File.createTempFile(this.outputfile,".pdf");
			
			Document document = new Document(PageSize.A4);
			PdfWriter.getInstance(document, new FileOutputStream(tempFile));
			document.open();

			document.add(new Paragraph(pageHeader));

			document.add(new LineSeparator(0.3f, 100, null, 0, -3));
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			
			document.add(dataHeader);
			document.add(Chunk.NEWLINE);
			for (String line : lines) {
				document.add(new Phrase(line, font));
				document.add(Chunk.NEWLINE);
			}
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);

			document.add(new LineSeparator(0.5f, 100, null, 0, -3));
			document.add(new Paragraph(footer));
			document.add(Chunk.NEWLINE);
			document.close();
			
			Path path = Paths.get(tempFile.getAbsolutePath());
			return Files.readAllBytes(path);
		} catch (FileNotFoundException e) {
			log.error("No se encontro el archivo salida", e);
			throw new RuntimeException("No se encontro el archivo salida", e);
		} catch (DocumentException e) {
			log.error("No se pudo generar el pdf", e);
			throw new RuntimeException("No se pudo generar el pdf", e);
		} catch (IOException e) {
			log.error("No se pudo generar el pdf", e);
			throw new RuntimeException("No se pudo generar el pdf", e);
		}
	}

}
