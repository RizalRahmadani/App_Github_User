package com.rzl.app_github_user.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rzl.app_github_user.ItemsItem
import com.rzl.app_github_user.databinding.FragmentFollowBinding
import com.rzl.app_github_user.ui.detail.DetailUserActivity

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loading.observe(viewLifecycleOwner){
            showLoading(it)
        }
        viewModel.listFollow.observe(viewLifecycleOwner){
                followList ->
            setToFragment(followList)
        }
        viewModel.getFollowersList(arguments?.getString(DetailUserActivity.EXTRA_FRAG).toString())
    }

    private fun setToFragment(followList : List<ItemsItem>){
        val listUSer = ArrayList<ItemsItem>()
        with(binding){
            for (user in followList){
                listUSer.clear()
                listUSer.addAll(followList)
            }
            rvFollow.layoutManager = LinearLayoutManager(context)
            val adapter = FollowAdapter(followList)
            rvFollow.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(isLoading : Boolean) {
        if (isLoading) {
            binding.progressBarFollow.visibility = View.VISIBLE
        } else {
            binding.progressBarFollow.visibility = View.GONE
        }
    }
}