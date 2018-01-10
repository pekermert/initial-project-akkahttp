package com.project.service

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.util.Timeout
import com.typesafe.scalalogging.StrictLogging
import nl.grons.metrics.scala.DefaultInstrumented

class RestService()( implicit val timeout: Timeout)
  extends SprayJsonSupport with DefaultInstrumented with StrictLogging {

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

  val routes = timer {
    statusRoute
  }
}
