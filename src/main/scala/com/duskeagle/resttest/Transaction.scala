package com.duskeagle.resttest

import java.text.SimpleDateFormat

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Json, Reads, Writes}
import java.time.LocalDate

case class Transaction(
  date: LocalDate,
  ledger: String,
  amount: BigDecimal,
  company: String
)

object Transaction {

  implicit val reads: Reads[Transaction] = (
    (JsPath \ "Date").read[String].map { LocalDate.parse(_) } and
    (JsPath \ "Ledger").read[String] and
    (JsPath \ "Amount").read[String].map { BigDecimal(_) } and
    (JsPath \ "Company").read[String]
  )(Transaction.apply _)

}
