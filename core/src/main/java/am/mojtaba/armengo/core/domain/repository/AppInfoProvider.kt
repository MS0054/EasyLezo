package am.mojtaba.armengo.core.domain.repository

interface AppInfoProvider {
    fun getVersionCode(): Int
}