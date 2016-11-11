package com.estrolo.scrapper.service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by sivag on 11/11/16.
 */
@Path("/products")
public class ProductListingAPI {

    private static final DBManager dbManager = new DBManager();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{queryId}")
    public List<ProductInfo> getProudcts(@PathParam("queryId") int queryId) {
        return dbManager.getProducts(queryId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void insertProducts(List<ProductInfo> list) {
        dbManager.insertProducts(list);
    }
}
