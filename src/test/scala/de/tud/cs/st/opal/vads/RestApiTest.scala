package de.tud.cs.st.opal.vads

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.client.{ ResponseHandler, HttpClient }
import org.apache.http.protocol.HttpContext
import org.apache.http.{ HttpRequest, HttpHost }
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class RestApiTest extends FlatSpec with ShouldMatchers {

  import dispatch._
  import Http._

  "The Server" should "display a plain text message on GET" in {

    Http(url("http://localhost:9000") <:< Map("Accept" -> "application/json") >>> System.out)
    
  }

}
