package com.example.dailycookingapplication.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailycookingapplication.R
import com.example.dailycookingapplication.data.Resources
import com.example.dailycookingapplication.loadResources

@Composable
fun ImageList(modifier: Modifier = Modifier, recipeViewModel: RecipeViewModel) {
    val resourceList = loadResources()
    val selectedRecipe = resourceList[recipeViewModel.selectedDay - 1]
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageCard(
            resource = selectedRecipe,
            modifier = Modifier
                .padding(8.dp)
                .weight(2f)
        )

        CommentArea(
            recipeViewModel = recipeViewModel,
            modifier = Modifier
                .padding(6.dp)
                .weight(2f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCard(resource: Resources, modifier: Modifier = Modifier) {
    var expandableState by remember { mutableStateOf(false) }
    Card(modifier = modifier, onClick = { expandableState = !expandableState }) {
        Column(
            modifier = modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Recipe of the Day: ${resource.day}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = resource.imageResourceId),
                contentDescription = resource.recipeDescription,
                modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = resource.recipe,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (expandableState) {
                    Text(
                        modifier = modifier.weight(6f),
                        text = "Collapse recipe description",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        modifier = modifier.weight(6f),
                        text = "Expand for recipe description",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                IconButton(
                    modifier = modifier.weight(1f),
                    onClick = { expandableState = !expandableState },
                ) {
                    Icon(
                        imageVector = if (expandableState) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Arrow",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            if (expandableState) {
                Text(
                    text = resource.recipeDescription,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentArea(recipeViewModel: RecipeViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Card(
            modifier = modifier.weight(1f)
        ) {
            var comment by remember { mutableStateOf("") }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    singleLine = true,
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                        disabledIndicatorColor = MaterialTheme.colorScheme.surface,
                    ),
                    label = { Text("Write Your Comments") },
                    isError = false,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            recipeViewModel.addComment(comment)
                            comment = ""
                        }
                    )
                )

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (comment != ""){
                            recipeViewModel.addComment(comment)
                            comment = ""
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.submit),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        Card(modifier = modifier
            .weight(3f)
            .fillMaxWidth()
        ) {
            Column(modifier = modifier
            ) {
                Text(
                    text = "Comments",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(recipeViewModel.resources) {
                            resource ->
                        if (resource.day == recipeViewModel.selectedDay){
                            resource.comments.forEach { comment ->
                                Text(
                                    text = comment,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
