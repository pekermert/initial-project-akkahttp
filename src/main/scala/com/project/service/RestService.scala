package com.project.service

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.project.actor.DbActor
import com.project.actor.DbActor._
import com.project.model.Account
import com.typesafe.scalalogging.StrictLogging
import nl.grons.metrics.scala.DefaultInstrumented
import slick.driver.PostgresDriver.api._
//import spray.json._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class RestService(db: Database)(implicit ec: ExecutionContext, implicit val timeout: Timeout)
  extends SprayJsonSupport with DefaultInstrumented with StrictLogging {

  implicit val context = ActorSystem("user-service")
  implicit val executor = context.dispatcher
  implicit val materializer = ActorMaterializer()

  val dbActor = context.actorOf(Props(new DbActor(db)))

  def timer: Directive0 = {
    mapInnerRoute { inner => ctx =>
      val path = ctx.request.uri.path.toString.drop(1).replaceAll("/", ".")
      val verb = ctx.request.method.name
      metrics.timer(s"$path.$verb").time(inner(ctx))
    }
  }

  val statusRoute = pathPrefix("status") {
    pathEndOrSingleSlash {
      complete("OK")
    }
  }
//
//  val accountRegister = pathPrefix("register"){
//    post {
//      pathEndOrSingleSlash {
//        import com.project.model.AccountProtocol._
//
//        entity(as[Account]){ accountData =>
//          onComplete(dbActor ? registerAccount(accountData)){
//            case Success(res) =>
//              complete(s"Registiration Completed Successfuly ${res}")
//            case Failure(e) =>
//              logger.info(s"${e}")
//              complete(s"Failure: ${e.toString}")
//          }
//        }
//      }
//    }
//  }

  val routes = timer {
    statusRoute
//    statusRoute ~
//      pathPrefix("account"){
//        accountRegister
//      }
  }
}
