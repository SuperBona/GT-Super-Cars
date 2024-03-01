# GT-Super-Cars
Done with Java, Hibernate, Spring Boot, and  SQL, it simulates the management of a car/bikes dealership. Future small changes can be done.

 <h2> IMPORTANT </h2>
 The project need to be connected to a local database (I created it in phpMyAdmin), so it's important to have a database where 
 you can run and test the website. <br>You can copy the database name from the "src/main/resources/application.properties" (open with Generic Editor -  Spring properties) and use it in your phpMyAdmin to create the database or change the name in the file and use the one you have choosen. 
 <br>(spring.datasource.url=jdbc:mysql://localhost:3306/concessionariafinale)<br>
 Then, run the project with the database opened and you'll se the creation of the tables and the content. To open the website page, use this link:
 "http://localhost:8080/login" and you'll be redirected to the login page. You have to access with "admin" as username and "admin" as password too.<br>
 You can change them in the configuration package in "src/main/java", in the file SpringSecurityConfig, from the Bean UserDetailsService.
<br><br>
 In the project I have implemented various functionalities that perform operations on both the database and the website, such as adding, removing,  modifying data, filtering vehicles according to user preferences, selling and buying, renting vehicles, etc. 
<br>
 I also have managed the data flow between a user table and a vehicle table, or on the single tables, using Sql queries
 and Hibernate annotations. In addition, I have used extensions such as Spring Security to enable logging in to the website as an admin.

![Cattura](https://github.com/SuperBona/GT-Super-Cars/assets/122936032/e8c06015-92d0-4eae-b6a7-8e5a098e32ff)
