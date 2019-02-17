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
import com.bmacedo.hiremenews.utils.LoadingDialog
import com.google.android.material.snackbar.Snackbar
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

    private val listController = NewsSourcesController()

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
        news_sources_list.setController(listController)

        if (savedInstanceState == null) {
            viewModel.getNewsSources()
        }

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

    private fun showError(errorMessage: String) {
        view?.let { view ->
            Snackbar.make(view, errorMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun hideLoading() {
        LoadingDialog.hide(this)
    }

    private fun showLoading() {
        LoadingDialog.show(this)
    }
}
