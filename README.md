# VIM
![Java CI](https://github.com/mathiasbosman/vim/workflows/Java%20CI/badge.svg)
![Build and Deploy to GKE](https://github.com/mathiasbosman/vim/workflows/Build%20and%20Deploy%20to%20GKE/badge.svg)
Vizit Inventory Management
### Database

Database connection parameters are set in `application.properties`
```properties
spring.datasource.url=jbcUrl
spring.datasource.username=username
spring.datasource.password=password
```

## Build / Deployment
### Code style
The code style used is [that of Google](https://github.com/google/styleguide).
### Maven build
```
mvn clean install
```

### Resource caching
Some browsers (such as Chrome) love to cache static resources such as CSS en JS files.
To disable this (for local development) an extra spring parameter can be added:
```properties
spring.resources.chain.strategy.content.enabled=true
```

## Resources
### Backend
* [Spring Boot](https://spring.io/guides/gs/serving-web-content/)
### CSS frameworks
* [Primer CSS](https://primer.style/css)
### Tools
* [IntelliJ IDE](https://www.jetbrains.com/idea/)
