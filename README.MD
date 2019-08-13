# Pet Store Demo

Project consists of 2 parts - Micro-Services and UI
Micro-Services on it's turn consists of 2 parts - Authentication Service and REST services with business logic.
Front End from [PetStoreUI](./FrontEnd/PetDemoUi) is embedded into REST microservice so no additional components is required.

# External dependencies

## Redis
Authentication service using Redis server to store/modify distributed security session.
For Debian based application use commands below to install & run redis

```
sudo apt-get install redis-server
sudo service redis-server start
```

## Reverse Proxy server

Reverse proxy server is used to map authentication and rest micro-services into same domain and avoid CORS problem with a browser.
Configuration for Apache 2 HTTP service is next:

```
<Location />
        ProxyPass http://localhost:8080/
        ProxyPassReverse http://localhost:8080/
</Location>

<Location /api/v1>
        ProxyPass http://localhost:8080/api/v1
        ProxyPassReverse http://localhost:8080/api/v1
</Location>

<Location /login>
        ProxyPass http://localhost:8081/login
        ProxyPassReverse http://localhost:8081/login
</Location>

```

# Build

```
cd DemoServices
mvn clean install -P release
```

# Run

1. Start authentication service
```
java -jar PetStoreDemoAuthServer/target/auth-server-1.0.0-RELEASE.jar --cookie.domain=localhost
```
 
2. Start REST service
```
java -jar PetStoreDemoRest/target/rest-secure-1.0.0.jar
```

3. Open "anonymous" broser session with empty cookies and navigate on
http://localhost8080/

Initially browser will be redirected on http://localhost/login for login. 
The list of credentials is next:


|  Username  | Password |      Role              |
|------------|----------|------------------------|
| user       | password | Read Only user         |
| pet_keeper | password | View/Add pets          |
| manager    | password | View/Add/Delete pets   |


Click login. Browser will be redirected back to http://localhost/ where it will be possible view/add/remove pets into the store based on granted role.

To login with different credentials re=open browser window in "anoymous" mode.


