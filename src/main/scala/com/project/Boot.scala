package com.project

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.project.service.RestService
import com.project.config.AppConfig._
import com.typesafe.scalalogging.StrictLogging
import nl.grons.metrics.scala.DefaultInstrumented
import com.codahale.metrics.ConsoleReporter

import scala.concurrent.duration._

object Boot extends StrictLogging with DefaultInstrumented {

  def main(args: Array[String]): Unit = {

    ConsoleReporter.forRegistry(metricRegistry)
      .convertRatesTo(TimeUnit.SECONDS)
      .convertDurationsTo(TimeUnit.MILLISECONDS)
      .build()
      .start(5, TimeUnit.MINUTES)

    implicit val system = ActorSystem()
    implicit val mat = ActorMaterializer()

    import system.dispatcher
    implicit val timeout: Timeout = 15.seconds

    val restService = new RestService()

    Http().bindAndHandle(restService.routes, serverHost, serverPort)
      .foreach(_ => logger.info(s"Server started at port $serverPort"))

  }
}
