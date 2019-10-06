//package net.rickiekarp.homeserver.rest.api;
//
//import net.rickiekarp.foundation.dto.exception.ErrorDTO;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.io.File;
//
//@Path("file")
//public class FileApi {
//
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
//}
