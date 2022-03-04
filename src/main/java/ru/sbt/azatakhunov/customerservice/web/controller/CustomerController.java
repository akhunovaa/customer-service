package ru.sbt.azatakhunov.customerservice.web.controller;

import ru.sbt.azatakhunov.customerservice.application.CustomerApplicationService;
import ru.sbt.azatakhunov.customerservice.application.CustomerQueryService;
import ru.sbt.azatakhunov.customerservice.web.controller.dto.CustomerInfoDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Path("/customers")
public class CustomerController {

    private final CustomerQueryService customerQueryService;

    private final CustomerApplicationService customerApplicationService;


    CustomerController(CustomerQueryService customerQueryService, CustomerApplicationService customerApplicationService) {
        this.customerQueryService = customerQueryService;
        this.customerApplicationService = customerApplicationService;
    }

    @Path("{id}")
    @GET
    public Response getCustomer(String id) {
        return Response.ok(customerQueryService.getCustomerById(id)).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @POST
    public Response registerCustomer(String id, CustomerInfoDTO customerInfo) {
        return Response.ok(customerApplicationService.registerCustomer(id, customerInfo.getPw(), customerInfo.getName(), customerInfo.getAddress()))
                .build();
    }
}
