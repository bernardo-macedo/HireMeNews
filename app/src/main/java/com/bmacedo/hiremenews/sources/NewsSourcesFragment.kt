package com.bmacedo.hiremenews.sources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.bmacedo.hiremenews.R
import com.bmacedo.hiremenews.app.base.BaseFragment
import com.bmacedo.hiremenews.models.domain.NewsSource
import com.bmacedo.hiremenews.utils.Executors
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.fragment_news_sources.*
import javax.inject.Inject

class NewsSourcesFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: NewsSourcesViewModel.Factory

    @Inject
    lateinit var executors: Executors

    private val viewModel: NewsSourcesViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(NewsSourcesViewModel::class.java)
    }

    private val scope: ScopeProvider by lazy { AndroidLifecycleScopeProvider.from(this) }

    private val listController = NewsSourcesController(context?.applicationContext)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_sources, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewComponents()
        if (savedInstanceState == null) {
            loadSources()
        }
        observeViewState()
    }

    private fun initializeViewComponents() {
        news_sources_list.setController(listController)
        news_sources_swipe_to_refresh.setOnRefreshListener { loadSources() }
    }

    private fun loadSources() {
        viewModel.getNewsSources()
    }

    private fun observeViewState() {
        viewModel.viewState()
            .observeOn(executors.mainThread())
            .autoDisposable(scope)
            .subscribe({ state ->
                updateUi(state)
            }, {})
    }

    private fun updateUi(state: NewsSourcesViewState?) {
        when (state) {
            is NewsSourcesViewState.Loading -> showLoading()
            is NewsSourcesViewState.Success -> {
                hideLoading()
                updateList(state.sources)
            }
            is NewsSourcesViewState.Error -> {
                hideLoading()
                showError(state.errorMessage)
            }
        }
    }

    private fun updateList(sources: List<NewsSource>) {
        listController.updateSources(sources)
    }

    private fun showLoading() {
        news_sources_swipe_to_refresh.isRefreshing = true
    }

    private fun hideLoading() {
        news_sources_swipe_to_refresh.isRefreshing = false
    }
}
