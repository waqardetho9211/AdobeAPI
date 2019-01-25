# AdobeAPI
A simple file exploring and a wall application

## How To Run
The app is made using Java and tested using JDK 1.8. One can form a jar file using this source code by using any build tool. In order to make a connection with the Mongo database one needs to make changes in the dbos in persistence package and provide the password of the Mongo DB At the right place.

## Architecture
The application is created considering model view controller design pattern in mind. 

The Mongo cloud database has been used. The application will automatically connect itself into the cloud database. However, if one needs to access the database through Mongo client or any utility such as Mongo campus then can use the following URL string.

For Mongo campus 1.12 and later use this connection string:
mongodb+srv://admin:<PASSWORD>@cluster0-1ng4z.mongodb.net/admin

For other versions of Mongo campus:
mongodb://admin:<PASSWORD>@cluster0-shard-00-00-1ng4z.mongodb.net:27017,cluster0-shard-00-01-1ng4z.mongodb.net:27017,cluster0-shard-00-02-1ng4z.mongodb.net:27017/admin?replicaSet=Cluster0-shard-0&ssl=true

## Database
The application automatically connects itself to a cloud Mongo database provided the connection password is replaced with "password" field.

## Testing
The application can be tested using JUnit. All the tests present in test package have only written using JUnit.

## References
Some ideas about the source code have been taken from the following sources: 
http://www.vogella.com/tutorials/REST/article.html
http://mongodb.github.io/mongo-java-driver/3.6/driver/getting-started/quick-start-pojo/
https://psamsotha.github.io/jersey/2015/10/18/http-caching-with-jersey.html#etagRevalidation


