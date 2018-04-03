mvn clean package
rm Deployment/*.jar
cp target/*.jar Deployment/
cd Deployment
docker build -t homologation-portal .