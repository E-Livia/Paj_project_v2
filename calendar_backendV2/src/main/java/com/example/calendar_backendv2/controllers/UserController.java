package com.example.calendar_backendv2.controllers;

import com.example.calendar_backendv2.models.User;
import com.example.calendar_backendv2.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Path("/create")
    public int createUser(User user) throws SQLException {
        return userService.createUser(user);
    }

    @GET
    @Path("/{username}")
    public User getUserByUsername(@PathParam("username") String username) throws SQLException {
        return userService.getUserByUsername(username);
    }

    @PUT
    @Path("/{username}")
    public void updateUserByUsername(@PathParam("username") String username, User user) throws SQLException {
        userService.updateUserByUsername(username, user);
    }

    @DELETE
    @Path("/{username}")
    public void deleteUserByUsername(@PathParam("username") String username) throws SQLException {
        userService.deleteUserByUsername(username);
    }

    @GET
    @Path("/checkUsernameUnique")
    public boolean checkUsernameUnique(@QueryParam("username") String username) throws SQLException {
        return userService.isUsernameUnique(username);
    }

    @GET
    @Path("/checkEmailUnique")
    public boolean checkEmailUnique(@QueryParam("email") String email) throws SQLException {
        return userService.isEmailUnique(email);
    }

    @GET
    @Path("/{username}/id")
    public int getUserIdByUsername(@PathParam("username") String username) throws SQLException {
        return userService.getUserIdByUsername(username);
    }
}