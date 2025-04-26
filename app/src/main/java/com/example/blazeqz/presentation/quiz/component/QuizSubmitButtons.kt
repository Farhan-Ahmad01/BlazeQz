package com.example.blazeqz.presentation.quiz.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QuizSubmitButtons(
    modifier: Modifier = Modifier,
    isPreviousButtonEnabled: Boolean,
    isNextButtonEnabled: Boolean,
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    onSubmitButtonClicked: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedIconButton(
            onClick = onPreviousButtonClicked,
            enabled = isPreviousButtonEnabled
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Previous"
            )
        }


        Button(
            modifier = Modifier.padding(horizontal = 30.dp),
            onClick = onSubmitButtonClicked
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = "Submit"
            )
        }

        OutlinedIconButton(
            onClick = onNextButtonClicked,
            enabled = isNextButtonEnabled
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Next"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubmitButtonPreview() {
    QuizSubmitButtons(
        modifier = Modifier.fillMaxWidth(),
        isPreviousButtonEnabled = true,
        onPreviousButtonClicked = {},
        isNextButtonEnabled = false,
        onSubmitButtonClicked = {},
        onNextButtonClicked = {}
    )
}