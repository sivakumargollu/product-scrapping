package com.estrolo.scrapper;

import java.util.HashMap;

/**
 * Created by sivag on 11/11/16.
 */
public class AppConfig {
    public static HashMap<String, String> props = new HashMap<String, String>();

    static void init() {
        props.put("site", "http://www.flipkart.com");// Need to change scrapping logic.If you change it to other site
        props.put("dbusername", "xxxx");//Modify
        props.put("dbpassword", "xxxx");//Modify
        props.put("db", "dbname");
        props.put("dburl", "url"); //Default url where db is running.
        props.put("baseUri", "http://localhost:7070");


    }
}
