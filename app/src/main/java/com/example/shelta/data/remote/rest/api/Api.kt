package com.example.shelta.data.remote.rest.api

import com.example.shelta.common.Message
import com.example.shelta.data.remote.rest.dto.ArtDto
import com.example.shelta.data.remote.rest.dto.ProfileUserDto
import com.example.shelta.domain.model.Feedback
import com.example.shelta.domain.model.PostArtModel
import com.example.shelta.domain.model.ResetModel
import com.example.shelta.domain.model.UpdateContact
import com.example.shelta.domain.model.UpdateEmail
import com.example.shelta.domain.model.UpdateName
import com.example.shelta.domain.model.UpdatePassword
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @POST("update_name/{id}")
    suspend fun updateName(
        @Path("id") id: Int,
        @Body updateName: UpdateName
    ) : Message

    @POST("update_contact/{id}")
    suspend fun updateContact(
        @Path("id") id: Int,
        @Body updateContact: UpdateContact
    ) : Message

    @POST("update_email/{id}")
    suspend fun updateEmail(
        @Path("id") id: Int,
        @Body updateEmail: UpdateEmail
    ) : Message

    @POST("update_password/{id}")
    suspend fun updatePassword(
        @Path("id") id: Int,
        @Body updatePassword: UpdatePassword
    ) : Message

    @POST("send_feedback")
    suspend fun sendFeedback(
        @Body feedBack: Feedback
    ) : Message

    @GET("user")
    suspend fun getUser(): ProfileUserDto

    @POST("reset_password")
    suspend fun resetPassword(
        @Body resetModel: ResetModel
    ): Message

    @GET("artwork")
    suspend fun getArtWork(): List<ArtDto>

    @POST("artwork")
    suspend fun postArtWork(
        @Body postArtModel: PostArtModel
    ): Message

    @GET("artwork/{id}")
    suspend fun getArtWorkDetails(
        @Path("id") id: Int,
    ): ArtDto
}