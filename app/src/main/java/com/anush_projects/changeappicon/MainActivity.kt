package com.anush_projects.changeappicon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.anush_projects.changeappicon.Adapters.AppIconAdapter
import com.anush_projects.changeappicon.Models.AppIconModel
import com.anush_projects.changeappicon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AppIconAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val model = listOf(
            AppIconModel("Default icon", R.drawable.app_icon, "0"),
            AppIconModel("Glossy Red", R.drawable.app_icon_red, "1"),
            AppIconModel("Premium Blue", R.drawable.app_icon_blue, "2"),
            AppIconModel("Glaze Blue", R.drawable.app_icon_glaze, "3"),
            AppIconModel("Premium Black", R.drawable.app_icon_black, "4")
        )
        adapter = AppIconAdapter(model, this)
        binding.iconRecView.layoutManager = GridLayoutManager(this, 2)
        binding.iconRecView.adapter = adapter
    }

}