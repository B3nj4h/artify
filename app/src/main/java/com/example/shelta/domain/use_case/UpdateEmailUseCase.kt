package com.example.shelta.domain.use_case

import com.example.shelta.common.Message
import com.example.shelta.common.Resource
import com.example.shelta.domain.model.UpdateEmail
import com.example.shelta.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateEmailUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(id: String, email: String): Flow<Resource<Message>> = flow {
        try {
            emit(Resource.Loading())
            val message = repository.updateEmail(id.toInt(), UpdateEmail(email))
            emit(Resource.Success(message))
        } catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error occurred"))
        } catch (e: IOException){
            emit(Resource.Error("Check your internet connection"))
        }
    }
}