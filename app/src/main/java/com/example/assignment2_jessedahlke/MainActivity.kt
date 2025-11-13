package com.example.assignment2_jessedahlke

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment2_jessedahlke.ui.theme.Assignment2_JesseDahlkeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment2_JesseDahlkeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        onExplicitClick = {
                            val intent = Intent(this, SecondActivity::class.java)
                            startActivity(intent)
                        },
                        onImplicitClick = {
                            val intent = Intent("com.example.assignment2_jessedahlke.ACTION_VIEW_CHALLENGES")
                            startActivity(intent)
                        },
                        onViewImageClick = {
                            val intent = Intent(this, ImageActivity::class.java)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onExplicitClick: () -> Unit,
    onImplicitClick: () -> Unit,
    onViewImageClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Jesse Dahlke\nStudent ID: 123456789",
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = onExplicitClick,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .width(250.dp)
        ) {
            Text("Start Activity Explicitly")
        }

        Button(
            onClick = onImplicitClick,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .width(250.dp)
        ) {
            Text("Start Activity Implicitly")
        }

        // New button for View Image Activity
        Button(
            onClick = onViewImageClick,
            modifier = Modifier.width(250.dp)
        ) {
            Text("View Image Activity")
        }
    }
}
