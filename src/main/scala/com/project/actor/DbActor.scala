package com.project.actor

import akka.actor.{Actor, ActorSystem}
import com.project.actor.DbActor._
import com.typesafe.scalalogging.StrictLogging
import slick.driver.PostgresDriver.api._
import akka.pattern.pipe

//import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext

class DbActor(db: Database)(implicit system: ActorSystem, implicit val ec: ExecutionContext) extends Actor with StrictLogging {

  override def receive: Receive = {

    case dbtest() =>
      ()

//    case registerAccount(account) =>
//      db.run(
//        DBIO.seq(
//          accountsQuery += account
//        )
//      ).pipeTo(sender)
//      ()
//
//    case updateAccountInfo(id, acc) =>
//      db.run(
//        accountsQuery.filter(_.id === id.bind)
//          .map(a => (a.name, a.surname, a.address, a.city))
//          .update((acc.name, acc.surname, acc.address, acc.city))
//      ).pipeTo(sender)
//      ()

  }

}

object DbActor{
  case class dbtest()
}