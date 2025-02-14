# Demo Scala 2 / Play 3 REST API Application

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=maksym-panov_scala-play-study-application&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=maksym-panov_scala-play-study-application)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=maksym-panov_scala-play-study-application&metric=bugs)](https://sonarcloud.io/summary/new_code?id=maksym-panov_scala-play-study-application)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=maksym-panov_scala-play-study-application&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=maksym-panov_scala-play-study-application)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=maksym-panov_scala-play-study-application&metric=coverage)](https://sonarcloud.io/summary/new_code?id=maksym-panov_scala-play-study-application)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=maksym-panov_scala-play-study-application&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=maksym-panov_scala-play-study-application)

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
3. Run linting
```shell
sbt scalafixAll
```
