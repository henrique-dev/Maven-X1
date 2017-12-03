//#!/bin/bash

echo "COMPILANDO"

javac -cp .:classes:/opt/pi4j/lib/'*' com/br/phdev/*.java com/br/phdev/driver*.java com/br/phdev/cmp/*.java
