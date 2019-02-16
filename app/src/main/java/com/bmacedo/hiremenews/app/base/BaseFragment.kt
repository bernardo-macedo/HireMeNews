package com.bmacedo.hiremenews.app.base

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment() {

    // Fragment lifecycle methods

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

}