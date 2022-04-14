package com.example.reactiveprogramming.ui.dataManagement

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import arrow.core.some
import com.example.common.UiConfigurationViewState
import com.example.common.fragment.CustomFragment
import com.example.domain.entity.Team
import com.example.reactiveprogramming.R
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataManagementFragment: CustomFragment(R.layout.data_management_fragment) {

    private val viewModel: DataManagementViewModel by viewModel()

    private val functionalTeamListRecycler by lazy { requireView().findViewById<RecyclerView>(R.id.functional_team_list_recycler) }
    private val reactiveTeamListRecycler by lazy { requireView().findViewById<RecyclerView>(R.id.reactive_team_list_recycler) }

    private val loadReactiveDataButton by lazy { requireView().findViewById<MaterialButton>(R.id.load_reactive_data_button) }
    private val loadFunctionalDataButton by lazy { requireView().findViewById<MaterialButton>(R.id.load_functional_data_button) }
    private val updateRandomTeamButton by lazy { requireView().findViewById<MaterialButton>(R.id.update_random_team_button) }
    private val removeRandomTeamButton by lazy { requireView().findViewById<MaterialButton>(R.id.remove_random_team_button) }
    private val createRandomTeamButton by lazy { requireView().findViewById<MaterialButton>(R.id.add_random_team_button) }

    override var uiConfigurationViewState = UiConfigurationViewState(
        showToolbar = true,
        statusBarColor = R.color.colorPrimaryDark.some(),
        toolbarColor = R.color.colorPrimary.some(),
        toolbarNavigationIconColor = R.color.white.some(),
        showLogoutButton = true
    )

    override fun handleUiConfigurationViewState(uiConfigurationViewState: UiConfigurationViewState) = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()

        setupViewModelLiveData()

        setupClickListeners()
    }

    private fun setupRecyclerViews() {
        functionalTeamListRecycler.setHasFixedSize(true)

        reactiveTeamListRecycler.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        reactiveTeamListRecycler.setHasFixedSize(true)

        functionalTeamListRecycler.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun setupViewModelLiveData() {
        lifecycleScope.launchWhenStarted {
            viewModel.dataManagementViewModelSateFlow.collectLatest { state ->
                when (state) {
                    is RetrievingReactiveTeams, RetrievingFunctionalTeams, UpdatingTeam, RemovingTeam, CreatingTeam -> {
                        showProgressDialog()
                    }
                    is RetrievedReactiveTeams -> {
                        onTeamsRetrieved(state.teams, reactiveTeamListRecycler)
                        hideProgressDialog()
                    }
                    is RetrievedFunctionalTeams -> {
                        onTeamsRetrieved(state.teams, functionalTeamListRecycler)
                        hideProgressDialog()
                    }
                    is UpdatedTeam, CreatedTeam, RemovedTeam -> {
                        hideProgressDialog()
                    }
                    is ErrorInOperation -> {
                        showError(getString(state.messageId, state.team.toString()))
                    }
                    else -> { /* no-op */ }
                }
            }
        }
    }

    private fun setupClickListeners() {
        loadReactiveDataButton.setOnClickListener { button ->
            viewModel.getTeamsReactive()
            button.isEnabled = false
        }

        loadFunctionalDataButton.setOnClickListener { viewModel.getTeamsFunctional() }

        updateRandomTeamButton.setOnClickListener {
            viewModel.updateTeam(reactiveTeamListRecycler.adapter?.let { adapter ->
                getRandomTeamFromAdapter(adapter)
            })
        }

        removeRandomTeamButton.setOnClickListener {
            viewModel.removeTeam(reactiveTeamListRecycler.adapter?.let { adapter ->
                getRandomTeamFromAdapter(adapter)
            })
        }

        createRandomTeamButton.setOnClickListener { viewModel.createNewTeam() }
    }

    private fun onTeamsRetrieved(teamsRetrieved: List<Team>, recyclerToUpdate: RecyclerView) {
        var adapter = recyclerToUpdate.adapter
        if (adapter != null) {
            adapter = adapter as DataManagementTeamAdapter
            adapter.onNewData(teamsRetrieved)
            recyclerToUpdate.adapter = adapter
        } else {
            recyclerToUpdate.adapter = DataManagementTeamAdapter(teamsRetrieved.toMutableList())
        }
    }

    private fun getRandomTeamFromAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>): Team? {
        val dataManagementAdapter = (adapter as DataManagementTeamAdapter).getTeamList()
        return if (dataManagementAdapter.isNotEmpty()) {
            dataManagementAdapter[(0 until adapter.itemCount).random()]
        } else {
            null
        }
    }
}





