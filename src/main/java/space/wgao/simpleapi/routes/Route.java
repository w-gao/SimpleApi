package space.wgao.simpleapi.routes;

import static spark.Spark.get;

/**
 * SimpleApi
 *
 * @author w.gao Copyright (c) 2018.05
 * @version 1.0
 */
public class Route {

    public static void init() {

        try {

            // read requests
            get("/hello", (req, res) -> "Hello World");

            get("/server", (req, res) -> "ws://192.168.0.149:8080/service");

        } catch (Exception ex) {
            // ignored
        }
    }
}
