# Demo Scala 2 / Play 3 REST API Application

## Requirements
- sbt version: 1.10.7 
- Scala version: 2.13.16

## Using the Application
1. Run the application using Scala Build Tool CLI
```shell
sbt -Dhttp.port=80 run
```
2. Check health
```shell
curl localhost/api/study/v0/platform/health
```
You should receive
```json
{ 
  "status": "HEALTHY" 
}
```
