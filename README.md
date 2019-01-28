# AdobeAPI
A simple file exploring and a wall application

## How To Run
The app is made using Java and tested using JDK 1.8. One can form a war file using this source code by using any build tool. In order to make a connection with the Mongo database one needs to make changes in the application.properties in resource package and provide the password of the Mongo DB At the right place.

## Architecture
The application is created considering model view controller design pattern in mind. 

The Mongo cloud database has been used. The application will automatically connect itself into the cloud database. However, if one needs to access the database through Mongo client or any utility such as Mongo campus then can use the following URL string.

For Mongo campus 1.12 and later use this connection string:
```
mongodb+srv://admin:<PASSWORD>@cluster0-1ng4z.mongodb.net/admin
```
For other versions of Mongo campus:
```
mongodb://admin:<PASSWORD>@cluster0-shard-00-00-1ng4z.mongodb.net:27017,cluster0-shard-00-01-1ng4z.mongodb.net:27017,cluster0-shard-00-02-1ng4z.mongodb.net:27017/admin?replicaSet=Cluster0-shard-0&ssl=true
```
## Database
The application automatically connects itself to a cloud Mongo database provided the connection password is replaced with "password" field. In "application.properties" specifically at "application.uri"

## Testing
The application can be tested using JUnit. All the tests present in test package have only written using JUnit.

## Usage Example 
Find a resource at a path for example path is "main"
http://localhost:8080/AdobeAPI/rest/path?path=main
Result:
```
[{"location":"root/user1","name":"index.html","path":"main","type":"File"},{"location":"root/user1","name":"index.html","path":"main","type":"File"},{"location":"root/user1","name":"documents","path":"main","type":"Directory"}]
```
Find a resource example file name = "next_gen.html"
http://localhost:8080/AdobeAPI/rest/resource?name=next_gen.html
Result:
```
{"location":"root/user6","name":"next_gen.html","path":"main/html","type":"File"}
```
Find all resources in the system: 
http://localhost:8080/AdobeAPI/rest/resources
Result:
```
{multiple json}
```

Add a resource through UI:
http://localhost:8080/AdobeAPI/resource.html

Add a new comment on the wall through UI:
http://localhost:8080/AdobeAPI/

List all posted comments through UI: 
http://localhost:8080/AdobeAPI/rest/comment

## References
Some ideas about the source code have been taken from the following sources: 
- http://www.vogella.com/tutorials/REST/article.html
- http://mongodb.github.io/mongo-java-driver/3.6/driver/getting-started/quick-start-pojo/
- https://psamsotha.github.io/jersey/2015/10/18/http-caching-with-jersey.html#etagRevalidation
- https://psamsotha.github.io/jersey/2015/10/18/http-caching-with-jersey.html
