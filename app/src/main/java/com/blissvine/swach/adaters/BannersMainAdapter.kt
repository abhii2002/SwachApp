package com.blissvine.swach.adaters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.blissvine.swach.R
import com.blissvine.swach.models.BannerModel

class BannersMainAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

     private var bannerList = emptyList<BannerModel>()

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.banner_main_rv_layout, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bannerList = bannerList[position]
        if (holder is MyViewHolder){
             holder.itemView.findViewById<ImageView>(R.id.iv_banner_main).setImageDrawable(holder.itemView.context.getDrawable(bannerList.image))
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, model: BannerModel)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }


    fun setData(bannerList : List<BannerModel>) {
         this.bannerList = bannerList
         notifyDataSetChanged()
    }


    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}