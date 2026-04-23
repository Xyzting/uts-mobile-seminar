package com.reyhanfathir.seminarapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.reyhanfathir.seminarapp.R
import com.reyhanfathir.seminarapp.databinding.ActivityMainBinding
import com.reyhanfathir.seminarapp.ui.auth.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var currentUsername: String = "User"
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentUsername = intent.getStringExtra(EXTRA_USERNAME) ?: "User"

        setSupportActionBar(binding.toolbar)

        binding.toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_logout) {
                showLogoutDialog(); true
            } else false
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            showTab(item.itemId)
            true
        }

        if (savedInstanceState == null) {
            val initialTab = intent.getIntExtra(EXTRA_TAB, R.id.menu_dashboard)
            if (binding.bottomNav.selectedItemId == initialTab) {
                showTab(initialTab)
            } else {
                binding.bottomNav.selectedItemId = initialTab
            }
        }
    }

    private fun showTab(menuId: Int) {
        val fragment: Fragment = when (menuId) {
            R.id.menu_dashboard -> DashboardFragment()
            R.id.menu_register -> RegisterFragment()
            R.id.menu_laporan -> LaporanFragment()
            else -> DashboardFragment()
        }
        updateToolbarTitle(menuId)
        showFragment(fragment)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        val tab = intent.getIntExtra(EXTRA_TAB, -1)
        if (tab != -1) binding.bottomNav.selectedItemId = tab
    }

    fun switchTab(menuId: Int) {
        binding.bottomNav.selectedItemId = menuId
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun updateToolbarTitle(menuId: Int) {
        binding.toolbar.title = when (menuId) {
            R.id.menu_dashboard -> getString(R.string.app_name)
            R.id.menu_register -> getString(R.string.form_title)
            R.id.menu_laporan -> getString(R.string.laporan_title)
            else -> getString(R.string.app_name)
        }
    }

    fun showLogoutDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.logout_title)
            .setMessage(R.string.logout_message)
            .setPositiveButton(R.string.logout_yes) { _, _ -> logout() }
            .setNegativeButton(R.string.logout_no, null)
            .show()
    }

    private fun logout() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }

    companion object {
        const val EXTRA_USERNAME = "USERNAME"
        const val EXTRA_TAB = "TAB"
    }
}
