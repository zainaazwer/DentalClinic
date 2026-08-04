package com.dentalclinic.webservice;

import com.dentalclinic.model.User; 
import com.dentalclinic.service.AuthenticationService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/authentication")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class AuthenticationWS {
	
    private AuthenticationService authService;

    public AuthenticationWS(){

        authService = new AuthenticationService();
    }

    @POST
    @Path("/Login")
    public Response login(User user){


        try {
            User authenticatedUser =
                authService.login(
                    user.getUsername(),
                    user.getPassword()
                );

            if(authenticatedUser != null){

                return Response
                        .ok(authenticatedUser)
                        .build();

            }

            return Response
                    .status(401)
                    .entity("Invalid login")
                    .build();

        }catch(Exception e){

            return Response.status(500).build();

        }

    }

}