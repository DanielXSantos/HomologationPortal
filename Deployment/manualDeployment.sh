#Create Mysql Container
#docker run --name mysql -e MYSQL_ROOT_PASSWORD=123 -d mysql:8

#Change privileges of user
#docker exec -it mysql /bin/bash
#mysql -u root -p123 -e "ALTER USER root IDENTIFIED WITH mysql_native_password BY '123';"
#exit
#exit

#Generate jar for the project
mvn clean package
rm Deployment/*.jar
cp target/*.jar Deployment/

#Create Image of Homologation Portal
cd Deployment
docker build --build-arg JAR_FILE=lab-0.0.1-SNAPSHOT.jar -t homologation-portal .
docker image save -o homologation-portal.tar homologation-portal
scp homologation-portal.tar lclaudio@10.51.52.135:/home/lclaudio

#Now all of commands have to be executed in cloud:

#Make a backup for Mysql and clean the database
docker exec -it mysql /bin/bash
mysqldump -u root -p --all-databases > mysqlbackup.sql
mysql -u root -p
drop database portal;
create database portal;
exit
exit
docker cp mysql:/mysqlbackup.sql ./

#If have a homologation-portal container running
docker container stop homologation-portal
docker container rm homologation-portal

#If have a homologation-portal image
docker image rm homologation-portal

#Load image of Homologation Portal
docker image load < homologation-portal.tar

#Create Volume for Homologation Portal
#Just execute in the first time
#Verify if exists with:  docker volume ls
#docker volume create homologation-portal

#Run Container of Homologation Portal
docker run -d --name homologation-portal --link mysql:mysql -p 8080:8080 -v homologation-portal:/uploads homologation-portal

#Recover mysql backup
docker cp ./mysqlbackup.sql mysql:/
docker exec -it mysql /bin/bash
mysql -u root -p < mysqlbackup.sql
exit

