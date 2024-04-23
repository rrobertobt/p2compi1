package edu.robertob.p2compi1.models;

import java.util.*;

public class Action {

    class ValidationMessages {
        public List<String> messages = new ArrayList<>();
        public List<String> errors = new ArrayList<>();
        boolean isValid = false;

        public List<String> getMessages() {
            return messages;
        }

        public void setMessages(List<String> messages) {
            this.messages = messages;
        }

        public List<String> getErrors() {
            return errors;
        }

        public void setErrors(List<String> errors) {
            this.errors = errors;
        }

        public boolean isValid() {
            return isValid;
        }

        public void setValid(boolean valid) {
            isValid = valid;
        }
    }

    Action(String name) {
        this.name = name;
    }

    ValidationMessages validationMessages = new ValidationMessages();
    boolean isValid = true;
    private static final Map<String, List<String>> actionsParameters = new HashMap<>();
    private static final Set<String> ID_PARAMETERS = new HashSet<>(Arrays.asList("ID", "USUARIO_CREACION", "USUARIO_MODIFICACION", "SITIO", "PADRE", "PAGINA"));
    private static final Map<String, List<String>> OPTIONAL_PARAMETERS = new HashMap<>();
    private static final Map<String, List<String>> COMP_CLASES_ATTRS = new HashMap<>();
    private List<String> errors = new ArrayList<>();
    private List<String> messages = new ArrayList<>();

    static {
        actionsParameters.put("NUEVO_SITIO_WEB", Arrays.asList("ID"));
        actionsParameters.put("BORRAR_SITIO_WEB", Arrays.asList("ID"));
//        actionsParameters.put("NUEVA_PAGINA", Arrays.asList("ID", "TITULO", "SITIO", "USUARIO_CREACION", "FECHA_CREACION", "FECHA_MODIFICACION", "USUARIO_MODIFICACION"));
        actionsParameters.put("NUEVA_PAGINA", Arrays.asList("ID", "TITULO", "SITIO"));
        actionsParameters.put("BORRAR_PAGINA", Arrays.asList("ID"));
        actionsParameters.put("MODIFICAR_PAGINA", Arrays.asList("ID", "TITULO"));
        actionsParameters.put("AGREGAR_COMPONENTE", Arrays.asList("ID", "PAGINA", "CLASE"));
        actionsParameters.put("BORRAR_COMPONENTE", Arrays.asList("ID", "PAGINA"));
        actionsParameters.put("MODIFICAR_COMPONENTE", Arrays.asList("ID", "PAGINA", "CLASE"));

        COMP_CLASES_ATTRS.put("TITULO", Arrays.asList("TEXTO", "COLOR"));

        OPTIONAL_PARAMETERS.put("NUEVA_PAGINA", Arrays.asList("USUARIO_CREACION", "FECHA_CREACION", "FECHA_MODIFICACION", "USUARIO_MODIFICACION", "PADRE"));
        OPTIONAL_PARAMETERS.put("NUEVO_SITIO_WEB", Arrays.asList("USUARIO_CREACION", "FECHA_CREACION", "FECHA_MODIFICACION", "USUARIO_MODIFICACION"));
    }

    private String name;
    private List<Parameter> parameters = new ArrayList<>();
    private List<Attribute> attributes = new ArrayList<>();
    private ActionStrategy strategy;

// Constructor, getters y setters

    public boolean validate() {
        // Validar que el nombre de la acción sea válido
        if (name == null || name.isEmpty()) {
            System.out.println("Error: Nombre de acción no válido");
            errors.add("Error: Nombre de acción no válido");
            isValid = false;
        }

        // Verificar si la acción está en el mapa
        if (!actionsParameters.containsKey(name)) {
            errors.add("Error: Acción no reconocida");
            validationMessages.errors = errors;
            isValid = false;
            return false;
        }


        // Verificar si la acción está en el mapa y obtener sus parámetros válidos
        List<String> validParameters = actionsParameters.get(name);
        if (validParameters == null) {
            errors.add("Error: Acción no reconocida");
            isValid = false;
        }
        List<String> optionalParameters = OPTIONAL_PARAMETERS.getOrDefault(name, Collections.emptyList());

        for (Parameter p : parameters) {
            if (!validParameters.contains(p.getName()) && !optionalParameters.contains(p.getName())) {
                errors.add("Error: Parámetro no válido para la acción " + name + ": " + p.getName());
                isValid = false;
            }

            if (ID_PARAMETERS.contains(p.getName())) {
                String valor = p.getValue();
                if (valor == null || !valor.startsWith("$")) {
                    errors.add("Error: El parámetro " + p.getName() + " debe ser un ID valido; comenzar con '$'");
                    isValid = false;
                }
            }

            if (optionalParameters.contains(p.getName()) && p.getValue() == null) {
                continue; // Omitir la validación si el parámetro es opcional y está ausente
            }
        }

        // Validar que todos los parámetros requeridos estén presentes
        for (String parametro : validParameters) {
            if (!optionalParameters.contains(parametro)) {
                boolean found = false;
                for (Parameter p : parameters) {
                    if (p.getName().equals(parametro)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    errors.add("Error: Falta el parámetro " + parametro + " para la acción " + name);
                    isValid = false;
                }
            }
        }


        for (Attribute a : attributes) {
            String clase = getParameterValue("CLASE"); // Obtener la clase del componente
            List<String> atributosValidos = COMP_CLASES_ATTRS.get(clase);
            if (atributosValidos == null || !atributosValidos.contains(a.getName())) {
                errors.add("Error: El atributo " + a.getName() + " no es válido para la clase de componente " + clase);
                isValid = false;
            }
        }

        if (isValid) {
            System.out.println("Acción válida: " + this);
        } else {
            System.out.println("Errores: ");
            for (String error : errors) {
                System.out.println(error);
            }
        }

        switch (name) {
            case "NUEVO_SITIO_WEB":
                strategy = new NewWebSiteStrategy();
                break;
            case "BORRAR_SITIO_WEB":
                strategy = new DeleteWebSiteStrategy();
                break;
            case "NUEVA_PAGINA":
                strategy = new NewPageStrategy();
                break;
            default:
                System.out.println("No se ha definido una estrategia para la acción " + name);
                break;
        }

        validationMessages.messages = messages;
        validationMessages.errors = errors;
        validationMessages.isValid = isValid;
        return isValid;
    }

    public void addParameter(Parameter parameter) {
        parameters.add(parameter);
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public void addError(String error) {
        errors.add(error);
    }

    @Override
    public String toString() {
        StringBuilder parameters = new StringBuilder();
        for (Parameter p : this.parameters) {
            parameters.append(p.getName()).append(": ").append(p.getValue()).append(", ");
        }

        StringBuilder attributes = new StringBuilder();
        for (Attribute a : this.attributes) {
            attributes.append(a.getName()).append(": ").append(a.getValue()).append(", ");
        }

        return "Action{" +
                "name='" + name + '\'' +
                ", parameters=" + parameters +
                ", attributes=" + attributes +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getParameterValue(String paramName) {
        for (Parameter p : parameters) {
            if (p.getName().equals(paramName)) {
                return p.getValue();
            }
        }
        return null;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
    public ActionStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(ActionStrategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        System.out.println("Ejecutando acción: " + name);
        if (strategy != null) {
            strategy.execute(this);
        } else {
            System.out.println("No se ha definido una estrategia para la acción " + name);
        }
    }

    public ValidationMessages getValidationMessages() {
        return validationMessages;
    }
}
