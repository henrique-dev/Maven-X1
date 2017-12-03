#!/bin/bash

echo "COMPILANDO"

sudo java -cp .:classes:/opt/pi4j/lib/'*' com/br/phdev/Controlador
