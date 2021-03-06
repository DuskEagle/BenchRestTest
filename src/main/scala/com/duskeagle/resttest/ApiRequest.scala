package com.duskeagle.resttest

import play.api.libs.json.{JsError, JsSuccess, Json}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}
import com.typesafe.scalalogging.LazyLogging

class ApiRequest(endpointBase: String) extends LazyLogging {

  private val transactionsPerPage = 10

  val endpoint = endpointBase.stripSuffix("/") + "/%d.json"

  /**
    * Fetch all pages of transactions from the given endpoint.
    */
  def getTransactions(): List[Transaction] = {
    val firstPageAttempt = Await.result(getPage(1), Duration.Inf)
    firstPageAttempt match {
      case Success(firstPage) =>
        if (firstPage.totalCount <= transactionsPerPage) {
          firstPage.transactions
        } else {
          val allTransactions = firstPage.transactions ++
            Await.result(Future.sequence((2 to math.ceil(firstPage.totalCount/transactionsPerPage.toFloat).toInt).map { i =>
              getPage(i)
            }), Duration.Inf).flatMap { pageAttempt =>
              pageAttempt match {
                case Success(page) => page.transactions
                case Failure(ex) =>
                  logger.warn(ex.getMessage)
                  Nil
              }
            }
          if (allTransactions.length != firstPage.totalCount) {
            logger.warn(s"Received ${allTransactions.length} transactions but expected ${firstPage.totalCount} transactions")
          }
          allTransactions
        }
      case Failure(ex) =>
        // We can't recover if we failed to fetch or parse the first page.
        throw ex
    }
  }

  private def getPage(page: Int): Future[Try[PageResponse]] = {
    val url = endpoint.format(page)
    WSClient.ws.url(url).withFollowRedirects(true).get().map { response =>
      Try {
        if (response.status == 200)
          Json.fromJson[PageResponse](Json.parse(response.body)) match {
            case JsSuccess(pr: PageResponse, _) => pr
            case e: JsError => sys.error(e.toString)
          }
        else {
          sys.error(s"Received HTTP status code ${response.status} while contacting $url")
        }
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
