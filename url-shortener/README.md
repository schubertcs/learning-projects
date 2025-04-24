# URL Shortener

This is a demo project to play around with some technologies.

The goal of this project is to provide a service that allows you to register a given URL and provides a shortened version that can be forwarded to others.
The shortened version will then be mapped back to the original URL, forwarding the original request to it.

## Usage

To run the application locally, use the following command:
```bash
mvn spring-boot:run
```

This then exposes the following API under port 8080:

- Shorten a given URL
  `POST /shorten`
  ```json
  {
    "url": "<url-to-be-shortened>"
  }
  ```


## Techstack

- Kotlin 2.1.10 (compiled to Java 21)
- Spring Boot 3.4.3