package mongo

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.commons.MongoDBObject
import org.bson.types.ObjectId
import org.json4s._
import org.json4s.ext.JodaTimeSerializers
import org.json4s.mongo._
import org.json4s.native.Serialization
import play.api.Logger

/**
 * Created by Gary Lo on 24/6/15.
 */
abstract class BaseMongoCollection[T <: MongoDBObjectId](collectionName: String, mongoDAO: MongoDAO) extends MongoObjectHelper[T]{
  protected implicit val formats: org.json4s.Formats = standardMongoSerializer

  protected val collection: MongoCollection = mongoDAO.db(collectionName)

  protected lazy val standardMongoSerializer: Formats =
    Serialization.formats(NoTypeHints) +
      new ObjectIdSerializer +
      new UUIDSerializer +
      new DateSerializer +
      new PatternSerializer

  protected lazy val jodaTimeSerializers: List[Serializer[_]] = JodaTimeSerializers.all

  def findOne(query: DBObject, fields: Option[MongoDBObject] = None)(implicit m: Manifest[T]): Option[T] = {
    Logger.debug(s"db.${this.collectionName}.findOne(query = ${query.toString})")
    val dbOpt = fields match {
      case Some(f) =>
        collection.findOne(o = query, fields = f)
      case None =>
        collection.findOne(o = query)
    }

    Logger.debug(s"Result: ${dbOpt.toString}")

    dbOpt.map{ o =>
      dbObj2Obj(o)
    }
  }

  def insert(o: T)(implicit m: Manifest[T]): Boolean = {

    Logger.debug(s"db.${this.collectionName}.insert($o)")

    collection.insert(obj2DbObj(o)).wasAcknowledged()
  }

  def upsert(o: T)(implicit m: Manifest[T]): Option[T] = {

    Logger.debug(s"db.${this.collectionName}.upsert($o)")

    collection.findAndModify(
      query = MongoDBObject("id" -> o._id),
      update = obj2DbObj(o),
      fields = MongoDBObject.empty,
      sort = MongoDBObject.empty,
      remove = false,
      returnNew = true,
      upsert = true
    ).map(dbObj2Obj)
  }


  def findAll()(implicit m: Manifest[T]): List[T] = {
    Logger.debug(s"db.${this.collectionName}.findAll()")

    collection.find().map(dbObj2Obj).toList
  }

  def findById(_id: ObjectId)(implicit m: Manifest[T]): Option[T] = {
    Logger.debug(s"db.${this.collectionName}.findById(${_id.toString})")

    collection.findOne(MongoDBObject("_id" -> _id)).map(dbObj2Obj)
  }

}

trait MongoDBObjectId {
  val _id: Option[ObjectId]
}