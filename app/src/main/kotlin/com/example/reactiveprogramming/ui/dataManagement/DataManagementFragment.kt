package com.example.reactiveprogramming.ui.dataManagement

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import arrow.core.some
import com.example.common.UiConfigurationViewState
import com.example.common.fragment.CustomFragment
import com.example.common.logger.LifecycleLogger
import com.example.common.logger.Logger
import com.example.domain.entity.Team
import com.example.reactiveprogramming.R
import com.google.android.material.button.MaterialButton
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataManagementFragment: CustomFragment(R.layout.data_management_fragment) {

    private val logger: Logger by inject()

    private val viewModel: DataManagementViewModel by viewModel()

    private val functionalTeamListRecycler by lazy { requireView().findViewById<RecyclerView>(R.id.functional_team_list_recycler) }
    private val reactiveTeamListRecycler by lazy { requireView().findViewById<RecyclerView>(R.id.reactive_team_list_recycler) }

    private val loadDataFromDatabaseButton by lazy { requireView().findViewById<MaterialButton>(R.id.load_data_button)}
    private val updateRandomTeamButton by lazy { requireView().findViewById<MaterialButton>(R.id.update_random_team_button) }
    private val removeRandomTeamButton by lazy { requireView().findViewById<MaterialButton>(R.id.remove_random_team_button) }
    private val createRandomTeamButton by lazy { requireView().findViewById<MaterialButton>(R.id.add_random_team_button)}

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

        viewLifecycleOwner.lifecycle.addObserver(LifecycleLogger(logger.tag("Home Fragment")))

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
        functionalTeamListRecycler.setHasFixedSize(true)

        setupViewModelLiveData()

        setupClickListeners()
    }

    private fun setupViewModelLiveData() {
        viewModel.getTeamsBySportLiveData().observe(this, Observer { state ->
            when (state) {
                is RetrievingTeams, UpdatingTeam, RemovingTeam, CreatingTeam -> {
                    showProgressDialog()
                }
                is RetrievedReactiveTeams -> {
                    hideProgressDialog()
                    onReactiveTeamsRetrieved(state.teams)
                }
                is RetrievedFunctionalTeams -> {
                    hideProgressDialog()
                    onFunctionalTeamsRetrieved(state.teams)
                }
                is UpdatedTeam, RemovedTeam, CreatedTeam -> {
                    hideProgressDialog()
                }
                is ErrorInOperation -> {
                    showError(state.message)
                }
            }
        })
    }

    private fun setupClickListeners() {
        loadDataFromDatabaseButton.setOnClickListener {
            viewModel.getTeamsReactive()
            viewModel.getTeamsFunctional()
        }

        updateRandomTeamButton.setOnClickListener {
            viewModel.updateTeam(reactiveTeamListRecycler.adapter?.let {
                (it as DataManagementTeamAdapter)
                    .getTeamList()[(0 until it.itemCount).random()]
            })
        }

        removeRandomTeamButton.setOnClickListener {
            viewModel.removeTeam(reactiveTeamListRecycler.adapter?.let {
                (it as DataManagementTeamAdapter)
                    .getTeamList()[(0 until it.itemCount).random()]
            })
        }

        createRandomTeamButton.setOnClickListener { viewModel.createNewTeam() }
    }

    private fun onReactiveTeamsRetrieved(teamsRetrieved: List<Team>) {
        var adapter = reactiveTeamListRecycler.adapter
        if (adapter != null) {
            adapter = adapter as DataManagementTeamAdapter
            adapter.onNewData(teamsRetrieved)
            reactiveTeamListRecycler.adapter = adapter
        } else {
            reactiveTeamListRecycler.adapter = DataManagementTeamAdapter(teamsRetrieved.toMutableList())
        }
    }

    private fun onFunctionalTeamsRetrieved(teamsRetrieved: List<Team>) {
        var adapter = functionalTeamListRecycler.adapter
        if (adapter != null) {
            adapter = adapter as DataManagementTeamAdapter
            adapter.onNewData(teamsRetrieved)
            functionalTeamListRecycler.adapter = adapter
        } else {
            functionalTeamListRecycler.adapter = DataManagementTeamAdapter(teamsRetrieved.toMutableList())
        }
    }
}


