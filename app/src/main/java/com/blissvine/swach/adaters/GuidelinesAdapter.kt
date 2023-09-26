package com.blissvine.swach.adaters



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.blissvine.swach.R
import com.blissvine.swach.models.GuideLinesModel

class GuidelinesAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var guidelinesList = emptyList<GuideLinesModel>()

    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_notice_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return guidelinesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val guidelinesList = guidelinesList[position]
        if (holder is MyViewHolder){
            holder.itemView.findViewById<TextView>(R.id.guideline_title).text = guidelinesList.headline
            holder.itemView.findViewById<TextView>(R.id.guideline_notice).text = guidelinesList.guidelines

            if(position % 2 == 0){
                holder.itemView.findViewById<LinearLayout>(R.id.linearL).background = ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.vertical_items_background_card_blue
                )
            }
        }
    }



    interface OnClickListener {
        fun onClick(position: Int, model: GuideLinesModel)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun setData(guidelinesList: List<GuideLinesModel>) {
        this.guidelinesList = guidelinesList
        notifyDataSetChanged()
    }


    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}