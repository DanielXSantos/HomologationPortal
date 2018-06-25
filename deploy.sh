mvn clean package
rm Deployment/*.jar
cp target/*.jar Deployment/
cd Deployment
docker build --build-arg JAR_FILE=lab-0.0.1-SNAPSHOT.jar -t homologation-portal .
