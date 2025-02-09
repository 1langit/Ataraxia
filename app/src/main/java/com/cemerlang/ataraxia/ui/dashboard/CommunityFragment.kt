package com.cemerlang.ataraxia.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cemerlang.ataraxia.R
import com.cemerlang.ataraxia.databinding.FragmentCommunityBinding
import com.cemerlang.ataraxia.ui.common.TabAdapter
import com.cemerlang.ataraxia.ui.community.CommunityHomeFragment
import com.cemerlang.ataraxia.ui.community.CommunityLikeFragment
import com.cemerlang.ataraxia.ui.community.CommunityMyPostFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator

class CommunityFragment : Fragment() {

    private lateinit var binding: FragmentCommunityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            viewPager.adapter = TabAdapter(
                this@CommunityFragment,
                arrayOf(CommunityHomeFragment(), CommunityLikeFragment(), CommunityMyPostFragment())
            )
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Beranda"
                    1 -> "Disukai"
                    2 -> "Milik saya"
                    else -> ""
                }
            }.attach()

            btnFilter.setOnClickListener {
                showCategoryDialog()
            }
        }
    }

    private fun showCategoryDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Pilih Kategori")
            .setMultiChoiceItems(
                resources.getStringArray(R.array.community_post_category
                ), null) { dialog, which, isChecked ->

            }
            .setPositiveButton("Pilih") { dialog, which ->
                dialog.dismiss()
            }
            .setNegativeButton("Tutup") { dialog, which ->
                dialog.dismiss()
            }.show()
    }

    fun navigateToPage(page: Int) {
        binding.viewPager.setCurrentItem(page, true)
    }
}