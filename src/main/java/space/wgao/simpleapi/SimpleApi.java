package space.wgao.simpleapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.wgao.simpleapi.routes.Route;
import space.wgao.simpleapi.socket.ServiceSocketHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static spark.Spark.*;

/**
 * SimpleApi
 *
 * @author w.gao Copyright (c) 2018.05
 * @version 1.0
 */
public class SimpleApi {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleApi.class);

    /**
     * entry of the program
     */
    public static void main(String[] args) {

        // start the API
        new SimpleApi().launch();
    }

    private void launch() {

        port(8080);

        staticFileLocation("public");

        webSocket("/service", ServiceSocketHandler.class);

        Route.init();

        LOG.warn("Server listening on port: " + port() + ", press <enter> to stop.");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            br.readLine();
        } catch (Exception ex) {
            // ignored
        }

        LOG.warn("Stopping the server");
        stop();
    }

}
