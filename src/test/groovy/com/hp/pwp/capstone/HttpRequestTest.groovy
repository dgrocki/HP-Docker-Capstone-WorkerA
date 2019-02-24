package com.hp.pwp.capstone

import groovy.mock.interceptor.MockFor

import org.junit.Test

public class HttpRequestTest extends GroovyTestCase {
    @Test
    void testBadURLGet() {
      HttpRequest test = new HttpRequest();
      String badURL = "This Is a Bad URL"
      String badURLGET = test.get(badURL);
      assert(badURLGET == "error: unable to open connection with " + badURL);
    }
    @Test 
    void testBadURLPOST() {
      HttpRequest test = new HttpRequest();
      String badURL = "This Is a Bad URL"
      String badURLGET = test.post(badURL, badURL);
      assert(badURLGET == "error: unable to open connection with " + badURL);
    }
}
