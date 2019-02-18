package com.bmacedo.hiremenews.sources

import android.content.Intent
import android.net.Uri
import androidx.navigation.findNavController
import com.airbnb.epoxy.EpoxyController
import com.bmacedo.hiremenews.R
import com.bmacedo.hiremenews.models.domain.NewsSource
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
                onSourceClicked { view ->
                    val action = NewsSourcesFragmentDirections.openArticlesOfSource(
                        source.id,
                        source.name ?: view.context?.getString(R.string.news_article_title)
                    )
                    view.findNavController().navigate(action)
                }
                onSourceUrlClicked { view ->
                    if (source.url != null) {
                        val context = view.context
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(source.url)
                        context?.startActivity(intent)
                    }
                }
            }
        }
    }


}