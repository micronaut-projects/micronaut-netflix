# Micronaut Netflix

[![Build Status](https://travis-ci.org/micronaut-projects/micronaut-netflix.svg?branch=master)](https://travis-ci.org/micronaut-projects/micronaut-netflix)

This project includes integration between Micronaut and Netflix OSS components.

NOTE: Many Netflix OSS projects have been placed in maintenance mode, so the components here are also in maintenance mode.

## Documentation

See the [Documentation](https://micronaut-projects.github.io/micronaut-netflix/latest/guide) for more information.

## Snapshots and Releases

Snaphots are automatically published to [JFrog OSS](https://oss.jfrog.org/artifactory/oss-snapshot-local/) using [Github Actions](https://github.com/micronaut-projects/micronaut-netflix/actions).

See the documentation in the [Micronaut Docs](https://docs.micronaut.io/latest/guide/index.html#usingsnapshots) for how to configure your build to use snapshots.

Releases are published to JCenter and Maven Central via [Github Actions](https://github.com/micronaut-projects/micronaut-netflix/actions).

A release is performed with the following steps:

* [Edit the version](https://github.com/micronaut-projects/micronaut-netflix/edit/master/gradle.properties) specified by `projectVersion` in `gradle.properties` to a semantic, unreleased version. Example `1.0.0`
* [Create a new release](https://github.com/micronaut-projects/micronaut-netflix/releases/new). The Git Tag should start with `v`. For example `v1.0.0`.
* [Monitor the Workflow](https://github.com/micronaut-projects/micronaut-netflix/actions?query=workflow%3ARelease) to check it passed successfully.
* Celebrate!