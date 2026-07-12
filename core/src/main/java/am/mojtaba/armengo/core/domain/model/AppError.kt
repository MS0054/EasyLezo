package am.mojtaba.armengo.core.domain.model

sealed class AppError : Exception() {
    data class Network(val errorKey: String = "err_network_unavailable") : AppError()
    data class Server(val errorKey: String) : AppError()
    data class Local(val errorKey: String = "err_local_data_empty") : AppError()
    data class Unknown(val errorKey: String = "err_unknown") : AppError()
}