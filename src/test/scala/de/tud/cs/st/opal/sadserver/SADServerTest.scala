package de.tud.cs.st.opal.sadserver

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.ShouldMatchers
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.client.{ ResponseHandler, HttpClient }
import org.apache.http.protocol.HttpContext
import org.apache.http.{ HttpRequest, HttpHost }
import dispatch.{ Http => DispatchHttp, _ }
import DispatchHttp._
import scala.sys.SystemProperties
import scala.xml.XML.loadString

import scala.xml.{ XML, Utility }

@RunWith(classOf[JUnitRunner])
class SADServerTest extends FlatSpec with ShouldMatchers with BeforeAndAfterAll {

  override def beforeAll(configMap: Map[String, Any]) {
    println("Starting tests")
    new SystemProperties += ("org.tud.cs.st.opal.sadserver.database" -> "jdbc:h2:mem:test1db;DB_CLOSE_DELAY=-1")
    SADServer
  }

  var host = "http://localhost:9000"
  var id1: String = _
  var id2: String = _

  val sad1 = XML.loadFile("src/test/resources/sad1.sad")
  val sad2 = XML.loadFile("src/test/resources/sad2.sad")

  val post = DoRestClient.post(Map("Accept" -> "application/xml")) _
  val get = DoRestClient.get(Map("Accept" -> "application/xml")) _
  val basicAuthGet = DoRestClient.get(Map("Accept" -> "application/xml"), new BasicAuth("admin", "password")) _

  "The '/sads' resource" should "create a SAD on POST via XML" in {
    val response = post(host + "/sads", sad1.toString)
    response.statusCode should equal(201)
    id1 = (XML.loadString(response.body) \\ "id").text
    println("ID" + id1)

  }

  it should "return the created SAD on GET providing its id" in {
    val response = get(host + "/sads/" + id1)
    response.statusCode should equal { 200 }
    SADParser(XML.loadString(response body)).diagramName should equal { "mapping.sad" }
  }

  it should "create another SAD on POST via XML" in {
    val response = post(host + "/sads", sad2.toString)
    response.statusCode should equal(201)
    id2 = (XML.loadString(response.body) \\ "id").text
  }

  it should "return the created second SAD on GET providing its id" in {
    val response = get(host + "/sads/" + id1)
    response.statusCode should equal { 200 }
    SADParser(XML.loadString(response body)).diagramName should equal { "mapping.sad" }
  }

  it should "return a list of created SADs on GET" in {
    val response = get(host + "/sads")
    response.statusCode should equal { 200 }
  }
  
   "The '/users' resource" should "return FORBIDDEN for unauthorized" in {
    val response = get(host + "/users")
    response.statusCode should equal(401)
  }
   
   it should "return OK for authorized users" in {
    val response = basicAuthGet(host + "/users")
    response.statusCode should equal(200)
  }
  

}

/**
 * Enables control over logging.
 */
object Http extends DispatchHttp {

  override def make_logger = new Logger {
    def info(msg: String, items: Any*) {
      // no-op
    }

    def warn(msg: String, items: Any*) {
      // no-op
    }
  }
}
