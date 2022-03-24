package ru.sbt.azatakhunov.customerservice.port.adapter.service.client;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import ru.sbt.azatakhunov.customerservice.web.controller.dto.CustomerInfoDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/customers")
@ApplicationScoped
@RegisterRestClient
@RegisterClientHeaders
public interface DeliveryRestClientService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<CustomerInfoDTO> getAllCustomerList();

}
