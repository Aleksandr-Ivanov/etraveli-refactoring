#!/bin/bash
rm -rf compiled/
javac src/dao/*.java src/domain/*.java src/service/*.java src/service/calculation/*.java src/*.java -d compiled/ -source 8 -target 8