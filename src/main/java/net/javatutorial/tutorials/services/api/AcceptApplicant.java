package net.javatutorial.tutorials.services.api;

import com.sun.jersey.multipart.FormDataParam;
import net.javatutorial.tutorials.services.services.DatabaseService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/acceptApplicant")
public class AcceptApplicant {

    DatabaseService databaseService = DatabaseService.getInstance();

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response acceptCandidate(
            @FormDataParam("userName") String username,
            @FormDataParam("courseId") String courseId
    ) {
        boolean success = databaseService.acceptStudentToCourse(username, courseId);

        if (success) {
            return Response.status(200).entity("all is well").build();
        }
        return Response.status(200).entity("failed to add").build();
    }

}
