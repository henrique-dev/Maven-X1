//#!/bin/bash

echo "COMPILANDO"

sudo javac -cp .:classes:/opt/pi4j/lib/'*' com/br/phdev/*.java com/br/phdev/driver*.java com/br/phdev/cmp/*.java com/br/phdev/conexao*.java
