package com.bmacedo.hiremenews.sources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bmacedo.hiremenews.R
import com.bmacedo.hiremenews.app.base.BaseFragment
import com.bmacedo.hiremenews.models.NewsSource
import kotlinx.android.synthetic.main.fragment_news_sources.*

class NewsSourcesFragment : BaseFragment() {

    private val listController = NewsSourcesController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_sources, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        news_sources_list.setController(listController)
        listController.updateSources(
            listOf(
                NewsSource(
                    id = "1",
                    name = "One",
                    description = "description",
                    url = "google.com"
                )
            )
        )
    }
}
