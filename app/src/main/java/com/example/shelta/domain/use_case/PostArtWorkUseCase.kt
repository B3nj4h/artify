package com.example.shelta.domain.use_case

import com.example.shelta.common.Message
import com.example.shelta.common.Resource
import com.example.shelta.domain.model.Feedback
import com.example.shelta.domain.model.PostArtModel
import com.example.shelta.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.http.Part
import java.io.File
import java.io.IOException
import javax.inject.Inject

class PostArtWorkUseCase @Inject constructor(
    private val repository: Repository
){
    operator fun invoke(file: File): Flow<Resource<Message>> = flow {
        try {
            emit(Resource.Loading())
            val message = repository.postArtWork(file)
            emit(Resource.Success(message))
        } catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error occurred"))
        } catch (e: IOException){
            emit(Resource.Error("Check your internet connection"))
        }
    }
}