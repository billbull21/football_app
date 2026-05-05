package com.oemam.footballapp.favorites.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.oemam.footballapp.R
import com.oemam.footballapp.favorites.databinding.ActivityFavoriteBinding
import com.oemam.footballapp.presentation.ui.adapter.TeamAdapter
import com.oemam.footballapp.presentation.ui.detail.DetailActivity
import com.oemam.footballapp.favorites.presentation.viewmodel.FavoriteViewModel
import com.oemam.footballapp.favorites.di.viewModelModule
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModel()

    private lateinit var adapter: TeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadKoinModules(viewModelModule)

        // Keep system bar styling consistent with the app theme.
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple_700)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TeamAdapter { team ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_TEAM_ID, team.id)
                putExtra(DetailActivity.EXTRA_TEAM_NAME, team.name)
                putExtra(DetailActivity.EXTRA_TEAM_BADGE, team.badge)
                putExtra(DetailActivity.EXTRA_TEAM_STADIUM, team.stadium)
                putExtra(DetailActivity.EXTRA_TEAM_DESC, team.description)
            }
            startActivity(intent)
        }

        binding.rvFavorites.adapter = adapter

        setupToolbar()

        observeFavorites()
    }

    private fun setupToolbar() {
        binding.toolbarFavorite.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeFavorites() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteTeams.collect { teams ->
                    adapter.submitList(teams)
                    binding.tvEmpty.visibility = if (teams.isEmpty()) View.VISIBLE else View.GONE
                    binding.rvFavorites.visibility = if (teams.isEmpty()) View.GONE else View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(viewModelModule)
    }
}