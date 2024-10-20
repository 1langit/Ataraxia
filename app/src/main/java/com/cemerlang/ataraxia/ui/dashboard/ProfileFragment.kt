package com.cemerlang.ataraxia.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cemerlang.ataraxia.data.PrefManager
import com.cemerlang.ataraxia.ui.auth.LoginActivity
import com.cemerlang.ataraxia.databinding.FragmentProfileBinding
import com.cemerlang.ataraxia.ui.profile.ProfileEditActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager.getInstance(requireContext())

        with(binding) {
            txtName.text = "${prefManager.getFirstName()} ${prefManager.getLastName()}"
            txtEmail.text = prefManager.getEmail()

            btnEdit.setOnClickListener {
                val newIntent = Intent(requireContext(), ProfileEditActivity::class.java)
                startActivity(newIntent)
            }

            btnLogout.setOnClickListener {
                showLogoutDialog(prefManager)
            }
        }
    }

    private fun showLogoutDialog(prefManager: PrefManager) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Logout dari Ataraxia?")
            .setPositiveButton("Logout") { dialog, _ ->
                prefManager.clear()
                val newIntent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(newIntent)
                activity?.finish()
            }.setNegativeButton("Batal"){ dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }
}