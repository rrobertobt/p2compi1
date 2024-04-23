package edu.robertob.p2compi1.models;

import java.nio.file.Files;
import java.nio.file.Path;

public class NewWebSiteStrategy implements ActionStrategy {
    private final String sitesDirectory = "/Users/robertob/Sites";

    @Override
    public void execute(Action action) {
//        System.out.println("generar html para: " + action.getName());
        // Crear directorio o verificar que exista
        Path sitePath = Path.of(sitesDirectory, action.getParameterValue("ID"));
        System.out.println("Directorio del sitio: " + sitePath);
        try {
            if (!Files.exists(sitePath)) {
                action.validationMessages.messages.add("Directorio del sitio creado / Sitio creado");
                Files.createDirectory(sitePath);
            } else {
                System.out.println("El directorio/sitio ya existe");
                action.validationMessages.errors.add("El directorio/sitio ya existe");
            }
        } catch (Exception e) {
            System.out.println("Error al crear el directorio del sitio: " + e);
        }
    }
}
