# product-scrapping

It will scrape the products from specified e-commerce site based on search query. Results will be saved into mysql DB.

http://host:port/products/scrapper/interface, Provides simple UI to accept search query
http://host:port/products/scrapper ,Contains scrapping logic.
http://host:port/products with POST, 
Request to insert fetched product details to DB , End point contains logic.

http://host:port/products/{searchQueryID} with GET

searchQueryID

It's an auto-generated Id when search-query saved into table(Table name:search_quries)

Execution instructions.



1. sql to create table is under resource folder. Execute it to create tables.

2. Configure application configuration in AppConfig file.

3. Package file using mvn clean package

4. Run jar using java -jar /patt/to/generated.jar
 



