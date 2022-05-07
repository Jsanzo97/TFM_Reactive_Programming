package com.example.reactiveprogramming.ui.dataManagement

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import arrow.core.some
import com.example.common.ConstantsAnimation
import com.example.common.UiConfigurationViewState
import com.example.common.extensions.rotate
import com.example.common.fragment.CustomFragment
import com.example.domain.entity.Team
import com.example.reactiveprogramming.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataManagementFragment: CustomFragment(R.layout.data_management_fragment) {

    private val viewModel: DataManagementViewModel by viewModel()

    private val reactiveResultTitleText by lazy { requireView().findViewById<MaterialTextView>(R.id.reactive_result_title_text)}
    private val functionalResultTitleText by lazy { requireView().findViewById<MaterialTextView>(R.id.functional_result_title_text)}

    private val functionalTeamListRecycler by lazy { requireView().findViewById<RecyclerView>(R.id.functional_team_list_recycler) }
    private val reactiveTeamListRecycler by lazy { requireView().findViewById<RecyclerView>(R.id.reactive_team_list_recycler) }

    private val loadReactiveDataButton by lazy { requireView().findViewById<FloatingActionButton>(R.id.load_reactive_data_button) }
    private val loadFunctionalDataButton by lazy { requireView().findViewById<FloatingActionButton>(R.id.load_functional_data_button) }
    private val updateRandomTeamButton by lazy { requireView().findViewById<FloatingActionButton>(R.id.update_random_team_button) }
    private val removeRandomTeamButton by lazy { requireView().findViewById<FloatingActionButton>(R.id.remove_random_team_button) }
    private val createRandomTeamButton by lazy { requireView().findViewById<FloatingActionButton>(R.id.add_random_team_button) }

    private val actionButtonsMotionLayout by lazy { requireView().findViewById<MotionLayout>(R.id.action_buttons_motion_layout)}
    private val expandActionButtonsButton by lazy { requireView().findViewById<FloatingActionButton>(R.id.expand_action_buttons_button)}

    private var isActionButtonsLayoutExpanded = false

    private val numberOfTeamsToCreate = 5

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

        viewModel.initializeDataBase(numberOfTeamsToCreate)
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
                    is InitializingDatabase, RetrievingReactiveTeams, RetrievingFunctionalTeams, UpdatingTeam, RemovingTeam, CreatingTeam -> {
                        //showProgressDialog()
                    }
                    is RetrievedReactiveTeams -> {
                        onTeamsRetrieved(state.teams, reactiveTeamListRecycler)
                        hideProgressDialog()
                    }
                    is RetrievedFunctionalTeams -> {
                        onTeamsRetrieved(state.teams, functionalTeamListRecycler)
                        hideProgressDialog()
                    }
                    is InitializedDatabase, UpdatedTeam, CreatedTeam, RemovedTeam -> {
                        hideProgressDialog()
                    }
                    is ErrorInOperationWithId -> {
                        showError(getString(state.messageId, state.team.toString()))
                    }
                    is ErrorInOperation -> {
                        showError(state.message)
                    }
                    else -> { /* no-op */ }
                }
            }
        }
    }

    private fun setupClickListeners() {
        loadReactiveDataButton.setOnClickListener { button ->
            viewModel.getTeamsReactive()
            viewModel.getTeamsFunctional()
            button.isEnabled = false
            reactiveResultTitleText.visibility = View.VISIBLE
            functionalResultTitleText.visibility = View.VISIBLE
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

        expandActionButtonsButton.setOnClickListener { toggleActionButtons(it) }
    }

    private fun toggleActionButtons(view: View) {
        if (isActionButtonsLayoutExpanded) {
            view.rotate(ConstantsAnimation.START_ROTATE)
            actionButtonsMotionLayout.transitionToStart()
        } else {
            view.rotate(ConstantsAnimation.COMPLETE_ROTATE)
            actionButtonsMotionLayout.transitionToEnd()
        }
        isActionButtonsLayoutExpanded = !isActionButtonsLayoutExpanded

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





