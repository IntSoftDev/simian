package com.intsoftdev.nreclient.data.mapper

/**
 * Interface for model mappers. It provides helper methods that facilitates
 * retrieving of models from outer data source layers
 *
 * @param <T> the cached model input type
 * @param <T> the remote model input type
 * @param <V> the model return type
 */
internal interface Mapper<E, D> {

    fun mapFromEntity(type: E): D

    fun mapToEntity(type: D): E

}