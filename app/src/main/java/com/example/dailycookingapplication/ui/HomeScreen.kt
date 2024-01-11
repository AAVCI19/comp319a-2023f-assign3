package com.example.dailycookingapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DaySelectionPage(
    recipeViewModel: RecipeViewModel,
    onItemSelected: (Int) -> Unit
) {
    val days = (1..30).toList()
    Column(modifier = Modifier.fillMaxHeight()
        .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(
            text = "Daily Recipe Recommendations",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(3.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
        {
            items(days) { day ->
                DaySelectionItem(
                    day = day,
                    isSelected = day == recipeViewModel.selectedDay,
                    onItemSelected = {
                        recipeViewModel.setSelectedDay(day)
                        onItemSelected(day)
                    }
                )
            }
        }

    }
}

@Composable
fun DaySelectionItem(
    day: Int,
    isSelected: Boolean,
    onItemSelected: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemSelected() }
            .background(if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(
            text = "Day $day",
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
