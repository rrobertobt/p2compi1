package org.example;
import org.example.Token;

import java_cup.runtime.*;
%%

%class ContentLexer
%public
%line
%column
%cup

digit = [0-9]
letter = [a-zA-Z]
whitespace = [\t\n\r ]

%{
    private Symbol symbol(int type) {
      return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value) {
      return new Symbol(type, yyline, yycolumn, value);
    }
%}

%%

// lenguaje a reconocer:
// Una acción:
//  <accion nombre="NOMBRE_DE_LA_ACCION">
  //	<parametros>
  //		<parametro nombre="NOMBRE_PARAMETRO">
  //			[VALOR DEL PARAMETRO]
  //            </parametro>
  //	</parametros>
  //</accion>
  //
  //Más de una acción:
  //<acciones>
  //      <accion nombre="...">
  //            .....
  //	</accion>
  //	<accion nombre= "....">
  //            .....
  //	</accion>
  //	.....
  //<acciones>

"<" {
//          System.out.println("< found");
          return symbol(sym.TAG_START);}

">" {
//          System.out.println("> found");
          return symbol(sym.TAG_END);}

"</" {
//          System.out.println("</ found");
          return symbol(sym.TAG_CLOSE);}

"/>" {
//          System.out.println("/> found");
          return symbol(sym.TAG_SELF_CLOSE);}

"accion" {
//          System.out.println("accion found");
          return symbol(sym.ACTION_TAG_NAME);}

"nombre" {
//          System.out.println("nombre found");
          return symbol(sym.NAME_ATTR);}

"parametros" {
//          System.out.println("parametros found");
          return symbol(sym.PARAMS_TAG_NAME);}

"parametro" {
//          System.out.println("parametro found");
          return symbol(sym.PARAM_TAG_NAME);}

"valor" {
//          System.out.println("valor found");
          return symbol(sym.VALUE_ATTR);}

"etiquetas" {
//          System.out.println("etiquetas found");
          return symbol(sym.TAGS_NAME);}

"etiqueta" {
//          System.out.println("etiqueta found");
          return symbol(sym.TAG_NAME);}

//"/" {System.out.println("/ found"); return symbol();}

"acciones" {
//          System.out.println("acciones found");
          return symbol(sym.ACTIONS_TAG_NAME);}

//<atributo nombre="TEXTO">
//			[Este es el texto que aparece en el titulo :) ]
//		</atributo>
"atributo" {
//          System.out.println("atributo found");
          return symbol(sym.ATTR_TAG_NAME);}

"atributos" {
//          System.out.println("atributos found");
          return symbol(sym.ATTRS_TAG_NAME);}

"=" {
//          System.out.println("= found");
          return symbol(sym.EQUALS);}

//"\"" {System.out.println("\" found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}



//[a-zA-Z0-9_-]+ {System.out.println("id found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}
\[[_$-]({letter}|{digit}|[$_])*({letter}|{digit}|[$_])*\] {
          String id = yytext().substring(1, yytext().length() - 1);
//          System.out.println("id found: "+ id);
          return symbol(sym.ID, id);
      }

\"({letter}|{digit}|_)+\" {
//          System.out.println("attr value found: "+ yytext());
          String value = yytext().substring(1, yytext().length() - 1);
            return symbol(sym.ATTR_VALUE, value);
      }

\[[^\]]+\] {
//          System.out.println("valor found" + yytext());
            String value = yytext().substring(1, yytext().length() - 1);
            return symbol(sym.PARAM_VALUE, value);

      }

// Ignorar espacios en blanco
{whitespace} {}

. {
//          System.out.println(" illegal found ");
      }