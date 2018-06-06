# ckan_dspace_migration_cli
This is the second assignment ot digital preservation at the technical university of vienna in the summer term 2018.

# Executing the tool

First adapt the configuration file with your properties under:

```shell
src/main/resources/application.yml
```

Second use the gradle wrapper to build a bootable jar.

```shell
./gradlew bootJar
  ```

Third execute the jar file from the created build folder. It is not necessary to pass any arguments because the tool is interactive and you can input your commands directly to the command line later on.
```shell
java -jar *.jar
  ```
  
This will present you with our tool which has a help option which gets displayed directly after the startup or by inputting "h" into the console.
