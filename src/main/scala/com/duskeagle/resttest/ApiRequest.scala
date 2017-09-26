package com.duskeagle.resttest

class ApiRequest(endpointBase: String) {

  val endpoint = endpointBase.stripSuffix("/") + "/%d.json"

  def getTransactions: List[Transaction] = {

  }

  private def firstPage: PageResponse = {
    endpoint.format(1)
  }

}

object ApiRequest {
  def apply(): ApiRequest = {
    new ApiRequest("http://resttest.bench.co/transactions/")
  }

  def apply(endpointBase: String): ApiRequest = {
    new ApiRequest(endpointBase)
  }

}
