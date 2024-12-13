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
