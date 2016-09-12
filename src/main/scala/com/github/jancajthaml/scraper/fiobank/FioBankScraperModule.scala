package com.github.jancajthaml.scraper.fiobank

import akka.actor.{ActorRef, ActorSystem}
import akka.event.LoggingAdapter
import eu.akkamo
import eu.akkamo._
import eu.akkamo.mongo.{ReactiveMongoApi, ReactiveMongoModule}

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._

//import com.github.jancajthaml.number.Real

import scala.util.Try

class FioBankScraperModule extends akkamo.Module with akkamo.Initializable with akkamo.Runnable {

  override def dependencies(dependencies: Dependency): Dependency = dependencies
      .&&[akkamo.AkkaModule].&&[akkamo.LogModule].&&[ReactiveMongoModule]

  override def initialize(ctx: Context): Res[Context] = Try {
    // inject dependencies
    val log: LoggingAdapter = ctx.get[LoggingAdapterFactory] apply getClass

    log.info("Initializing FIO Bank Scraper module")

    ctx
  }

  override def run(ctx: Context): Res[Context] = Try {
    val mongo: ReactiveMongoApi = ctx.get[ReactiveMongoApi](Keys.ServiceName)
    val system: ActorSystem = ctx.get[ActorSystem](Keys.ServiceName)

    implicit val eCtx: ExecutionContextExecutor = system.dispatcher

    // TODO load token from MongoDB instead on system environment
    val token: String = Option(System.getProperty("fio.token")).getOrElse(
      throw new IllegalArgumentException("Missing FIO token in system env 'fio.token'")
    )
    val scraperActor: ActorRef = system.actorOf(ScraperActor.props(token))
    system.scheduler.schedule(5.seconds, 30.seconds, scraperActor, ScraperActor.Fetch)

    ctx
  }
}