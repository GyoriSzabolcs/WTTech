package net.javatutorial.tutorials.services.api;


import com.sun.jersey.multipart.FormDataParam;
import net.javatutorial.tutorials.services.services.DatabaseService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/api/register")
public class RegisterUser {

    private DatabaseService databaseService = DatabaseService.getInstance();

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response registerUser(
            @FormDataParam("username") String username,
            @FormDataParam("password") String password,
            @FormDataParam("realname") String realname,
            @FormDataParam("email") String email
    ) throws SQLException {

        if (!databaseService.isUsernameAvailable(username)) {
            return Response.status(200).entity("Username already in use").build();
        }
        boolean success = databaseService.registerUser(username, password, realname, email);

        if (success) {
            return Response.status(300).entity("User successfully registered").build();
        } else {
            return Response.status(300).entity("Something went wrong. Failed to create user").build();
        }
    }

}
