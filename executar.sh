#!/bin/bash

echo "EXECUTANDO CONTROLADOR MAVEN"

sudo java -cp .:classes:/opt/pi4j/lib/'*' src/com/br/phdev/Controlador
