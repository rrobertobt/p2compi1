#!/usr/bin/env bash
# shellcheck disable=SC2059

# Color variables
BOLD="\033[1m"
UNDERLINE="\033[4m"
RESET="\033[0m"

GREEN="\033[32m"
RED="\033[31m"
BLUE="\033[34m"
YELLOW="\033[33m"

printf "\n"
printf "${UNDERLINE}${BOLD}[SCRIPT] MOVING TO DIRECTORY...${RESET} \n"
printf "${BOLD}${GREEN}$ cd ~/Developer/IdeaProjects/testing || exit${RESET} \n"
cd ~/Developer/IdeaProjects/p2compi1 || exit
pwd

printf "\n${UNDERLINE}${BOLD}${BLUE}[FLEX] STARTING JFLEX COMPILING...${RESET} \n"
printf "${BOLD}${GREEN}$ jflex -d src/main/java/edu/robertob/p2compi1 src/main/resources/edu/robertob/p2compi1/ContentLexer.flex${RESET}\n\n"

jflex -d src/main/java/edu/robertob/p2compi1/models src/main/resources/edu/robertob/p2compi1/ContentLexer.flex

printf "\n${UNDERLINE}${BOLD}${YELLOW}[CUP] STARTING CUP COMPILING...${RESET} \n"
printf "${BOLD}${GREEN}$ java -jar ~/.bin/java-cup-11b.jar -destdir "./src/main/java/edu/robertob/p2compi1/models/ContentParser" -parser ContentParser ./src/main/resources/edu/robertob/p2compi1/ContentParser.cup${RESET}\n\n"
#printf "${BOLD}${GREEN}$ java -jar ~/.bin/java-cup-11b.jar -parser InputFileParser src/main/resources/edu/robertob/p1compi1/InputFileParser.cup${RESET}\n\n"
#
##java -jar ~/.bin/java-cup-11b.jar -parser InputFileParser src/main/resources/edu/robertob/p1compi1/InputFileParser.cup
java -jar ~/.bin/java-cup-11b.jar -destdir "./src/main/java/edu/robertob/p2compi1/models" -parser ContentParser ./src/main/resources/edu/robertob/p2compi1/ContentParser.cup
#printf "\n${UNDERLINE}${BOLD}${YELLOW}[CUP] MOVING GENERATED CUP FILES...${RESET} \n"





#echo "[FLEX] FINISHED JFLEX COMPILING, MOVING FILES TO JAVA DIRECTORY:"
#mv ~/NetBeansProjects/p1compi1/src/main/resources/edu/robertob/p1compi1/InputFileLexer.java ~/NetBeansProjects/p1compi1/src/main/java/edu/robertob/p1compi1/Lexer/InputFileLexer.java