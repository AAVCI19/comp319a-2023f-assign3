package com.example.dailycookingapplication.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dailycookingapplication.data.Resources
import com.example.dailycookingapplication.loadResources

class RecipeViewModel: ViewModel() {
    private val _selectedDay = mutableStateOf(1)
    val selectedDay
        get() = _selectedDay.value
    fun setSelectedDay(day: Int) {
        _selectedDay.value = day
    }
    private val _resources = mutableStateOf<List<Resources>>(emptyList())
    val resources
        get() = _resources.value
    init {
        _resources.value = loadResources().map { it.copy(comments = emptyList()) }
    }
    fun addComment(comment: String) {
        val currentSelectedDay = _selectedDay.value
        _resources.value = _resources.value.map {
            if (it.day == currentSelectedDay) {
                it.copy(comments = it.comments + comment)
            } else {
                it
            }
        }
    }
}