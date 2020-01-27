package net.javatutorial.tutorials.services.api;

import net.javatutorial.tutorials.services.classes.Courses;
import net.javatutorial.tutorials.services.classes.Student;
import net.javatutorial.tutorials.services.classes.StudentCourses;
import net.javatutorial.tutorials.services.services.DatabaseService;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/getApplicants")
public class GetApplicants {

    DatabaseService databaseService = DatabaseService.getInstance();

    @GET
    public Response getApplicants() {
        List<StudentCourses> students = databaseService.getPendingStudents();

        JSONArray output = new JSONArray();
        if (students != null) {
            for (StudentCourses student : students) {
                JSONObject coursesJSON = new JSONObject();
                try {
                    coursesJSON.put("realName", student.getStudent().getRealName());
                    coursesJSON.put("email", student.getStudent().getEmail());
                    coursesJSON.put("username", student.getStudent().getUsername());
                    coursesJSON.put("courseName", student.getCoursesList().get(0).getCourseName());
                    coursesJSON.put("courseId", student.getCoursesList().get(0).getCourseId());
                    coursesJSON.put("courseCredits", student.getCoursesList().get(0).getCredits());
                    output.put(coursesJSON);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return Response.status(200).entity(output).build();
    }
}
