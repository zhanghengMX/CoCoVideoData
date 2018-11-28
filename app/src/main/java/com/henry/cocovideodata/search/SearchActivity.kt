package com.henry.cocovideodata.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.henry.cocovideodata.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity() : AppCompatActivity(), SearchContract.View{

    override var presenter: SearchContract.Presenter = SearchPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
    }

    private fun initView() {

    }
    override fun refreshData(data: Any) {
    }

    fun onSearchClick(view : View) {
        presenter.loadData(searchEditText.text.toString())
    }
}
