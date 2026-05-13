package com.appricut.easylezo.core.data.repository

import android.content.Context
import android.os.Build
import com.appricut.easylezo.core.domain.repository.AppInfoProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppInfoProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AppInfoProvider { // این اینترفیس در لایه Domain تعریف شده
    override fun getVersionCode(): Int {
        return context.packageManager.getPackageInfo(context.packageName, 0).let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.longVersionCode.toInt()
            } else {
                it.versionCode
            }
        }
    }
}