import model.UserHandler
import model.UserHandler._
import akka.actor.{ActorRef, ActorSystem}
import akka.event.{Logging, LoggingAdapter}
import akka.stream.{ActorMaterializer, Materializer}
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes.{InternalServerError, NoContent, OK}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import model.mapping
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.StdIn

import Repository.ConcreteRedis
import redis.RedisClient


trait UserJson  extends SprayJsonSupport  with DefaultJsonProtocol {

  implicit val usr = jsonFormat3(User)
  implicit val Updater = jsonFormat2(Update)
  implicit val deleteU = jsonFormat1(deleteUser)
  implicit val  getU = jsonFormat1(getUser)

}


trait UserService extends UserJson with mapping // UserService Handler
{
   implicit val system: ActorSystem

   implicit def executor: ExecutionContextExecutor

   implicit val materializer: Materializer



   val route : Route = {
      pathPrefix("zeta") {
       path("Users")
        {
          get {
            complete(listbuf.toList)
          }
        }~
         path("User") {
            post {
               entity(as[User]) { usr =>
                  //User(usr.id, usr.name, usr.amount)
                 MAMT += (usr.id -> usr.amount)
                 MName += (usr.id -> usr.name)
                 listbuf += User(usr.id,usr.name,usr.amount)
                  complete("User Created")
               }
            } ~
              delete {
                 entity(as[deleteUser]){ x =>
                  // deleteUser(x.id)
                   var pre = MAMT.getOrElse(x.id, default = -1)

                   if (pre > 0) {
                     MAMT = MAMT.-(x.id)
                     MName = MName.-(x.id)
                     complete("User Deleted")
                   }
                   else
                     complete("User Not Found")
                 }
              } ~
              get {
                      entity(as[getUser]) { x =>
                         var pre = MAMT.getOrElse(x.id,default = -1)
                         var npre = MName.getOrElse(x.id,default = "none")
                        if(pre>0)
                            complete(s"User Name is ${npre} and Amount is ${pre}")
                        else
                          complete("User Not Found")
                      }
         } ~
           put {
              entity(as[Update]) { x =>

                var pre = MAMT.getOrElse(x.id,default = -1)

                if(pre>0)
                  {
                 //Update(x.id, x.amount)
                 MAMT += (x.id -> x.amount)
                 complete("User details Updated")
                  }
                else
                  complete("User Not Found")
              }
           }


        }
      }
   }


}




object Server2 extends App with UserService
{

     implicit val system = ActorSystem()
     implicit val executor = system.dispatcher
     implicit val materializer = ActorMaterializer()



   println("Server is working on http://localhost:9090")

  val bindingFuture = Http().bindAndHandle(route,"0.0.0.0",9090)


}