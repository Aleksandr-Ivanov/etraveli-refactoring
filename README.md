# Results description from Aleksandr:

Code could be cloned with repo from github and also pull-request was created to show differences history on github web-interface comparing to initial state.

Refactored to follow three-tier backend architecture with service layer containing logic, data access layer to connect to data stores and domain model layer describing business models.

Refactoring is made to Java 8 version bearing in mind, that existing systems could have potentially not the most recent version of sources. Further in the future code could be easily adapted to higher version of Java

Initially added acceptance test suit to ensure functionality is not broken during refactoring, then added unit test suit during the development.

Added scripts for ease of compilation and run

To compile:

```
./compile.sh
```

To run:

```
./run.sh
```

Combined to speed up testing:
```
./compile.sh && ./run.sh
```

**Manual installation in case of necessity:**

- To clean-up compilation folder:
```
rm -rf compiled/
```


- To compile:
```
javac src/dao/*.java src/domain/*.java src/service/*.java src/service/calculation/*.java src/*.java -d compiled/ -source 8 -target 8
```

- To run:
```
java -cp compiled/ Main
```

Possible necessary steps:

- Make scripts executable:

```
chmod +x compile.sh
chmod +x run.sh
```

- To remove 
"warning: [options] bootstrap class path not set in conjunction with -source 8"

Comment out in compile.sh:

```
javac src/dao/*.java src/domain/*.java src/service/*.java src/service/calculation/*.java src/*.java -d compiled #-source 8 -target 8
```
Prospective evolving steps for the project:

- externalize calculation numbers to properties for ease of configuration 
- introduce project specific exceptions
- evolve logging

# Initial description
# Refactoring Java

The code creates an information slip about movie rentals.
Rewrite and improve the code after your own liking.

Think: you are responsible for the solution, this is a solution you will have to put your name on.


## Handing in the assignment

Reason how you have been thinking and the decisions you took. 
You can hand in the result any way you feel (git patch, pull-request or ZIP-file).
Note: the Git history must be included.


## To run the test:

```
javac src/*.java
java -cp src Main
```
