package edu.robertob.p2compi1.controller;

import com.google.gson.Gson;
import edu.robertob.p2compi1.models.Action;
import edu.robertob.p2compi1.models.ContentLexer;
import edu.robertob.p2compi1.models.ContentParser;
import edu.robertob.p2compi1.utils.GsonWrapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

@WebServlet("/main")
public class MainServlet extends HttpServlet {

    class ErrorResponse {
        private final String message;
        private final String exception;
        private final String stackTrace;

        public ErrorResponse(String message, String exception, String stackTrace) {
            this.message = message;
            this.exception = exception;
            this.stackTrace = stackTrace;
        }

        public String getMessage() {
            return message;
        }

        public String getException() {
            return exception;
        }

        public String getStackTrace() {
            return stackTrace;
        }

        public String toString() {
            return "ErrorResponse{" +
                    "message='" + message + '\'' +
                    ", exception='" + exception + '\'' +
                    ", stackTrace='" + stackTrace +
                    '}';

        }
    }

    private  GsonWrapper gsonWrapper;
    private  ContentLexer contentLexer;
    private  ContentParser contentParser;
    public MainServlet() {
        this.gsonWrapper = new GsonWrapper();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = getRequestBody(request);
        this.contentLexer = new ContentLexer(new StringReader(requestBody));
        this.contentParser = new ContentParser(contentLexer);
        Action parseResult = null;
        try {
            parseResult = (Action) contentParser.parse().value;
            boolean valid = parseResult.validate();
            if (valid) parseResult.execute();
            this.gsonWrapper.sendAsJson(response, parseResult.getValidationMessages());
        } catch (Exception e) {
            this.gsonWrapper.sendAsJson(response, new ErrorResponse("Hubo un error al interpretar la entrada, por favor revisa el texto", e.toString(), e.getStackTrace().toString()));
            throw new RuntimeException(e);
        }
        // do analysis
        // todo
    }


    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
//                System.out.println("[SERVLET] Reading line: " + line);
//                sb.append(line).append(" "); // Append space character
                sb.append(line).append("\n"); // Append newline character
            }
        }
        return sb.toString();
    }
}
