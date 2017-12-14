#!/bin/bash
#
# /etc/init.d/meuScript
 
case "$1" in
    start)
        echo "Iniciando serviço..."
        sudo java -cp .:classes:/opt/pi4j/lib/'*' com/br/phdev/Controlador
        ;;
 
    stop)
        echo "Parando serviço..."
        # comando para parar o serviço
        ;;
 
    restart)
        echo "Reiniciando serviço..."
        # comando para reiniciar o serviço
        ;;
 
    *)
        echo "Operação inválida"
        ;;
esac