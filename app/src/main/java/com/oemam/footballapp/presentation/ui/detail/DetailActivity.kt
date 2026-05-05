package com.oemam.footballapp.presentation.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.oemam.footballapp.R
import com.oemam.footballapp.databinding.ActivityDetailBinding
import com.oemam.footballapp.core.domain.model.Team
import com.oemam.footballapp.presentation.viewmodel.DetailViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    companion object {
        const val EXTRA_TEAM_ID = "extra_team_id"
        const val EXTRA_TEAM_NAME = "extra_team_name"
        const val EXTRA_TEAM_BADGE = "extra_team_badge"
        const val EXTRA_TEAM_STADIUM = "extra_team_stadium"
        const val EXTRA_TEAM_DESC = "extra_team_desc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Keep system bar styling consistent with the app theme.
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple_700)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val teamId = intent.getStringExtra(EXTRA_TEAM_ID) ?: ""
        val teamName = intent.getStringExtra(EXTRA_TEAM_NAME) ?: ""
        val teamBadge = intent.getStringExtra(EXTRA_TEAM_BADGE)
        val teamStadium = intent.getStringExtra(EXTRA_TEAM_STADIUM)
        val teamDesc = intent.getStringExtra(EXTRA_TEAM_DESC)

        val team = Team(
            id = teamId,
            name = teamName,
            shortName = null,
            badge = teamBadge,
            stadium = teamStadium,
            description = teamDesc,
            formedYear = null,
            website = null
        )

        setupUI(team)
        setupToolbar()
        viewModel.checkFavoriteStatus(teamId)
        observeFavoriteStatus(team)
    }

    private fun setupToolbar() {
        binding.toolbarDetail.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupUI(team: Team) {
        binding.tvDetailName.text = team.name
        binding.tvDetailDescription.text = team.description
        Glide.with(this).load(team.badge).into(binding.ivDetailBadge)

        binding.btnFavorite.setOnClickListener {
            viewModel.toggleFavorite(team)
        }
    }

    private fun observeFavoriteStatus(team: Team) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isFavorite.collect { isFav ->
                    binding.btnFavorite.text = if (isFav) "Remove from Favorite" else "Add to Favorite"
                }
            }
        }
    }
}
