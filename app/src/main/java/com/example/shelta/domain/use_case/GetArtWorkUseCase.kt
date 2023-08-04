package com.example.shelta.domain.use_case

import com.example.shelta.common.Resource
import com.example.shelta.data.remote.rest.dto.toArtModel
import com.example.shelta.domain.model.ArtModel
import com.example.shelta.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetArtWorkUseCase @Inject constructor(
    private val repository: Repository
){
    operator fun invoke(): Flow<Resource<List<ArtModel>>> = flow {
        try {
            emit(Resource.Loading())
            val artwork = repository.getArtWork().map { it.toArtModel() }
            emit(Resource.Success(artwork))
        } catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error occurred"))
        } catch (e: IOException){
            emit(Resource.Error("Check your internet connection"))
        }
    }
}