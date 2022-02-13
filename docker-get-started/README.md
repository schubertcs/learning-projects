# Docker - Get Started

This directory contains files related to the Get Started guide of docker: <https://docs.docker.com/get-started/>

## Interesting New Learnings

### Secret Management

You should nor use environment variables to pass secrets into your application: <https://diogomonica.com/2017/03/27/why-you-shouldnt-use-env-variables-for-secret-data/>

Instead, the application should read secrets from files.
Orchestration support for this are [`docker secret`](https://docs.docker.com/engine/reference/commandline/secret/) on docker swarm, or [`kubectl create secret`](https://kubernetes.io/docs/concepts/configuration/secret/) on Kubernetes.

### Multi-stage Builds

To separate build-time and runtime dependencies, multi-stage build can help.

For example, the following _Dockerfile_ builds a Java application and copies over the result into a tomcat container.

```Dockerfile
# syntax=docker/dockerfile:1
FROM maven AS build
WORKDIR /app
COPY . .
RUN mvn package

FROM tomcat
COPY --from=build /app/target/file.war /usr/local/tomcat/webapps 
```

This way, the build tool _maven_ does not become part of the final image, reducing the attack surface.

### Database on Kubernetes

An interesting project seems to be [Vitess](https://vitess.io/).
This support automated scaling of databases running on Kubernetes.
