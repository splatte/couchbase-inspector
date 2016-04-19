# Couchbase Inspector for Android

Library for Android that provides an Activity that can be added to an app to show and edit the contents of a Couchbase database during development.

## Features

Only in proof of concept stage for now:

 * show list of document IDs and their content

## Setup

See demonstration app that uses Couchbase Inspector in `example/`.

 * Add dependency in `build.gradle`.
 * Declare `CouchbaseInspectorActivity` in `AndroidManifest.xml`
 * Start `CouchbaseInspectorActivity` when needed.

## Screenshot
![Example: Couchbase Inspector Activity](https://github.com/splatte/couchbase-inspector/raw/master/screenshot1.png "Couchbase Inspector Activity")
