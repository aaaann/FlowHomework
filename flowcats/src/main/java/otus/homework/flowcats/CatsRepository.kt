package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatsRepository(
    private val catsService: CatsService,
    private val coroutineDispatchers: CoroutineDispatchers,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatImages() = flow {
        while (true) {
            try {
                val imageUrl = catsService.getCatImage().url
                emit(Result.Success(imageUrl))
                delay(refreshIntervalMs)
            } catch (e: Exception) {
                emit(Result.Error(e.localizedMessage))
            }
        }
    }.flowOn(coroutineDispatchers.ioDispatcher)
}