# ckan_dspace_migration_cli
This is the second assignment ot digital preservation at the technical university of vienna in the summer term 2018


## CKAN API 

### list data sets

* http://192.168.33.60:5000/api/3/action/package_list

### data set specific

* http://192.168.33.60:5000/api/3/action/package_show?id=

https://stackoverflow.com/questions/33055773/adding-a-new-bitstream-to-dspace-item-using-dspace-rest-api
https://wiki.duraspace.org/display/DSDOC5x/REST+API#RESTAPI-RESTEndpoints
http://docs.ckan.org/en/2.8/api/index.html#authentication-and-api-keys  

postman
localhost:8080/rest/items/4/bitstreams?name=angabe.pdf&description=text

header:
rest-dspace-token


postman login (default user vagrant) für 6.x
http://localhost:8080/rest/login?email=dspacedemo%2Badmin@gmail.com&password=vagrant