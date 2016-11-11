package com.estrolo.scrapper.service;


import com.estrolo.scrapper.AppConfig;
import com.estrolo.scrapper.ServiceMain;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created by sivag on 11/11/16.
 */
public class DBManager {
    static final String driver = "com.mysql.jdbc.Driver";
    static private Connection conn = null;
    private Statement stmt = null;
    static Logger logger = Logger.getLogger(DBManager.class);
    final static Properties prop = new Properties();

    public static Connection getConnection() {
        if (conn == null) {
            try {
                InputStream input = ServiceMain.class.getClassLoader().getResourceAsStream("src/main/application.properties");
                prop.load(input);
                Class.forName(driver);
                conn = DriverManager.getConnection(AppConfig.props.get("url"), AppConfig.props.get("username"),AppConfig.props.get("password"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public List<ProductInfo> getProducts(int id) {

        ArrayList<ProductInfo> list = new ArrayList<ProductInfo>();
        try {
            PreparedStatement stmt = conn.prepareStatement("select * from product_info where search_query_id=?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProductInfo p = new ProductInfo();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertProducts(List<ProductInfo> list) {
        try {

            Iterator<ProductInfo> ir = list.iterator();
            ProductInfo pi = null;
            String sql = "insert into product_info(name,price,search_query_id) ";
            String values = "";
            String query = "";
            Statement stmt = getConnection().createStatement();
            while (ir.hasNext()) {
                pi = ir.next();
                query = sql + " values ('" + pi.getName() + "'," + pi.getPrice() + "," + pi.getSearchQueryId() + " )";
                logger.info("Debug stmt " + query);
                stmt.addBatch(query);
            }
            stmt.executeBatch();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int insertSearchTag(String searchStr){
        int searchQueryID = 0;

        try {
            PreparedStatement statement = getConnection().prepareStatement("insert into products.search_quries(query) values(" + searchStr + ")",Statement.RETURN_GENERATED_KEYS);
            int rows =  statement.executeUpdate();
            if(rows == 0){
                logger.info("Failed to insert");
            }
            else{
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    searchQueryID = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return searchQueryID;
    }
}