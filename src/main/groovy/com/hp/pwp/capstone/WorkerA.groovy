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

		def parser = new JsonSlurper();
		InputPDF input = new InputPDF();
		String json = "";

		HttpRequest httpClient = new HttpRequest();
		EventClient eventClient = new EventClient(input);
		// Connect to websocket.				
		eventClient.connect();

		while(1){	
			//Get Work from workManager.
			json = httpClient.get("http://localhost:8080/workManager/getWorkA");
			if (json != null) {
				println json;

				//Let the workmanager know we are ready for work.
				eventClient.ready();

				
				//Convert our JSON to JAVA.
				def data = parser.parseText(json)

				

				//Load in the input pdf.
				data.status = input.getPages(data.startPage, data.endPage, data.path, data.status);

				Gson gson = new Gson()
				String jsonString = gson.toJson(data)
				System.out.println(jsonString)

				//Post status to workManager.
				println httpClient.post("http://localhost:8080/workManager/postWork/",jsonString);
			} 
		}
	}
}


//Function to handle the input to pdfs.
class InputPDF {
	// List of strings to be added to the pdf.
	private List<String> quotes;

	public InputPDF() {
		quotes = new ArrayList<String>()
	}
	
	// Member function used to add new quote to the array of quotes.
	public void setQuotes(String[] newQuotes) {
		quotes = new ArrayList<String>()
		for (String quote : newQuotes) {
			quotes.add(quote);
		}
	}

	public int getPages(int start, int finish, String path, int status){
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
				try{
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
							status = 200;
					} 
				}catch(Exception e) {
					System.out.println("An Error Occured.")
					status = 500
					return status;
				}
		//Saving and closing the document
		document.save(new File(path))
		document.close()
		return status;
	}
}
