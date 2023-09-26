package com.blissvine.swach.adaters



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.blissvine.swach.R
import com.blissvine.swach.models.GuideLinesModel
import com.blissvine.swach.models.VendorsModel

class VendorsAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var vendorsList = emptyList<VendorsModel>()

    private var onClickListener: OnClickListener? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_vendors_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vendorsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vendorsList = vendorsList[position]
        if (holder is MyViewHolder){
            holder.itemView.findViewById<TextView>(R.id.assigned_to).text = "Assigned to"
            holder.itemView.findViewById<TextView>(R.id.vendors_name).text = vendorsList.vendorName
            holder.itemView.findViewById<ImageView>(R.id.iv_board_image).setImageDrawable(holder.itemView.context.getDrawable(vendorsList.vendorImage))


            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(position, vendorsList)
                }
            }

        }
    }



    interface OnClickListener {
        fun onClick(position: Int, model: VendorsModel)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun setData(vendorsList: List<VendorsModel>) {
        this.vendorsList = vendorsList
        notifyDataSetChanged()
    }


    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}