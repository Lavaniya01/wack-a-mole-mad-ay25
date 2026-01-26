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
                WackAMoleApp()
            }
        }
    }
}

@Composable
fun WackAMoleApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "game") {
        composable("game") {
            GameScreen(
                onOpenSettings = { navController.navigate("settings") }
            )
        }
        composable("settings") {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    onOpenSettings: () -> Unit
) {
    val context = LocalContext.current
    val prefs = remember {
        context.getSharedPreferences("wackamole_prefs", Context.MODE_PRIVATE)
    }

    // Persistent high score
    var highScore by remember { mutableIntStateOf(prefs.getInt("high_score", 0)) }

    // Game state
    var score by remember { mutableIntStateOf(0) }
    var timeLeft by remember { mutableIntStateOf(30) } // 30 or 60 seconds (your choice)
    var moleIndex by remember { mutableIntStateOf(Random.nextInt(0, 9)) }
    var isRunning by remember { mutableStateOf(false) }
    var showGameOver by remember { mutableStateOf(false) }

    // TIMER: decreases every second
    LaunchedEffect(isRunning) {
        if (isRunning) {
            showGameOver = false
            while (timeLeft > 0 && isRunning) {
                delay(1000)
                timeLeft -= 1
            }

            // Game ends
            if (timeLeft <= 0) {
                isRunning = false
                showGameOver = true

                // Update high score on game end
                if (score > highScore) {
                    highScore = score
                    prefs.edit().putInt("high_score", score).apply()
                }
            }
        }
    }

    // MOLE MOVEMENT: changes position every 700–1000ms
    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (timeLeft > 0 && isRunning) {
                delay(Random.nextLong(700, 1001))
                moleIndex = Random.nextInt(0, 9)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wack-a-Mole") },
                actions = {
                    IconButton(onClick = onOpenSettings) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "High Score: $highScore",
                style = MaterialTheme.typography.bodyLarge
            )


            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Score: $score", style = MaterialTheme.typography.titleMedium)
                    Text("Time: $timeLeft", style = MaterialTheme.typography.titleMedium)
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Start / Restart buttons
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {
                        score = 0
                        timeLeft = 30
                        moleIndex = Random.nextInt(0, 9)
                        isRunning = true
                        showGameOver = false
                    }
                ) { Text("Start") }

                OutlinedButton(
                    onClick = {
                        score = 0
                        timeLeft = 30
                        moleIndex = Random.nextInt(0, 9)
                        isRunning = true
                        showGameOver = false
                    }
                ) { Text("Restart") }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 3x3 grid
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                for (row in 0 until 3) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        for (col in 0 until 3) {
                            val index = row * 3 + col
                            HoleButton(
                                isMole = (index == moleIndex),
                                enabled = isRunning,
                                onClick = {
                                    if (isRunning && index == moleIndex) {
                                        score += 1
                                    }
                                }
                            )
                        }
                    }
                }
            }

            if (showGameOver) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Game over! Final score: $score",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
private fun HoleButton(
    isMole: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.size(88.dp),
        shape = RoundedCornerShape(14.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(text = if (isMole) "🐹" else "")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Settings")
        }
    }
}
