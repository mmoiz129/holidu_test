# Tree Radius Interview Assignment

Hi there! Congratulations on making it to the next step!

You are given a scaffold application based on Spring Boot to save your time, but you are free to use any other frameworks if you would like to.

Your task is to implement a specific feature as a REST service that uses some 3rd party API.
A service should make an aggregated search of trees (plants, not binary trees) in the circle.

Input:
  - X and Y of the cartesian center point
  - A search radius in meters

Output:
  - Aggregation by "common name" (please see in the documentation on the same page above which field to refer to)

Example of the expected output:
```json
{
    "red maple": 30,
    "American linden": 1,
    "London planetree": 3
}
```

## Run Application

> mvn clean package \
> java -jar target/holidu_test.jar

## To Run Test Cases 
> mvn test 