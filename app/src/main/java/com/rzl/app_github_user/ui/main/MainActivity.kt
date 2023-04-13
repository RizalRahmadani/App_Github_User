package com.rzl.app_github_user.ui.main


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rzl.app_github_user.ItemsItem
import com.rzl.app_github_user.R
import com.rzl.app_github_user.databinding.ActivityMainBinding
import com.rzl.app_github_user.ui.detail.DetailUserActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var rvList: RecyclerView


    companion object{
        const val EXTRA_DATA = "extra_data"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        viewModel.listUsers.observe(this){ ItemsItem ->
            recyclerList(ItemsItem)
        }

        viewModel.loading.observe(this){
            showLoading(it)
        }
        viewModel.error.observe(this){
            Toast.makeText(this,"Data tidak ditemukan", Toast.LENGTH_SHORT).show()
            viewModel._error.value = false
        }

        rvList = binding.rvUser
        rvList.setHasFixedSize(true)


    }

    private fun recyclerList(userList : List<ItemsItem>){
        rvList.layoutManager = LinearLayoutManager(this)
        val searchUserAdapter = MainAdapter(userList)
        rvList.adapter = searchUserAdapter


        searchUserAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra(EXTRA_DATA, data)
                startActivity(intent)
            }

        })



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem : MenuItem = menu!!.findItem(R.id.search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
               return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                viewModel.detailUser()
                return true
            }

        })


        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                    viewModel.searchUser(query.toString())
                    searchView.clearFocus()

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })


        return true
    }

    private fun showLoading(isLoading : Boolean) {
        if (isLoading) {
            binding.progressBarMain.visibility = View.VISIBLE
        } else {
            binding.progressBarMain.visibility = View.GONE
        }
    }

}


