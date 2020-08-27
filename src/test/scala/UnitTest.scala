import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.server._
import Directives._
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.HttpMethods
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.MediaTypes
import akka.util.ByteString
import org.scalatest.{FunSuite, Matchers, WordSpec, stats}
import org.scalatest.mock.MockitoSugar


class UnitTest extends WordSpec with UserService  with Matchers with ScalatestRouteTest with MockitoSugar {

// -------------------------------------Unit Test---------------------------------------

  // Create User Request test

  "Post Request " should {

    "create a user" in
      {
      val jsonRequest = ByteString(
        s"""
           |{
           |    "id":2,
           |    "name":"Raghav",
           |    "amount":23000
           |}
        """.stripMargin)
      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/zeta/User",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))

      postRequest ~> Route.seal(route) ~> check {
        status.isSuccess() shouldEqual true
        responseAs[String] shouldEqual "User Created"

      }
    }
  }

  // Delete user Request  Unit test

  "delete request " should {
    "delete a user if present" in {

      val jsonrequest2 = ByteString(
        s"""
           |{
           |   "id":5
           |}
           |""".stripMargin)

      val delrequest = HttpRequest(
        HttpMethods.DELETE,
        uri = "/zeta/User",
        entity = HttpEntity(MediaTypes.`application/json`, jsonrequest2))


      delrequest ~> route ~> check {
        status.isSuccess() shouldEqual true
        responseAs[String] shouldEqual "User Not Found"
      }
    }
  }
   // Update User Unit Request

  "Put request" should {

    "Update details of User if present" in {
      val jsonrequest3 = ByteString(
        s"""
           |{
           |   "id":5,
           |   "amount":120000
           |}
           |""".stripMargin)

      val updrequest = HttpRequest(
        HttpMethods.PUT,
        uri = "/zeta/User",
        entity = HttpEntity(MediaTypes.`application/json`, jsonrequest3))


      updrequest ~> route ~> check {
        status.isSuccess() shouldEqual true
        responseAs[String] shouldEqual "User Not Found"
      }
    }
  }


   // Get User Details Unit Test
"Get Request " should {
  "Get User details if present" in {
    val jsonrequest4 = ByteString(
      s"""
         |{
         |   "id":5
         |}
         |""".stripMargin)

    val getrequest = HttpRequest(
      HttpMethods.GET,
      uri = "/zeta/User",
      entity = HttpEntity(MediaTypes.`application/json`, jsonrequest4))


    getrequest ~> route ~> check {
      status.isSuccess() shouldEqual true
      responseAs[String] shouldEqual "User Not Found"
    }
  }
}

  // List of user id check

  "Get Request " should {
    "Get Users id details if present" in {

      val getrequest = HttpRequest(
        HttpMethods.GET,
        uri = "/zeta/Users")


      getrequest ~> route ~> check {
        status.isSuccess() shouldEqual true
      }
    }
  }

  // ------------------------------------  Functional test -----------------------------------------


  "Post request" should {
    "Create user" in{
  val jsonRequest = ByteString(
    s"""
       |{
       |    "id":2,
       |    "name":"Raghav",
       |    "amount":23000
       |}
        """.stripMargin)
  val postRequest = HttpRequest(
    HttpMethods.POST,
    uri = "/zeta/User",
    entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))

  postRequest ~> Route.seal(route) ~> check {
    status.isSuccess() shouldEqual true
    responseAs[String] shouldEqual "User Created"

  }}}




  "Get Request" should {
    "Get the details of the users" in {
      val jsonrequest5 = ByteString(
        s"""
           |{
           |   "id":2
           |}
           |""".stripMargin)

      val getrequest1 = HttpRequest(
        HttpMethods.GET,
        uri = "/zeta/User",
        entity = HttpEntity(MediaTypes.`application/json`, jsonrequest5))


      getrequest1 ~> route ~> check {
        status.isSuccess() shouldEqual true
        responseAs[String] shouldEqual "User Name is Raghav and Amount is 23000"
      }
    }
  }

  // Update Tests

  "Put request " should {
    "Update Users details" in {
      val jsonrequest6 = ByteString(
        s"""
           |{
           |   "id":2,
           |   "amount":120000
           |}
           |""".stripMargin)

      val updrequest1 = HttpRequest(
        HttpMethods.PUT,
        uri = "/zeta/User",
        entity = HttpEntity(MediaTypes.`application/json`, jsonrequest6))


      updrequest1 ~> route ~> check {
        status.isSuccess() shouldEqual true
        responseAs[String] shouldEqual "User details Updated"
      }
    }
  }
      // Delete request Functional test

  "Delete request" should {
   "Delete user  details"  in {
      val jsonrequest7 = ByteString(
        s"""
           |{
           |   "id":2
           |}
           |""".stripMargin)

      val delrequest1 = HttpRequest(
        HttpMethods.DELETE,
        uri = "/zeta/User",
        entity = HttpEntity(MediaTypes.`application/json`, jsonrequest7))


      delrequest1 ~> route ~> check {
        status.isSuccess() shouldEqual true
        responseAs[String] shouldEqual "User Deleted"
      }
    }
  }
}
