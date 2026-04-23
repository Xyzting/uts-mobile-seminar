package com.reyhanfathir.seminarapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.reyhanfathir.seminarapp.R
import com.reyhanfathir.seminarapp.data.RegistrationRepository
import com.reyhanfathir.seminarapp.databinding.FragmentRegisterBinding
import com.reyhanfathir.seminarapp.model.Registration
import com.reyhanfathir.seminarapp.model.SeminarData
import com.reyhanfathir.seminarapp.ui.seminar.SeminarResultActivity
import com.reyhanfathir.seminarapp.utils.ValidationUtils

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private var selectedSeminar = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSeminarDropdown()
        setupTextWatchers()
        binding.btnSubmit.setOnClickListener {
            if (validateAll()) showConfirmationDialog()
        }
    }

    private fun setupSeminarDropdown() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            SeminarData.seminarList
        )
        binding.actvSeminar.setAdapter(adapter)
        binding.actvSeminar.setOnItemClickListener { _, _, position, _ ->
            selectedSeminar = SeminarData.seminarList[position]
            binding.tilSeminar.error = null
        }
    }

    private fun setupTextWatchers() {
        binding.etNama.addTextChangedListener(makeWatcher {
            binding.tilNama.error = ValidationUtils.validateName(it?.toString() ?: "")
        })
        binding.etEmail.addTextChangedListener(makeWatcher {
            binding.tilEmail.error = ValidationUtils.validateEmail(it?.toString() ?: "")
        })
        binding.etPhone.addTextChangedListener(makeWatcher {
            binding.tilPhone.error = ValidationUtils.validatePhone(it?.toString() ?: "")
        })
    }

    private fun makeWatcher(action: (CharSequence?) -> Unit) = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { action(s) }
        override fun afterTextChanged(s: Editable?) {}
    }

    private fun validateAll(): Boolean {
        val nama = binding.etNama.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()

        val namaError = ValidationUtils.validateName(nama)
        val emailError = ValidationUtils.validateEmail(email)
        val phoneError = ValidationUtils.validatePhone(phone)

        binding.tilNama.error = namaError
        binding.tilEmail.error = emailError
        binding.tilPhone.error = phoneError

        if (binding.rgGender.checkedRadioButtonId == -1) {
            Snackbar.make(binding.root, "Pilih jenis kelamin terlebih dahulu!", Snackbar.LENGTH_SHORT).show()
            return false
        }

        if (selectedSeminar.isEmpty()) {
            binding.tilSeminar.error = "Pilih seminar terlebih dahulu"
            return false
        }

        if (!binding.cbSetuju.isChecked) {
            Snackbar.make(binding.root, "Harap centang persetujuan data terlebih dahulu!", Snackbar.LENGTH_SHORT).show()
            return false
        }

        return namaError == null && emailError == null && phoneError == null
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.dialog_title)
            .setMessage(R.string.dialog_message)
            .setPositiveButton(R.string.dialog_yes) { _, _ -> saveAndNavigate() }
            .setNegativeButton(R.string.dialog_no) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun saveAndNavigate() {
        val nama = binding.etNama.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val gender = if (binding.rbLakiLaki.isChecked) "Laki-laki" else "Perempuan"

        val registration: Registration = RegistrationRepository.add(
            context = requireContext(),
            nama = nama,
            email = email,
            phone = phone,
            gender = gender,
            seminar = selectedSeminar
        )

        val intent = Intent(requireContext(), SeminarResultActivity::class.java).apply {
            putExtra(SeminarResultActivity.EXTRA_ID, registration.id)
            putExtra(SeminarResultActivity.EXTRA_NAMA, registration.nama)
            putExtra(SeminarResultActivity.EXTRA_EMAIL, registration.email)
            putExtra(SeminarResultActivity.EXTRA_PHONE, registration.phone)
            putExtra(SeminarResultActivity.EXTRA_GENDER, registration.gender)
            putExtra(SeminarResultActivity.EXTRA_SEMINAR, registration.seminar)
        }
        startActivity(intent)

        resetForm()
    }

    private fun resetForm() {
        binding.etNama.text?.clear()
        binding.etEmail.text?.clear()
        binding.etPhone.text?.clear()
        binding.rgGender.clearCheck()
        binding.actvSeminar.setText("", false)
        binding.cbSetuju.isChecked = false
        selectedSeminar = ""
        binding.tilNama.error = null
        binding.tilEmail.error = null
        binding.tilPhone.error = null
        binding.tilSeminar.error = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
