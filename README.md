# AdobeAPI
A simple file exploring and wall application

#Architecture 
The application is created considering model view controller design pattern in mind. 

Mongo cloud database has been used. The application will automatically connect itself into the cloud database. However, if one needs to access the database through mongo client or any utility such as mongo campus then can use the following url string. 

For mongo campus 1.12 and later
mongodb+srv://admin:<PASSWORD>@cluster0-1ng4z.mongodb.net/admin

For other versions of mongo campus. 
mongodb://admin:<PASSWORD>@cluster0-shard-00-00-1ng4z.mongodb.net:27017,cluster0-shard-00-01-1ng4z.mongodb.net:27017,cluster0-shard-00-02-1ng4z.mongodb.net:27017/admin?replicaSet=Cluster0-shard-0&ssl=true



Some ideas about the source code have been taken from the following sources: 
http://www.vogella.com/tutorials/REST/article.html
http://mongodb.github.io/mongo-java-driver/3.6/driver/getting-started/quick-start-pojo/
https://psamsotha.github.io/jersey/2015/10/18/http-caching-with-jersey.html#etagRevalidation


