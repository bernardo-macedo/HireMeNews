package com.bmacedo.hiremenews.models.mappers

import com.bmacedo.hiremenews.models.api.GenericResponse

interface DomainMapper<in T : GenericResponse, out R> {

    fun toDomainModel(response: T?): R?

}