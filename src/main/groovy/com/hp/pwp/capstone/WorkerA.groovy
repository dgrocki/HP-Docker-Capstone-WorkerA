package com.hp.pwp.capstone

import com.google.gson.Gson
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.Writer  
import java.util.Random

import org.apache.pdfbox.pdmodel.PDDocument 
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.font.FileSystemFontProvider


public class Convert {
	public static void main(String [] args) {
		//JSON string
		String json = "{" + "'jobBool': 'True'," + "'path': '/mnt/TestPDF.pdf'," + "'outPath':'/mnt/FinalPDF.pdf'," + "'pageLength': 9," + "'startPage':1," + "'endPage': 9," + "'WID': 1019," + "'JID': 1109," + "'status': 200" + "}"
		
		//Convert our JSON to JAVA
		JsontoJava data = new Gson().fromJson(json, JsontoJava.class)
		
		InputPDF input = new InputPDF()



		input.getPages(data.startPage, data.endPage, data.path)
		
		//Prints out our JSON now converted to JAVA
		/*
		System.out.println("PathtoPDF:" + data.path + " WorkerID:" + data.WID  + " JobID:" + data.JID + " startPage:" + data.startPage + " EndPage:" + data.endPage)
		*/

		//Converts our Java back to JSON
		/*
		Gson gson = new Gson()

		String jsonString = gson.toJson(data)
		System.out.println(jsonString)
		*/
		
	}
}

//Functions that handle the JSON conversions
class JsontoJava {
	private boolean jobBool
	private String path
	private String outPath
	private int pageLength
	private int startPage
	private int endPage
	private int WID
	private int JID
	private int status

	public boolean getJobBool() {return jobBool}
	public String getPath() {return path}
	public String getOutPath() {return outPath}
	public int getPageLength() {return pageLength}
	public int getStart() {return startPage}
	public int getEnd() {return endPage}
	public int getWID() {return WID}
	public int getJID() {return JID}
	public int getStatus() {return status}

	public void setJobBool(boolean jobBool) {this.jobBool = jobBool}
	public void setPath(String path) {this.path = path}
	public void setOutPath(String outpath) {this.outPath = outPath}
	public void setpageLength(int pageLength) {this.pageLength = pageLength}
	public void setStart(int startPage) {this.startPage = startPage}
	public void setEnd(int endPage) {this.endPage = endPage}
	public void setWID(int WID) {this.WID = WID}
	public void setJID(int JID) {this.JID = JID}
	public void setStatus(int status) {this.status = status}
}

//Function to handle the input to pdfs.
class InputPDF {
	public void getPages(int start, int finish, String path){
		//This gets rid of all the warnings caused by not having fonts installed
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog")
		
		//Random Computer Science Quotes I found Online 
		String[] quotes = [
		"Computing science is no more about computers than astronomy is about telescopes. -Edsger W. Dijkstra",
		"I don’t need to waste my time with a computer just because I am a computer scientist. -Edsger W. Dijkstra", 
		"The purpose of computing is insight, not numbers. -Richard Hamming",
		"An algorithm must be seen to be believed. -Donal Knuth",
		"Syntactic sugar causes cancer of the semicolon. -Alan J Perlis",
		"If a program manipulates a large amount of data, it does so in a small number of ways. -Alan J Perlis",
		"A good system can’t have a weak command language. -Alan J. Perlis",
		"Representation is the essence of programming. -Fred Brooks",
		"Elegance is not a dispensable luxury but a quality that decides between success and failure. -Edsger W. Dijkstra",
		"There is no programming language, no matter how structured, that will prevent programmers from writing bad programs. -Lawrence Flon"
		]

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

