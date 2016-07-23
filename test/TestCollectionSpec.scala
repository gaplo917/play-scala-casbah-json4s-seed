import collections.{Test, TestCollection, TestNested}
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
      collection.insert(Test(name = "123")) mustBe true

      collection.insert(
        Test(
          name = "123",
          arrayObjs = List(
            TestNested(address = "1"),
            TestNested(address = "2"),
            TestNested(address = "3"),
            TestNested(address = "4")
          )
        )
      ) mustBe true

    }

    "upsert" in {
      val collection = app.injector.instanceOf[TestCollection]
      val obj = Test(name = "123")

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
      val upserted = collection.upsert(Test(name = "123"))

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
