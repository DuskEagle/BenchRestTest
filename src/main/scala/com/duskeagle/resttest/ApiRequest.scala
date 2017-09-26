package com.duskeagle.resttest

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.libs.ws.ahc.StandaloneAhcWSClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration

class ApiRequest(endpointBase: String) {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val endpoint = endpointBase.stripSuffix("/") + "/%d.json"
  val firstPageTimeout = Duration(1, scala.concurrent.duration.SECONDS)
  val timeout = Duration(5, scala.concurrent.duration.SECONDS)
  val ws = StandaloneAhcWSClient()

  def getTransactions(): List[Transaction] = {
    val firstPage = Await.result(getPage(1), firstPageTimeout)
    if (firstPage.totalCount <= 1) {
      firstPage.transactions
    } else {
      firstPage.transactions ++ Await.result(Future.sequence((2 to firstPage.totalCount).map { i =>
        getPage(i)
      }), timeout).flatMap { _.transactions }
    }
  }

  private def getPage(page: Int): Future[PageResponse] = {
    ws.url(endpoint.format(page)).withFollowRedirects(true).get().map { response =>
      Json.fromJson[PageResponse](Json.parse(response.body)) match {
        case JsSuccess(pr: PageResponse, _) => pr
        case e: JsError => sys.error(e.toString)
      }
    }
  }

}

object ApiRequest {
  def apply: ApiRequest = {
    new ApiRequest("http://resttest.bench.co/transactions/")
  }

  def apply(endpointBase: String): ApiRequest = {
    new ApiRequest(endpointBase)
  }

}
