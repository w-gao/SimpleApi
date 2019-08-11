package space.wgao.simpleapi.controllers;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * SimpleApi
 *
 * @author w.gao Copyright (c) 2018.05
 * @version 1.0
 */
public class TestController {

    public static Object test(Request req, Response res) {

        Map<String, String> params = new HashMap<>();

        for (String param : req.queryParams()) {
            params.putIfAbsent(param, req.queryParams(param));
        }

        return new Gson().toJson(params);
    }
}
