package com.duskeagle.resttest

import play.api.libs.json.Json

case class Transaction(
  Date: String,
  Ledger: String,
  Amount: String,
  Company: String
)

object PageResponse {
  implicit val format = Json.format[Transaction]
}
