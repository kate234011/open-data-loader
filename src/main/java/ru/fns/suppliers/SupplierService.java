package ru.fns.suppliers;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

public interface SupplierService {

    Response flushAll(@QueryParam("value") String value);

    Response cleanUp(@QueryParam("value") String value);

    Response sync(@QueryParam("value") String value) throws Exception;

}
