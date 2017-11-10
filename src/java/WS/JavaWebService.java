/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WS;

import com.google.gson.Gson;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import models.DataBaseManager;
import models.Titles;

/**
 * REST Web Service
 *
 * @author Yuri.Ramos
 */
@Path("movies")
public class JavaWebService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of JavaWebService
     */
    public JavaWebService() {
    }

    /**
     * Retrieves representation of an instance of WS.JavaWebService
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // : .* permite que parametros entrem com valores nulos //
    @Path("/{search: .*}&{filter: .*}") 
    public String getJson(@PathParam("search") String search, @PathParam("filter") String filter) {
        
        if(search.equals(" ") || search == null) {
            search = "";
            if(filter.equals(" ") || filter == null) {
                filter = "";
            }
        }
        
        if(search.compareTo(" ") != 0 || search != null) {
            if(filter.equals(" ") || filter == null) {
                filter = "";
            }
        }
        
        if(filter.equals(" ") || filter == null) {
            filter = "title";
        }
        
        ArrayList<Titles> titlesList = new ArrayList<Titles>();       
        DataBaseManager dataBase = new DataBaseManager();
        Gson gson = new Gson();
        
        dataBase.OpenConnectionDataBase();
        titlesList = dataBase.ExecuteQuerySelectTitles(search, filter);
        dataBase.CloseConnection();
        
        return gson.toJson(titlesList);
    }

    /**
     * PUT method for updating or creating an instance of JavaWebService
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
