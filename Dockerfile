FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY SimpleBackend.java .
RUN javac SimpleBackend.java
EXPOSE 8080
CMD ["java", "SimpleBackend"]
