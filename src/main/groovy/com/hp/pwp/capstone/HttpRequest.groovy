package com.hp.pwp.capstone

public class HttpRequest {
  public static String get(String iURL) {
    // Create a new URL from the given input URL and open a connection with it.
    def get = new URL(iURL)
      .openConnection();
    // Get the response code from the given url
    def getRC = get.getResponseCode();
    println(getRC);
    if( getRC.equals(200)) {
      return get.getInputStream().getText();
    }
    else {
      return "error";
    }
  }

  public static String post(String iURL, String message) {
    def post = new URL(iURL)
      .openConnection();
    post.setRequestMethod("POST")
    post.setDoOutput(true)
    post.setRequestProperty("Content-Type", "application/json")
    post.getOutputStream()
      .write(message.getBytes("UTF-8"));
    def postRC = post.getResponseCode();
    println(postRC);
    if(postRC.equals(200)) {
        println(post.getInputStream().getText());
    }
  }
}