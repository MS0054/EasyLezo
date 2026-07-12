package am.mojtaba.armengo.core.domain.usecase.metadata

import am.mojtaba.armengo.core.domain.model.Error
import android.content.Context
import android.widget.Toast

import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddMetadataErrorUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(error: Error) {
        val errors = metadataRepository.observeMetadata().map { it.errors }.first()

        if (errors.any { it.code == error.code }) {
            Toast.makeText(context, "This error already exists", Toast.LENGTH_LONG).show()
        }else{
            val newErrors = errors + error
            try {
                metadataRepository.updateMetadataErrorsServer(newErrors)
            } catch (e: Exception) {
            }
        }
    }
}