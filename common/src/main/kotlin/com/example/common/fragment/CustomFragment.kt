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
import com.google.android.material.textview.MaterialTextView
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Base fragment to have some common functions and code in all fragments of the application
 * @param contentLayoutId layout to bind with the fragment to paint it on the screen
 */
abstract class CustomFragment(private val contentLayoutId: Int) : Fragment() {

    // ViewModel to manage ui configuration changes
    private val uiConfigurationViewModel by sharedViewModel<UiConfigurationViewModel>()

    // UI Configuration for custom toolbar, fragments should override this property
    protected abstract val uiConfigurationViewState: UiConfigurationViewState

    // Common custom views for fragments
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

    /**
     * Shows error message on the screen
     * @param message description of the error
     */
    fun showError(message: String) {
        hideProgressDialog()
        CustomDialogFragment(
            message = message,
            title = getString(R.string.error)
        ).show(requireFragmentManager(), DIALOG_FRAGMENT_TAG)
    }

    /**
     * Shows progress dialog on the screen
     * @param title title of the progress dialog
     */
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

    /**
     * Changes the progress dialog title
     * @param title new title for the progress dialog
     */
    fun updateProgressDialogTitle(title: String) {
        if (::progressDialog.isInitialized) {
            progressView.findViewById<MaterialTextView>(R.id.title_progress_dialog).text = title
        }
    }

    /**
     * Hides the progress dialog
     */
    fun hideProgressDialog() {
        if (::progressDialog.isInitialized) {
            progressDialog.dismiss()
        }
    }

}
