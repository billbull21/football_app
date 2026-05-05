package com.oemam.footballapp.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.oemam.footballapp.databinding.FragmentTeamListBinding
import com.oemam.footballapp.presentation.ui.adapter.TeamAdapter
import com.oemam.footballapp.presentation.ui.detail.DetailActivity
import com.oemam.footballapp.presentation.viewmodel.MainUiState
import com.oemam.footballapp.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TeamListFragment : Fragment() {

    private var _binding: FragmentTeamListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModel()

    private val adapter by lazy {
        TeamAdapter { team ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_TEAM_ID, team.id)
                putExtra(DetailActivity.EXTRA_TEAM_NAME, team.name)
                putExtra(DetailActivity.EXTRA_TEAM_BADGE, team.badge)
                putExtra(DetailActivity.EXTRA_TEAM_STADIUM, team.stadium)
                putExtra(DetailActivity.EXTRA_TEAM_DESC, team.description)
            }
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTeams.adapter = adapter
        observeUiState()
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is MainUiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is MainUiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            adapter.submitList(state.teams)
                        }
                        is MainUiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

