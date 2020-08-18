##Updating React API client

1. run swagger-cli bundle ../paradine/src/main/resources/swagger/api.yml -t yaml > ../paradine/src/main/resources/swagger/api-resolved.yml
2. run ./node_modules/.bin/restful-react import --file ../paradine/src/main/resources/swagger/api-resolved.yml --output src/apiclient/paradine-client.tsx
3. rm ../paradine/src/main/resources/swagger/api-resolved.yml
