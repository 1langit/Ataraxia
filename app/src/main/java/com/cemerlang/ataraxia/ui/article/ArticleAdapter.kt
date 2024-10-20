package com.cemerlang.ataraxia.ui.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cemerlang.ataraxia.R
import com.cemerlang.ataraxia.data.models.article.Article
import com.cemerlang.ataraxia.databinding.ComponentArticleBinding
import com.google.android.material.chip.Chip

class ArticleAdapter(private val onClick: (Article) -> Unit) : RecyclerView.Adapter<ArticleAdapter.ItemArticleViewHolder>() {

    private val articleList = ArrayList<Article>()

    inner class ItemArticleViewHolder(private val binding: ComponentArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            with(binding) {
                txtName.text = article.author
                txtClinicTimestamp.text = "${article.clinic}  •  ${article.timestamp}"
                txtTitle.text = article.title
                txtContent.text = article.content

                chipCategory.removeAllViews()
                article.categories.forEach {
                    val chip = Chip(itemView.context, null, R.style.ChipCategory).apply {
                        text = it
                        setChipBackgroundColorResource(R.color.ungu_tua_50)
                    }
                    chipCategory.addView(chip)
                }
            }

            itemView.setOnClickListener {
                onClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemArticleViewHolder {
        val binding = ComponentArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ItemArticleViewHolder, position: Int) {
        holder.bind(articleList[position])
    }

    fun setArticles(articles: List<Article>) {
        articleList.clear()
        articleList.addAll(articles)
        notifyDataSetChanged()
    }
}