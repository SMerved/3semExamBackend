package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BoatDto;
import facades.AdminFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@RolesAllowed("admin")
@Path("admin")
public class AdminResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final AdminFacade FACADE =  AdminFacade.getAdminFacade(EMF);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @POST
    @Path("boat")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createBoat(String content) {
        BoatDto boatDto = GSON.fromJson(content, BoatDto.class);
        BoatDto newBoatDto = FACADE.createBoat(boatDto);
        return Response.ok().entity(GSON.toJson(newBoatDto)).build();
    }


}
