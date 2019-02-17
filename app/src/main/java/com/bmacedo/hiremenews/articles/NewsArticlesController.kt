package com.bmacedo.hiremenews.articles

import com.airbnb.epoxy.EpoxyController
import com.bmacedo.hiremenews.models.domain.NewsArticle
import com.bmacedo.hiremenews.newsArticleItem

class NewsArticlesController : EpoxyController() {

    private val articleList = mutableListOf<NewsArticle>()

    fun updateArticles(articles: List<NewsArticle>) {
        articleList.clear()
        articleList.addAll(articles)
        requestModelBuild()
    }

    override fun buildModels() {
        articleList.forEach { article ->
            newsArticleItem {
                id(article.url)
                article(article)
            }
        }
    }
}