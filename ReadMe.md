# Running via command line
mvn clean install && mvn package spring-boot:repackage && mvn docker:build && mvn verify -P acceptance-tests