package net.javatutorial.tutorials.services.api;

import com.sun.jersey.multipart.FormDataParam;
import net.javatutorial.tutorials.services.services.DatabaseService;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/addCourse")
public class AddCourse {

    private DatabaseService databaseService = DatabaseService.getInstance();

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addCourse(
        @FormDataParam("courseName") String courseName,
        @FormDataParam("courseId") String courseId,
        @FormDataParam("courseCredits") int courseCredits
    ) {
        boolean success = databaseService.addCourse(courseId, courseName, courseCredits);
        if (success) {
            return Response.status(200).entity("all is well").build();
        }
        return Response.status(200).entity("failed to add").build();
    }
}
