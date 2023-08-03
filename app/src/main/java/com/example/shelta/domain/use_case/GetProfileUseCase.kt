package com.example.shelta.domain.use_case

import com.example.shelta.common.Resource
import com.example.shelta.data.remote.rest.dto.toProfile
import com.example.shelta.domain.model.Profile
import com.example.shelta.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<Resource<Profile>> = flow {
        try {
            emit(Resource.Loading())
            val user = repository.getUser().toProfile()
            emit(Resource.Success(user))
        } catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error occurred"))
        } catch (e: IOException){
            emit(Resource.Error("Check your internet connection"))
        }
    }
}