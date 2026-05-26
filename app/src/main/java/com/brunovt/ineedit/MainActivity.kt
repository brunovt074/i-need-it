package com.brunovt.ineedit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.brunovt.ineedit.data.prefs.ThemePrefs
import com.brunovt.ineedit.ui.AppNavHost
import com.brunovt.ineedit.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themePrefs: ThemePrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme(themePrefs = themePrefs) {
                AppNavHost()
            }
        }
    }
}
