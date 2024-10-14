package com.codek.loginapp.presentation.ui.actions

class ButtonClickAction {
    private var lastClickTime = 0L

    fun offClick(): Boolean {
        val currentTime = System.currentTimeMillis()
        return if (currentTime - lastClickTime > 1000) { // Verifica se passou 1 segundo
            lastClickTime = currentTime
            true
        } else {
            false
        }
    }
}