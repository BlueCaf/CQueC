cat <<EOF > src/main/resources/application.properties
spring.application.name=cquec
server.port=80
spring.data.redis.host=<Redis Host>
spring.data.redis.port=<Redis Port>
spring.data.redis.password=""
spring.data.redis.timeout=2000
EOF

chmod +x gradlew
./gradlew clean build --no-daemon
