package ec.edu.ups.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.ForeignKey;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import ec.edu.ups.ejb.ClienteFacade;
import ec.edu.ups.ejb.ReservaFacade;
import ec.edu.ups.ejb.RestauranteFacade;
import ec.edu.ups.entidades.Cliente;
import ec.edu.ups.entidades.Reserva;
import ec.edu.ups.entidades.Restuarante;

@Path("/reservas/")
public class reservas {

	@EJB
	private ClienteFacade ejbCliente; 
	@EJB
	private ReservaFacade ejbReserva;
	@EJB
	private RestauranteFacade ejbResturante;
	
	
    public reservas() {
        // TODO Auto-generated constructor stub
    }

    @POST
    @Path("/listarCliente")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response listar(@FormParam("cedula") String cedula) {
	
    	Cliente cliente = ejbCliente.buscarPorCedula(cedula);
    	
    	List<Reserva> pedido = new ArrayList<Reserva>();
    	
    	
    	
    	for (Reserva pedidoCabecera : cliente.getReservasCliente()) {
    		
    		Cliente p = new Cliente(cliente.getId(), cliente.getCedula(), cliente.getNombre(), cliente.getApellido(), cliente.getTelefono(), cliente.getDireccion(), cliente.getCorreo());
			Reserva r = new Reserva(pedidoCabecera.getId(), pedidoCabecera.getFecha(), pedidoCabecera.getNumeroPersonas(), p);
			
			pedido.add(r);
		}
    	
    	
    	Jsonb jsonb = JsonbBuilder.create();
    	return Response.status(201).entity(jsonb.toJson(pedido))
    		.header("Access-Control-Allow-Origin", "*")
    		.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
    		.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
    }
    
    @POST
    @Path("/listarRest")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response listarRest(@FormParam("nombre") String nombre) {
	
    	Restuarante restuarante = ejbResturante.buscarPorNombre(nombre);
    	
    	List<Reserva> pedido = new ArrayList<Reserva>();
    	
    	
    	
    	for (Reserva pedidoCabecera : restuarante.getReservasRestaurante()) {
    		
    		Restuarante res = new Restuarante(pedidoCabecera.getRestauranteReserva().getId(), pedidoCabecera.getRestauranteReserva().getNombre(), 
    				pedidoCabecera.getRestauranteReserva().getDireccion(), pedidoCabecera.getRestauranteReserva().getTelefono(), 
    				pedidoCabecera.getRestauranteReserva().getAforo());
    		Reserva r = new Reserva(pedidoCabecera.getId(), pedidoCabecera.getFecha(), pedidoCabecera.getNumeroPersonas(), res);
			
			pedido.add(r);
		}
    	
    	
    	Jsonb jsonb = JsonbBuilder.create();
    	return Response.status(201).entity(jsonb.toJson(pedido))
    		.header("Access-Control-Allow-Origin", "*")
    		.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
    		.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
    }
    
   /* @POST
    @Path("/CrearReserva")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response CrearReserva(@FormParam("numeropersonas") Integer numeropersonas, @FormParam("ClienteReserva") Integer cliente,@FormParam("RestauranteReserva") Integer restaurante) {
	
    	//Restaurante restuarante = ejbResturante.buscarPorNombre(nombre);
    	
    	
    	List<Reserva> pedido = new ArrayList<Reserva>();
    	
    	
    	
    	for (Reserva pedidoCabecera : restuarante.getReservasRestaurante()) {
    		
    		Restaurante res = new Restaurante(pedidoCabecera.getRestauranteReserva().getId(), pedidoCabecera.getRestauranteReserva().getNombre(), 
    				pedidoCabecera.getRestauranteReserva().getDireccion(), pedidoCabecera.getRestauranteReserva().getTelefono(), 
    				pedidoCabecera.getRestauranteReserva().getAforo());
    		Reserva r = new Reserva(pedidoCabecera.getId(), pedidoCabecera.getFecha(), pedidoCabecera.getNumeroPersonas(), res);
			
			pedido.add(r);
		}
    	
    	
    	Jsonb jsonb = JsonbBuilder.create();
    	return Response.status(201).entity(jsonb.toJson(pedido))
    		.header("Access-Control-Allow-Origin", "*")
    		.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
    		.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
    }*/

}