package com.rzl.app_github_user.ui.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rzl.app_github_user.ItemsItem
import com.rzl.app_github_user.databinding.ItemUserBinding

class FollowAdapter(private val gitUserList: List<ItemsItem>) : RecyclerView.Adapter<FollowAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gitUSer = gitUserList[position]
        Glide.with(holder.itemView.context)
            .load(gitUSer.avatarUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .circleCrop()
            .into(holder.binding.imgUser)
        holder.apply {
            binding.apply {
                tvUsername.text = gitUSer.login
            }
        }
    }

    override fun getItemCount(): Int = gitUserList.size
}