package com.example.reactiveprogramming.ui.home

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateMargins
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.common.UiConfigurationViewModel
import com.example.common.UiConfigurationViewState
import com.example.common.extensions.*
import com.example.reactiveprogramming.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity: AppCompatActivity() {

    private val toolbar by lazy { findViewById<MaterialToolbar>(R.id.toolbar) }
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val navController by lazy { findNavController(R.id.main_nav_host_fragment) }
    private val navDrawer by lazy { findViewById<NavigationView>(R.id.main_nav_view) }

    private val uiConfigurationViewModel: UiConfigurationViewModel by viewModel()
    private val colorPrimary by lazy { getColorCompat(R.color.colorPrimary) }
    private val colorPrimaryDark by lazy { getColorCompat(R.color.colorPrimaryDark) }
    private val toolbarHeight by lazy { getActionBarSize() }
    private val toolbarDefaultColor by lazy {
        getColorFromStyle(
            R.style.AppTheme_AppBarOverlay,
            android.R.attr.background,
            colorPrimary
        )
    }
    private val toolbarNavigationIconDefaultColor by lazy {
        getColorFromStyle(
            R.style.AppTheme_AppBarOverlay,
            R.attr.titleTextColor,
            Color.BLACK
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.home_activity)

        setSupportActionBar(toolbar)

        val drawer = findViewById<DrawerLayout>(R.id.main_drawer_layout)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home_fragment,
                R.id.data_management_fragment,
                R.id.sensors_fragment,
                R.id.big_collections_fragment
            ), drawer
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        drawer.systemUiVisibility =
            (drawer.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)

        drawer.setOnApplyWindowInsetsListener { _, insets ->
            uiConfigurationViewModel.setInsets(WindowInsets(insets))

            handleUiConfigurationViewStates(uiConfigurationViewModel.getCurrentState())

            (navDrawer.layoutParams as DrawerLayout.LayoutParams).apply {
                topMargin = insets.systemWindowInsetTop
                bottomMargin = insets.systemWindowInsetBottom
            }

            insets
        }

        navDrawer.setupWithNavController(navController)

        uiConfigurationViewModel.getUiConfigurationLiveData().observe(this, Observer {
            handleUiConfigurationViewStates(it)
        })

    }

    private fun handleUiConfigurationViewStates(uiConfigurationViewState: UiConfigurationViewState) {
        uiConfigurationViewState.insets.fold(
            ifEmpty = { },
            ifSome = { insets ->
                applyUiConfigurationViewState(insets, uiConfigurationViewState)
            }
        )
    }

    private fun applyUiConfigurationViewState(
        insets: WindowInsets,
        uiConfigurationViewState: UiConfigurationViewState
    ) {
        toolbar.changeVisibility(uiConfigurationViewState.showToolbar)

        val statusBarColor =
            getColorOrDefault(uiConfigurationViewState.statusBarColor, colorPrimaryDark)

        window.statusBarColor = statusBarColor

        if (uiConfigurationViewState.showToolbar) {
            val toolbarColor =
                getColorOrDefault(uiConfigurationViewState.toolbarColor, toolbarDefaultColor)
            toolbar.background = ColorDrawable(toolbarColor)

            uiConfigurationViewState.toolbarColor.fold(
                ifEmpty = {
                    toolbar.setOnTouchListener(null)
                },
                ifSome = { color ->
                    if (color == R.color.transparent) {
                        toolbar.setOnTouchListener { _, event ->
                            uiConfigurationViewModel.toolbarMotion.postValue(event)
                            true
                        }
                    } else {
                        toolbar.setOnTouchListener(null)
                    }
                }
            )

            toolbar.navigationIcon?.apply {
                val toolbarNavigationIconColor =
                    uiConfigurationViewState.toolbarNavigationIconColor.fold(
                        ifEmpty = { toolbarNavigationIconDefaultColor },
                        ifSome = { getColorCompat(it) }
                    )

                when (this) {
                    is DrawerArrowDrawable -> color = toolbarNavigationIconColor
                    else -> {
                        setColorFilter(toolbarNavigationIconColor, PorterDuff.Mode.SRC_IN)
                        alpha = 255
                    }
                }
            }

            (toolbar.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                updateMargins(top = insets.systemWindowInsetTop)
            }
        }

        val newContentTopMargin = when {
            uiConfigurationViewState.showToolbar -> toolbarHeight
            else -> 0
        }

        var newContentBottomMargin = 0
        if (!ViewConfiguration.get(applicationContext).hasPermanentMenuKey()) {
            newContentBottomMargin = -insets.systemWindowInsetBottom
        }

        val content = findViewById<ConstraintLayout>(R.id.content)
        (content.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
            updateMargins(top = newContentTopMargin, bottom = newContentBottomMargin)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.main_nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun lockNavDrawer() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.main_drawer_layout)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun unlockNavDrawer() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.main_drawer_layout)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

}