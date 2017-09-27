package com.duskeagle.resttest

import java.time.LocalDate

class TransactionManager(transactions: List[Transaction]) {

  def totalBalance: BigDecimal = {
    balanceForTransactions(transactions)
  }

  private def balanceForTransactions(ts: List[Transaction]): BigDecimal = {
    ts.foldLeft(BigDecimal("0"))(_ + _.amount)
  }

  /**
    * Returns a list of (date, balance at end of that date). The list extends from
    * the earliest date in the list of transactions to the latest.
    */
  def runningBalance: List[(LocalDate, BigDecimal)] = {

    /**
      * Looks at the list of dates to find the earliest and latest date in the list,
      * and then returns a list of every day between those days (inclusive).
      */
    def dateRange(dates: List[LocalDate]): List[LocalDate] = {
      dates.sortWith { _.isBefore(_) } match {
        case first +: _ :+ last =>
          Iterator.iterate(first) { _.plusDays(1) }.takeWhile(_.isBefore(last.plusDays(1))).toList
        case first +: _ => List(first)
        case _ => List.empty
      }
    }

    val transactionsByDate = transactions.groupBy { _.date }
    val dates = dateRange(transactionsByDate.keys.toList)
    dates.foldLeft(List.empty[(LocalDate, BigDecimal)]) { case (runningList, date) =>
      val previousBalance = runningList.headOption match {
        case None => BigDecimal("0")
        case Some((_, oldBalance)) => oldBalance
      }
      (date, previousBalance + balanceForTransactions(transactionsByDate.getOrElse(date, List.empty))) +: runningList
    }.reverse
  }


}
