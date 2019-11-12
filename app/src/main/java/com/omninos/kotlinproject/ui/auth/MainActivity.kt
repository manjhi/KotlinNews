package com.omninos.kotlinproject.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.omninos.kotlinproject.R
import com.omninos.kotlinproject.data.network.responses.NewsResponse
import com.omninos.kotlinproject.databinding.ActivityMainBinding
import com.omninos.kotlinproject.ui.adapter.NewsAdapter
import com.omninos.kotlinproject.ui.home.WebActivity
import com.omninos.kotlinproject.util.isOnline
import com.omninos.kotlinproject.util.snackBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CallBacks, CoroutineScope, KodeinAware {

    override val kodein by kodein()

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


    private var size: Int? = null
    lateinit var recyclerView: RecyclerView
    lateinit var swipe_layout: SwipeRefreshLayout
    private val factory: ViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this, factory).get(MyViewModel::class.java)
        binding.data = viewModel

        viewModel.listener = this

        recyclerView = findViewById(R.id.recyclerView)
        swipe_layout = findViewById(R.id.swipe_layout)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        launch {
            if (isOnline(this@MainActivity)) {
                viewModel.getNewsData()
            } else {
                root_layout.snackBar("No Internet Connection")
            }
        }

        swipe_layout.setOnRefreshListener {
            launch {
                viewModel.getNewsData()
            }
        }
    }

    override fun onStarted() {
        swipe_layout.isRefreshing = true
        println("Data: Started")
    }

    override fun onSuccess(newsResponse: NewsResponse) {
        swipe_layout.isRefreshing = false
        size = newsResponse.articles?.size
        for (i in 0 until size!!) {
            println("Data Is:" + newsResponse.articles?.get(i)?.author)
        }
        recyclerView.adapter = NewsAdapter(
            newsResponse.articles!!,
            this,
            ({
                startActivity(
                    Intent(this@MainActivity, WebActivity::class.java).putExtra(
                        "Url",
                        newsResponse.articles!!.get(it).url
                    )
                )
            })
        )
        recyclerView.adapter?.notifyDataSetChanged()

        recyclerView
    }

    override fun onFailer(message: String) {
        swipe_layout.isRefreshing = true
        println("Data: Fail")
    }

}
