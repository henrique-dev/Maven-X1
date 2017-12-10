#!/bin/bash

echo "COMPILANDO"

sudo javac -cp .:classes:/opt/pi4j/lib/'*' src/com/br/phdev/*.java src/com/br/phdev/driver/*.java src/com/br/phdev/cmp/*.java src/com/br/phdev/conexao/*.java
