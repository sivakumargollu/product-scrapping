package com.estrolo.scrapper.service;

import com.estrolo.scrapper.AppConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sivag on 11/11/16.
 */
@Path("products")
public class Scapper {

    static DBManager dbManager = new DBManager();
    static Wait<WebDriver> wait;


    @GET
    @Path("scrapper")
    public String scapeProducts(@QueryParam("searchQuery") String query, @QueryParam("maxCount") int maxCount) {

        WebDriver driver = performSearchAction(query);
        List<ProductInfo> list = new ArrayList<ProductInfo>();
        //Fetching the pagination elements.
        List<WebElement> paginationList = driver.findElements(By.className("_33m_Yg"));
        Iterator<WebElement> iterator = paginationList.iterator();
        int extractedProductCount = 0;
        //Inserting into db
        int searchQueryID = dbManager.insertSearchTag(query);

        while (iterator.hasNext()) {
            extractedProductCount = extractProductInformation(driver, list, searchQueryID);
            if (extractedProductCount >= maxCount) {
                break;
            }
            WebElement paginationElement = iterator.next();
            Actions actions = new Actions(driver);
            Actions nextPage = actions.moveToElement(paginationElement).click();
        }
        dbManager.insertProducts(list);
        String result = "Product details are saved!! \n You can view list at http://yourhost:port/products/" + searchQueryID;
        return result;
    }

    public WebDriver getDriver() {
        File f = new File("/usr/bin/google-chrome");
        System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
        WebDriver driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 59);
        driver.get(AppConfig.props.get("site"));
        return driver;
    }

    private WebDriver performSearchAction(String query) {
        WebDriver driver = getDriver();
        WebElement searchBox = driver.findElement(By.className("LM6RPg"));
        if (searchBox != null) {
            Actions builder = new Actions(driver);
            Actions searchAction = builder.moveToElement(searchBox).click().sendKeys(searchBox, query);
            searchAction.perform();
        }
        return driver;
    }

    private int extractProductInformation(WebDriver driver, List<ProductInfo> list, int searchQueryID) {
        int counter = 0;
        List<WebElement> mainDivsList = driver.findElements(By.className("zZCdz4"));//Returns DIV  that contains products information.
        Iterator<WebElement> it = mainDivsList.iterator();
        WebElement we;
        while (it.hasNext()) {
            we = it.next();
            ProductInfo pi = new ProductInfo();
            String name = we.findElement(By.className("3wU53n")).getText();
            String priceStr = we.findElement(By.className("_1uv9Cb")).findElement(By.className("_1vC4OE")).getText();
            pi.setName(name);
            pi.setPrice(Double.parseDouble(priceStr));
            pi.setSearchQueryId(searchQueryID);
            list.add(pi);
            counter++;
        }
        return counter;
    }

    @GET
    @Path("scrapper/interface")
    @Produces(MediaType.TEXT_HTML)
    public InputStream getInterface() {
        File f = new File("resources/SimpleUI.html");
        InputStream streamHtml = null;
        try {
            streamHtml = new FileInputStream(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return streamHtml;
    }
}
