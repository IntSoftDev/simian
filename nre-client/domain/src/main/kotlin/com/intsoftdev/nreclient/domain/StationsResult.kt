package com.intsoftdev.nreclient.domain

data class StationsResult(
    val version: Version,
    val stations : List<StationModel>
)