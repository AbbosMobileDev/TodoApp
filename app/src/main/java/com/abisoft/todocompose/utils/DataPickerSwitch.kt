package com.abisoft.todocompose.utils

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abisoft.todocompose.R

@Composable
fun DatePickerSwitch() {
    var isSwitchOn by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Сделать до", modifier = Modifier.weight(1f))
            Switch(
                checked = isSwitchOn,
                onCheckedChange = { isChecked ->
                    isSwitchOn = isChecked
                    if (!isChecked) {
                        selectedDate = ""
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        if (isSwitchOn && selectedDate.isEmpty()) {
            var monthName: String
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            android.app.DatePickerDialog(
                context,
                { _, selectedYear, selectedMonth, selectedDay ->
                    monthName = when (selectedMonth) {
                        0 -> "Января"
                        1 -> "Февраля"
                        2 -> "Марта"
                        3 -> "Апреля"
                        4 -> "Мая"
                        5 -> "Июня"
                        6 -> "Июля"
                        7 -> "Августа"
                        8 -> "Сентября"
                        9 -> "Октября"
                        10 -> "Ноября"
                        11 -> "Декабря"
                        else -> "Января"
                    }
                    selectedDate = "$selectedDay $monthName $selectedYear"
                },
                year, month, day
            ).show()
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (selectedDate.isNotEmpty()) {
            Text(
                text = selectedDate,
                style = TextStyle(
                    color = colorResource(id = R.color.Color_blue),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
            )


        }
    }
}