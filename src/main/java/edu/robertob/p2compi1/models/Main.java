package edu.robertob.p2compi1.models;

import java.io.StringReader;

public class Main {
    public static void main(String[] args) {
        StringReader sr = new StringReader("<accion nombre=\"NUEVO_SITIO_WEB\">\n" +
                "\t<parametros>\n" +
                "           <parametro nombre=\"ID\">\n" +
                "              \t[$234]\n" +
                "            </parametro>\n" +
                "            <parametro nombre=\"USUARIO_CREACION\">\n" +
                "              \t[$324]\n" +
                "            </parametro>\n" +
                "\t    <parametro nombre=\"FECHA_CREACION\">\n" +
                "              \t[1-31-2022]\n" +
                "            </parametro>\n" +
                "\t    <parametro nombre=\"FECHA_MODIFICACION\">\n" +
                "              \t[1-31-2022]\n" +
                "            </parametro>\n" +
                "\t    <parametro nombre=\"USUARIO_MODIFICACION\">\n" +
                "              \t[$324]\n" +
                "            </parametro>\n" +
                "\t</parametros>\n" +
                "</accion>");
        sr = new StringReader("<accion nombre=\"NUEVA_PAGINA\">\n" +
                "\t<parametros>\n" +
                "    \t      <parametro nombre=\"ID\">\n" +
                "        \t      [$producto2s]\n" +
                "        \t</parametro>\n" +
                "    \t      <parametro nombre=\"PADRE\">\n" +
                "        \t      [$productos]\n" +
                "        \t</parametro>\n" +
                "    \t      <parametro nombre=\"TITULO\">\n" +
                "        \t      [productos]\n" +
                "        \t</parametro>\n" +
                "\t\t<parametro nombre=\"SITIO\">\n" +
                "        \t      [$234]\n" +
                "        \t</parametro>\n" +
                "\t</parametros>\n" +
                "</accion>");
        ContentLexer contentLexer = new ContentLexer(sr);
        ContentParser contentParser = new ContentParser(contentLexer);
        try {
            Action parseResult = (Action) contentParser.parse().value;
            boolean valid = parseResult.validate();
            System.out.println("Resultado: " + parseResult.toString());
            if (valid) parseResult.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}