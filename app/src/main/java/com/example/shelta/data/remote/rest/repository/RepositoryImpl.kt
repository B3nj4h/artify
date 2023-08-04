package com.example.shelta.data.remote.rest.repository

import com.example.shelta.common.Message
import com.example.shelta.data.remote.rest.api.Api
import com.example.shelta.data.remote.rest.dto.ArtDto
import com.example.shelta.data.remote.rest.dto.ProfileUserDto
import com.example.shelta.domain.model.Feedback
import com.example.shelta.domain.model.PostArtModel
import com.example.shelta.domain.model.ResetModel
import com.example.shelta.domain.model.UpdateContact
import com.example.shelta.domain.model.UpdateEmail
import com.example.shelta.domain.model.UpdateName
import com.example.shelta.domain.model.UpdatePassword
import com.example.shelta.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: Api
): Repository {
    override suspend fun getUser(): ProfileUserDto {
        return api.getUser()
    }

    override suspend fun updateName(id: Int, updateName: UpdateName): Message {
        return api.updateName(id, updateName)
    }

    override suspend fun updateContact(id: Int, updateContact: UpdateContact): Message {
        return api.updateContact(id, updateContact)
    }

    override suspend fun updateEmail(id: Int, updateEmail: UpdateEmail): Message {
        return api.updateEmail(id, updateEmail)
    }

    override suspend fun updatePassword(id: Int, updatePassword: UpdatePassword): Message {
        return api.updatePassword(id, updatePassword)
    }

    override suspend fun sendFeedback(feedback: Feedback): Message {
        return api.sendFeedback(feedback)
    }

    override suspend fun resetPassword(resetModel: ResetModel): Message {
        return api.resetPassword(resetModel)
    }

    override suspend fun getArtWork(): List<ArtDto> {
        return api.getArtWork()
    }

    override suspend fun postArtWork(postArtModel: PostArtModel): Message {
        return api.postArtWork(postArtModel)
    }
}