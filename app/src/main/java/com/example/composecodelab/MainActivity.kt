package com.example.composecodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composecodelab.ui.theme.ComposeCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCodelabTheme {
                // A surface container using the 'background' color from the theme
                myApp()
            }
        }
    }
}

@Composable
private fun myApp() {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
        Greetings()
    }
}


@Composable
fun Greeting(name: String) {
    val expanded = rememberSaveable { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (!expanded.value) MaterialTheme.colors.primary
        else MaterialTheme.colors.secondary,
        tween(500, 0, FastOutSlowInEasing)
    )
    Card(
        backgroundColor = color,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .animateContentSize(tween(500, 0, LinearOutSlowInEasing))
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = "Hello,")
                Text(
                    text = "$name!", style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                if (expanded.value) {
                    Text(
                        text = ("Composem ipsum color sit lazy, " +
                                "padding theme elit, sed do bouncy. ").repeat(4),
                    )
                }
            }
            IconButton(onClick = { expanded.value = !expanded.value }) {
                Crossfade(
                    targetState = expanded.value,
                    animationSpec = tween(500, 0, LinearOutSlowInEasing)
                ) {
                    if (it) {
                        Icon(
                            imageVector = Icons.Filled.ExpandLess,
                            contentDescription = "Show Less",
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.ExpandMore,
                            contentDescription = "Show More",
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greetings(names: List<String> = List(1000) { "$it" }) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) {
            Greeting(name = it)
        }
    }

}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeCodelabTheme {
        myApp()
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome to the Compose Basics")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }

        }

    }
}