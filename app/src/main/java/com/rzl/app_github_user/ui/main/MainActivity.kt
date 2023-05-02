package com.rzl.app_github_user.ui.main


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_DATA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rzl.app_github_user.ItemsItem
import com.rzl.app_github_user.R
import com.rzl.app_github_user.databinding.ActivityMainBinding
import com.rzl.app_github_user.ui.ViewModelFactory
import com.rzl.app_github_user.ui.detail.DetailUserActivity
import com.rzl.app_github_user.ui.favorite.FavoriteActivity
import com.rzl.app_github_user.ui.settings.SettingActivity
import com.rzl.app_github_user.ui.settings.SettingPreference
import com.rzl.app_github_user.ui.settings.SettingViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var rvList: RecyclerView



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


        val pref = SettingPreference.getInstance(dataStore)

        val themeSettingView = ViewModelProvider(this, ViewModelFactory(pref)).get(SettingViewModel::class.java)

        themeSettingView.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


    }

    private fun recyclerList(userList : List<ItemsItem>){
        rvList.layoutManager = LinearLayoutManager(this)
        val searchUserAdapter = MainAdapter(userList)
        rvList.adapter = searchUserAdapter


        searchUserAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USER, data.login)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.themeSetting -> {
                Intent(this@MainActivity, SettingActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading : Boolean) {
        if (isLoading) {
            binding.progressBarMain.visibility = View.VISIBLE
        } else {
            binding.progressBarMain.visibility = View.GONE
        }
    }

}


