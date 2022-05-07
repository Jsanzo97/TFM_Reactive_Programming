package com.example.reactiveprogramming.ui.bigCollections

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import arrow.core.some
import com.example.common.UiConfigurationViewState
import com.example.common.fragment.CustomFragment
import com.example.reactiveprogramming.R
import com.google.android.material.button.MaterialButton
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class BigCollectionsFragment: CustomFragment(R.layout.big_collections_fragment) {

    private val bigCollectionsViewModel: BigCollectionsViewModel by viewModel()

    private val resultChart by lazy { requireView().findViewById<LineChart>(R.id.result_chart)}

    private val toggleRealSequenceDataButton by lazy { requireView().findViewById<ExtendedFloatingActionButton>(R.id.toggle_real_sequence_data_button) }
    private val toggleAvgSequenceDataButton by lazy { requireView().findViewById<ExtendedFloatingActionButton>(R.id.toggle_avg_sequence_data_button) }
    private val toggleRealListDataButton by lazy { requireView().findViewById<ExtendedFloatingActionButton>(R.id.toggle_real_list_data_button) }
    private val toggleAvgListDataButton by lazy { requireView().findViewById<ExtendedFloatingActionButton>(R.id.toggle_avg_list_data_button) }
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

    var sequenceValuesSet = LineDataSet(listOf(), "Sequence times in ms")
    var listValueSet = LineDataSet(listOf(), "List times in ms")
    var sequenceAvgValuesSet = LineDataSet(listOf(), "Sequence avg times in ms")
    var listAvgValueSet = LineDataSet(listOf(), "List avg times in ms")

    var testCaseNumber = 0

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
                    updateProgressDialogTitle("Done Sequence nº $testCaseNumber")
                    sequenceTestCasesResults.add(state.previousTestCaseResult)
                    executeSequenceTestCases(testCaseNumber)
                    testCaseNumber++
                }
                is SequenceTestCasesFinished -> {
                    testCaseNumber = 0
                    updateProgressDialogTitle("Done Sequence operations")
                    sequenceTestCasesResults.add(state.testCaseResult)
                    executeListTestCases(testCaseNumber)
                    hideProgressDialog()
                }
                is SequenceOperationError -> {
                    hideProgressDialog()
                    showError(state.message)
                }
                is ExecuteNextListTestCase -> {
                    updateProgressDialogTitle("Done List nº $testCaseNumber")
                    executeListTestCases(testCaseNumber)
                    listTestCasesResults.add(state.previousTestCaseResult)
                    testCaseNumber++
                }
                is ListTestCasesFinished -> {
                    testCaseNumber = 0
                    updateProgressDialogTitle("Done List operations")
                    listTestCasesResults.add(state.testCaseResult)
                    hideProgressDialog()
                    generateChartResult()
                }
                is ListOperationError -> {
                    hideProgressDialog()
                    showError(state.message)
                    generateChartResult()
                }
            }
        })
    }

    private fun setupListeners() {
        toggleRealSequenceDataButton.setOnClickListener {
            sequenceValuesSet.isVisible = !sequenceValuesSet.isVisible
            resultChart.invalidate()
        }

        toggleRealListDataButton.setOnClickListener {
            listValueSet.isVisible = !listValueSet.isVisible
            resultChart.invalidate()
        }

        toggleAvgSequenceDataButton.setOnClickListener {
            sequenceAvgValuesSet.isVisible = !sequenceAvgValuesSet.isVisible
            resultChart.invalidate()
        }

        toggleAvgListDataButton.setOnClickListener {
            listAvgValueSet.isVisible = !listAvgValueSet.isVisible
            resultChart.invalidate()
        }

        calculateOperationButton.setOnClickListener { executeSequenceTestCases() }
    }

    private fun setupChart() {
        resultChart.apply {
            description.isEnabled = false
            setPinchZoom(false)
            setDrawGridBackground(false)
            setTouchEnabled(false)
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
            isWordWrapEnabled = true
            form = Legend.LegendForm.SQUARE
        }
    }

    private fun executeSequenceTestCases(testCaseNumber: Int = 0) {
        val testCase = defaultTestCases[testCaseNumber]
        try {
            val sequenceOfElements = generateSequence(0) { it + 1 }.take(testCase.listLength)
            bigCollectionsViewModel.findFirstNumbersInSequenceThatAreOddAndTheirSquareHasCertainDigits(
                sequenceOfElements,
                testCase,
                testCaseNumber
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
                testCase,
                testCaseNumber
            )
        } catch (e: Throwable) {
            bigCollectionsViewModel.errorInListOperation(e)
        }
    }

    private fun generateChartResult() {
        val sequenceValues = sequenceTestCasesResults.mapIndexed { index, result -> Entry(index.toFloat(), result.time.toFloat()) }
        val listValues = listTestCasesResults.mapIndexed { index, result -> Entry(index.toFloat(), result.time.toFloat()) }

        val avgSequenceValues = sequenceTestCasesResults.mapIndexed { index, result ->
            val time = if (index == 0 || index >= sequenceTestCasesResults.size - 1) {
                result.time.toFloat()
            } else {
                (result.time.toFloat() + sequenceTestCasesResults[index -1].time.toFloat() + sequenceTestCasesResults[index +1].time.toFloat()) / 3.0f
            }

            Entry(index.toFloat(), time)
        }

        val avgListValues = listTestCasesResults.mapIndexed { index, result ->
            val time = if (index == 0 || index >= listTestCasesResults.size - 1) {
                result.time.toFloat()
            } else {
                (result.time.toFloat() + listTestCasesResults[index -1].time.toFloat() + listTestCasesResults[index +1].time.toFloat() ) / 3.0f
            }

            Entry(index.toFloat(), time)
        }

        sequenceValuesSet = LineDataSet(sequenceValues, "Sequence times in ms")
        sequenceValuesSet.apply {
            color = resources.getColor(R.color.fontColorGreen, null)
            setDrawValues(false)
            setDrawCircles(false)
        }

        listValueSet = LineDataSet(listValues, "List times in ms")
        listValueSet.apply {
            color = resources.getColor(R.color.fontColorRed, null)
            setDrawValues(false)
            setDrawCircles(false)
        }

        sequenceAvgValuesSet = LineDataSet(avgSequenceValues, "Sequence avg times in ms")
        sequenceAvgValuesSet.apply {
            color = resources.getColor(R.color.fontColorYellow, null)
            setDrawValues(false)
            setDrawCircles(false)
            isVisible = false
        }

        listAvgValueSet = LineDataSet(avgListValues, "List avg times in ms")
        listAvgValueSet.apply {
            color = resources.getColor(R.color.fontColorBlue, null)
            setDrawValues(false)
            setDrawCircles(false)
            isVisible = false
        }

        val dataSets = arrayListOf<ILineDataSet>(sequenceValuesSet, listValueSet, sequenceAvgValuesSet, listAvgValueSet)
        val data = LineData(dataSets)

        data.setValueTypeface(ResourcesCompat.getFont(requireContext(), R.font.roboto_light))

        resultChart.data = data
        resultChart.invalidate()
    }
}
