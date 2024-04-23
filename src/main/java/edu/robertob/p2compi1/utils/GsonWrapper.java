package edu.robertob.p2compi1.utils;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GsonWrapper {
    private final Gson gson;
    public GsonWrapper() {
        this.gson = new Gson();
    }

    public void sendAsJson(HttpServletResponse response, Object object) throws IOException {
        response.setContentType("application/json");

        String res = gson.toJson(object);

        var out = response.getWriter();

        out.print(res);
    }
}
