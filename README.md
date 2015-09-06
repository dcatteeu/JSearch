JSearch is a Java library with algorithms to find a path between two vertices of a graph. It provides classic search algorithms (depth-first,
breadth-first, A*, ...) which can be run on any problem that
implements the `SearchProblemInterface`. [PCP Solver](https://github.com/dcatteeu/pcpsolver) is an example of an application.

Status: Usable but lacks documentation.

# Source code

The [source code](https://github.com/dcatteeu/jsearch/) is available on GitHub and is licensed under [GPLv3](http://www.gnu.org/licenses/gpl.html).

## Dependencies

* Java 6
* JUnit 3.8.1 for the unit tests
* Maven3 to build

## Install

1. Clone repository
2. Build with `mvn package`

## Use

1. Add the JAR you builded to your classpath.
2. Extend `AbstractSearchProblem` and implement the `SearchProblemInterface`. See `Graph` for an example.
3. Call one of the predefined search algorithms. See `Example1` for an example.

## Bugs, issues, suggestions, ...

Please [report](https://github.com/dcatteeu/jsearch/issues/new) any bugs or suggestions via GitHub.

# Alternatives

The following three Java graph libraries are Open Source Software.

* [JSL](http://jsl.sourceforge.net) serves the same purpose as JSearch, but is more limited.
* [JGraphT](http://jgrapht.org) is a graph library that provides mathematical graph-theory objects and algorithms.
* [JGraphX](https://github.com/jgraph/jgraphx) is a Java Swing diagramming (graph visualisation) library.
