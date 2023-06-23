package org.br.mineracao.client;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.br.mineracao.dto.CurrencyPriceDTO;

@Path("/last")
@RegisterRestClient(baseUri="https://economia.awesomeapi.com.br")
@ApplicationScoped
public interface CurrencyPriceClient {
	
	@GET
	@Path("/{pair}")
	CurrencyPriceDTO getPricesByPair(@PathParam("pair") String pair );
	
}
