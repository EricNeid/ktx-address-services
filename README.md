<!--
SPDX-FileCopyrightText: 2021 Eric Neidhardt
SPDX-License-Identifier: CC-BY-4.0
-->
<!-- markdownlint-disable MD022 MD032 MD024-->
<!-- markdownlint-disable MD041-->
# About

KTX-address-services is a simple flow wrapper around some address resolution and geocoding services.
Currently supported:

* HERE - geolocation service of here
* Nominatim

## Gradle

```gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.github.ericneid:ktx-address-service:0.1.0'
}
```

## Usage

```kotlin
val resultHere = HereApiService("apikey").getSuggestResult("Ber")

val resultNominatim = NominatimApiService().getSearchResults("Ber")
```

## Question or comments

Please feel free to open a new issue:
<https://github.com/EricNeid/ktx-address-services/issues>
