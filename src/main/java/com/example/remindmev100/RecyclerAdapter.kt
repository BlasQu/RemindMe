package com.example.remindmev100

import android.graphics.Color
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.postOnAnimationDelayed
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.remindmev100.room.Creds
import kotlinx.android.synthetic.main.rv_item.view.*
import java.util.logging.Handler

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    var credsList = mutableListOf<Creds>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.pinnote.setOnClickListener {
                AlertDialogBuilder(it.context).pinnote(credsList[adapterPosition].id, credsList[adapterPosition].pin, credsList[adapterPosition].note, credsList[adapterPosition].pincontent, credsList[adapterPosition].notecontent)
            }
            itemView.settings.setOnClickListener {
                AlertDialogBuilder(it.context).addcolor(credsList[adapterPosition].id)
            }
            itemView.setOnLongClickListener {
                AlertDialogBuilder(it.context).editCreds(credsList[adapterPosition].id, credsList[adapterPosition].title, credsList[adapterPosition].login, credsList[adapterPosition].password, credsList[adapterPosition].pin, credsList[adapterPosition].note, credsList[adapterPosition].color, credsList[adapterPosition].pincontent, credsList[adapterPosition].notecontent)
                return@setOnLongClickListener true
            }
            itemView.setOnClickListener {view ->
                view.hidepass.setOnClickListener {
                    if (view.herepass.visibility == View.GONE){
                        view.herepass.visibility = View.VISIBLE
                        view.herepass.alpha = 0f
                        view.herepass.animate().apply {
                            duration = 200
                            alpha(1f)
                        }
                    } else {
                        view.herepass.alpha = 1f
                        view.herepass.animate().apply {
                            duration = 200
                            alpha(0f)
                        }
                        view.herepass.postDelayed({ view.herepass.visibility = View.GONE }, 200)
                    }
                }
                if (view.contentrl.visibility == View.VISIBLE){
                    view.contentrl.visibility = View.GONE
                } else {
                    view.contentrl.visibility = View.VISIBLE
                }
            }
        }
    }

    class DiffCallback(val oldList: List<Creds>, val newList: List<Creds>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].login == newList[newItemPosition].login
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false))
    }

    override fun getItemCount(): Int {
        return credsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.textview1.text = credsList[position].title
        holder.itemView.herelogin.text = credsList[position].login
        holder.itemView.herepass.text = credsList[position].password
        holder.itemView.cardview.setCardBackgroundColor(getColor(holder.itemView.context, colorId(credsList[position].color)))
        holder.itemView.pinnote.visibility = pinnote(credsList[position].pin, credsList[position].note)
    }

    fun submitData(newList: List<Creds>){
        val DiffResult = DiffUtil.calculateDiff(DiffCallback(credsList, newList))
        credsList.clear()
        credsList.addAll(newList)
        DiffResult.dispatchUpdatesTo(this)
    }

    fun colorId(id : Int) : Int{
        return when (id){
            1 -> R.color.black
            2 -> R.color.blue
            3 -> R.color.green
            4 -> R.color.red
            5 -> R.color.violet
            6 -> R.color.pink
            7 -> R.color.orange
            8 -> R.color.sky
            else -> R.color.grey
        }
    }

    fun pinnote(id : Boolean, id2 : Boolean): Int {
        if (id || id2){
            return View.VISIBLE
        }
        return View.GONE
    }
}