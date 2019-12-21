# [util](https://julianmendez.github.io/util/)
*Utility classes*

[![build](https://travis-ci.org/julianmendez/util.png?branch=master)](https://travis-ci.org/julianmendez/util)
[![coverage](https://coveralls.io/repos/github/julianmendez/util/badge.svg?branch=master)](https://coveralls.io/github/julianmendez/util?branch=master)
[![maven central](https://maven-badges.herokuapp.com/maven-central/de.tu-dresden.inf.lat.util/util-parent/badge.svg)](https://search.maven.org/#search|ga|1|g%3A%22de.tu-dresden.inf.lat.util%22)
[![license](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.txt)

`util` is a collection of modules containing utility classes to be used across different projects.


## Download

* [The Central Repository](https://repo1.maven.org/maven2/de/tu-dresden/inf/lat/util/)

* Map module

```xml
<dependency>
  <groupId>de.tu-dresden.inf.lat.util</groupId>
  <artifactId>util-map</artifactId>
  <version>0.1.0</version>
</dependency>
```


## Usage

Each module of `util` can be used independently.


## Source code

To checkout and compile the project, use:

```
$ git clone https://github.com/julianmendez/util.git
$ cd util
$ mvn clean install
```

To compile the project offline, first download the dependencies:

```
$ mvn dependency:go-offline
```

and once offline, use:

```
$ mvn --offline clean install
```

The bundles uploaded to [Sonatype](https://oss.sonatype.org/) are created with:

```
$ mvn clean install -DperformRelease=true
```

and then on each module:

```
$ cd target
$ jar -cf bundle.jar util-*
```

and on the main directory:

```
$ cd target
$ jar -cf bundle.jar util-parent-*
```

The version number is updated with:

```
$ mvn versions:set -DnewVersion=NEW_VERSION
```

where *NEW_VERSION* is the new version.


## Author

[Julian Mendez](https://julianmendez.github.io)


## License

This software is distributed under the [Apache License Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt).


## Release notes

See [release notes](https://julianmendez.github.io/util/RELEASE-NOTES.html).


### Modules

`util` is composed by the following modules:

* `util-map` : contains utilitity classes for maps.


## Contact

In case you need more information, please contact @julianmendez .


