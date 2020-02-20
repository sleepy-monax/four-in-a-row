#!/bin/bash
ant compile && ant jar && java -jar out/jar/application.jar
