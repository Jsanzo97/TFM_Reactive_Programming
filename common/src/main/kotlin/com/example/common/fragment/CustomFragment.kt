package com.example.common.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.common.DIALOG_FRAGMENT_TAG
import com.example.common.R
import com.example.common.UiConfigurationViewModel
import com.example.common.UiConfigurationViewState
import com.example.common.extensions.hideKeyboard
import com.google.android.material.textview.MaterialTextView
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class CustomFragment(private val contentLayoutId: Int) : Fragment() {

    private val uiConfigurationViewModel by sharedViewModel<UiConfigurationViewModel>()
    protected abstract val uiConfigurationViewState: UiConfigurationViewState

    private lateinit var progressView: View
    private lateinit var progressDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        uiConfigurationViewModel.updateViewState(uiConfigurationViewState)

        return inflater.inflate(contentLayoutId, container, false)
    }

    protected abstract fun handleUiConfigurationViewState(uiConfigurationViewState: UiConfigurationViewState)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiConfigurationViewModel.getUiConfigurationLiveData().observe(this, {
            handleUiConfigurationViewState(it)
        })
    }

    fun showError(message: String) {
        hideKeyboard()
        hideProgressDialog()
        CustomDialogFragment(
            message = message,
            title = getString(R.string.error)
        ).show(requireFragmentManager(), DIALOG_FRAGMENT_TAG)
    }

    fun showProgressDialog(title: String = getString(R.string.loading)) {
        if (!::progressDialog.isInitialized) {
            progressView = layoutInflater.inflate(R.layout.custom_progress_dialog, null)
            progressView.findViewById<MaterialTextView>(R.id.title_progress_dialog).text = title
            progressDialog = AlertDialog.Builder(requireContext())
                .setCancelable(false)
                .setView(progressView)
                .create()
        }

        progressDialog.show()
    }

    fun updateProgressDialogTitle(title: String) {
        if (::progressDialog.isInitialized) {
            progressView.findViewById<MaterialTextView>(R.id.title_progress_dialog).text = title
        }
    }

    fun hideProgressDialog() {
        if (::progressDialog.isInitialized) {
            progressDialog.dismiss()
        }
    }

}
