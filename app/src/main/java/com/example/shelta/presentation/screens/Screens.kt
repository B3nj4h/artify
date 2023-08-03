package com.example.shelta.presentation.screens

sealed class Screens(val route: String){
    object LoadScreen: Screens("load_screen")
    object WelcomeScreen: Screens("welcome_screen")
    object LoginScreen: Screens("login_screen")
    object SignUpScreen: Screens("sign_up_screen")
    object ForgotPasswordScreen: Screens("forgot_password_screen")
    object MainScreen: Screens("main_screen")
    object ProfileScreen: Screens("profile_Screen")
    object ChangeContactScreen: Screens("change_contact_screen")
    object ChangeEmailScreen: Screens("change_email_screen")
    object ChangeNameScreen: Screens("change_name_screen")
    object ChangePasswordScreen: Screens("change_password_screen")
    object SendFeedbackScreen: Screens("send_feedback_screen")
}