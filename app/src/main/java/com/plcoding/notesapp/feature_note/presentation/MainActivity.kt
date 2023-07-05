package com.plcoding.notesapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.notesapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.plcoding.notesapp.feature_note.presentation.notes.NotesScreen
import com.plcoding.notesapp.feature_note.presentation.util.Screen
import com.plcoding.notesapp.ui.theme.NotesApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesApp {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController() //This line initializes a NavController using the rememberNavController function from the Navigation component.
                    //The NavController is responsible for handling navigation within the app, such as moving between different screens or destinations.


                    NavHost(  //NavHost is a composable provided by the Navigation component that acts as a container for hosting navigation destinations (screens or fragments).
                       //The navController is the previously initialized NavController, and startDestination is the initial screen or destination to be shown when the app starts

                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    ) {
                        composable(route = Screen.NotesScreen.route) {
                            NotesScreen(navController = navController)  //It takes the navController as a parameter to enable navigation to other screens.
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route +
                                    "?noteId={noteId}&noteColor={noteColor}", //route for a specific screen or destination in the app using the composable function of the Navigation component. The route includes parameters or dynamic segments that can be passed values during navigation.
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}
