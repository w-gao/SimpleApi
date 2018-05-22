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

            // get requests
            get("/hello", (req, res) -> "Hello World");


        } catch (Exception ex) {
            // ignored
        }
    }
}
