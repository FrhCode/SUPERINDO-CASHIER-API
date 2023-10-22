FROM openjdk:17-ea-30-jdk-buster

RUN apt update -y \ 
		&& apt-get install -y \ 
		dos2unix

RUN wget https://dlcdn.apache.org/maven/maven-3/3.9.4/binaries/apache-maven-3.9.4-bin.tar.gz && \
    tar -xvf apache-maven-3.9.4-bin.tar.gz -C /opt && \
    rm apache-maven-3.9.4-bin.tar.gz

# Set environment variables for Maven
ENV M2_HOME=/opt/apache-maven-3.9.4
ENV PATH=$M2_HOME/bin:$PATH

WORKDIR /app

COPY . .

RUN mvn clean install

EXPOSE 8080

# CMD ["sleep", "infinity"]

RUN mv target/cashier-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "cashier-0.0.1-SNAPSHOT.jar"]
