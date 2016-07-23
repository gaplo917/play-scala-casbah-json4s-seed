package mongo

/**
 * Created by Gaplo917 on 9/8/15.
 */
import com.google.inject.{Inject, Singleton}
import com.mongodb.casbah.{MongoClient, MongoClientURI}
import play.api.Configuration
import play.api.Play._

@Singleton
class MongoDAO @Inject() (config: Configuration){
  val address = config.getString("mongo.address").getOrElse("localhost")
  val port = config.getInt("mongo.port").getOrElse(27017)
  val uri = MongoClientURI(s"mongodb://$address:$port/")
  val mongoClient =  MongoClient(uri)
  val db = mongoClient("play-mongo-db")
}