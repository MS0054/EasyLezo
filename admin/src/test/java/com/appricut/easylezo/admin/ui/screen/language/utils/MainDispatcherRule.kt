package com.appricut.easylezo.admin.ui.screen.language.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.rules.TestWatcher
import org.junit.runner.Description

//@OptIn(ExperimentalCoroutinesApi::class)
//class MainDispatcherRule(
//    private val dispatcher: TestDispatcher = StandardTestDispatcher()
//) : TestWatcher() {
//
//    override fun starting(description: Description) {
//        Dispatchers.setMain(dispatcher)
//    }
//
//    override fun finished(description: Description) {
//        Dispatchers.resetMain()
//    }
//}