package com.bmacedo.hiremenews.sources

import android.content.Context
import androidx.navigation.findNavController
import com.airbnb.epoxy.EpoxyController
import com.bmacedo.hiremenews.R
import com.bmacedo.hiremenews.models.domain.NewsSource
import com.bmacedo.hiremenews.newsSourceItem

class NewsSourcesController(private val context: Context?) : EpoxyController() {

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
                onSourceClicked { view ->
                    val action = NewsSourcesFragmentDirections.openArticlesOfSource(
                        source.id,
                        source.name ?: context?.getString(R.string.news_article_title)
                    )
                    view.findNavController().navigate(action)
                }
            }
        }
    }


}