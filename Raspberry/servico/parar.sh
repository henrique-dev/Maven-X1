#!bin/bash
pid=`ps aux | grep maven | awk '{print $2}'`
kill -9 $pid
