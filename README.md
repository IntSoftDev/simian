# Simian

[![](https://jitpack.io/v/com.intsoftdev/simian.svg)](https://jitpack.io/#com.intsoftdev/simian)

An Android library for integrating with UK Train stations data

## About

Simian requires at minimum Java 8 and Android API 21+.

## Get Started

Add this in your app's build.gradle file:

```
dependencies {
    implementation 'com.intsoftdev:simian:0.2'
}
```
In the `Application::OnCreate` class add these lines
```
StationsClientInitializer(this).initialise()
```
Create an instance of `StationsClient`

```
var client = StationsClient()
var stationsObservable: ReturnState<StationsResult> = client.getAllStations()
// subscribe to the observable
// a list of StationModel is returned
var stations : List<StationModel> = returnState.data.stations
