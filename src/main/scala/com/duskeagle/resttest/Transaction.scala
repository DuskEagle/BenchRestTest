package com.duskeagle.resttest

import java.text.SimpleDateFormat

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Json, Reads, Writes}
import java.util.Date

case class Transaction(
  date: Date,
  ledger: String,
  amount: BigDecimal,
  company: String
)

object Transaction {

  private val dateFormat = new SimpleDateFormat("yyyy-MM-dd")

  implicit val reads: Reads[Transaction] = (
    (JsPath \ "Date").read[String].map { dateFormat.parse(_) } and
    (JsPath \ "Ledger").read[String] and
    (JsPath \ "Amount").read[String].map { BigDecimal(_) } and
    (JsPath \ "Company").read[String]
  )(Transaction.apply _)

}
