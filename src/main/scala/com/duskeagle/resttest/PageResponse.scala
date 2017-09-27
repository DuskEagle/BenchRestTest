package com.duskeagle.resttest

import play.api.libs.json.Json

case class PageResponse(
  totalCount: Int,
  page: Int,
  transactions: List[Transaction]
)

object PageResponse {
  implicit val reads = Json.reads[PageResponse]
}
