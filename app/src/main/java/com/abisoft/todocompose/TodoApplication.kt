package com.abisoft.todocompose

import MyAppComponent
import android.app.Application

class TodoApplication: Application() {

    private lateinit var appComponent: MyAppComponent
    override fun onCreate() {
        super.onCreate()


    }

}