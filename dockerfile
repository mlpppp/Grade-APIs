# pull a parent layer 
FROM amazoncorretto:17

# specify the working dir in image
  # all following command are run inside the dir 
WORKDIR /app

# COPY from src dir(rel:dockerfile) to image dir(rel:working directory) 
COPY . .

# expose a port of the container (require for docker desktop port mapping)
EXPOSE 8080

# AFTER BUILD: command to run after the container loaded(runtime)
CMD ["./mvnw", "spring-boot:run"]