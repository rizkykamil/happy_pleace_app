package com.example.happypleaceapp.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.happypleaceapp.R
import com.example.happypleaceapp.activity.AddHappyPlaceActivity
import com.example.happypleaceapp.activity.MainActivity
import com.example.happypleaceapp.database.DatabaseHandler
import com.example.happypleaceapp.database.HappyPlacesModel
import kotlinx.android.synthetic.main.item_happy_places.view.*

class HappyPlacesAdapter(
    private val context: Context,
    private var list: ArrayList<HappyPlacesModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_happy_places,
                parent,
                false
            )
        )
    }

    fun notifyEditItem(activity: Activity, position: Int, requestCode: Int) {
        val intent = Intent(context, AddHappyPlaceActivity::class.java)
        intent.putExtra(MainActivity.EXTRA_PLACE_DETAIL, list[position])
        activity.startActivityForResult(
            intent,
            requestCode
        )
        notifyItemChanged(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        if (holder is MyViewHolder) {
            holder.itemView.iv_place_image.setImageURI(Uri.parse(model.image))
            holder.itemView.tvTitle.text = model.title
            holder.itemView.tvDescription.text = model.description

            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(position, model)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun removeAt(position: Int) {
        val dbHandler = DatabaseHandler(context)
        val isDeleted = dbHandler.deleteHappyPlace(list[position])
        if (isDeleted > 0)
            list.removeAt(position)
        notifyItemRemoved(position)
    }

    interface OnClickListener {
        fun onClick(position: Int, model: HappyPlacesModel)
    }

}

private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

}