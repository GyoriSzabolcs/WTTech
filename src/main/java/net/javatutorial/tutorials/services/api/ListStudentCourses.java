package net.javatutorial.tutorials.services.api;

import com.sun.jersey.multipart.FormDataParam;
import net.javatutorial.tutorials.services.classes.Courses;
import net.javatutorial.tutorials.services.classes.StudentCourses;
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

@Path("/api/listStudentCourses")
public class ListStudentCourses {

    private DatabaseService databaseService = DatabaseService.getInstance();

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response getCourses(
            @FormDataParam("username") String username
    ) throws Exception {
        StudentCourses studentCourses = databaseService.getStudentCourses(username);

        JSONArray output = new JSONArray();
        if (studentCourses != null) {
            for (Courses course : studentCourses.getCoursesList()) {
                JSONObject coursesJSON = new JSONObject();
                coursesJSON.put("courseId", course.getCourseId());
                coursesJSON.put("courseName", course.getCourseName());
                coursesJSON.put("courseCredits", course.getCredits());
                coursesJSON.put("grade", course.getGrade());
                coursesJSON.put("status", course.getStatus());
                output.put(coursesJSON);
            }
        }

        return Response.status(200).entity(output).build();

    }
}
