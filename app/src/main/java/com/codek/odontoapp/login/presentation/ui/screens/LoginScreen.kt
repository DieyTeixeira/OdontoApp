package com.codek.odontoapp.login.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.codek.odontoapp.R
import com.codek.odontoapp.adm_app.theme.LoginBack
import com.codek.odontoapp.adm_app.theme.LoginPri
import com.codek.odontoapp.adm_app.theme.LoginSec
import com.codek.odontoapp.login.presentation.states.RegisterUiState
import com.codek.odontoapp.login.presentation.states.SignInUiState
import com.codek.odontoapp.login.presentation.ui.components.FooterBar
import com.codek.odontoapp.login.presentation.viewmodel.RegisterViewModel
import com.codek.odontoapp.login.presentation.viewmodel.SignInViewModel

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun LoginScreen(
    viewModelSignIn: SignInViewModel,
    viewModelRegister: RegisterViewModel,
    uiStateSignIn: SignInUiState,
    uiStateRegister: RegisterUiState,
    onSignInClick: () -> Unit,
    onCreateClick: () -> Unit
) {

    val tamCorPri = 410.dp
    val tamCorSec = 200.dp
    val tamQuadro = tamCorPri + tamCorSec
    val roundedShape = 15.dp
    val animateDuration = 500

    var isMovedUp by remember { mutableStateOf(false) }
    val offsetY by animateDpAsState(
        targetValue = if (isMovedUp) 0.dp else tamCorPri,
        animationSpec = tween(animateDuration)
    )
    val roundedShapeUp by animateDpAsState(
        targetValue = if (isMovedUp) roundedShape else 0.dp,
        animationSpec = tween(animateDuration)
    )
    val roundedShapeDown by animateDpAsState(
        targetValue = if (isMovedUp) 0.dp else roundedShape,
        animationSpec = tween(animateDuration)
    )



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LoginBack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Spacer(modifier = Modifier.height(75.dp))
            Box {
                Image(
                    painter = painterResource(id = R.drawable.logo_odonto),
                    contentDescription = "logo",
                    modifier = Modifier
                        .height(50.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Box {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(tamQuadro)
                        .background(
                            color = LoginSec,
                            shape = RoundedCornerShape(roundedShape)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = offsetY)
                            .height(tamCorSec)
                            .background(
                                color = LoginPri,
                                shape = RoundedCornerShape(
                                    roundedShapeUp,
                                    roundedShapeUp,
                                    roundedShapeDown,
                                    roundedShapeDown
                                )
                            )
                    )
                    this@Column.AnimatedVisibility(
                        visible = !isMovedUp,
                        enter = fadeIn(animationSpec = tween(animateDuration + 200)),
                        exit = fadeOut(animationSpec = tween(animateDuration - 100))
                    ) {
                        SignInScreen(
                            viewModel = viewModelSignIn,
                            uiState = uiStateSignIn,
                            onSignInClick = onSignInClick,
                            onGoRegisterClick = { isMovedUp = !isMovedUp }
                        )
                    }
                    this@Column.AnimatedVisibility(
                        visible = isMovedUp,
                        enter = fadeIn(animationSpec = tween(animateDuration + 200)),
                        exit = fadeOut(animationSpec = tween(animateDuration - 100))
                    ) {
                        RegisterScreen(
                            viewModel = viewModelRegister,
                            uiState = uiStateRegister,
                            onCreateClick = onCreateClick,
                            onBackSignInClick = { isMovedUp = !isMovedUp }
                        )
                    }
                }
            }
        }
    }

    /***** RODAPÉ *****/
    FooterBar(color = Color.LightGray)

}