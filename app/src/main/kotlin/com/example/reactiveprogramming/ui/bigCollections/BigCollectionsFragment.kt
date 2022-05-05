package com.example.reactiveprogramming.ui.bigCollections

import android.graphics.Color
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
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class BigCollectionsFragment: CustomFragment(R.layout.big_collections_fragment) {

    private val bigCollectionsViewModel: BigCollectionsViewModel by viewModel()

    private val sequenceOperationResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.sequence_result_text) }
    private val listOperationResultText by lazy { requireView().findViewById<MaterialTextView>(R.id.list_result_text) }
    private val resultChart by lazy { requireView().findViewById<LineChart>(R.id.result_chart)}

    private val calculateOperationButton by lazy { requireView().findViewById<MaterialButton>(R.id.calculate_operation_button) }

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
        setupChart()
        setupListeners()
    }

    private fun setupViewModelLiveData() {
        bigCollectionsViewModel.bigCollectionsViewModelLiveData.observe(this, Observer { state ->
            when (state) {
                is PerformingOperation -> { showProgressDialog() }
                is ExecuteNextSequenceTestCase -> {
                    updateProgressDialogTitle("Done Sequence nº ${state.previousTestCaseResult.id}")
                    sequenceTestCasesResults.add(state.previousTestCaseResult)
                    executeSequenceTestCases(state.previousTestCaseResult.id + 1)
                }
                is SequenceTestCasesFinished -> {
                    updateProgressDialogTitle("Done Sequence operations")
                    sequenceOperationResultText.text =
                        listOperationResultText.text.toString() +
                                " Done sequence operations"

                    sequenceTestCasesResults.add(state.testCaseResult)
                    executeListTestCases()
                    hideProgressDialog()
                }
                is SequenceOperationError -> {
                    sequenceOperationResultText.text =
                        sequenceOperationResultText.text.toString() +
                                " Error in sequence ${state.message}"
                    hideProgressDialog()
                }
                is ExecuteNextListTestCase -> {
                    updateProgressDialogTitle("Done List nº ${state.previousTestCaseResult.id}")
                    executeListTestCases(state.previousTestCaseResult.id + 1)
                    listTestCasesResults.add(state.previousTestCaseResult)
                }
                is ListTestCasesFinished -> {
                    updateProgressDialogTitle("Done List operations")
                    listOperationResultText.text =
                        listOperationResultText.text.toString() +
                                " Done list operations"

                    listTestCasesResults.add(state.testCaseResult)
                    hideProgressDialog()
                    generateChartResult()
                }
                is ListOperationError -> {
                    listOperationResultText.text =
                        listOperationResultText.text.toString() +
                                " Error in list ${state.message}"
                    hideProgressDialog()
                    generateChartResult()
                }
            }
        })
    }

    private fun setupListeners() {
        calculateOperationButton.setOnClickListener { executeSequenceTestCases() }
    }

    private fun setupChart() {
        resultChart.apply {
            description.isEnabled = false
            setPinchZoom(false)
            setDrawGridBackground(false)
            axisRight.isEnabled = false
            axisRight.setDrawGridLines(false)
            axisLeft.setDrawGridLines(false)
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
        }

        resultChart.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            orientation = Legend.LegendOrientation.HORIZONTAL
            setDrawInside(false)
            form = Legend.LegendForm.SQUARE
        }
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

    private fun generateChartResult() {
        val sequenceValues = sequenceTestCasesResults.mapIndexed { index, result -> Entry(index.toFloat(), result.time.toFloat()) }
        val listValues = listTestCasesResults.mapIndexed { index, result -> Entry(index.toFloat(), result.time.toFloat()) }

        val sequenceValuesSet = LineDataSet(sequenceValues, "Sequence times")
        sequenceValuesSet.apply {
            color = Color.GREEN
            setDrawValues(false)
        }

        val listValueSet = LineDataSet(listValues, "List times")
        listValueSet.apply {
            color = Color.RED
            setDrawValues(false)
        }

        val dataSets = arrayListOf<ILineDataSet>(sequenceValuesSet, listValueSet)
        val data = LineData(dataSets)

        resultChart.data = data
        resultChart.invalidate()

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