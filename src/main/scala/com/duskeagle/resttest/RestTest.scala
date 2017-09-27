package com.duskeagle.resttest

import java.text.NumberFormat
import java.util.Locale

object RestTest {

  def printBalanceSummary(tm: TransactionManager): Unit = {
    val nf = NumberFormat.getCurrencyInstance(Locale.US)
    tm.runningBalance.foreach { case (date, balance) =>
      println(s"$date\t${nf.format(balance)}")
    }
    println(s"Total\t${nf.format(tm.totalBalance)}")
  }

  def main(args: Array[String]): Unit = {
    val apiRequest = args.toList match {
      case Nil => ApiRequest.apply
      case endpoint :: Nil => ApiRequest(endpoint)
      case _ =>
        System.err.println("Usage: ./resttest [endpoint]")
        System.err.println("where `endpoint` defaults to http://resttest.bench.co/transactions/")
        sys.exit(1)
    }
    printBalanceSummary(new TransactionManager(apiRequest.getTransactions()))
    WSClient.shutdown()
  }

}