package net.javatutorial.tutorials.services.api;

import net.javatutorial.tutorials.services.classes.Courses;
import net.javatutorial.tutorials.services.services.DatabaseService;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/listCourses")
public class ListCourses {

    private DatabaseService databaseService = DatabaseService.getInstance();

    @GET
    public Response getCourses() throws Exception {
        List<Courses> courses = databaseService.getCourses();

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
