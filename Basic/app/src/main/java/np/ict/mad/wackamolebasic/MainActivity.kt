package np.ict.mad.wackamolebasic

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = "game") {
        composable("game") {
            GameScreen {
                nav.navigate("settings")
            }
        }
        composable("settings") {
            SettingsScreen {
                nav.popBackStack()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(openSettings: () -> Unit) {

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("wackamole", Context.MODE_PRIVATE)

    var highScore by remember { mutableIntStateOf(prefs.getInt("highScore", 0)) }

    var score by remember { mutableIntStateOf(0) }
    var time by remember { mutableIntStateOf(30) }
    var molePos by remember { mutableIntStateOf(Random.nextInt(0, 9)) }
    var running by remember { mutableStateOf(false) }
    var gameOver by remember { mutableStateOf(false) }

    LaunchedEffect(running) {
        if (running) {
            gameOver = false
            while (time > 0 && running) {
                delay(1000)
                time--
            }
            running = false
            gameOver = true

            if (score > highScore) {
                highScore = score
                prefs.edit().putInt("highScore", score).apply()
            }
        }
    }

    LaunchedEffect(running) {
        if (running) {
            while (time > 0 && running) {
                delay(Random.nextLong(700, 1000))
                molePos = Random.nextInt(0, 9)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wack-a-Mole") },
                actions = {
                    IconButton(onClick = openSettings) {
                        Icon(Icons.Filled.Settings, null)
                    }
                }
            )
        }
    ) { pad ->

        Column(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Score: $score")
                    Spacer(modifier = Modifier.width(24.dp))
                    Text("Time: $time")
                }
            }

            Spacer(Modifier.height(16.dp))

            Column {
                for (r in 0..2) {
                    Row {
                        for (c in 0..2) {
                            val idx = r * 3 + c
                            Button(
                                modifier = Modifier
                                    .size(88.dp)
                                    .padding(4.dp),
                                enabled = running,
                                shape = RoundedCornerShape(12.dp),
                                onClick = {
                                    if (running && idx == molePos) {
                                        score++
                                        molePos = Random.nextInt(0, 9)
                                    }
                                }
                            ) {
                                if (idx == molePos) Text("🐹")
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Text("High Score: $highScore")

            if (gameOver) {
                Spacer(Modifier.height(8.dp))
                Text("Game Over! Final Score: $score")
            }

            Spacer(Modifier.weight(1f))

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        score = 0
                        time = 30
                        molePos = Random.nextInt(0, 9)
                        running = true
                    }
                ) {
                    Text("Start")
                }

                Spacer(Modifier.width(10.dp))

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        score = 0
                        time = 30
                        molePos = Random.nextInt(0, 9)
                        running = true
                    }
                ) {
                    Text("Restart")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(back: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = back) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { pad ->
        Box(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Settings")
        }
    }
}
