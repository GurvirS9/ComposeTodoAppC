package com.compose.todolist.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.core.graphics.toColorInt
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.todolist.data.ThemeOption
import com.compose.todolist.ui.theme.LocalCustomColors

@Composable
fun ThemeThumbnail(
    theme: ThemeOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val customColors = LocalCustomColors.current
    val backgroundColor = Color(theme.colors["appBackground"]!!.toColorInt())
    val cardColor = Color(theme.colors["cardBackground"]!!.toColorInt())
    val headerTextColor = Color(theme.colors["headerText"]!!.toColorInt())
    val fabColor = Color(theme.colors["fabBackground"]!!.toColorInt())
    val counterColor = Color(theme.colors["counterBackground"]!!.toColorInt())

    Box(
        modifier = Modifier
            .width(120.dp)
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(backgroundColor)
                    .border(
                        width = if (isSelected) 3.dp else 1.dp,
                        color = if (isSelected) Color(0xFF3D8BFF) else Color.Gray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable(onClick = onClick)
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // top app bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp, end = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 30.dp, height = 4.dp)
                                .background(headerTextColor)
                        )
                        Row {
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .background(headerTextColor)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .background(headerTextColor)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(cardColor)
                            .padding(6.dp)
                    ) {
                        Row {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .border(1.dp, Color.Gray, RoundedCornerShape(1.dp))
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Column {
                                Box(
                                    modifier = Modifier
                                        .size(width = 40.dp, height = 2.dp)
                                        .background(Color.Gray)
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Box(
                                    modifier = Modifier
                                        .size(width = 35.dp, height = 2.dp)
                                        .background(Color.Gray.copy(alpha = 0.7f))
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Box(
                                    modifier = Modifier
                                        .size(width = 20.dp, height = 2.dp)
                                        .background(Color.Gray.copy(alpha = 0.5f))
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // counter and fab
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 30.dp, height = 10.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(counterColor)
                        )

                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(fabColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(3.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.8f))
                            )
                        }
                    }
                }

                // selected indicator
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF3D8BFF))
                            .align(Alignment.Center),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Text(
                text = theme.name,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = customColors.headerText,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}