package com.dreamlive.copyjokes

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.dreamlive.jokes.adapter.AndroidArticleAdapter
import com.dreamlive.jokes.entity.AndroidAricle
import com.dreamlive.jokes.retrofit.RetrofitFactory
import com.dreamlive.jokes.retrofit.api.CommonApi
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity(), BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    override fun onLoadMoreRequested() {
        ++mPage
        requestData()
    }

    override fun onRefresh() {
        mSwipeRefreshLayout.isRefreshing = true
        mData.clear()
        mPage = 1
        requestData()
    }

    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    lateinit var mRecyclerView: RecyclerView
    lateinit var mLayoutManager: LinearLayoutManager

    var mData = mutableListOf<AndroidAricle>()


    var mPage: Int = 1//当前页码
    var mPageSize: Int = 8//默认返回条数，最大20条
    internal var mAdapter: AndroidArticleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initAction()
        requestData()
    }

    private fun initView() {
        mSwipeRefreshLayout = findViewById(R.id.main_srl) as SwipeRefreshLayout
        mRecyclerView = findViewById(R.id.main_rv) as RecyclerView

        mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView.layoutManager = mLayoutManager

        mAdapter = AndroidArticleAdapter(mData)
        mRecyclerView.adapter = mAdapter
        mAdapter!!.notifyDataSetChanged()
    }

    private fun initAction() {
        mSwipeRefreshLayout.setOnRefreshListener(this)
        mAdapter!!.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT)//item从左往右加载动画
        mAdapter!!.setOnLoadMoreListener(this, mRecyclerView)
        mAdapter!!.setOnItemChildClickListener { adapter, view, position ->
            Toast.makeText(this, "" + mAdapter!!.data.get(position)._id, Toast.LENGTH_SHORT)
        }
    }

    private fun requestData() {
        RetrofitFactory.getControllerSingleTonOperation(CommonApi::class.java)
                .getAndroidArticle(mPageSize, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread() as Scheduler)
                .subscribe({ result: List<AndroidAricle> ->
                    mAdapter!!.loadMoreComplete()
                    mSwipeRefreshLayout.isRefreshing = false
                    if (result != null && result!!.size > 0) {
                        mData.addAll(result)
                        mAdapter!!.notifyDataSetChanged()
                        for (item in mData) {
                            println(item.desc)
                        }
                    }
                }, { t: Throwable? -> }, {
                    mAdapter!!.loadMoreComplete()
                    mSwipeRefreshLayout.isRefreshing = false
                })
    }
}
