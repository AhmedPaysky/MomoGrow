package com.paysky.momogrow.views.products

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.paysky.momogrow.R
import com.paysky.momogrow.data.local.ProductEntity
import com.paysky.momogrow.data.models.momo.CatgoriesItem
import com.paysky.momogrow.data.models.momo.DataItem
import kotlinx.android.synthetic.main.categoryitem.view.*
import kotlinx.android.synthetic.main.custom_item_catalog.view.*
import kotlinx.android.synthetic.main.custom_item_order.view.tvAmount
import kotlinx.android.synthetic.main.custom_item_order.view.tvStatus
import java.util.*
import kotlin.random.Random

class CategoriesAdapter(var mContext: Context) :
    RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>() {


    fun setCategories(categories: List<CatgoriesItem?>?) {
        this.categories = categories as MutableList<CatgoriesItem>
        notifyDataSetChanged()
    }
    fun getCategories() : List<CatgoriesItem>{
        return categories
    }

    private var categories = mutableListOf<CatgoriesItem>()
    private var listener: onItemClick? = null
    private var selectedID = 0

    interface onItemClick {
        fun onClicked(productObj: CatgoriesItem)
    }

    public fun setListener(onItemClick: onItemClick) {
        this.listener = onItemClick
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.categoryitem, parent, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category = categories[position]
        holder.itemView.categoryname.text = category.name
        if (selectedID == category.id) {
            holder.itemView.categoryname.setBackgroundResource(R.drawable.bc_blue_shape)
            holder.itemView.categoryname.setTextColor(ContextCompat.getColor(mContext, android.R.color.white))
        }
        else {
            holder.itemView.categoryname.setBackgroundColor(Color.TRANSPARENT)
            holder.itemView.categoryname.setTextColor(ContextCompat.getColor(mContext, android.R.color.darker_gray))
        }
        holder.itemView.setOnClickListener {
            if (listener != null) {
                selectedID = category.id!!
                notifyDataSetChanged()
                listener?.onClicked(category)
            }
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    }
