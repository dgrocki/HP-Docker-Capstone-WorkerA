package com.hp.pwp.capstone

import com.google.gson.Gson
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.Writer

public class Convert {
	public static void main(String [] args) {
		String json = "{" + "'path': './Json/Pages'," + "'WID': 1019," + "'JID': 1109," + "'jpages':9" + "}"
		JsontoJava data = new Gson().fromJson(json, JsontoJava.class)
		System.out.println("PathtoPDF:" + data.path + " WorkerID:" + data.WID  + " JobID:" + data.JID + " JobPages:" + data.jpages)

		Gson gson = new Gson()

		String jsonString = gson.toJson(data)
		System.out.println(jsonString)

		PrintWriter writer = new PrintWriter("./mnt/test.txt", "UTF-8");
		writer.println("The first line");
		writer.println("The second line");
		writer.close();
	}
}


class JsontoJava {
	private String path
	private int WID
	private int JID
	private int jpages

	public String getPath() {return path}
	public int getWID() {return WID}
	public int getJID() {return JID}
	public int getJpages() {return jpages}

	public void setPath(String path) {this.path = path}
	public void setWID(int WID) {this.WID = WID}
	public void setJID(int JID) {this.JID = JID}
	public void setJPages(int jpages) {this.jpages = jpages}
}