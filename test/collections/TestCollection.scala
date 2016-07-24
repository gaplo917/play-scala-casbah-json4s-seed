package collections

import java.util.UUID

import collections.UserStatus.UserStatus
import com.google.inject.{Inject, Singleton}
import mongo.{BaseMongoCollection, MongoDAO, MongoDBObjectId}
import org.bson.types.ObjectId
import org.joda.time.DateTime
import org.json4s.Formats
import org.json4s.ext.EnumNameSerializer

/**
  * Created by Gaplo917 on 23/7/2016.
  */

case class Address(address: String, country: String = "HK")

object UserStatus extends Enumeration {
  type UserStatus = Value
  val Active, Disabled = Value
}

case class TestUser(
                     _id: Option[ObjectId] = None, // mongodb will auto-generate the _id by default
                     name: String, // required
                     nickName: Option[String] = None, // if application-wise is optional => should use option Type and default value = None
                     address: Address = Address(address = "address"), // can nested object
                     friends: List[TestUser] = List(),
                     uuid: UUID = UUID.randomUUID(),
                     jodaDateTime: DateTime = new DateTime(),
                     status: UserStatus = UserStatus.Active
               ) extends MongoDBObjectId

@Singleton
class TestCollection @Inject() (mongoDAO: MongoDAO)
  extends BaseMongoCollection[TestUser](collectionName = "test", mongoDAO) {
  // add enum serializer
  override protected implicit val formats: Formats = standardMongoSerializer ++ jodaTimeSerializers + new EnumNameSerializer(UserStatus)
}
