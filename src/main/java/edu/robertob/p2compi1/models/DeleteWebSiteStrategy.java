package edu.robertob.p2compi1.models;

import java.nio.file.Files;
import java.nio.file.Path;

public class DeleteWebSiteStrategy implements  ActionStrategy {
    private final String sitesDirectory = "/Users/robertob/Sites";

    @Override
    public void execute(Action action) {
        // Eliminar directorio
        String siteId = action.getParameterValue("ID");
        Path sitePath = Path.of(sitesDirectory, siteId);
        System.out.println("Directorio del sitio: " + sitePath);
        try {
            if (Files.exists(sitePath)) {
                Files.delete(sitePath);
                action.validationMessages.messages.add("Directorio eliminado con éxito / sitio eliminado con éxito");
            } else {
                System.out.println("El directorio/sitio no existe");
                action.validationMessages.errors.add("El directorio/sitio no existe");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el directorio del sitio: " + e);
        }
    }
}
