package com.duskeagle.resttest

object RestTest {

  def printBalanceSummary(tm: TransactionManager): Unit = {
    tm.runningBalance.foreach { case (date, balance) =>
      println(s"$date\t$$$balance")
    }
    println(s"Total\t$$${tm.totalBalance}")
  }

  def main(args: Array[String]): Unit = {
    val apiRequest = args.toList match {
      case Nil => ApiRequest.apply
      case endpoint :: Nil => ApiRequest(endpoint)
      case _ => sys.error("Usage: ./resttest [alternative endpoint]")
    }
    printBalanceSummary(new TransactionManager(apiRequest.getTransactions()))
  }

}