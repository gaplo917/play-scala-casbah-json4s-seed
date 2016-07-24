import collections.{TestCollection, TestUser}
import com.mongodb.casbah.commons.MongoDBObject
import org.scalatestplus.play.{PlaySpec, _}
import play.api.Logger

/**
  * Created by Gaplo917 on 23/7/2016.
  */
class TestCollectionSpec extends PlaySpec with OneAppPerTest {


  "TestCollection" should {
    "insert" in {
      val collection = app.injector.instanceOf[TestCollection]
      collection.insert(TestUser(name = "123")) mustBe true

      collection.insert(
        TestUser(
          name = "123",
          friends = List(
            TestUser(name = "friend1"),
            TestUser(name = "friend2"),
            TestUser(name = "friend3"),
            TestUser(name = "friend4")
          )
        )
      ) mustBe true

    }

    "upsert" in {
      val collection = app.injector.instanceOf[TestCollection]
      val obj = TestUser(name = "123")

      val upsertedObj = collection.upsert(obj)

      // must be exist
      upsertedObj.isDefined mustBe true

      // mongodb should generated a new _id  for this object
      upsertedObj.get._id.isDefined mustBe true
    }

    "findAll" in {
      val collection = app.injector.instanceOf[TestCollection]

     println(collection.findAll())

    }

    "findOne" in {
      val collection = app.injector.instanceOf[TestCollection]

      val result = collection.findOne(query = MongoDBObject("name" -> "123"))

      result.isDefined mustBe true
      result.get.name mustBe "123"
    }

    "insert & findOne" in {
      val collection = app.injector.instanceOf[TestCollection]
      val upserted = collection.upsert(TestUser(name = "123"))

      upserted.flatMap(_._id) match {
        case Some(_id) =>
          val foundOpt = collection.findOne(MongoDBObject("_id" -> _id))

          Logger.debug(foundOpt.toString)

          foundOpt.isDefined mustBe true

        case None =>
          fail("No _id is generated ")
      }


    }

  }

}
