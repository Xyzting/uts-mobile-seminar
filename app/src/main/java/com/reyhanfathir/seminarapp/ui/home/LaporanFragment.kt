package com.reyhanfathir.seminarapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.reyhanfathir.seminarapp.R
import com.reyhanfathir.seminarapp.data.RegistrationRepository
import com.reyhanfathir.seminarapp.databinding.FragmentLaporanBinding
import com.reyhanfathir.seminarapp.model.Registration

class LaporanFragment : Fragment() {

    private var _binding: FragmentLaporanBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RegistrationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaporanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RegistrationAdapter(emptyList()) { showDeleteDialog(it) }
        binding.rvLaporan.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLaporan.adapter = adapter

        binding.btnEmptyDaftar.setOnClickListener {
            (activity as? MainActivity)?.switchTab(R.id.menu_register)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun refreshList() {
        val list = RegistrationRepository.getAll(requireContext())
        adapter.submit(list)
        binding.tvCount.text = getString(R.string.laporan_count, list.size)
        if (list.isEmpty()) {
            binding.rvLaporan.visibility = View.GONE
            binding.layoutEmpty.visibility = View.VISIBLE
        } else {
            binding.rvLaporan.visibility = View.VISIBLE
            binding.layoutEmpty.visibility = View.GONE
        }
    }

    private fun showDeleteDialog(registration: Registration) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_title)
            .setMessage(getString(R.string.delete_message, registration.nama))
            .setPositiveButton(R.string.delete_yes) { _, _ ->
                RegistrationRepository.delete(requireContext(), registration.id)
                refreshList()
                Snackbar.make(binding.root, R.string.delete_success, Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton(R.string.delete_no, null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
