#VIM [![Build Status](https://cloud.drone.io/api/badges/mathiasbosman/vim/status.svg)](https://cloud.drone.io/mathiasbosman/vim)  [![Build travis](https://travis-ci.org/mathiasbosman/vim.svg?branch=master)](https://travis-ci.org/mathiasbosman/vim)
Vizit Inventory Management
### Database

Database connection parameters are set in `application.properties`
```
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
```
spring.resources.chain.strategy.content.enabled=true
```

### Builders
We currently use Jenkins locally (with the help of ngrok) and Travis.org remotely

## Resources
### Backend
* [Spring Boot](https://spring.io/guides/gs/serving-web-content/)
* [Thymeleaf](https://www.thymeleaf.org/)
### Front-end
* [Primer](https://primer.style/) (+ [octicons](https://octicons.github.com/))
* [Font Awesome icons](https://fontawesome.com/)
### Tools
* [IntelliJ IDE](https://www.jetbrains.com/idea/)
