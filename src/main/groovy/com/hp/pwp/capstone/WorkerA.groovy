package com.hp.pwp.capstone

public class Convert {

	public static void main(String [] args) {
		String json = 
			"{"
				+"'path': './Json/Pages',"
				+"'WID': 1019,"
				+"'JID': 1109,"
				+"'jpages':9"
			+"}";
		JsontoJava data = new Gson().fromJson(json, JsontoJava.class);

		System.out.println(data);
    }

}

class JsontoJava {
	private String path;
	private int WID;
	private int JID;
	private int jpages;

	public String getPath() {return path};
	public int getWID() {return WID};
	public int getJID() {return JID};
	public int getJpages() {return jpages};

	public void setPath(String path) {this.path = path};
	public void setWID(int WID) {this.WID = WID};
	public void setJID(int JID) {this.JID = JID};
	public void setJPages(int jpages) {this.jpages = jpages};


}