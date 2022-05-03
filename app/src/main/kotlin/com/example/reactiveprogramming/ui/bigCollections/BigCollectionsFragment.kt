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

    private val sequenceTestCasesResults = mutableListOf<BigCollectionsTestCaseResult>()
    private val listTestCasesResults = mutableListOf<BigCollectionsTestCaseResult>()

    override fun handleUiConfigurationViewState(uiConfigurationViewState: UiConfigurationViewState) = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModelLiveData()

        setupListeners()
    }

    private fun setupViewModelLiveData() {
        bigCollectionsViewModel.bigCollectionsViewModelLiveData.observe(this, Observer { state ->
            when (state) {
                is PerformingOperation -> { showProgressDialog() }
                is ExecuteNextSequenceTestCase -> {
                    sequenceOperationResultText.text =
                        sequenceOperationResultText.text.toString() +
                            "Sequence op ${state.previousTestCaseResult.id} " +
                            "in ${state.previousTestCaseResult.time} " +
                            "to take ${state.previousTestCaseResult.elements.size} elements \n"

                    sequenceTestCasesResults.add(state.previousTestCaseResult)
                    executeSequenceTestCases(state.previousTestCaseResult.id + 1)
                }
                is SequenceTestCasesFinished -> {
                    sequenceOperationResultText.text =
                        sequenceOperationResultText.text.toString() +
                                "Sequence op ${state.testCaseResult.id} " +
                                "in ${state.testCaseResult.time} " +
                                "to take ${state.testCaseResult.elements.size} elements \n"

                    sequenceTestCasesResults.add(state.testCaseResult)
                    executeListTestCases()
                    hideProgressDialog()
                }
                is SequenceOperationError -> {
                    sequenceOperationResultText.text =
                        sequenceOperationResultText.text.toString() +
                                "Error in sequence ${state.message}"
                    hideProgressDialog()
                }
                is ExecuteNextListTestCase -> {
                    listOperationResultText.text =
                        listOperationResultText.text.toString() +
                                "List op ${state.previousTestCaseResult.id} " +
                                "in ${state.previousTestCaseResult.time} " +
                                "to take ${state.previousTestCaseResult.elements.size} elements \n"

                    executeListTestCases(state.previousTestCaseResult.id + 1)
                    listTestCasesResults.add(state.previousTestCaseResult)
                }
                is ListTestCasesFinished -> {
                    listOperationResultText.text =
                        listOperationResultText.text.toString() +
                                "List op ${state.testCaseResult.id} " +
                                "in ${state.testCaseResult.time} " +
                                "to take ${state.testCaseResult.elements.size} elements \n"
                    hideProgressDialog()
                }
                is ListOperationError -> {
                    listOperationResultText.text =
                        listOperationResultText.text.toString() +
                                "Error in list ${state.message}"
                    hideProgressDialog()
                }
            }
        })
    }

    private fun setupListeners() {
        calculateOperationButton.setOnClickListener { executeSequenceTestCases() }
    }

    private fun executeSequenceTestCases(testCaseNumber: Int = 0) {
        val testCase = defaultTestCases[testCaseNumber]
        try {
            val sequenceOfElements = generateSequence(0) { it + 1 }.take(testCase.listLength)
            bigCollectionsViewModel.findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
                sequenceOfElements,
                testCase
            )
        } catch (e: Throwable) {
            bigCollectionsViewModel.errorInSequenceOperation(e)
        }
    }

    private fun executeListTestCases(testCaseNumber: Int = 0) {
        val testCase = defaultTestCases[testCaseNumber]
        try {
            val listOfElements = List(testCase.listLength) { it }
            bigCollectionsViewModel.findFirstNumbersInListThatAreOddAndTheirSquareHasCertainDigits(
                listOfElements,
                testCase
            )
        } catch (e: Throwable) {
            bigCollectionsViewModel.errorInListOperation(e)
        }
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