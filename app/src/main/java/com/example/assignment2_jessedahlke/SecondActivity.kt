package com.example.assignment2_jessedahlke

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment2_jessedahlke.ui.theme.Assignment2_JesseDahlkeTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment2_JesseDahlkeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SecondScreen(
                        modifier = Modifier.padding(innerPadding),
                        onMainClick = {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SecondScreen(
    modifier: Modifier = Modifier,
    onMainClick: () -> Unit
) {
    val challenges = listOf(
        "1. Handling device fragmentation (different screens & OS versions)",
        "2. Managing limited battery and memory resources",
        "3. Ensuring app security and user privacy",
        "4. Designing responsive and intuitive UI/UX",
        "5. Maintaining offline functionality and data synchronization"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mobile Software Engineering Challenges:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        challenges.forEach {
            Text(text = it, fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp))
        }

        Button(
            onClick = onMainClick,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text("Main Activity")
        }
    }



}

