package com.bmacedo.hiremenews.models.mappers

import com.bmacedo.hiremenews.models.api.NewsSourceListResponse
import com.bmacedo.hiremenews.models.domain.NewsSource

class NewsSourceMapper : DomainMapper<NewsSourceListResponse, List<NewsSource>> {

    override fun toDomainModel(response: NewsSourceListResponse?): List<NewsSource>? {
        if (response == null) return emptyList()

        val newsSources = mutableListOf<NewsSource>()

        response.sources.forEach { sourceItem ->
            newsSources.add(
                NewsSource(
                    id = sourceItem.id,
                    name = sourceItem.name,
                    description = sourceItem.description,
                    url = sourceItem.url,
                    category = sourceItem.category,
                    language = sourceItem.language,
                    country = sourceItem.country
                )
            )
        }

        return newsSources
    }
}