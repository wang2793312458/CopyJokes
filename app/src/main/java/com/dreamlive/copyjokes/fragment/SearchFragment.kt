package com.dreamlive.copyjokes.fragment

import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.dreamlive.copyjokes.adapter.ClassifyDataAdapter
import com.dreamlive.jokes.entity.ClassifyData

/**
 * Created by win10 on 2017/7/21.
 */
class SearchFragment : Fragment(), BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    override fun onLoadMoreRequested() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRefresh() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var type: String = "福利"
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    lateinit var mRecyclerView: RecyclerView
    lateinit var mLayoutManager: LinearLayoutManager

    var mData = mutableListOf<ClassifyData>()


    var mPage: Int = 1//当前页码
    var mPageSize: Int = 8//默认返回条数，最大20条
    internal var mAdapter: ClassifyDataAdapter? = null
}