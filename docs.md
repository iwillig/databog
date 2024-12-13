<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en-US" xml:lang="en-US">
<head>
  <meta charset="utf-8" />
  <meta name="generator" content="pandoc" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes" />
  <title></title>
  <style>
    code{white-space: pre-wrap;}
    span.smallcaps{font-variant: small-caps;}
    div.columns{display: flex; gap: min(4vw, 1.5em);}
    div.column{flex: auto; overflow-x: auto;}
    div.hanging-indent{margin-left: 1.5em; text-indent: -1.5em;}
    /* The extra [class] is a hack that increases specificity enough to
       override a similar rule in reveal.js */
    ul.task-list[class]{list-style: none;}
    ul.task-list li input[type="checkbox"] {
      font-size: inherit;
      width: 0.8em;
      margin: 0 0.8em 0.2em -1.6em;
      vertical-align: middle;
    }
  </style>
</head>
<body>
<header id="title-block-header">
<h1 class="title">Databog Documentation</h1>
<p class="author">Ivan Willig</p>
<div class="abstract">
<div class="abstract-title"></div>
Documentation for the databog project.

</div>
</header>
<nav id="TOC" role="doc-toc">
-   [Introduction](#introduction){#toc-introduction}
    -   [Run a fake GraphQL
        Server](#run-a-fake-graphql-server){#toc-run-a-fake-graphql-server}
    -   [Statically check your GraphQL queries and schema
        files.](#statically-check-your-graphql-queries-and-schema-files.){#toc-statically-check-your-graphql-queries-and-schema-files.}
    -   [GraphQL Query
        coverage](#graphql-query-coverage){#toc-graphql-query-coverage}
    -   [Check grammar](#check-grammar){#toc-check-grammar}
    -   [Install](#install){#toc-install}
        -   [Clojure](#clojure){#toc-clojure}
        -   [Pandoc](#pandoc){#toc-pandoc}
    -   [babashka](#babashka){#toc-babashka}

</nav>
# Introduction

TODO This is still inspirational. Using a document first approach.

This document is a collection of essays and tutorials about building
data driven APIs. It covers REST, GraphQL and more. Included in this
project is a tool called databog. Its similar to pandoc, but for APIs
and data modeling, and validation

Included in the project are a collection of tools for parsing OpenAPI
and GraphQL schemas and working with them.

With Databog you can write an API in GraphQL, start a command line
server and have a working GraphQL or REST API in seconds. Databog will
inspect your schema and generated sensible data. You can then use
databog to iteration your web application development.

## Run a fake GraphQL Server

TODO

``` shell
databog graphql server --schema-file schema.graphql
```

## Statically check your GraphQL queries and schema files.

TODO

``` shell
databog graphql check --schema-file schema.graphql --queries queries
```

## GraphQL Query coverage

TODO

``` shell
databog graphql coverage --schema-file schema.graphql --queries queries
```

## Check grammar

TODO

    databog graphql check-grammar --schema-file schema.graphql --queries queries

![](plantuml-images/1a463f399d09a4179740b726214d201b4928ddfd.svg)

## Install

### Clojure

[Clojure cli](https://clojure.org/guides/install_clojure)

``` shell
brew install pandoc
```

### Pandoc

[Pandoc](https://pandoc.org/)

``` shell
brew install pandoc
```

## babashka

[babashka](https://babashka.org/)

``` shell
brew install borkdude/brew/babashka
```

</body>
</html>
