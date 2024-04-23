package edu.robertob.p2compi1.models;

import java.nio.file.Files;
import java.nio.file.Path;

public class NewPageStrategy implements ActionStrategy {
    private final String sitesDirectory = "/Users/robertob/Sites";

    @Override
    public void execute(Action action) {
        String pageId = action.getParameterValue("ID");
        String siteId = action.getParameterValue("SITIO");
//        String parentPage = action.getParameterValue("PADRE");

        // First, check if the site exists
        Path sitePath = Path.of(sitesDirectory, siteId);
        if (!Files.exists(sitePath)) {
            System.out.println("El sitio no existe");
            action.validationMessages.errors.add("El sitio no existe");
            return;
        }
        // Second, check if parent parameter is set and if the parent page exists
        if (action.getParameterValue("PADRE") != null) {
            String parentPage = action.getParameterValue("PADRE");
            System.out.println("Parent page: " + parentPage);
            Path parentPagePath = Path.of(sitesDirectory, siteId, parentPage);
            if (!Files.exists(parentPagePath)) {
                System.out.println("La página padre no existe");
                action.validationMessages.errors.add("La página padre no existe");
                return;
            }
        }

        Path pagePath;
        Path pageParentPath = null;
        if (action.getParameterValue("PADRE") != null) {
            pagePath = Path.of(sitesDirectory, siteId, action.getParameterValue("PADRE"), pageId);
        } else {
            pagePath = Path.of(sitesDirectory, siteId, pageId);
            pageParentPath = Path.of(sitesDirectory, siteId, pageId);
            System.out.println("Parent path: " + pageParentPath);
        }
        pagePath = pagePath.resolveSibling(pageId + ".html");

        String documentContent = "<html>\n" +
                "<head>\n" +
                "<title>"+action.getParameterValue("TITULO")+"</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>"+action.getParameterValue("TITULO")+"</h1>\n" +
                "</body>\n" +
                "</html>";

        try {
            // Create the page html
            if (!Files.exists(pagePath)) {
                Files.writeString(pagePath, documentContent);
            } else {
                System.out.println("La página ya existe");
                action.validationMessages.errors.add("La página ya existe");
            }
            // Create the page folder to hold children pages
            if (action.getParameterValue("PADRE") == null && pageParentPath != null && !Files.exists(pageParentPath)) {
                Files.createDirectory(pageParentPath);
            }
        } catch (Exception e) {
            System.out.println("Error al crear la página: " + e);
        }



    }
}
