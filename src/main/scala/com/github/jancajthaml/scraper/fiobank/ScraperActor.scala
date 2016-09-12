package com.github.jancajthaml.scraper.fiobank

import akka.actor.{Actor, ActorLogging, Props}

class ScraperActor extends Actor with ActorLogging {

  import ScraperActor.Fetch

  override def receive: Receive = {
    case Fetch =>
      log.info("Performing fetch operation...")
  }
}

object ScraperActor {
  def props: Props = Props(new ScraperActor())

  case object Fetch

}
