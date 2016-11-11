package com.estrolo.scrapper;


import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


public class ServiceMain {
    static final Logger logger = Logger.getLogger(ServiceMain.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();//log4j config
        String baseUri = AppConfig.props.get("baseUri");
        baseUri = (baseUri != null) ? baseUri : "http://localhost:7070/";
        logger.info("base uri" + baseUri);
        final Map<String, String> initParams = new HashMap<String, String>();
        initParams.put("com.sun.jersey.config.property.packages", "com.estrolo.scrapper.service");
        try {
            GrizzlyWebContainerFactory.create(baseUri, initParams);
            logger.info(String.format("Service started at   " + baseUri));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
