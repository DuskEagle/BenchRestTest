package com.duskeagle.resttest

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.ws.ahc.StandaloneAhcWSClient

object WSClient {

  private implicit val system = ActorSystem()
  private implicit val materializer = ActorMaterializer()

  val ws = StandaloneAhcWSClient()

  /**
    * This must be called before the application will be able to exit.
    */
  def shutdown(): Unit = {
    ws.close()
    system.terminate()
  }

}