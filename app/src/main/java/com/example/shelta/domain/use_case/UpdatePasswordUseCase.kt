package com.example.shelta.domain.use_case

import com.example.shelta.common.Message
import com.example.shelta.common.Resource
import com.example.shelta.domain.model.UpdatePassword
import com.example.shelta.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(id: String, oldPassword: String, newPassword: String): Flow<Resource<Message>> = flow {
        try {
            emit(Resource.Loading())
            val message = repository.updatePassword(id.toInt(), UpdatePassword(oldPassword, newPassword))
            emit(Resource.Success(message))
        } catch (e: HttpException){
            if (e.response()?.code() == 404){
                emit(Resource.Error("Wrong old password"))
                return@flow
            }
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error occurred"))
        } catch (e: IOException){
            emit(Resource.Error("Check your internet connection"))
        }
    }
}