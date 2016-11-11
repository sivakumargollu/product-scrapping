package com.estrolo.scrapper.service;

import java.io.Serializable;

/**
 * Created by sivag on 11/11/16.
 */

public class ProductInfo implements Serializable {

    private int id;
    private String name;
    private Double price;
    private int searchQueryId;

    public ProductInfo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getSearchQueryId() {
        return searchQueryId;
    }

    public void setSearchQueryId(int searchQueryId) {
        this.searchQueryId = searchQueryId;
    }

    public ProductInfo(int id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
