#Play Scala with casbah (MongoDB drive)

This is a seed project for whom is seeking a as close as native Mongo query syntax to access Mongo (Thanks for casbah) and auto map the document to scala defined classes.

* Using casbah for accessing MongoDB

* Using json4s to de/serialize the MongoDBObject to scala case class

* Convenient abstract class `BaseMongoCollection` with very basic API to access the document

#Example

Find the details examples of usages in the ./test/collections folder

Run the `TestCollectionSpec` test case to see the result (Please make sure your mongo is ready in localhost:27017, OR you can modify the `MongoDAO` class for other settings)



