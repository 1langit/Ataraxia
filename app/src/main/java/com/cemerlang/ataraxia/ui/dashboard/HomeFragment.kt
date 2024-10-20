package com.cemerlang.ataraxia.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.cemerlang.ataraxia.R
import com.cemerlang.ataraxia.data.PrefManager
import com.cemerlang.ataraxia.data.seeder.ArticleSeed
import com.cemerlang.ataraxia.data.seeder.CommunitySeed
import com.cemerlang.ataraxia.databinding.FragmentHomeBinding
import com.cemerlang.ataraxia.ui.article.ArticleActivity
import com.cemerlang.ataraxia.ui.article.ArticleReadActivity
import com.cemerlang.ataraxia.ui.chat.ChatActivity
import com.cemerlang.ataraxia.ui.clinic.ClinicMapsActivity
import com.cemerlang.ataraxia.ui.community.CommunityPostActivity
import com.cemerlang.ataraxia.ui.journal.JournalAddActivity
import com.google.android.material.chip.Chip

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topCommunity = CommunitySeed().getArticle(0)
        val topArticle = ArticleSeed().getArticle(5)
        prefManager = PrefManager.getInstance(requireContext())

        with(binding) {
            txtName.text = prefManager.getFirstName()

            communityTop.apply {
                txtName.text = topCommunity.author
                txtTimestamp.text = topCommunity.timestamp
                txtTitle.text = topCommunity.title
                txtContent.text = topCommunity.content
                txtLike.text = "${topCommunity.likesCount.toString()} suka"
                btnComment.text = "${topCommunity.commentCount.toString()} komentar"

                chipCategory.removeAllViews()
                topCommunity.categories.forEach {
                    val chip = Chip(requireContext(), null, R.style.ChipCategory).apply {
                        text = it
                        setChipBackgroundColorResource(R.color.ungu_tua_50)
                    }
                    chipCategory.addView(chip)
                }

                root.setOnClickListener {
                    val newIntent = Intent(requireContext(), CommunityPostActivity::class.java)
                    newIntent.putExtra("POST", topCommunity)
                    startActivity(newIntent)
                }
            }

            articleTop.apply {
                txtName.text = topArticle.author
                txtClinicTimestamp.text = "${topArticle.clinic}  •  ${topArticle.timestamp}"
                txtTitle.text = topArticle.title
                txtContent.text = topArticle.content

                chipCategory.removeAllViews()
                topArticle.categories.forEach {
                    val chip = Chip(requireContext(), null, R.style.ChipCategory).apply {
                        text = it
                        setChipBackgroundColorResource(R.color.ungu_tua_50)
                    }
                    chipCategory.addView(chip)
                }

                root.setOnClickListener {
                    val newIntent = Intent(requireContext(), ArticleReadActivity::class.java)
                    newIntent.putExtra("ARTICLE", topArticle)
                    startActivity(newIntent)
                }
            }

            setMoodButtonClickListener(cardMood.btnMoodExcellent)
            setMoodButtonClickListener(cardMood.btnMoodGood)
            setMoodButtonClickListener(cardMood.btnMoodNormal)
            setMoodButtonClickListener(cardMood.btnMoodBad)
            setMoodButtonClickListener(cardMood.btnMoodWorse)

            btnChatbot.setOnClickListener {
                val newIntent = Intent(requireContext(), ChatActivity::class.java)
                startActivity(newIntent)
            }

            btnArticle.setOnClickListener {
                val newIntent = Intent(requireContext(), ArticleActivity::class.java)
                startActivity(newIntent)
            }

            btnClinic.setOnClickListener {
                val newIntent = Intent(requireContext(), ClinicMapsActivity::class.java)
                startActivity(newIntent)
            }

            btnOtherPost.setOnClickListener {
                val parentActivity = requireActivity() as DashboardActivity
                parentActivity.navigateToPage("community")
            }

            btnOtherArticle.setOnClickListener {
                val newIntent = Intent(requireContext(), ArticleActivity::class.java)
                startActivity(newIntent)
            }
        }
    }

    private fun setMoodButtonClickListener(button: Button) {
        button.setOnClickListener {
            val newIntent = Intent(requireContext(), JournalAddActivity::class.java)
            newIntent.putExtra("MOOD", button.text)
            startActivity(newIntent)
        }
    }
}