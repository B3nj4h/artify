package com.example.shelta.domain.repository

import com.example.shelta.common.Message
import com.example.shelta.data.remote.rest.dto.ArtDto
import com.example.shelta.data.remote.rest.dto.ProfileUserDto
import com.example.shelta.domain.model.ArtModel
import com.example.shelta.domain.model.Feedback
import com.example.shelta.domain.model.PostArtModel
import com.example.shelta.domain.model.ResetModel
import com.example.shelta.domain.model.UpdateContact
import com.example.shelta.domain.model.UpdateEmail
import com.example.shelta.domain.model.UpdateName
import com.example.shelta.domain.model.UpdatePassword
import retrofit2.http.Part
import java.io.File

interface Repository {
    suspend fun updateName(id:Int, updateName: UpdateName) : Message
    suspend fun updateContact(id: Int, updateContact: UpdateContact) : Message
    suspend fun updateEmail(id: Int, updateEmail: UpdateEmail) : Message
    suspend fun updatePassword(id: Int, updatePassword: UpdatePassword) : Message
    suspend fun sendFeedback(feedback: Feedback): Message
    suspend fun getUser(): ProfileUserDto
    suspend fun resetPassword(resetModel: ResetModel): Message
    suspend fun getArtWork(): List<ArtDto>
    suspend fun postArtWork(file: File): Message
    suspend fun getArtWorkDetails(id: Int): List<ArtDto>
    suspend fun searchArtWork(name: String): List<ArtDto>
}