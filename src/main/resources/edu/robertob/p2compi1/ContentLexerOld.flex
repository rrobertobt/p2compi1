package org.example;
import org.example.Token;

import java_cup.runtime.*;
%%

%class ContentLexer
%type Token
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

"<" {System.out.println("< found"); System.out.println(" yytext: "+yytext()); return new Token(yytext());}

">" {System.out.println("> found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}

"</" {System.out.println("</ found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}

"/>" {System.out.println("/> found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}

"accion" {System.out.println("accion found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}

"nombre" {System.out.println("nombre found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}

"parametros" {System.out.println("parametros found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}

"parametro" {System.out.println("parametro found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}

"valor" {System.out.println("valor found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}

"etiquetas" {System.out.println("etiquetas found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}

"etiqueta" {System.out.println("etiqueta found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}

"/" {System.out.println("/ found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}

"acciones" {System.out.println("acciones found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}

"=" {System.out.println("= found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}

//"\"" {System.out.println("\" found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}



//[a-zA-Z0-9_-]+ {System.out.println("id found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}
\"[_$-]({letter}|{digit}|[$_])*({letter}|{digit}|[$_])*\" {System.out.println("id found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}

\"({letter}|{digit}|_)+\" {System.out.println("(maybe) accion nombre found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}

\[[^\]]+\] {System.out.println("valor found"); System.out.println(" yytext: " + yytext() + " "); return new Token(yytext());}

// Ignorar espacios en blanco
{whitespace} {}

. {System.out.println(" illegal found ");}