What
----

JSearch is a Java library with classic search algorithms (depth-first,
breadth-first, A*, ...). These can be run on any problem as long as it
implements the SearchProblemInterface.


Dependencies
------------

Java 6
JUnit 3.8.1 for the unit tests
Maven3 to build


Install
-------

1. Clone repository
2. Build with "mvn package"


Use
---

1. Add the JAR you builded to your classpath 

2. Extend AbstractSearchProblem and implement the
SearchProblemInterface. See Graph for an example.

3. Call one of the predefined search algorithms. See Example1 for an
example.


Status
------

Usable but lacks documentation.
