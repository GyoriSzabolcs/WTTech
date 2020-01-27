package net.javatutorial.tutorials.services.api;

import com.sun.jersey.multipart.FormDataParam;
import net.javatutorial.tutorials.services.classes.Courses;
import net.javatutorial.tutorials.services.services.DatabaseService;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/getAvailableCourses")
public class GetAvailableCourses {

    private DatabaseService databaseService = DatabaseService.getInstance();

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response getCourses(
            @FormDataParam("userName") String username
    ) throws Exception {
        List<Courses> courses = databaseService.getAvailableCourses(username);

        JSONArray output = new JSONArray();
        for(Courses course : courses) {
            JSONObject coursesJSON = new JSONObject();
            coursesJSON.put("courseId", course.getCourseId());
            coursesJSON.put("courseName", course.getCourseName());
            coursesJSON.put("courseCredits", course.getCredits());
            output.put(coursesJSON);
        }

        return Response.status(200).entity(output).build();

    }
}
