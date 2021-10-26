<!--
SPDX-FileCopyrightText: 2021 Eric Neidhardt
SPDX-License-Identifier: CC-BY-4.0
-->
<!-- markdownlint-disable MD022 MD032 MD024-->
<!-- markdownlint-disable MD041-->
# About

RX-address-services is a simple rxJava2 wrapper around some address resolution and geocoding services.
Currently supported:

* HERE - geolocation service of here

## Gradle

```gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.github.ericneid:rx-address-service:0.1.0'
}
```

## Usage

```kotlin
val service = HereApiService("apikey")

val result = service.getSuggestResult("Ber")
```

## Question or comments

Please feel free to open a new issue:
<https://github.com/EricNeid/rx-address-services/issues>
