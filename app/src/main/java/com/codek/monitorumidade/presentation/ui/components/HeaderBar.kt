package com.codek.monitorumidade.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.monitorumidade.R
import com.codek.monitorumidade.presentation.ui.actions.ButtonClickAction

@Composable
fun HeaderBar(
    onSignOutClick: () -> Unit
) {
    val buttonClickAction = remember { ButtonClickAction() }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box() {
            Image(
                painter = painterResource(id = R.drawable.topbar_fundo2),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 40.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.agrosolutions_white),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .height(45.dp)
                    )
                    Text(
                        text = "Monitoramento de solo",
                        fontSize = 12.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.sign_out),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(20.dp)
                        .align(Alignment.CenterVertically)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
                                if (buttonClickAction.offClick()) {
                                    focusManager.clearFocus()
                                    onSignOutClick()
                                }
                            }
                        )
                )
            }
        }
    }
}

@Preview
@Composable
private fun TopBarMonitorPreview() {
    HeaderBar(
        onSignOutClick = {}
    )
}