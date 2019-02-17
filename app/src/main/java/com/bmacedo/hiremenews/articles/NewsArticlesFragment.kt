package com.bmacedo.hiremenews.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bmacedo.hiremenews.R
import com.bmacedo.hiremenews.app.base.BaseFragment

class NewsArticlesFragment : BaseFragment() {

    val args: NewsArticlesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_articles, container, false)
    }

}
