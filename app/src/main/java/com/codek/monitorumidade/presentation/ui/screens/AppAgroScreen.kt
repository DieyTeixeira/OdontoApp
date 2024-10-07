package com.codek.monitorumidade.presentation.ui.screens

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codek.monitorumidade.presentation.states.RegisterUiState
import com.codek.monitorumidade.presentation.ui.components.EquipamentoDropDown
import com.codek.monitorumidade.presentation.ui.components.FooterBar
import com.codek.monitorumidade.presentation.ui.components.HeaderBar
import com.codek.monitorumidade.presentation.ui.theme.DarkGradient
import com.codek.monitorumidade.presentation.ui.theme.Green700
import com.codek.monitorumidade.presentation.viewmodel.RegisterViewModel
import com.codek.monitorumidade.ui.viewmodels.AppAgroViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AppAgroScreen(
    viewModel: AppAgroViewModel,
    onSignOutClick: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val registerViewModel: RegisterViewModel = viewModel()
    val pagerState = rememberPagerState(pageCount = { HomeTabs.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val createNameDialog = remember { mutableStateOf(false) }
    val preferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    LaunchedEffect(Unit) {
        viewModel.createName.collect { insertValue ->
            createNameDialog.value = insertValue
        }
    }

    if (createNameDialog.value) {
        NomeDialogo(
            preferences = preferences,
            createNameDialog = createNameDialog,
            onUpdateNameClick = { nome ->
                registerViewModel.viewModelScope.launch {
                    registerViewModel.insertName(nome)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGradient)
    ) {
        HeaderBar(onSignOutClick = onSignOutClick)

        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            containerColor = Green700,
            indicator = { tabPositions ->
                Box(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex.value])
                        .padding(horizontal = 25.dp)
                        .height(4.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )
            }
        ) {
            HomeTabs.entries.forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.LightGray,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    },
                    modifier = Modifier.height(52.dp),
                    text = {
                        Column(
                            modifier = Modifier
                                .padding(bottom = 5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = if (selectedTabIndex.value == index)
                                    currentTab.selectedIcon else currentTab.unselectedIcon,
                                contentDescription = "Tab Icon",
                                modifier = Modifier.size(30.dp) // Ajuste do tamanho do ícone
                            )
                            Text(
                                text = currentTab.text,
                                style = TextStyle.Default.copy(
                                    fontSize = 10.sp
                                ),
                                modifier = Modifier.padding(top = 0.dp) // Remove o espaçamento
                            )
                        }
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.Top
        ) { page ->
            when (page) {
                0 -> {
                    Column {
                        EquipamentoDropDown(
                            viewModel = viewModel,
                            modifier = Modifier
                                .fillMaxWidth(),
                        )
                        MonitorScreen(
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                1 -> {
                    LoadingScreen()
                }
                2 -> {
                    LoadingScreen()
                }
            }
        }
    }

    FooterBar(color = Color.LightGray)
}

enum class HomeTabs(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val text: String
) {
    Shop(
        unselectedIcon = Icons.Outlined.Assessment,
        selectedIcon = Icons.Filled.Assessment,
        text = "Monitor"
    ),
    Favourite(
        unselectedIcon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings,
        text = "Ajustes"
    ),
    Profile(
        unselectedIcon = Icons.Outlined.Person,
        selectedIcon = Icons.Filled.Person,
        text = "Profile"
    )
}

@Composable
private fun NomeDialogo(
    preferences: SharedPreferences,
    createNameDialog: MutableState<Boolean>,
    onUpdateNameClick: (String) -> Unit
) {
    var nome by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { createNameDialog.value = false },
        title = { Text(text = "INSIRA SEU NOME") },
        text = {
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text(text = "Nome") },
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(onClick = {
                preferences.edit()
                    .putString("nome", nome)
                    .apply()
                createNameDialog.value = false
                onUpdateNameClick(nome)
            }) {
                Text(text = "Sim")
            }
        },
        dismissButton = {
            TextButton(onClick = { createNameDialog.value = false }) {
                Text(text = "Não")
            }
        }
    )
}

@Preview
@Composable
private fun AppAgroScreenPreview() {
    AppAgroScreen(
        viewModel = viewModel(),
        onSignOutClick = {}
    )
}