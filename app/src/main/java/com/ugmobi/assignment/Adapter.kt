package com.ugmobi.assignment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mikhaellopez.circularimageview.CircularImageView

class Adapter(private val context: Context, private var itemList: ArrayList<Videos>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.video_items,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        holder.title.text = itemList[position].title
        Glide.with(context).load(itemList[position].videourl).into(holder.profileimage)
        holder.ch_name.text = itemList[position].ch_name+"."
        Glide.with(context).load(itemList[position].thumbnail).into(holder.thumbnail)
        holder.likes.text = itemList[position].likes.toString()+" Likes"

    }

    fun filterList(filteredList: ArrayList<Videos>) {
        itemList = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title:AppCompatTextView = itemView.findViewById(R.id.title)
        val ch_name :AppCompatTextView = itemView.findViewById(R.id.channelname)
        val thumbnail : AppCompatImageView = itemView.findViewById(R.id.thumbnail)
        val profileimage : CircularImageView = itemView.findViewById(R.id.profileimage)
        val likes : AppCompatTextView = itemView.findViewById(R.id.likes)


    }
}