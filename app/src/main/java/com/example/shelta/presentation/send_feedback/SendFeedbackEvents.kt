package com.example.shelta.presentation.send_feedback

sealed class SendFeedbackEvents {
    data class OnEmailChanged(val email: String): SendFeedbackEvents()
    data class OnIncidentChanged(val incident: String): SendFeedbackEvents()
    data class OnPhoneNumberChanged(val phoneNumber: String): SendFeedbackEvents()
    data class OnGenderChanged(val gender: String): SendFeedbackEvents()
    data class OnFirstNameChanged(val firstName: String): SendFeedbackEvents()
    data class OnLastNameChanged(val lastname: String): SendFeedbackEvents()
    object OnSubmitClicked: SendFeedbackEvents()
}