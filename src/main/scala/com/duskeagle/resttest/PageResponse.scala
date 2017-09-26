package com.duskeagle.resttest

import play.api.libs.json.Json

case class PageResponse(
  totalCount: Int,
  page: Int,
  transaction: List[Transaction]
)

object PageResponse {
  implicit val format = Json.format[PageResponse]
}
