# Service Discovery

## Apache Zookeeper

ZooKeeper is a centralized service for maintaining configuration information, naming, providing distributed synchronization, and offering group services. These services are essential for distributed applications, but their implementation is often complex, leading to bugs and management difficulties. As a result, applications usually implement them poorly, making them fragile and hard to manage. Different implementations further complicate management when applications are deployed.

ZooKeeper simplifies these services by providing a very simple interface to a centralized coordination service that is both distributed and highly reliable.

## Apache Zookeeper in Spring Cloud

Spring Cloud Zookeeper integrates Apache Zookeeper with Spring Boot applications through autoconfiguration and binding to the Spring Environment. With a few simple annotations, you can quickly enable and configure common patterns like Service Discovery and Distributed Configuration, facilitating the creation of large distributed systems.

## Apache Zookeeper Service Discovery

Service Discovery is crucial in microservice architectures. Manually configuring each client or using conventions can be complex and fragile. Curator, a Java library for Zookeeper, offers Service Discovery via an extension. Spring Cloud Zookeeper utilizes this extension for service registration and discovery.

### Apache Zookeeper services

By adding the dependency org.springframework.cloud
, you enable an automatic configuration that establishes Spring Cloud Zookeeper Discovery.

#### Maven dependencies

```
    <properties>
            <java.version>21</java.version>
            <spring-cloud.version>2023.0.2</spring-cloud.version>
    </properties>
    
    <dependencies>
            
            ...
            
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
            </dependency>
            
            ...
    
    </dependencies>
    
    <dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-dependencies</artifactId>
                    <version>${spring-cloud.version}</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
            </dependencies>
        </dependencyManagement>
```

#### Service code

In a Java Spring Boot application using Zookeeper, the @EnableDiscoveryClient annotation enables service registration and discovery. It allows the application to automatically register itself with Zookeeper as a service and discover other services that are registered in the same distributed environment.

```
@SpringBootApplication
@EnableDiscoveryClient
public class TimeServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(TimeServiceApplication.class, args);
	}

}
```

Spring Cloud allows clients to access a registered service.

```
@RestController
public class TimeController {

    @GetMapping("/time")
    public String getCurrentTime() {
        return LocalTime.now().toString();
    }
}
```

#### configuration

service configuration file:

* server.port=8080: The application uses port 8080 to accept HTTP requests.
* spring.application.name=time-service: application name
* spring.cloud.zookeeper.connect-string=zookeeper:2181: Connects to Zookeeper located at zookeeper on port 2181.
* spring.cloud.zookeeper.discovery.enabled=true: Enables service discovery via Zookeeper.
* spring.cloud.zookeeper.discovery.root=/services: Services are registered in Zookeeper under the path /services.

```
server.port=8080

spring.application.name=time-service

spring.cloud.zookeeper.connect-string=zookeeper:2181
spring.cloud.zookeeper.discovery.enabled=true
spring.cloud.zookeeper.discovery.root=/services
```

#### Docker configuration

To containerize the service add a Dockerfile.

```
  zookeeper:
    image: zookeeper:3.7
    container_name: zookeeper
    ports:
      - "2181:2181"

  time-service:
    build:
      context: ./time-service
      dockerfile: Dockerfile
    image: time-service
    ports:
      - "8080:8080"
    environment:
      SPRING_PROFILES_ACTIVE: docker
      SPRING_CLOUD_ZOOKEEPER_CONNECT_STRING: zookeeper:2181
    depends_on:
      - zookeeper
```

Once everything in set up, we can run the example project with:

```
mvn clean package
docker-compose up --build
```

### Test

Make sure you have ZooKeeper installed and running on your system. You can download ZooKeeper from the official website and follow the installation instructions.

After that you can run a client zookeeper:

```
.\bin\zkCli.cmd -server localhost:2181
```

Then you can verify the registered service:

```
[zk: localhost:2181(CONNECTED) 0] ls /
[services, zookeeper]
[zk: localhost:2181(CONNECTED) 1] ls /services
[time-service]
```

## Resources

- https://zookeeper.apache.org/
- https://cwiki.apache.org/confluence/display/ZOOKEEPER/Index
- https://spring.io/projects/spring-cloud-zookeeper
