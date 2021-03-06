package cgl.iotcloud.api.http;

import cgl.iotcloud.core.Constants;
import cgl.iotcloud.core.IoTCloud;
import cgl.iotcloud.core.sensor.SCSensor;
import cgl.iotcloud.core.sensor.SCSensorUtils;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.status;

@Path(HttpAPIConstants.CLIENT_API)
public class ClientServices {

    @Context
    private ServletContext servletContext;

    @GET
    @Path(HttpAPIConstants.SENSORS)
    @Produces("text/xml")
    public Response getSensors() {
        IoTCloud iotCloud = (IoTCloud) servletContext.getAttribute(
                Constants.IOT_CLOUD_SERVLET_PROPERTY);

        List<SCSensor> sensorList = iotCloud.getSensorCatalog().getSensors();

        Response.ResponseBuilder r = status(Response.Status.OK);

        String sensors = SCSensorUtils.convertToString(sensorList);
        r = r.entity(sensors);

        return r.build();
    }

    @GET
    @Path(HttpAPIConstants.SENSORS + "/{" + HttpAPIConstants.ID +"}")
    @Produces("text/xml")
    public Response getSensor(@PathParam(HttpAPIConstants.ID) String id) {
        IoTCloud iotCloud = (IoTCloud) servletContext.getAttribute(
                Constants.IOT_CLOUD_SERVLET_PROPERTY);

        SCSensor sensorList = iotCloud.getSensorCatalog().getSensor(id);

        Response.ResponseBuilder r = status(Response.Status.OK);

        String sensors = SCSensorUtils.convertToString(sensorList);
        r = r.entity(sensors);

        return r.build();
    }
}
