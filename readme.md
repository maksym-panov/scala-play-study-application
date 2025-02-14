# Demo Scala 2 / Play 3 REST API Application

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=maksym-panov_scala-play-study-application&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=maksym-panov_scala-play-study-application)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=maksym-panov_scala-play-study-application&metric=bugs)](https://sonarcloud.io/summary/new_code?id=maksym-panov_scala-play-study-application)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=maksym-panov_scala-play-study-application&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=maksym-panov_scala-play-study-application)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=maksym-panov_scala-play-study-application&metric=coverage)](https://sonarcloud.io/summary/new_code?id=maksym-panov_scala-play-study-application)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=maksym-panov_scala-play-study-application&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=maksym-panov_scala-play-study-application)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=maksym-panov_scala-play-study-application&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=maksym-panov_scala-play-study-application)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=maksym-panov_scala-play-study-application&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=maksym-panov_scala-play-study-application)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=maksym-panov_scala-play-study-application&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=maksym-panov_scala-play-study-application)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=maksym-panov_scala-play-study-application&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=maksym-panov_scala-play-study-application)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=maksym-panov_scala-play-study-application&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=maksym-panov_scala-play-study-application)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=maksym-panov_scala-play-study-application&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=maksym-panov_scala-play-study-application)

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
curl http://localhost/api/study/v0/platform/health
```
You should receive
```json
{
  "payloadType": "dto.HealthResponseDto",
  "payload": {
    "status":"HEALTHY"
  }
}
```
3. Run linting
```shell
sbt scalafmt
```
