package com.duskeagle.resttest

object RestTest {

  def main(args: Array[String]): Unit = {
    val apiRequest = args.toList match {
      case Nil => ApiRequest.apply
      case endpoint :: Nil => ApiRequest(endpoint)
      case _ => sys.error("Usage: ./resttest [alternative endpoint]")
    }
    val transactionManager = new TransactionManager(apiRequest.getTransactions())
    println(transactionManager.totalBalance)
  }

}