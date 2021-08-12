package ru.fns.suppliers;

import ru.fns.suppliers.cdi.FTPLoad;
import ru.fns.suppliers.cdi.Flush;
import ru.fns.suppliers.service.flusher.Flusher;
import ru.fns.suppliers.service.loader.Loader;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



@Path("/open-data-loader")
public class UnfairSupplierService implements SupplierService {

    @Inject
    @FTPLoad
    Loader loader;

    @Inject
    @Flush
    Flusher flusher;

    /* Очистка всех директорий и логов перед рестартом приложения.
    * http://localhost:8080/open-data-loader/flush-all?value=1
    * */
    @GET
    @Path("flush-all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response flushAll(@QueryParam("value") String value) {
        return Response.status(Response.Status.ACCEPTED).entity(flusher.flushRepository(value)).build();
    }

    /* Чистим папку загрузки. Один раз загрузили, вставили в базу, очистили папку.
    * http://localhost:8080/open-data-loader/clean-up?value=1
    */
    @GET
    @Path("clean-up")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cleanUp(@QueryParam("value") String value) {
       return Response.ok(flusher.cleanUpRepository(value)).build();
    }

    // * Для джоба: обновляем данные в директории и пишем лог
    //http://localhost:8080/open-data-loader/sync?value=1
    @GET
    @Path("sync")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sync(@QueryParam("value") String value) throws Exception {
        return Response.ok(loader.sync(value)).build();
    }
}
