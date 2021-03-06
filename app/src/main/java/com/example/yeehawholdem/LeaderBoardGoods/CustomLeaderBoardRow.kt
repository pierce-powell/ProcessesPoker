package com.example.yeehawholdem.LeaderBoardGoods

import android.app.Person
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yeehawholdem.LogicGoods.Player
import com.example.yeehawholdem.ui.theme.Typography
//coment
@Composable
fun CustomLeaderBoardRow(curPlayer: LeaderBoardPlayer, index: Int, isCurrentPlayer : Boolean) {

    var rowColor = Color.Unspecified

    if (isCurrentPlayer == false)
        rowColor = Color(0xFFFFAF2F)
    else
        rowColor = Color.Yellow

    Row(
        modifier = Modifier
            .background(rowColor)
            .fillMaxWidth()
            .padding(6.dp)
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.fillMaxWidth(.2f))
        {
            Text(
                text = "${index}",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Box(Modifier.fillMaxWidth(.3f))
        {
            Text(
                text = "${curPlayer.playerBalance}",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.padding(3.dp))
        Box(Modifier.fillMaxWidth(.9f)) {
            curPlayer.playerName?.let {
                Text(
                    text = it,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
@Preview
fun CustomRowPreview()
{
    CustomLeaderBoardRow(curPlayer = LeaderBoardPlayer("shit", 69), 0, true)
}