package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BoatDto;
import dtos.OwnerDto;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

//Todo Remove or change relevant parts before ACTUAL use
@RolesAllowed("user")
@Path("boats")
public class UserResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final UserFacade FACADE =  UserFacade.getUserFacade(EMF);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("owners")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllOwners() {
        List<OwnerDto> ownerDtos = FACADE.getAllOwners();
        System.out.println(ownerDtos);

        return Response.ok().entity(GSON.toJson(ownerDtos)).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllBoatsFromHarbour(@PathParam("id") String content) {
        Long harbourId = GSON.fromJson(content, Long.class);
        List<BoatDto> boatDtos = FACADE.getBoatsFromHarbour(harbourId);

        return Response.ok().entity(GSON.toJson(boatDtos)).build();
    }

    @GET
    @Path("owners/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllOwnersFromBoat(@PathParam("id") String content) {
        Long boatId = GSON.fromJson(content, Long.class);
        List<OwnerDto> ownerDtos = FACADE.getOwnersFromBoat(boatId);

        return Response.ok().entity(GSON.toJson(ownerDtos)).build();
    }

}
