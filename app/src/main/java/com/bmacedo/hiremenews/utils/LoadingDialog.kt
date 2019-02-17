package com.bmacedo.hiremenews.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bmacedo.hiremenews.R
import com.bmacedo.hiremenews.app.base.BaseFragment

/**
 * Simple Loading Dialog that presents a spinning circle
 * and blocks user interactions.
 */
class LoadingDialog : DialogFragment() {

    private var rootView: View? = null

    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.LoadingDialog)
        isCancelable = false
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(context)
        rootView = inflater.inflate(R.layout.dialog_loading, null)

        val builder = AlertDialog.Builder(context!!, R.style.LoadingDialog)
        builder.setView(rootView)
        alertDialog = builder.create()

        return alertDialog!!
    }

    override fun dismiss() {
        if (fragmentManager != null) {
            super.dismiss()
        }
    }


    companion object {
        private const val LOADING_FRAGMENT_TAG = "loading_dialog"

        fun show(fragment: BaseFragment): LoadingDialog? {
            val manager = fragment.fragmentManager
            return manager?.let { show(manager) }
        }

        fun hide(fragment: BaseFragment) {
            val manager = fragment.fragmentManager
            manager?.let { hide(manager) }
        }

        private fun hide(fragmentManager: FragmentManager) {
            val handler = Handler()
            handler.post {
                val loadingFragment = fragmentManager.findFragmentByTag(LOADING_FRAGMENT_TAG)
                if (loadingFragment != null && loadingFragment.isAdded && loadingFragment is DialogFragment) {
                    loadingFragment.dismissAllowingStateLoss()
                }
            }
        }

        private fun show(manager: FragmentManager): LoadingDialog {
            val dialog = newInstance()
            dialog.show(manager, LOADING_FRAGMENT_TAG)
            return dialog
        }

        private fun newInstance(): LoadingDialog {
            return LoadingDialog()
        }
    }

}