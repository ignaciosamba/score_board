package com.sambataro.ignacio.scoreboard.ui.fragment.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sambataro.ignacio.scoreboard.R
import com.sambataro.ignacio.scoreboard.databinding.NewsFragmentBinding
import com.sambataro.ignacio.scoreboard.databinding.NewsItemBinding
import com.sambataro.ignacio.scoreboard.domain.NewsInfo
import com.sambataro.ignacio.scoreboard.ui.adapters.NewsAdapter
import kotlinx.android.synthetic.main.activity_main.*

class NewsFragment : Fragment(){

    private lateinit var binding: NewsFragmentBinding

    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        val application = requireNotNull(activity).application
        binding = DataBindingUtil.inflate(inflater, R.layout.news_fragment,
            container, false)

        binding.lifecycleOwner = this

        val standingViewModelFactory = NewsViewModelFactory(application)
        viewModel = ViewModelProvider(this, standingViewModelFactory)
            .get(NewsViewModel::class.java)

        binding.newsViewModel = viewModel

        val adapter = NewsAdapter()
        binding.newsRecycler.adapter = adapter
        val manager = LinearLayoutManager(activity)
        binding.newsRecycler.layoutManager = manager

        viewModel.news.observe(viewLifecycleOwner, Observer { news->
            news?.let {
                adapter.submitList(news)
            }
        })

        return binding.root
    }

}