package net.rickiekarp.homeserver.rest.api

import net.rickiekarp.homeserver.dao.UpdateDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.w3c.dom.Document
import java.io.StringWriter
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

@RestController
@RequestMapping("app")
class ApplicationApi {

    @Autowired
    var repo: UpdateDAO? = null

    @GetMapping("update")
    fun getUpdateInfo(
//            @QueryParam("identifier") appIdentifier: String,
//            @QueryParam("channel") updateChannel: Int
    ): ResponseEntity<String> {
//        val application = repo!!.findByName(appIdentifier, updateChannel)
        return ResponseEntity("not_implemented_yet", HttpStatus.OK)
    }

    @GetMapping("changelog")
    fun getChangelog(
//            @QueryParam("identifier") appIdentifier: String
    ): ResponseEntity<String> {
//        val xmlString = FileParser.readFileAndReturnString(ServerContext.serverContext.getHomeDirPath(AppServer::class.java) + "/Resources/" + appIdentifier + "/changelog.xml")
        return ResponseEntity("not_implemented_yet", HttpStatus.OK)
    }

    private fun getDocumentString(doc: Document): String {
        try {
            val sw = StringWriter()
            val tf = TransformerFactory.newInstance()
            val transformer = tf.newTransformer()
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no")
            transformer.setOutputProperty(OutputKeys.METHOD, "xml")
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8")

            transformer.transform(DOMSource(doc), StreamResult(sw))
            return sw.toString()
        } catch (ex: Exception) {
            throw RuntimeException("Error converting to String", ex)
        }

    }
}
