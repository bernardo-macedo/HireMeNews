package com.bmacedo.hiremenews.sources

import androidx.navigation.findNavController
import com.airbnb.epoxy.EpoxyController
import com.bmacedo.hiremenews.R
import com.bmacedo.hiremenews.models.NewsSource
import com.bmacedo.hiremenews.newsSourceItem

class NewsSourcesController : EpoxyController() {

    private val sourceList = mutableListOf<NewsSource>()

    fun updateSources(sources: List<NewsSource>) {
        sourceList.clear()
        sourceList.addAll(sources)
        requestModelBuild()
    }

    override fun buildModels() {
        sourceList.forEach { source ->
            newsSourceItem {
                id(source.id)
                source(source)
                onSourceClicked { view -> view.findNavController().navigate(R.id.newsArticlesFragment) }
            }
        }
    }


}