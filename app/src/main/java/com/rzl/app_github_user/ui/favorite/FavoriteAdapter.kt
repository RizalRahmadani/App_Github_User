package com.rzl.app_github_user.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rzl.app_github_user.data.local.entity.UserEntity
import com.rzl.app_github_user.databinding.ItemUserBinding
import com.rzl.app_github_user.ui.detail.DetailUserActivity
import com.rzl.app_github_user.utils.DiffCallback

class FavoriteAdapter : ListAdapter<UserEntity, FavoriteAdapter.FavoriteViewHolder>(
DIFF_CALLBACK) {

    private val listFavorite = ArrayList<UserEntity>()

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UserEntity> =
            object : DiffUtil.ItemCallback<UserEntity>() {
                override fun areItemsTheSame(oldUser: UserEntity, newUser: UserEntity): Boolean {
                    return oldUser.login == newUser.login
                }

                override fun areContentsTheSame(oldUser: UserEntity, newUser: UserEntity): Boolean {
                    return oldUser == newUser
                }
            }
    }


    fun setFavorite(favorites : List<UserEntity>){
        val diffCallback = DiffCallback(this.listFavorite, favorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(favorites)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemRowSearchUserBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(itemRowSearchUserBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorites = listFavorite[position]
        holder.bind(favorites)
    }

    class FavoriteViewHolder (val binding : ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(favoriteUser: UserEntity){
            with(binding){
                tvUsername.text = favoriteUser.login
                itemView.setOnClickListener{
                    val intent = Intent(itemView.context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_USER, favoriteUser.login)
                    itemView.context.startActivity(intent)
                    println(favoriteUser.login)
                }
            }
            Glide.with(itemView.context)
                .load(favoriteUser.imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .circleCrop()
                .into(binding.imgUser)
        }
    }

    override fun getItemCount(): Int = listFavorite.size

}