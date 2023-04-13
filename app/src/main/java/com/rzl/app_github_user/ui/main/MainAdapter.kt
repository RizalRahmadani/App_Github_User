package com.rzl.app_github_user.ui.main


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rzl.app_github_user.ItemsItem
import com.rzl.app_github_user.databinding.ItemUserBinding


class MainAdapter(private val userList: List<ItemsItem>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {


    private lateinit var onItemClickCallBack: OnItemClickCallback

    class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imgUser)
                tvUsername.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
        holder.itemView.setOnClickListener{
            onItemClickCallBack.onItemClicked(userList[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = userList.size


    fun setOnItemClickCallback(onItemClickCallback : OnItemClickCallback){
        this.onItemClickCallBack = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

}