package com.example.shelta.presentation.navigation.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.shelta.presentation.art_details.components.ArtDetailsScreen
import com.example.shelta.presentation.auth.forgot_password.components.ForgotPasswordScreen
import com.example.shelta.presentation.auth.login.components.LoginScreen
import com.example.shelta.presentation.auth.sign_up.components.SignUpScreen
import com.example.shelta.presentation.change_contact.components.ChangeContactScreen
import com.example.shelta.presentation.change_email.components.ChangeEmailScreen
import com.example.shelta.presentation.change_name.components.ChangeNameScreen
import com.example.shelta.presentation.change_password.components.ChangePasswordScreen
import com.example.shelta.presentation.load.components.LoadScreen
import com.example.shelta.presentation.main.components.MainScreen
import com.example.shelta.presentation.profile.components.ProfileScreen
import com.example.shelta.presentation.screens.Screens
import com.example.shelta.presentation.search.components.SearchScreen
import com.example.shelta.presentation.welcome.components.WelcomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavGraph(
    navHostController: NavHostController
) {
    AnimatedNavHost(
        navController = navHostController,
        startDestination = Screens.LoadScreen.route
    ){
        composable( route = Screens.LoadScreen.route ){ LoadScreen(navHostController) }

        composable(
            route = Screens.WelcomeScreen.route ,
            popExitTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(1000))
                    else -> null
                }
            }
        ){
            WelcomeScreen(onNavigate = { navHostController.navigate(it.route) })
        }

        composable(
            route = Screens.LoginScreen.route,
            enterTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            initialOffset = {0},
                            animationSpec = tween(300)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(1000))
                    else -> null
                }
            }
        ){
            LoginScreen(
                onNavigate = { navHostController.navigate(it.route){
                    popUpTo(Screens.MainScreen.route){
                        inclusive = true
                    }
                } },
                onPopBackStack = { navHostController.popBackStack() })
        }
        composable(
            route = Screens.ForgotPasswordScreen.route,
            enterTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            initialOffset = {0},
                            animationSpec = tween(300)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(1000))
                    else -> null
                }
            }
        ){
            ForgotPasswordScreen(onPopBackStack = { navHostController.popBackStack() })
        }
        composable(
            route = Screens.SignUpScreen.route,
            enterTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            initialOffset = {0},
                            animationSpec = tween(300)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(1000))
                    else -> null
                }
            }
        ){
            SignUpScreen(
                onNavigate = { navHostController.navigate(it.route){
                    popUpTo(Screens.WelcomeScreen.route){ inclusive = true } } },
                onPopBackStack = { navHostController.popBackStack() })
        }

        composable(
            route = Screens.MainScreen.route,
            enterTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            initialOffset = {0},
                            animationSpec = tween(300)
                        )
                    else -> null
                }
            },
        ){ MainScreen(
            navHostController,
            onNavigate = { navHostController.navigate(it.route) }) }

        composable(
            route = Screens.ProfileScreen.route,
            enterTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            initialOffset = {0},
                            animationSpec = tween(300)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                    else -> null
                }
            }
        ){
            ProfileScreen(
                onPopBackStack = { navHostController.popBackStack()},
                navHostController,
                onNavigate = { navHostController.navigate(it.route)},)
        }

        composable(
            route = Screens.ChangeNameScreen.route + "/{id}",
            enterTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            initialOffset = {0},
                            animationSpec = tween(300)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(1000))
                    else -> null
                }
            }
        ){
            ChangeNameScreen(navHostController = navHostController)
        }

        composable(
            route = Screens.ChangeContactScreen.route + "/{id}",
            enterTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            initialOffset = {0},
                            animationSpec = tween(300)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(1000))
                    else -> null
                }
            }
        ){
            ChangeContactScreen(onPopBackStack = { navHostController.popBackStack()})
        }

        composable(
            route = Screens.ChangePasswordScreen.route + "/{id}",
            enterTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            initialOffset = {0},
                            animationSpec = tween(300)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(1000))
                    else -> null
                }
            }
        ){
            ChangePasswordScreen(onPopBackStack = { navHostController.popBackStack()})
        }

        composable(
            route = Screens.ChangeEmailScreen.route + "/{id}",
            enterTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            initialOffset = {0},
                            animationSpec = tween(300)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(1000))
                    else -> null
                }
            }
        ){
            ChangeEmailScreen(onPopBackStack = { navHostController.popBackStack()})
        }

        composable(
            route = Screens.ArtWorkDetailsScreen.route + "/{id}",
            enterTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            initialOffset = {0},
                            animationSpec = tween(300)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(1000))
                    else -> null
                }
            }
        ){
            ArtDetailsScreen(
                onPopBackStack = { navHostController.popBackStack() }
            )
        }

        composable(
            route = Screens.SearchScreen.route,
            enterTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            initialOffset = {0},
                            animationSpec = tween(300)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    navHostController.currentDestination?.route ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(1000))
                    else -> null
                }
            }
        ){
            SearchScreen(
                navHostController,
                onPopBackStack = { navHostController.popBackStack() }
            )
        }

//        composable(
//            route = Screens.SendFeedbackScreen.route,
//            enterTransition = {
//                when (targetState.destination.route) {
//                    navHostController.currentDestination?.route ->
//                        slideIntoContainer(
//                            AnimatedContentScope.SlideDirection.Left,
//                            initialOffset = {0},
//                            animationSpec = tween(300)
//                        )
//                    else -> null
//                }
//            },
//            popExitTransition = {
//                when (targetState.destination.route) {
//                    navHostController.currentDestination?.route ->
//                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(1000))
//                    else -> null
//                }
//            }
//        ){
//            SendFeedbackScreen(navHostController)
//        }
    }
}