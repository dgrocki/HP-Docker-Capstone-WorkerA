package com.hp.pwp.capstone

import com.google.gson.Gson
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.Writer  
import java.util.Random
import java.util.List
import java.util.ArrayList

import org.apache.pdfbox.pdmodel.PDDocument 
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.font.FileSystemFontProvider
import groovy.json.JsonSlurper



public class Convert {
	public static void main(String [] args) {

		HttpRequest httpClient = new HttpRequest();
		EventClient eventClient = new EventClient();
		def parser = new JsonSlurper();
		InputPDF input;
		String json = "";

		while(1){	
			// Reset input at the start of each loop;
			input = new InputPDF();

			//Get Work from workManager.
			json = httpClient.get("http://localhost:8080/workManager/getWorkA");
			if (json != null) {
				println json;

				for (int i=0; i<10; i++) {
					input.addQuote(quotes[i]);
				}
				*/
				
				// Connect to websocket.				
				eventClient.connect();
				
				//Convert our JSON to JAVA.
				def data = parser.parseText(json)

				

				//Load in the input pdf.
				input.getPages(data.startPage, data.endPage, data.path);

				//Post status to workManager.
				println httpClient.post("http://0.0.0.0:8080/workManager/postWork/",json);
			} 
		}
	}
}

//Functions that handle the JSON conversions
class JsontoJava {
	private String path
		private int WID
		private int JID
		private int startPage
		private int endPage

		public String getPath() {return path}
	public int getWID() {return WID}
	public int getJID() {return JID}
	public int getStart() {return startPage}
	public int getEnd() {return endPage}

	public void setPath(String path) {this.path = path}
	public void setWID(int WID) {this.WID = WID}
	public void setJID(int JID) {this.JID = JID}
	public void setStart(int startPage) {this.startPage = startPage}
	public void setEnd(int endPage) {this.endPage = endPage}
}

//Function to handle the input to pdfs.
class InputPDF {
	// List of strings to be added to the pdf.
	private List<String> quotes;

	public InputPDF() {
		quotes = new ArrayList<String>()
	}
	
	// Member function used to add new quote to the array of quotes.
	public void addQuote(String quote) {
		quotes.add(quote);
	}

	public void getPages(int start, int finish, String path){
		//This gets rid of all the warnings caused by not having fonts installed
		println "InputPDF"
		println path;
		println finish;
		println start;
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog")


				//Loading an existing document
				File file = new File(path)
				PDDocument document = PDDocument.load(file)
				start = start - 1
				finish = finish - 1
				//Inserting content on each page of the PDF.
				for (start;start<=finish;start++) {
					PDPage page = document.getPage(start)
						PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,true,true)
						contentStream.beginText()       
						//Styling for Content
						contentStream.setFont(PDType1Font.TIMES_ROMAN, 18)
						contentStream.newLineAtOffset(297, 25)
						//Inserting page numbers
						String pageNum = Integer.toString(start+1)
						contentStream.showText(pageNum)      
						contentStream.setFont(PDType1Font.TIMES_ROMAN, 12)
						contentStream.newLineAtOffset(-250, 600)
						contentStream.showText(quotes[start])      
						contentStream.endText()
						contentStream.close()
				}
		//Saving and closing the document
		document.save(new File(path))
			document.close()
	}
}
