package com.omninos.kotlinproject.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omninos.kotlinproject.R
import com.omninos.kotlinproject.data.network.responses.NewsResponse
import com.omninos.kotlinproject.databinding.ActivityMainBinding
import com.omninos.kotlinproject.ui.adapter.NewsAdapter
import com.omninos.kotlinproject.ui.home.WebActivity
import com.omninos.kotlinproject.util.hide
import com.omninos.kotlinproject.util.isOnline
import com.omninos.kotlinproject.util.show
import com.omninos.kotlinproject.util.snackBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CallBacks, CoroutineScope {
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private var size: Int? = null
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        binding.data = viewModel

        viewModel.listener = this

        recyclerView = findViewById(R.id.recyclerView)

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
    }

    override fun onStarted() {
        println("Data: Started")
        progress_circular.show()
    }

    override fun onSuccess(newsResponse: NewsResponse) {
        progress_circular.hide()
        size = newsResponse.articles?.size
        for (i in 0 until size!!) {
            println("Data Is:" + newsResponse.articles?.get(i)?.author)
        }
        recyclerView.adapter = NewsAdapter(
            newsResponse.articles!!,
            this,
            ({
//                root_layout.snackBar("" + newsResponse.articles?.get(it)?.title)
                startActivity(
                    Intent(this@MainActivity, WebActivity::class.java).putExtra(
                        "Url",
                        newsResponse.articles!!.get(it).url
                    )
                )
            })
        )
    }

    override fun onFailer(message: String) {
        progress_circular.hide()
        println("Data: Fail")
    }

}
