#!/bin/sh

ELEON_CLASSPATH="libs/exprimo.jar:./mpiro_authoring_v4_4.jar:.:libs/antlr-2.7.5.jar:libs/NL.jar:libs/arq.jar:libs/arq-extra.jar:libs/commons-logging-1.1.jar:libs/concurrent.jar:libs/exprimo.jar:libs/icu4j_3_4.jar:libs/iri.jar:libs/jena.jar:libs/jenatest.jar:libs/json.jar:libs/junit.jar:libs/libtcs-1.0.0-beta.jar:libs/log4j-1.2.12.jar:libs/lucene-core-2.0.0.jar:libs/stax-api-1.0.jar:libs/wstx-asl-3.0.0.jar:libs/xercesImpl.jar:libs/xml-apis.jar:libs/antlr.jar:libs/commons-logging.jar:libs/icu4j.jar:libs/jakarta-oro-2.0.5.jar:libs/log4j-1.2.7.jar:libs/rdf-api-2001-01-19.jar"

exec java -mx512M -classpath $ELEON_CLASSPATH gr.demokritos.iit.mpiro.authoring.Mpiro

