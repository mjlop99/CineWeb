/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinewebbackend.Rest;

import cinewebbackend.control.UsuarioBean;
import cinewebbackend.entities.Usuarios;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mjlopez
 */
//applicacion path:       http://localhost:8080/CineWebBackend/resources/
@Path("user")
public class UsuarioRest {

    @Inject
    UsuarioBean ubean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Usuarios> findRange(
            @QueryParam("first") @DefaultValue("0") int first,
            @QueryParam("page_size") @DefaultValue("50") int pageSize) {
        return ubean.FindRange(first, pageSize);
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("id") final Integer idUsuario) {
        
          if (idUsuario != null) {
            Usuarios encontrado = ubean.FindById(idUsuario); 
            if (encontrado != null) {
                return Response.status(Response.Status.OK)
                        .entity(encontrado)
                        .build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .header("not-found", "id")
                    .build();
        }
        return Response.status(422)
                .header("missing-parameter", "id")
                .build();
    }
     @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Usuarios usuario, @Context UriInfo info) {
        if (usuario != null) {
            try {
                ubean.Create(usuario);
                URI requestUri = info.getRequestUri();
                return Response.status(Response.Status.CREATED)
                        .header("location", requestUri.toString() + "/" + usuario.getId())
                        .build();
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                return Response.status(500)
                        .header("create-exception", ex.getMessage())
                        .build();
            }
        }
        return Response.status(422)
                .header("missing-parameter", "id")
                .build();
    }
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Integer idUsuario, Usuarios UsuarioActualizado) {
        if (idUsuario != null && UsuarioActualizado != null) {
            Usuarios UsuarioExistente = ubean.FindById(idUsuario);
            if (UsuarioExistente != null) {
                try {
                    // Actualiza los campos necesarios del UsuarioExistente con los del UsuarioActualizado
                    // UsuarioExistente.setNombre(UsuarioActualizado.getNombre());
                    // Actualiza otros campos si es necesario

                    UsuarioExistente = ubean.Modify(UsuarioActualizado);
                    return Response.status(Response.Status.OK)
                            .entity(UsuarioExistente)
                            .build();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    return Response.status(500)
                            .header("update-exception", ex.getMessage())
                            .build();
                }
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .header("not-found", "id")
                        .build();
            }
        }
        return Response.status(422)
                .header("missing-parameter", "id o datos de actualización")
                .build();
    }
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer idUsuario) {
        if (idUsuario != null) {
            Usuarios Usuario = ubean.FindById(idUsuario);
            if (Usuario != null) {
                try {
                    System.out.println(Usuario);
                    ubean.Delete(Usuario);
                    return Response.status(Response.Status.NO_CONTENT).build();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    return Response.status(500)
                            .header("delete-exception", ex.getMessage())
                            .build();
                }
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .header("not-found", "id")
                        .build();
            }
        }
        return Response.status(422)
                .header("missing-parameter", "id")
                .build();
    }

}
