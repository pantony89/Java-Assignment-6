/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Products;

import ProductDetails.Product;
import ProductDetails.ListOfProducts;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author c0633176
 */
@Path("/products")
@RequestScoped
public class GenericResource {

    @Inject
    ListOfProducts listOfProducts;
    
    @GET
    @Produces("application/json")
    public Response getAll() {
        return Response.ok(listOfProducts.toJSON()).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") int id) {
        return Response.ok(listOfProducts.get(id).toJSON()).build();
    }

    @POST
    @Consumes("application/json")
    public Response add(JsonObject json) {
        Response res;
        try{
            listOfProducts.add(new Product(json));
            res = Response.ok(listOfProducts.get(json.getInt("productID")).toJSON()).build();
        } catch(Exception ex){
            res = Response.status(500).build();
        }
        return res;
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response set(@PathParam("id") int id, JsonObject json) {
        Response res;
        try{
            Product product = new Product(json);
            listOfProducts.set(id, product);
            res = Response.ok(listOfProducts.get(id).toJSON()).build();
        } catch(Exception ex){
            res = Response.status(500).build();
        }
        return res;
    }
    
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        Response response;
        try{
            listOfProducts.remove(id);
            response = Response.ok("Deleted product with id "+id).build();
        } catch(Exception ex){
            response = Response.status(500).build();
        }
        return response;
    }
}
