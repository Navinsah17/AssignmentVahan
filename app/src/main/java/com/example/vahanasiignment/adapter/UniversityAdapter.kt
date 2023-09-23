package com.example.vahanasiignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vahanasiignment.R
import com.example.vahanasiignment.models.University

class UniversityAdapter(private val listener: OnItemsClick):RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder>() {


    class UniversityViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

        val univName: TextView = itemView.findViewById(R.id.nameTextView)
        val univUrl: TextView = itemView.findViewById(R.id.urlTextView)
        val uinvCountry: TextView = itemView.findViewById(R.id.countryTextView)

    }
    private val differCallback = object : DiffUtil.ItemCallback<University>(){
        override fun areItemsTheSame(oldItem: University, newItem: University): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: University, newItem: University): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityViewHolder{
        //val inflater = LayoutInflater.from(parent.context)
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_uni,
            parent,false)

        return UniversityViewHolder(view)

    }


    override fun onBindViewHolder(holder: UniversityViewHolder, position: Int) {
        val article = differ.currentList[position]
            //Glide.with(this).load(article.urlToImage).into(ivArticleImage)
            holder.univName.text = article.name
            holder.univUrl.text = article.webPages[0]
            holder.uinvCountry.text = article.country
            holder.univUrl.setOnClickListener {
            listener.onClickWebsite(article.webPages[0])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    interface OnItemsClick {
        fun onClickWebsite(link: String)
    }
}