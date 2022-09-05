package com.example.dsa1

import android.app.Application
import android.widget.Toast

/**
 * @作者 王能
 * @时间 2022/5/7
 */
class App : Application() {
    companion object {
        internal var _app: App? = null
    }

    override fun onCreate() {
        super.onCreate()
        _app = this
    }
}

val app: App
    get() = App._app!!

fun String?.toast() {
    if (this.isNullOrEmpty()) {
        return
    }
    Toast.makeText(app, this, Toast.LENGTH_SHORT).show()
}