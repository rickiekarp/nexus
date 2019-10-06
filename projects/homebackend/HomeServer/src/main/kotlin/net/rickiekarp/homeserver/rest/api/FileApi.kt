package net.rickiekarp.homeserver.rest.api

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

import java.io.IOException

@RestController
@RequestMapping("file")
class FileApi {

    //    @GET
//    @Path("download")
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
//    public Response getFile(
//            @QueryParam("identifier") String appIdentifier,
//            @QueryParam("channel") String updateChannel) {
//        File file = new File(System.getProperty("user.dir") + "/files/apps/" + appIdentifier + "/download/" + updateChannel + "/" + appIdentifier + ".jar");
//        if (file.exists()) {
//            return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
//                    .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" )
//                    .build();
//        } else {
//            ErrorDTO errorDTO = new ErrorDTO("File '" + appIdentifier + "' not found!");
//            return Response.ok(errorDTO, MediaType.APPLICATION_JSON).build();
//        }
//    }

    val file: ByteArray
        @GetMapping(value = ["get"], produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
        @ResponseBody
        @Throws(IOException::class)
        get() {
            val `in` = this.javaClass.classLoader.getResourceAsStream("botmanager/changelog.xml")
            return `in`!!.readAllBytes()
        }
}