package com.bmacedo.hiremenews.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.bmacedo.hiremenews.R
import com.bmacedo.hiremenews.app.base.BaseFragment
import com.bmacedo.hiremenews.models.domain.NewsArticle
import com.bmacedo.hiremenews.utils.Executors
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.fragment_news_articles.*
import javax.inject.Inject

class NewsArticlesFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: NewsArticlesViewModel.Factory

    @Inject
    lateinit var executors: Executors

    private val viewModel: NewsArticlesViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(NewsArticlesViewModel::class.java)
    }

    private val scope: ScopeProvider by lazy { AndroidLifecycleScopeProvider.from(this) }

    private val listController = NewsArticlesController()

    private val args: NewsArticlesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_articles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        news_articles_list.setController(listController)

        if (savedInstanceState == null) {
            viewModel.getArticlesFromSource(args.sourceId)
        }

        viewModel.viewState()
            .observeOn(executors.mainThread())
            .autoDisposable(scope)
            .subscribe({ state ->
                updateUi(state)
            }, {})
    }

    private fun updateUi(state: NewsArticlesViewState?) {
        when (state) {
            is NewsArticlesViewState.Loading -> showLoading()
            is NewsArticlesViewState.Success -> {
                hideLoading()
                updateList(state.articles)
            }
            is NewsArticlesViewState.Error -> {
                hideLoading()
                showError(state.errorMessage)
            }
        }
    }

    private fun updateList(articles: List<NewsArticle>) {
        listController.updateArticles(articles)
    }

}
