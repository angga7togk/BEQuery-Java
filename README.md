# BEQuery-Java - Library Query Minecraft Bedrock Server

Java library that allows to query other minecraft bedrock servers to get information options

Base Code (PHP Language for Pocketmine) >>> https://github.com/jasonw4331/libpmquery

## Maven / Dependencies

Required Java 8 or higher to use this library

### Repository
```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```

### Dependency
```xml
<dependency>
    <groupId>com.github.angga7togk</groupId>
    <artifactId>BEQuery-Java</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
### Recommended
add it to your project's pom.xml, this will build your project and automatically include your .jar / dependencies so no need to bother installing external libraries
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>3.2.4</version>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                    <configuration>
                        <createDependencyReducedPom>false</createDependencyReducedPom>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```


## Example
```java
try{
    // host
    // port default = 19132
    // timeout default = 4
    BEQuery query = BEQuery.connect("localhost", 19132, 4);

    // information list
    System.out.println(query.getGameName());
    System.out.println(query.getHostName());
    System.out.println(query.getProtocol());
    System.out.println(query.getVersion());
    System.out.println(query.getPlayers());
    System.out.println(query.getMaxPlayers());
    System.out.println(query.getServerId());
    System.out.println(query.getMap());
    System.out.println(query.getGameMode());
    System.out.println(query.getNintendoLimited());
    System.out.println(query.getIpv4Port());
    System.out.println(query.getIpv6Port());
    System.out.println(query.getExtra());
}catch (BEQueryException e){
    e.printStackTrace();
}

```
