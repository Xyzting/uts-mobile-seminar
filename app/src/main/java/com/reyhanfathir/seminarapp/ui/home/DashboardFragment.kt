package com.reyhanfathir.seminarapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.reyhanfathir.seminarapp.R
import com.reyhanfathir.seminarapp.data.RegistrationRepository
import com.reyhanfathir.seminarapp.databinding.FragmentDashboardBinding
import com.reyhanfathir.seminarapp.model.Registration
import com.reyhanfathir.seminarapp.model.SeminarData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = (activity as? MainActivity)?.currentUsername ?: "User"
        binding.tvGreeting.text = getString(R.string.dashboard_greeting, username)

        binding.cardDaftarSeminar.setOnClickListener {
            (activity as? MainActivity)?.switchTab(R.id.menu_register)
        }
        binding.cardLihatLaporan.setOnClickListener {
            (activity as? MainActivity)?.switchTab(R.id.menu_laporan)
        }
        binding.btnLogout.setOnClickListener {
            (activity as? MainActivity)?.showLogoutDialog()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshContent()
    }

    private fun refreshContent() {
        val context = requireContext()
        val list = RegistrationRepository.getAll(context)

        binding.tvStatPendaftar.text = list.size.toString()
        binding.tvStatSeminar.text = SeminarData.seminarList.size.toString()

        if (list.isEmpty()) {
            binding.layoutEmptyLatest.visibility = View.VISIBLE
            binding.layoutLatest.visibility = View.GONE
        } else {
            binding.layoutEmptyLatest.visibility = View.GONE
            binding.layoutLatest.visibility = View.VISIBLE
            bindLatest(list.first())
        }
    }

    private fun bindLatest(latest: Registration) {
        binding.tvLatestInitial.text = latest.nama.firstOrNull()?.uppercase() ?: "?"
        binding.tvLatestNama.text = latest.nama
        binding.tvLatestSeminar.text = latest.seminar
        binding.tvLatestTime.text = relativeTime(latest.timestamp)
    }

    private fun relativeTime(timestamp: Long): String {
        val diff = System.currentTimeMillis() - timestamp
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        val days = TimeUnit.MILLISECONDS.toDays(diff)
        return when {
            minutes < 1 -> "Baru saja"
            minutes < 60 -> "$minutes menit lalu"
            hours < 24 -> "$hours jam lalu"
            days < 7 -> "$days hari lalu"
            else -> SimpleDateFormat("dd MMM yyyy", Locale("id")).format(Date(timestamp))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
