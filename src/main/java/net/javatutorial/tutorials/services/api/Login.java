package net.javatutorial.tutorials.services.api;


import com.sun.jersey.multipart.FormDataParam;
import net.javatutorial.tutorials.services.services.DatabaseService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/api/login")
public class Login {

    private DatabaseService databaseService = DatabaseService.getInstance();

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response login(
            @FormDataParam("username") String username,
            @FormDataParam("password") String password
    ) throws SQLException {
        if (databaseService.isUserRegistered(username, password)) {
            return Response.status(200).entity(username + "&isAdmin=" + databaseService.isAdmin(username)).build();
        } else {
            return Response.status(300).entity(username + " does not exist!").build();
        }
    }

}
