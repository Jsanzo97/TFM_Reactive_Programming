package com.example.reactiveprogramming.ui.bigCollections

import android.os.Bundle
import android.view.View
import arrow.core.some
import com.example.common.UiConfigurationViewState
import com.example.common.fragment.CustomFragment
import com.example.reactiveprogramming.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer

class BigCollectionsFragment: CustomFragment(R.layout.big_collections_fragment) {

    private val bigCollectionsViewModel: BigCollectionsViewModel by viewModel()

    private val sequenceOperationResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.sequence_result_text) }
    private val listOperationResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.list_result_text) }

    private val calculateOperationButton by lazy { requireView().findViewById<MaterialButton>(R.id.calculate_operation) }

    override var uiConfigurationViewState = UiConfigurationViewState(
        showToolbar = true,
        statusBarColor = R.color.colorPrimaryDark.some(),
        toolbarColor = R.color.colorPrimary.some(),
        toolbarNavigationIconColor = R.color.white.some(),
        showLogoutButton = true
    )

    private val listLength = 5000000

    private var firstListOfElements = listOf<Int>()
    private var secondListOfElements = listOf<Int>()

    override fun handleUiConfigurationViewState(uiConfigurationViewState: UiConfigurationViewState) =
        Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModelLiveData()

        setupListeners()

        try {
            firstListOfElements = List(listLength) { it }
        } catch (e: Throwable) {
            showError("Error in sequence $e")
        }

        try {
            secondListOfElements = List(listLength) { it }
        } catch (e: Throwable) {
            showError("Error in list $e")
        }
    }

    private fun setupViewModelLiveData() {
        bigCollectionsViewModel.bigCollectionsViewModelLiveData.observe(this, Observer { state ->
            when (state) {
                is PerformingOperation -> {}
                is SequenceOperationFinished -> {
                    sequenceOperationResultText.text =
                        "Sequence operations done in ${state.timeNeeded} filtered elements are ${state.elements.size}"
                }
                is ListOperationFinished -> {
                    listOperationResultText.text =
                        "List operations done in ${state.timeNeeded} filtered elements are ${state.elements.size}"
                }
            }
        })
    }

    private fun setupListeners() {
        calculateOperationButton.setOnClickListener { setupAndLaunchOperations() }
    }

    private fun setupAndLaunchOperations() {
        val maxNumberToEvaluate = 3000000
        val numbersToTake = 10000
        val maxNumberLength = 9

        bigCollectionsViewModel.findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
            secondListOfElements,
            maxNumberToEvaluate,
            numbersToTake,
            maxNumberLength
        )

        bigCollectionsViewModel.findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
            firstListOfElements.asSequence(),
            maxNumberToEvaluate,
            numbersToTake,
            maxNumberLength
        )

    }
}

/*

val sequence = listOf("One", "Two", "Three", "Four")
val list = listOf(1,2,3,4)
val listAsSequence = list.asSequence()

val strNumberSize = sequence
    .asSequence()
    .filter { it.length > 3 }
    .map { it.length }
    .toList()

val randomNumbers = sequence {
    yield(3)
    yieldAll(listOf(4, 5, 6, 8, 25))
    yieldAll(generateSequence(2) { it*it })
}

PROBAR ZIP y GROUPBY

val numbersToTake = 1000
val maxNumber = 1000000
val maxLength = 7

var startTimeOperation: Long
var endTimeOperation: Long



 */