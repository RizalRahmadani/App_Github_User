package com.rzl.app_github_user.ui.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rzl.app_github_user.ItemsItem
import com.rzl.app_github_user.databinding.FragmentFollowBinding
import com.rzl.app_github_user.ui.detail.DetailViewModel
import com.rzl.app_github_user.ui.main.MainActivity


class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var viewModel: DetailViewModel

    companion object{
        const val TAB_TITLES = "tab_titles"
        const val GIT_FOLLOWERS = "Followers"
        const val GIT_FOLLOWING = "Following"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

        val gitUser : ItemsItem = requireActivity().intent.getParcelableExtra(MainActivity.EXTRA_DATA)!!
        binding.rvFollow.layoutManager = LinearLayoutManager(activity)
        val git_tabTitle = arguments?.getString(TAB_TITLES)
        if (git_tabTitle == GIT_FOLLOWERS){
            viewModel.getFollowersList(gitUser.login)
        }else if (git_tabTitle == GIT_FOLLOWING){
            viewModel.getFollowingList(gitUser.login)
        }

        viewModel.loading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        viewModel.listFollow.observe(viewLifecycleOwner){
            val adapter = FollowAdapter(it)
            binding.apply {
                rvFollow.adapter = adapter
            }
        }

        viewModel.error.observe(viewLifecycleOwner){
            Toast.makeText(context, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show()
            viewModel._error.value = false
        }

        return binding.root
    }

    private fun showLoading(isLoading : Boolean) {
        if (isLoading) {
            binding.progressBarFollow.visibility = View.VISIBLE
        } else {
            binding.progressBarFollow.visibility = View.GONE
        }
    }


}