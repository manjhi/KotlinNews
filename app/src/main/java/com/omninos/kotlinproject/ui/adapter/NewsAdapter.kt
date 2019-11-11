package com.omninos.kotlinproject.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omninos.kotlinproject.R
import com.omninos.kotlinproject.data.network.responses.Article
import kotlinx.android.synthetic.main.item_list.view.*

class NewsAdapter(val items: List<Article>, val context: Context, val listener: (Int) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tv_animal_type.setText(items.get(position).title)
        holder.tv_animal_type.setOnClickListener {
            listener(position)
        }
        if (!items.get(position).urlToImage.isNullOrEmpty()) {
            Glide.with(context).load(items.get(position).urlToImage).into(holder.newsImg)
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_animal_type = view.tv_animal_type
        val newsImg = view.newsImg
    }
}