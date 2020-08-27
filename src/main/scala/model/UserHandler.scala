package model
import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern.pipe

import scala.collection.mutable.ListBuffer



object UserHandler{
  case class User(id: Int , name: String,amount: Int)
  case class Update(id: Int,amount: Int)
  case class getUser(id: Int)
  case class deleteUser(id: Int)
}

trait mapping {
  import UserHandler._
  var MName : Map[Int,String] = Map()
  var MAMT : Map[Int,Int] = Map()
  var Mlist : Map[Int,User] = Map()
}
