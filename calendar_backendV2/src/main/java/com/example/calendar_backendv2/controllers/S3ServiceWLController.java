package com.example.calendar_backendv2.controllers;

import com.example.calendar_backendv2.models.ServiceResponse;
import com.example.calendar_backendv2.services.S3ServiceWithLambda;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/s3")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class S3ServiceWLController {

    private final S3ServiceWithLambda s3ServiceWithLambda;

    @Inject
    public S3ServiceWLController(S3ServiceWithLambda s3ServiceWithLambda) {
        this.s3ServiceWithLambda = s3ServiceWithLambda;
    }

    @POST
    @Path("/upload")
    public ServiceResponse uploadS3(@QueryParam("filePath") String filePath,
                                    @QueryParam("keyname") String keyname) {
        return s3ServiceWithLambda.uploadS3(filePath, keyname);
    }

    @DELETE
    @Path("/delete")
    public ServiceResponse deleteS3(@QueryParam("keyname") String keyname) {
        return s3ServiceWithLambda.deleteS3(keyname);
    }

    @GET
    @Path("/retrieve")
    public ServiceResponse retrieveS3(@QueryParam("keynames") String keynames,
                                      @QueryParam("baseFilePath") String baseFilePath) {
        return s3ServiceWithLambda.retrieveS3(keynames, baseFilePath);
    }
}