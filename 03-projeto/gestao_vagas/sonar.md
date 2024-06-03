
```bash
docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:9.9.0-community
```

Configura manualmente para rodar local e rode o comando abaixo.

```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=gestao_vagas \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=<token>
```

Acesse a interface em localhost:9000