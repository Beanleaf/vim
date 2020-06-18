# VIM ![Java CI & Build](https://github.com/mathiasbosman/vim/workflows/Java%20CI/badge.svg)
Vizit Inventory Management


## Build / Development
### Code style
The code style used is [that of Google](https://github.com/google/styleguide).

## Deployment
CD to the `vim-web` module and execute below (either with or without promote)
```
mvn clean package appengine:deploy -Dapp.deploy.projectId=beanleaf-vizit
mvn clean package appengine:deploy -Dapp.deploy.projectId=beanleaf-vizit -Dapp.deploy.promote=false
```

### Database

Database connection parameters are set in `application.properties`.
```properties
spring.datasource.url=jbcUrl
spring.datasource.username=username
spring.datasource.password=password
```

### Docker
When running locally the `docker-compose.yml` file can be used to set up a database and mail container.

### Resource caching
Some browsers love to cache static resources such as CSS en JS files.
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
