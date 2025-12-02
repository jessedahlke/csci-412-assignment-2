package com.example.assignment2_jessedahlke

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    private val REQ_MSE412 = 1001
    private val MSE412_PERM = "com.example.assignment2_jessedahlke.MSE412"

    // simple runtime flag (checked/updated in permission callbacks)
    private var mse412Granted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Check current permission status
        mse412Granted = ContextCompat.checkSelfPermission(
            this,
            MSE412_PERM
        ) == PackageManager.PERMISSION_GRANTED

        // Request permission proactively in onCreate if not already granted (assignment requirement)
        if (!mse412Granted) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(MSE412_PERM),
                REQ_MSE412
            )
        }

        setContent {
            Assignment2_JesseDahlkeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        onExplicitClick = {
                            // only start if permission is granted; otherwise ask and inform
                            if (mse412Granted) {
                                startSecondActivity()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Please grant permission to view the challenges.",
                                    Toast.LENGTH_LONG
                                ).show()
                                // re-request in case user tapped the button before granting
                                ActivityCompat.requestPermissions(
                                    this,
                                    arrayOf(MSE412_PERM),
                                    REQ_MSE412
                                )
                            }
                        },
                        onImplicitClick = {
                            // implicit intent — make sure the action matches your manifest!
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

    // start SecondActivity explicitly
    private fun startSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }

    // handle the runtime permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_MSE412) {
            mse412Granted = grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED

            if (mse412Granted) {
                // optionally start the second activity immediately after grant
                startSecondActivity()
            } else {
                Toast.makeText(
                    this,
                    "Permission required to view mobile software engineering challenges.",
                    Toast.LENGTH_LONG
                ).show()
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

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    Assignment2_JesseDahlkeTheme {
        MainScreen(Modifier.padding(), {}, {}, {})
    }
}
