FROM armv7/armhf-openjdk:latest

# Install OpenJDK 8
RUN apt-get update && \
    apt-get upgrade
