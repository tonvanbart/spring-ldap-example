[![Build Status](https://travis-ci.com/tonvanbart/spring-ldap-example.svg?token=m6sYUWpdsZjJBPpPVpoi&branch=master)](https://travis-ci.com/tonvanbart/spring-ldap-example)

## Spring LDAP login example

This project shows:
* how to configure Spring Boot to log in with credentials from LDAP
and roles derived from LDAP groups
* how to run this locally with an embedded server (switched on via Spring profile)
* how to pre-populate the embedded LDAP with test data.

### Usage
This setup can be helpful if you want to have an LDAP enabled project that you can run locally against a 
directory server where you can control the contents. This setup can also be used for integration tests.

Running the project:

    mvn clean spring-boot:run
    
After the project starts, visit http://localhost:8080/principal or http://localhost:8080/userinfo in a browser. You will be redirected
to a login page; the embedded LDAP server has two users `bartt` and `singhs`, both with password `test`. Log in with either
user and you will get a JSON response with all the security information that was 
retrieved from the LDAP.

### Moving parts; bind login verification
The setup works as follows: the Spring Boot plugin configuration in `pom.xml` activates a profile `embedldap`. 
This causes the properties for that profile `application-embedldap.properties` to be read in addition to the normal `application.properties`.
The profile application properties create an embedded LDAP server, tell it to initialize from `test-schema.ldif`
and override the configuration of `ldap.url` to point to the embedded server.

In `application.properties` the values used in `SecurityConfig.java` are set up. Logon works by trying to connect to the 
LDAP with the provided username and password, using `ldap.user.dn.pattern` to create a distinguished name from the login user.
The configuration for `ldap.groups.search.base` sets up where to look for groups, and in `SecurityConfig` we set
up which property is used to set up group membership. This last property is not externalized for now but could be.
<br>
If it's able to connect to the LDAP with the provided credentials, Spring Security will take all group memberships and 
turn them into roles by uppercasing them and putting `ROLE_` in front
as can be seen in the output page. This (default) behaviour can be customized.

`TestController` shows two ways in which the user details can be retrieved - simplest one is to just 
add an argument of type `Principal` to your controller method, which will be populated by Spring.

In one of our projects this setup was quite helpful as it was not possible to connect to the cluster LDAP
from our development machines. If you make `test-schema.ldif` reflect your company LDAP tree then you can verify
your login and roles setup locally.

### License
This project is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)


