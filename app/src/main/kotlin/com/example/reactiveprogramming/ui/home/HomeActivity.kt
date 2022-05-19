package com.example.reactiveprogramming.ui.home

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Build
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
import com.example.common.extensions.getActionBarSize
import com.example.common.extensions.getColorCompat
import com.example.common.extensions.getColorFromStyle
import com.example.common.extensions.getColorOrDefault
import com.example.reactiveprogramming.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Manage the UI Configuration application toolbar and setup navigation drawer
 *
 * The application structure is one activity and many fragments
 */
class HomeActivity: AppCompatActivity() {

    // ViewModel to manage the ui configuration
    private val uiConfigurationViewModel: UiConfigurationViewModel by viewModel()

    // Colors for toolbar and status bar
    private val colorPrimary by lazy { getColorCompat(R.color.colorPrimary) }
    private val colorPrimaryDark by lazy { getColorCompat(R.color.colorPrimaryDark) }

    // Navigation drawer configuration
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val navController by lazy { findNavController(R.id.main_nav_host_fragment) }
    private val navDrawer by lazy { findViewById<NavigationView>(R.id.main_nav_view) }

    // Toolbar configuration
    private val toolbar by lazy { findViewById<MaterialToolbar>(R.id.toolbar) }
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

    /**
     * Manage the changes on the ui state
     * @param uiConfigurationViewState new configuration state of the ui
     */
    private fun handleUiConfigurationViewStates(uiConfigurationViewState: UiConfigurationViewState) {
        uiConfigurationViewState.insets.fold(
            ifEmpty = { },
            ifSome = { insets ->
                applyUiConfigurationViewState(insets, uiConfigurationViewState)
            }
        )
    }

    /**
     * Apply changes on the ui configuration
     * @param insets measure of how much need to move the toolbar and the screen view
     * @param uiConfigurationViewState new configuration state
     */
    private fun applyUiConfigurationViewState(
        insets: WindowInsets,
        uiConfigurationViewState: UiConfigurationViewState
    ) {
        window.statusBarColor = getColorOrDefault(uiConfigurationViewState.statusBarColor, colorPrimaryDark)

        toolbar.background = ColorDrawable(
            getColorOrDefault(uiConfigurationViewState.toolbarColor, toolbarDefaultColor)
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        colorFilter = BlendModeColorFilter(toolbarNavigationIconColor, BlendMode.SRC_ATOP)
                    } else {
                        setColorFilter(toolbarNavigationIconColor, PorterDuff.Mode.SRC_ATOP)
                    }
                    alpha = 255
                }
            }
        }

        (toolbar.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
            updateMargins(top = insets.systemWindowInsetTop)
        }

        var newContentBottomMargin = 0
        if (!ViewConfiguration.get(applicationContext).hasPermanentMenuKey()) {
            newContentBottomMargin = -insets.systemWindowInsetBottom
        }

        val content = findViewById<ConstraintLayout>(R.id.content)
        (content.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
            updateMargins(top = toolbarHeight, bottom = newContentBottomMargin)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.main_nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}