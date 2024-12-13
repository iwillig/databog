# Introduction

TODO This is still inspirational

This document is a collection of essays and tutorials about building
data driven APIs. It covers REST, GraphQL and more. Included in the
project are a collection of tools for parsing OpenAPI and GraphQL
schemas and working with them.

With Databog you can write an API in GraphQL, start a command line
server and have a working GraphQL or REST API in seconds. Databog will
inspect your schema and generated sensible data. You can then use
databog to iteration your web application development.

## Run a fake GraphQL Server

TODO

```shell
databog graphql server --schema-file schema.graphql
```


## Statically check your GraphQL queries and schema files.

TODO

```shell
databog graphql check --schema-file schema.graphql --queries queries
```


~~~plantuml
@startmindmap
* databog
** GraphQL
*** Relay API
*** Connections API
*** Globals IDs
*** Mutations
** REST API
*** HATOS
*** HTMLX
*** OpenAPI
** SQL
*** SQLite
** Datalog
@endmindmap
~~~

## Introduction Rest API

REST API or RESTFuls API have been common for decades now. There is not
standard for REST, though most people point to Fieldings paper from
the 90's as the definitive version of REST.

REST uses the underlying HTTP transport layers to communicate error to
the client. As a result it has incredible adoption because it used the
"language of the web".

Originally REST and HTML where combined with browsers to provide an
linking technology called hypermedia. HTML tags like "forms" could
produce POST to add new objects

The original REST spec was later fused with the JSON Data transport
language and gave rise to modern "RESTFul" APIs. Most of these REST
API

There is disagreement among engineers if JSON based REST APIS are
faithful implementations of the rest spec and Fieldings ideas. Some
people use the term "Restful" to denote these JSON APIs.

There are serveral more recent attempts at patching

## Introduction GraphQL

GraphQL was released by
[meta](https://en.wikipedia.org/wiki/GraphQL#History) in 2015. It has
since grown to become a commonly used method for building HTTP APS for
web applications.

It is often combined with TypeScript and Rest.js to provide a typed
save method for building Single Page Applications.

```graphql



```
