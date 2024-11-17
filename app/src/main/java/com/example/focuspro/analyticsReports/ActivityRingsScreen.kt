package com.example.focuspro.analyticsReports // Asegúrate de que esta sea la ruta correcta

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.focuspro.R // Asegúrate de que esta línea está correcta

@Composable
fun ActivityRingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.activity_rings_title),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActivityRing(
                progress = 0.7f,
                ringColor = Color.Red,
                label = stringResource(id = R.string.focus_label),
                percentage = 70
            )
            ActivityRing(
                progress = 0.5f,
                ringColor = Color.Green,
                label = stringResource(id = R.string.break_label),
                percentage = 50
            )
            ActivityRing(
                progress = 0.9f,
                ringColor = Color.Blue,
                label = stringResource(id = R.string.tasks_label),
                percentage = 90
            )
        }
    }
}

@Composable
fun ActivityRing(
    progress: Float,
    ringColor: Color,
    label: String,
    percentage: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.size(120.dp)
    ) {
        Canvas(modifier = Modifier.size(100.dp)) {
            // Background circle (empty)
            drawArc(
                color = ringColor.copy(alpha = 0.2f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
            )
            // Foreground circle (filled based on progress)
            drawArc(
                color = ringColor,
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.percentage_format, percentage),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}
