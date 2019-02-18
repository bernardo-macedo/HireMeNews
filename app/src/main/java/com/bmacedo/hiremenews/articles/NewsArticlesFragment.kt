package com.bmacedo.hiremenews.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bmacedo.hiremenews.R
import com.bmacedo.hiremenews.app.base.BaseFragment
import com.bmacedo.hiremenews.models.domain.NewsArticle
import com.bmacedo.hiremenews.utils.Executors
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.processors.PublishProcessor
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

    private val paginator = PublishProcessor.create<Int>()

    private var loading = false

    private var pageNumber = 1

    private var lastVisibleItem: Int = 0

    private var totalItemCount: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_articles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewComponents()
        setUpLoadMoreListener()
        subscribeForData()
        if (savedInstanceState == null) {
            loadArticles()
        }
    }

    private fun subscribeForData() {
        paginator.onBackpressureDrop()
            .flatMap { page ->
                showLoading()
                viewModel.getArticlesFromSource(args.sourceId, page)
                return@flatMap viewModel.viewState()
            }
            .observeOn(executors.mainThread())
            .autoDisposable(scope)
            .subscribe({ state ->
                updateUi(state)
            }, { })
    }

    private fun setUpLoadMoreListener() {
        val layoutManager = LinearLayoutManager(context)
        news_articles_list.layoutManager = layoutManager
        news_articles_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                totalItemCount = layoutManager.itemCount
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (!loading && totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD) {
                    pageNumber++
                    paginator.onNext(pageNumber)
                }
            }
        })
    }

    private fun initializeViewComponents() {
        news_articles_list.setController(listController)
        news_articles_swipe_to_refresh.setOnRefreshListener {
            pageNumber = 1
            loadArticles()
        }
    }

    private fun loadArticles() {
        paginator.onNext(pageNumber)
    }

    private fun observeViewState() {
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
        if (pageNumber == 1) {
            if (articles.isEmpty()) {
                news_articles_list.visibility = View.INVISIBLE
                news_articles_empty_state.visibility = View.VISIBLE
            } else {
                news_articles_list.visibility = View.VISIBLE
                news_articles_empty_state.visibility = View.INVISIBLE
                listController.updateArticles(articles)
            }
        } else {
            listController.addArticles(articles)
        }
    }

    private fun showLoading() {
        loading = true
        news_articles_swipe_to_refresh.isRefreshing = true
    }

    private fun hideLoading() {
        loading = false
        news_articles_swipe_to_refresh.isRefreshing = false
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 1
    }

}
