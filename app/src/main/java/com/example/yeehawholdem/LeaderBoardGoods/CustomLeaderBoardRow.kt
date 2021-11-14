package com.example.yeehawholdem.LeaderBoardGoods

import android.app.Person
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yeehawholdem.LogicGoods.Player
import com.example.yeehawholdem.ui.theme.Typography

@Composable
fun CustomLeaderBoardRow(curPlayer: Player) {
    Row(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .padding(12.dp)
            .height(36.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "${curPlayer.balance}",
            color = Color.Black,
            fontSize = Typography.h4.fontSize,
            fontWeight = FontWeight.Bold
        )
        curPlayer.name?.let {
            Text(
                text = it,
                color = Color.Black,
                fontSize = Typography.h4.fontSize,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
@Preview
fun CustomRowPreview()
{
    CustomLeaderBoardRow(curPlayer = Player(
        name = "Pierce",
        balance = 5f
    ))
}