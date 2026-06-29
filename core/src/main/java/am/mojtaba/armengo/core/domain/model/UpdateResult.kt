package am.mojtaba.armengo.core.domain.model

import am.mojtaba.armengo.core.data.datastore.enums.UpdateType

data class UpdateResult(
    val type: UpdateType ,
    val info: UpdateInfo
)