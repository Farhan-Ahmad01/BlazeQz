package com.example.blazeqz.presentation.common_component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blazeqz.presentation.theme.gray
import com.example.blazeqz.presentation.theme.orange

@Composable
fun UserProgressCardLayout(
    totalQuestions: Int,
    attemptedQuestionsCount: Int,
    correctAnswersCount: Int
) {

    val attemptPercentage = (attemptedQuestionsCount * 100) / totalQuestions
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(12.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left box with details
        Box(
            modifier = Modifier
//                .weight(1f)
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DetailCard(title = "Total", count = totalQuestions, color = Color(0xFFE53935))
                DetailCard(title = "Attempted", count = attemptedQuestionsCount, color = Color(0xFFFFC107))
                DetailCard(title = "Correct", count = correctAnswersCount, color = Color(0xFF00BFA5))
            }
        }

        // Right box with progress indicator
        Box(
            modifier = Modifier
                .weight(1f),
//                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            CustomCircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(0.9f)  // Take 90% of the available width
                    .aspectRatio(1f),    // Maintain 1:1 aspect ratio
                initialValue = attemptPercentage.toFloat(),
                primaryColor = orange,
                secondaryColor = gray,
                circleRadius = 100f,     // Adjusted radius to fit better
                onPositionChange = {}
            )
        }
    }
}

@Composable
fun DetailCard(title: String, count: Int, color: Color) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2E2E2E)
        ),
        modifier = Modifier.width(110.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(color, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, color = Color.Gray, fontSize = 12.sp)
                Text(text = "$count", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Preview
@Composable
private fun ItsPreview() {
    UserProgressCardLayout(
        totalQuestions = 333,
        attemptedQuestionsCount = 10,
        correctAnswersCount = 150
    )
}
