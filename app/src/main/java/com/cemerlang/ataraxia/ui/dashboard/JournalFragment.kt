package com.cemerlang.ataraxia.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.cemerlang.ataraxia.databinding.FragmentJournalBinding
import com.cemerlang.ataraxia.ui.common.TabAdapter
import com.cemerlang.ataraxia.ui.journal.JournalAddActivity
import com.cemerlang.ataraxia.ui.journal.JournalListFragment
import com.cemerlang.ataraxia.ui.journal.JournalStatisticFragment
import com.cemerlang.ataraxia.ui.journal.JournalViewModel
import com.google.android.material.tabs.TabLayoutMediator

class JournalFragment : Fragment() {

    private lateinit var binding: FragmentJournalBinding
    private val journalViewModel by activityViewModels<JournalViewModel>()
    private val activityContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            journalViewModel.fetchJournals()
            binding.viewPager.setCurrentItem(1, true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJournalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            viewPager.adapter = TabAdapter(
                this@JournalFragment,
                arrayOf(JournalStatisticFragment(), JournalListFragment())
            )
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Statistik"
                    1 -> "Journal Saya"
                    else -> ""
                }
            }.attach()

            fab.setOnClickListener {
                val newIntent = Intent(requireContext(), JournalAddActivity::class.java)
                activityContract.launch(newIntent)
            }
        }
    }
}