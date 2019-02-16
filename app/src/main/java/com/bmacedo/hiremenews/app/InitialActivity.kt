package com.bmacedo.hiremenews.app

import android.os.Bundle
import com.bmacedo.hiremenews.R
import com.bmacedo.hiremenews.app.base.BaseActivity

class InitialActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)
    }
}
