package com.duskeagle.resttest

import java.time.LocalDate

import org.scalatest._
import play.api.libs.json.{JsError, JsSuccess, Json}

import scala.io.Source

class TransactionTest extends FlatSpec with Matchers {

  val testPageResponse = Source.fromURL(getClass.getResource("/testPageResponse.json"))

  behavior of "PageResponse"

  Json.fromJson[PageResponse](Json.parse(testPageResponse.mkString)) match {
    case JsSuccess(pr: PageResponse, _) =>
      it should "have three transactions" in {
        pr.transactions.length should be (3)
      }
      val first = pr.transactions(0)
      behavior of "first transaction"
      it should "parse the date correctly" in {
        first.date should be (LocalDate.parse("2013-12-22"))
      }
      it should "parse the amount correctly" in {
        first.amount should be (BigDecimal("-110.71"))
      }
      behavior of "TransactionManager"
      val transactionManager = new TransactionManager(pr.transactions)
      it should "calculate total balance" in {
        transactionManager.totalBalance should be (BigDecimal("19711.79"))
      }
      it should "calculate running balance" in {
        val runningBalance = transactionManager.runningBalance
        runningBalance.length should be (4)
        runningBalance(0)._1 should be (LocalDate.parse("2013-12-19"))
        runningBalance(0)._2 should be (BigDecimal("20000"))
        runningBalance(1)._1 should be (LocalDate.parse("2013-12-20"))
        runningBalance(1)._2 should be (BigDecimal("19822.5"))
        runningBalance(2)._1 should be (LocalDate.parse("2013-12-21"))
        runningBalance(2)._2 should be (BigDecimal("19822.5"))
        runningBalance(3)._1 should be (LocalDate.parse("2013-12-22"))
        runningBalance(3)._2 should be (BigDecimal("19711.79"))
      }

    case e: JsError => fail
  }

}
