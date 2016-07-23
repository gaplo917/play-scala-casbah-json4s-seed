package collections

import com.google.inject.{Inject, Singleton}
import mongo.{BaseMongoCollection, MongoDAO, MongoDBObjectId}
import org.bson.types.ObjectId

/**
  * Created by Gaplo917 on 23/7/2016.
  */

case class TestNested(address: String)

case class Test(
                 _id: Option[ObjectId] = None, // mongodb will auto-generate the _id by default
                 name: String, // required
                 nickName: Option[String] = None, // if application-wise optional => should use option Type and default value = None
                 nested: TestNested = TestNested(address = "address"), // can nested object
                 arrayObjs: List[TestNested] = List()
               ) extends MongoDBObjectId

@Singleton
class TestCollection @Inject() (mongoDAO: MongoDAO)
  extends BaseMongoCollection[Test](collectionName = "test", mongoDAO) {

}