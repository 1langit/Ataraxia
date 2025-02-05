package com.cemerlang.ataraxia.ui.dashboard

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cemerlang.ataraxia.R
import com.cemerlang.ataraxia.data.PrefManager
import com.cemerlang.ataraxia.databinding.ActivityDashboardBinding
import com.cemerlang.ataraxia.ui.community.CommunityViewModel
import com.cemerlang.ataraxia.ui.journal.JournalViewModel

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController
    private lateinit var journalViewmodel: JournalViewModel
    private lateinit var communityViewModel: CommunityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val prefManager = PrefManager.getInstance(this)

        journalViewmodel = ViewModelProvider(this)[JournalViewModel::class.java]
        journalViewmodel.setToken(prefManager.getToken())
        journalViewmodel.fetchJournals()

        communityViewModel = ViewModelProvider(this)[CommunityViewModel::class.java]
        communityViewModel.setToken(prefManager.getToken())
        communityViewModel.fetchPosts()

        navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            window.statusBarColor = when(destination.label) {
                "fragment_home" -> getColor(R.color.ungu_tua_300)
                else -> getColor(R.color.white)
            }
        }
    }

    fun navigateToPage(name: String) {
        binding.bottomNavigationView.selectedItemId = when (name) {
            "journal" -> R.id.journalFragment
            "community" -> R.id.communityFragment
            "profile" -> R.id.profileFragment
            else -> R.id.homeFragment
        }
    }
}