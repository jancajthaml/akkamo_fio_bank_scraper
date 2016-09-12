package com.github.jancajthaml.scraper.fiobank

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import play.api.libs.json.JsValue

import scala.concurrent.{ExecutionContextExecutor, Future}

class ScraperActor(token: String) extends Actor with ActorLogging with PlayJsonSupport {

  private implicit val as: ActorSystem = context.system
  private implicit val eCtx: ExecutionContextExecutor = as.dispatcher
  private implicit val materializer = ActorMaterializer()

  // TODO parametrize hardcoded date parameters
  private val transactionsUrl: String = s"https://www.fio.cz/ib_api/rest/by-id/$token/2016/1/transactions.json"

  import ScraperActor.Fetch

  override def receive: Receive = {
    case Fetch =>
      log.info("Performing fetch operation...")
      Http().singleRequest(HttpRequest(
        method = HttpMethods.GET,
        uri = transactionsUrl
      )) flatMap processResponse
  }

  private def processResponse(response: HttpResponse): Future[Unit] = {
    Unmarshal(response.entity).to[JsValue] map { json =>
      // TODO handle JSON response
      println("RESPONSE RECEIVED: " + json)
    }

    Future.successful()
  }
}

object ScraperActor {
  def props(token: String): Props = Props(new ScraperActor(token))

  case object Fetch

}
