# Ticket Service Challenge

## Dependencies

* Java
* Gradle
* Spring

## Compiling

```
$ gradle build
```

## Run Tests

```
$ gradle test
```

To generate a coverage report with jacoco - 
```
$  gradle test jacocoTestReport
```

Report will be openable with a web browser from ```build/reports/coverage/index.html```

(Report output location controlled by gradle.build)

## Notes

Assumed no persistence - all data is managed in memory.

If runtime aborts, data is lost.