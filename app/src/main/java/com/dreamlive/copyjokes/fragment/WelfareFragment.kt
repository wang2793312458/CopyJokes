package com.dreamlive.copyjokes.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.dreamlive.copyjokes.R
import com.dreamlive.jokes.adapter.WelfareAdapter
import com.dreamlive.jokes.entity.Welfare
import com.dreamlive.jokes.retrofit.RetrofitFactory
import com.dreamlive.jokes.retrofit.api.CommonApi
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by win10 on 2017/7/21.
 */
class WelfareFragment : Fragment(), BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
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

    lateinit var rootView: View
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    lateinit var mRecyclerView: RecyclerView
    lateinit var mLayoutManager: LinearLayoutManager

    var mData = mutableListOf<Welfare>()


    var mPage: Int = 1//当前页码
    var mPageSize: Int = 20//默认返回条数，最大20条
    internal var mAdapter: WelfareAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater!!.inflate(R.layout.content_main, container, false)
        initView()
        initAction()
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requestData()
    }

    private fun initView() {
        mSwipeRefreshLayout = rootView.findViewById(R.id.main_srl) as SwipeRefreshLayout
        mRecyclerView = rootView.findViewById(R.id.main_rv) as RecyclerView

        mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView.layoutManager = mLayoutManager

        mAdapter = WelfareAdapter(mData)
        mRecyclerView.adapter = mAdapter
        mAdapter!!.notifyDataSetChanged()
    }

    private fun initAction() {
        mSwipeRefreshLayout.setOnRefreshListener(this)
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)
        mAdapter!!.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT)//item加载动画
        mAdapter!!.setOnLoadMoreListener(this, mRecyclerView)
        mAdapter!!.setOnItemClickListener { adapter, view, position ->
            Toast.makeText(context, "点击", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 数据请求
     */
    private fun requestData() {
        RetrofitFactory.getControllerSingleTonOperation(CommonApi::class.java)
                .getWelfare(mPageSize, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread() as Scheduler)
                .subscribe({
                    result: List<Welfare> ->
                    mAdapter!!.loadMoreComplete()
                    mSwipeRefreshLayout.isRefreshing = false
                    if (result != null && result!!.size > 0) {
                        mData.addAll(result)
                        mAdapter!!.notifyDataSetChanged()
                        for (item in mData) {
                            print(item.desc)
                        }
                    }
                }, { t: Throwable -> }, {
                    mAdapter!!.loadMoreComplete()
                    mSwipeRefreshLayout.isRefreshing = false
                })
    }
}