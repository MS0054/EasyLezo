package am.mojtaba.armengo.core.domain.manager

interface SyncManager {
    fun syncCategoryToServer(workerTag: String)
    fun syncLanguageToServer(workerTag: String)
    fun syncSentenceToServer(workerTag: String)
}