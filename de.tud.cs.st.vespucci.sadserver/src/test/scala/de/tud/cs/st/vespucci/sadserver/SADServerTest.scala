package de.tud.cs.st.vespucci.sadserver

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.ShouldMatchers
import org.dorest.client.SimpleClient
import org.dorest.client._
import org.dorest.client.{ BasicAuth, DigestAuth }
import scala.sys.SystemProperties
import scala.xml.XML.loadString
import java.io._

import GlobalProperties.{ authority, port, rootPath, userCollectionPath, descriptionCollectionPath, modelPath, documentationPath }

import scala.xml.{ XML, Utility }

@RunWith(classOf[JUnitRunner])
class SADServerTest extends FlatSpec with ShouldMatchers with BeforeAndAfterAll {

  GlobalProperties.loadPropertiesFile("src/test/resources/sadserver-test.properties")

  override def beforeAll(configMap: Map[String, Any]) {
    println("Starting SADServerTests")
    SADServer
  }

  val host = "http://" + authority + ":" + port + rootPath
  var id1: String = _
  var id2: String = _

  val sad1 = XML.loadFile("src/test/resources/newDescription.xml")
  val pdfEntity = Entity(new File("src/test/resources/test.pdf"), "application/pdf")

  val registeredUser = new DigestAuth("somebody", "password")

  val acceptsXml = Map("Accept" -> "application/xml")

  val post = SimpleClient.post(acceptsXml) _
  val get = SimpleClient.get(acceptsXml) _
  val authGet = SimpleClient.get(acceptsXml, registeredUser) _
  val authPost = SimpleClient.post(acceptsXml, registeredUser) _
  val authPut = SimpleClient.put(acceptsXml, registeredUser) _
  val authDelete = SimpleClient.delete(acceptsXml, registeredUser) _
  val put = SimpleClient.put(acceptsXml) _
  val delete = SimpleClient.delete(acceptsXml) _

  "UserCollectionResource" should "return FORBIDDEN on POST for no credentials" in {
    val response = SimpleClient.post(acceptsXml)(host + "/users", Entity(new File("src/test/resources/newUser.xml"), "application/xml", "UTF-8"))
    response.statusCode should equal(401)
  }

  it should "return FORBIDDEN on POST for wrong credentials" in {
    val response = SimpleClient.post(acceptsXml, new DigestAuth("intruder", "letmein"))(host + "/users", Entity(new File("src/test/resources/newUser.xml"), "application/xml", "UTF-8"))
    response.statusCode should equal(401)
  }

  it should "return FORBIDDEN on POST for some user with right password" in {
    val response = SimpleClient.post(acceptsXml, new DigestAuth("intruder", "password"))(host + "/users", Entity(new File("src/test/resources/newUser.xml"), "application/xml", "UTF-8"))
    response.statusCode should equal(401)
  }

  it should "return OK on POST for authorized" in {
    val response = SimpleClient.post(acceptsXml, new DigestAuth("admin", "password"))(host + "/users", Entity(new File("src/test/resources/newUser.xml"), "application/xml", "UTF-8"))
    response.statusCode should equal(201)
  }

  it should "return FORBIDDEN on GET for unauthorized" in {
    val response = get(host + "/users")
    response.statusCode should equal(401)
  }

  it should "return OK on GET for authorized" in {
    val response = SimpleClient.get(acceptsXml, new DigestAuth("admin", "password"))(host + "/users")
    response.statusCode should equal(200)

  }

  "DescriptionCollectionResource" should "return 401 on unauthorized POST" in {
    val response = post(host + descriptionCollectionPath, Entity(sad1.toString))
    response.statusCode should equal(401)
  }

  it should "return 401 on POST with wrong credentials" in {
    val response = SimpleClient.post(acceptsXml,
      new DigestAuth("nobody", "noidea"))(host + descriptionCollectionPath, Entity(sad1.toString, "application/xml", "UTF-8"))
    response.statusCode should equal(401)
  }

  it should "return 201 on authorized POST" in {
    val response = authPost(host + descriptionCollectionPath, Entity(sad1.toString, "application/xml", "UTF-8"))
    response.statusCode should equal(201)
    id1 = Description(XML.loadString(response.body)).id
  }

  it should "return 201 on another authorized POST" in {
    val response = authPost(host + descriptionCollectionPath, Entity(sad1.toString, "application/xml", "UTF-8"))
    response.statusCode should equal(201)
    id2 = Description(XML.loadString(response.body)).id
  }

  it should "return 200 on unauthorized GET and print a list" in {
    val response = get(host + descriptionCollectionPath)
    response.statusCode should equal { 200 }
    println(response.body)
  }

  it should "return 200 on authorized GET and print a list" in {
    val response = authGet(host + descriptionCollectionPath)
    response.statusCode should equal { 200 }
    println(response.body)
  }

  it should "return 401 on unauthorized PUT" in {
    val response = put(host + descriptionCollectionPath, Entity(new File("src/test/resources/test_utf-8.xml"), "application/xml", "UTF-8"))
    response.statusCode should equal { 401 }
  }

  it should "return 405 on authorized PUT" in {
    val response = authPut(host + descriptionCollectionPath, Entity(new File("src/test/resources/test_utf-8.xml"), "application/xml", "UTF-8"))
    response.statusCode should equal { 405 }
  }

  it should "return 401 on unauthorized DELETE" in {
    val response = delete(host + descriptionCollectionPath)
    response.statusCode should equal { 401 }
  }

  it should "return 405 on authorized DELETE" in {
    val response = authDelete(host + descriptionCollectionPath)
    response.statusCode should equal { 405 }
  }

  "DescriptionResource" should "return 200 on GET" in {
    val path = host + descriptionCollectionPath + "/" + id1
    val response = get(path)
    response.contentType should equal { "application/xml; charset=UTF-8" }
    response.statusCode should equal { 200 }
  }

  it should "return 200 on GET on 2nd created description" in {
    val response = get(host + descriptionCollectionPath + "/" + id2)
    response.contentType should equal { "application/xml; charset=UTF-8" }
    response.statusCode should equal { 200 }
  }

  it should "return 404 on GET with unknown id" in {
    val response = get(host + descriptionCollectionPath + "/" + "someIdThatDoesNotExist")
    response.statusCode should equal { 404 }
  }

  it should "return 404 on GET on known id but unknown sub-resource" in {
    val response = get(host + descriptionCollectionPath + "/" + id1 + "/someResourceThatDoesNotExist")
    response.statusCode should equal { 404 }
  }

  it should "return 401 on unauthorized PUT" in {
    val response = put(host + descriptionCollectionPath + "/" + id1, Entity(new File("src/test/resources/test_utf-8.xml"), "application/xml", "UTF-8"))
    response.statusCode should equal { 401 }
  }

  it should "return 200 on authorized PUT" in {
    val response = authPut(host + descriptionCollectionPath + "/" + id1, Entity(new File("src/test/resources/test_utf-8.xml"), "application/xml", "UTF-8"))
    response.statusCode should equal { 200 }
  }

  it should "return 401 on unauthorized POST" in {
    val response = post(host + descriptionCollectionPath + "/" + id1, Entity(new File("src/test/resources/test_utf-8.xml"), "application/xml", "UTF-8"))
    response.statusCode should equal { 401 }
  }

  it should "return 405 on authorized POST" in {
    val response = authPost(host + descriptionCollectionPath + "/" + id1, Entity(new File("src/test/resources/test_utf-8.xml"), "application/xml", "UTF-8"))
    response.statusCode should equal { 405 }
  }

  it should "return 401 on unauthorized DELETE" in {
    val response = delete(host + descriptionCollectionPath + "/" + id2)
    response.statusCode should equal { 401 }
  }

  it should "return 204 on authorized DELETE" in {
    val response = authDelete(host + descriptionCollectionPath + "/" + id2)
    response.statusCode should equal { 204 }
  }

  it should "return 404 on GET when successfully deleted" in {
    val response = get(host + descriptionCollectionPath + "/" + id2)
    response.statusCode should equal { 404 }
  }
  
  it should "return update then name when PUT" in {
    val path = host + descriptionCollectionPath + "/" + id1
    val description = Description(XML.loadString(get(path).body))
    description.name = "foo"
    description.modified = description.modified + 1000
    val descriptionPart = Part(description.toXML.toString, "application/xml")
    val putResponse = authPut(path, Entity("description" -> descriptionPart))
    putResponse.statusCode should equal { 200 }
    Description(XML.loadString(get(path).body)).name should equal { "foo" }
  }

  // ModelResource

  "ModelResource" should "return 404 on GET when model not set" in {
    val response = get(host + descriptionCollectionPath + "/" + id1 + modelPath)
    response.statusCode should equal { 404 }
  }

  it should "return 401 on unauthorized PUT" in {
    val response = put(host + descriptionCollectionPath + "/" + id1 + modelPath, Entity(new File("src/test/resources/test_utf-8.xml"), "application/xml", "UTF-8"))
    response.statusCode should equal { 401 }
  }

  it should "return 200 on authorized PUT" in {
    val response = authPut(host + descriptionCollectionPath + "/" + id1 + modelPath, Entity(new File("src/test/resources/test_utf-8.xml"), "application/xml", "UTF-8"))
    response.statusCode should equal { 200 }
  }

  it should "return 404 on authorized PUT with non-existest id" in {
    val response = authPut(host + descriptionCollectionPath + "/" + "thisIdDoesNotExist" + modelPath, Entity(new File("src/test/resources/test_utf-8.xml"), "application/xml", "UTF-8"))
    response.statusCode should equal { 404 }
  }

  it should "return 404 on GET when model depends on non-existent id" in {
    val response = get(host + descriptionCollectionPath + "/" + "thisIdDoesNotExist" + modelPath)
    response.statusCode should equal { 404 }
  }

  it should "return 401 on unauthorized POST" in {
    val response = post(host + descriptionCollectionPath + "/" + id1 + modelPath, Entity(new File("src/test/resources/test_utf-8.xml"), "application/xml", "UTF-8"))
    response.statusCode should equal { 401 }
  }

  it should "return 405 on authorized POST" in {
    val response = authPost(host + descriptionCollectionPath + "/" + id1 + modelPath, Entity(new File("src/test/resources/test_utf-8.xml"), "application/xml", "UTF-8"))
    response.statusCode should equal { 405 }
  }

  it should "return 200 on GET when model is set" in {
    val response = get(host + descriptionCollectionPath + "/" + id1 + modelPath)
    response.statusCode should equal { 200 }
    response.contentType should equal { "application/xml; charset=UTF-8" }
    println(response.body)
  }

  it should "return 401 on unauthorized DELETE" in {
    val response = delete(host + descriptionCollectionPath + "/" + id1 + modelPath)
    response.statusCode should equal { 401 }
  }

  it should "return 204 on authorized DELETE" in {
    val response = authDelete(host + descriptionCollectionPath + "/" + id1 + modelPath)
    response.statusCode should equal { 204 }
  }

  it should "return 404 on GET when model deleted" in {
    val response = get(host + descriptionCollectionPath + "/" + id1 + modelPath)
    response.statusCode should equal { 404 }
  }

  it should "return 200 on authorized PUT again" in {
    val response = authPut(host + descriptionCollectionPath + "/" + id1 + modelPath, Entity(new File("src/test/resources/test_utf-8.xml"), "application/xml", "UTF-8"))
    response.statusCode should equal { 200 }
  }
  
  "DescriptionRessource" should "allow deletion of model using PUT" in {
    val path = host + descriptionCollectionPath + "/" + id1
    get(path + "/model").statusCode should equal { 200 } // model exists
    val description = Description(XML.loadString(get(path).body))
    description.model.isDefined should equal { true }
    description.model.get.size = 0 // we set the model to size of 0
    description.modified = description.modified + 1000
    val descriptionPart = Part(description.toXML.toString)
    authPut(path, Entity("description" -> descriptionPart)).statusCode should equal { 200 }
    get(path + "/model").statusCode should equal { 404 } // model is deleted
  }

  // DocumentationResource

  "DocumentationResource" should "return 404 on GET when documentation not set" in {
    val response = SimpleClient.get(Map("Accept" -> "application/pdf"))(host + descriptionCollectionPath + "/" + id1 + documentationPath)
    response.statusCode should equal { 404 }
  }

  it should "return 401 on unauthorized PUT" in {
    val response = put(host + descriptionCollectionPath + "/" + id1 + documentationPath, pdfEntity)
    response.statusCode should equal { 401 }
  }

  it should "return 200 on authorized PUT" in {
    val response = authPut(host + descriptionCollectionPath + "/" + id1 + documentationPath, pdfEntity)
    response.statusCode should equal { 200 }
  }

  it should "return 401 on unauthorized POST" in {
    val response = post(host + descriptionCollectionPath + "/" + id1 + documentationPath, pdfEntity)
    response.statusCode should equal { 401 }
  }

  it should "return 405 on authorized POST" in {
    val response = authPost(host + descriptionCollectionPath + "/" + id1 + documentationPath, pdfEntity)
    response.statusCode should equal { 405 }
  }

  it should "return 200 on GET when documentation is set and PDF expected" in {
    val response = SimpleClient.get(Map("Accept" -> "application/pdf"))(host + descriptionCollectionPath + "/" + id1 + documentationPath)
    response.statusCode should equal { 200 }
    response.contentType should equal { "application/pdf; charset=UTF-8" }
    org.apache.commons.io.FileUtils.writeByteArrayToFile(new java.io.File("temp/server/model_pdfExpected.pdf"), response.bytes)
  }

  // FIXME somehow acceptance of multiple document-kinds is not recognized properly. Lets fixit later.
  //    it should "return 200 on GET when documentation is set and PDF, XML expected" in {
  //      val response = SimpleClient.get(Map("Accept" -> "application/pdf, application/xml"))(host + descriptionCollectionPath + "/" + id1 + documentationPath)
  //      response.statusCode should equal { 200 }
  //      response.contentType should equal { "application/pdf; charset=UTF-8" }
  //      org.apache.commons.io.FileUtils.writeByteArrayToFile(new java.io.File("temp/server/model_pdfXmlExpected.pdf"), response.bytes)
  //  
  //    }

  it should "return 401 on unauthorized DELETE" in {
    val response = delete(host + descriptionCollectionPath + "/" + id1 + documentationPath)
    response.statusCode should equal { 401 }

  }

  it should "return 204 on authorized DELETE" in {
    val response = authDelete(host + descriptionCollectionPath + "/" + id1 + documentationPath)
    response.statusCode should equal { 204 }
  }

  it should "return 404 on GET when model deleted" in {
    val response = SimpleClient.get(Map("Accept" -> "application/pdf"))(host + descriptionCollectionPath + "/" + id1 + documentationPath)
    response.statusCode should equal { 404 }
  }

  it should "return 200 on authorized PUT again" in {
    val response = authPut(host + descriptionCollectionPath + "/" + id1 + documentationPath, pdfEntity)
    response.statusCode should equal { 200 }
  }

  // print a final collection view

  "DescriptionCollectionResource" should "should print a list containing a description with subresources" in {
    val response = get(host + descriptionCollectionPath)
    response.statusCode should equal { 200 }
    println(response.body)
  }

  //  it should "let upload files" in {
  //    val response = SimpleClient.putFile(acceptsXml, "application/pdf")(host + descriptionCollectionPath + "/" + id1 + documentationPath, new java.io.File("/Users/mateusz/Desktop/Comet.pdf"))
  //    response.statusCode should equal(200)
  //  }
  //
  //  it should "get a stream" in {
  //    val response = SimpleClient.get(Map("Accept" -> "application/pdf"))(host + descriptionCollectionPath + "/" + id1 + documentationPath)
  //    response.statusCode should equal(200)
  //    org.apache.commons.io.FileUtils.writeByteArrayToFile(new java.io.File("foo.pdf"), response.bytes)
  //  }

}
