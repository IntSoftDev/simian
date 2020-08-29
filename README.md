# Simian

[![](https://jitpack.io/v/com.intsoftdev/simian.svg)](https://jitpack.io/#com.intsoftdev/simian)  &nbsp;![Android CI](https://github.com/IntSoftDev/simian/workflows/Android%20CI/badge.svg)


An Android library for integrating with UK Train stations data

## About

Simian is written in Kotlin and requires minimum Android API 21+.

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
