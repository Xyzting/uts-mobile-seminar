# Seminar Registration App

> UTS Pemrograman Mobile I — TIF K 24 A

| Info | Detail |
|------|--------|
| **Nama** | Reyhan Fathir A |
| **NIM** | 24552011032 |
| **Mata Kuliah** | Pemrograman Mobile I |
| **Kelas** | TIF K 24 A |
| **Dosen** | Erryck Norrys, S.Kom |

---

## Video Demo & Penjelasan

> **Link Video:** `https://youtu.be/WA1J3PHDFQg`

Video berisi penjelasan lengkap:
- Halaman Login & validasinya
- Halaman Register Akun
- Halaman Utama dengan **Bottom Navigation** (3 menu)
  - **Dashboard** — greeting, statistik, aksi cepat, pendaftaran terbaru
  - **Register** — form pendaftaran seminar
  - **Laporan** — daftar semua pendaftar dengan fitur hapus
- Validasi real-time (error langsung muncul saat mengetik)
- Dialog Konfirmasi sebelum simpan data
- Halaman Hasil Pendaftaran
- Penyimpanan data lokal via SharedPreferences (JSON)
- Fitur Logout dari toolbar
- Penjelasan kode (struktur project, Kotlin, Material Design 3, Fragment, RecyclerView)

---

## Deskripsi Aplikasi

Aplikasi Android berbasis **Kotlin** untuk pendaftaran seminar mahasiswa. Dibangun menggunakan **Material Design 3** dengan arsitektur **Bottom Navigation + Fragments** dan penyimpanan lokal via **SharedPreferences**.

### Alur Aplikasi
```
Login / Register Akun
        │
        ▼
┌────────────────────────────────────────┐
│        MainActivity (Container)        │
│  ┌──────────┬──────────┬─────────────┐ │
│  │Dashboard │ Register │  Laporan    │ │  ← BottomNavigationView
│  └──────────┴──────────┴─────────────┘ │
└────────────────────────────────────────┘
        │
        ├─ Register → Submit → Dialog → Simpan → Result → Kembali ke Laporan
        └─ Laporan  → Lihat daftar / Hapus pendaftar
```

---

## Fitur Utama

### Login & Register Akun
- Autentikasi sederhana (hardcode: `reyhan` / `fathir123`)
- Validasi field kosong dengan error message
- Link navigasi antar Login dan Register

### Dashboard (Home)
- Greeting personal berdasarkan username
- **Kartu Statistik**: total pendaftar + total seminar tersedia
- **Aksi Cepat**: shortcut ke Register / Laporan
- **Pendaftaran Terbaru**: preview data pendaftar terakhir
- Auto-refresh setiap kali tab dibuka

### Register Seminar (Form)
| Field | Validasi |
|-------|----------|
| Nama Lengkap | Wajib, minimal 2 karakter |
| Email | Wajib, harus mengandung `@` dan format valid |
| Nomor HP | Hanya angka, 10–13 digit, diawali `08` |
| Jenis Kelamin | RadioButton (Laki-laki / Perempuan) |
| Pilihan Seminar | Dropdown (6 pilihan) |
| Persetujuan | Checkbox wajib dicentang |

**Validasi Real-time** → error langsung muncul saat user mengetik (TextWatcher)

### Dialog Konfirmasi
- Muncul setelah klik Submit
- "Apakah data yang Anda isi sudah benar?"
- **Ya** → simpan ke SharedPreferences → buka Halaman Hasil
- **Tidak** → kembali ke form

### Halaman Hasil
- Menampilkan detail data yang baru didaftarkan
- Tombol **Lihat Laporan** langsung pindah ke tab Laporan (data baru tampil di list)

### Laporan Pendaftaran
- **RecyclerView** menampilkan semua pendaftar (terbaru di atas)
- Badge hitung jumlah peserta total
- **Kartu item** berisi: avatar initial, nama, gender, email, HP, seminar, waktu daftar
- **Hapus pendaftaran** via tombol tempat sampah + dialog konfirmasi
- **Empty state** ketika belum ada pendaftaran (dengan CTA langsung ke form)

### Logout
- Icon logout di Toolbar
- Dialog konfirmasi sebelum keluar
- Kembali ke halaman Login

---

## Pilihan Seminar (Data Hardcode)

1. Seminar AI & Machine Learning
2. Seminar Keamanan Siber (Cybersecurity)
3. Seminar Mobile Development
4. Seminar Cloud Computing
5. Seminar UI/UX Design
6. Seminar Data Science & Big Data

---

## Tech Stack

| Teknologi | Versi |
|-----------|-------|
| Kotlin | 1.9.23 |
| Android Gradle Plugin | 8.3.2 |
| Material Design 3 | 1.12.0 |
| AndroidX Fragment KTX | 1.8.2 |
| AndroidX RecyclerView | 1.3.2 |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 34 (Android 14) |
| ViewBinding | ✓ |
| SharedPreferences + JSONArray | ✓ (storage lokal) |

---

## Struktur Project

```
app/src/main/
├── java/com/reyhanfathir/seminarapp/
│   ├── ui/
│   │   ├── auth/
│   │   │   ├── LoginActivity.kt          → Halaman Login
│   │   │   └── RegisterActivity.kt       → Halaman Register Akun
│   │   ├── home/
│   │   │   ├── MainActivity.kt           → Container + BottomNav + Toolbar
│   │   │   ├── DashboardFragment.kt      → Dashboard (stats + aksi cepat)
│   │   │   ├── RegisterFragment.kt       → Form pendaftaran seminar
│   │   │   ├── LaporanFragment.kt        → List semua pendaftar
│   │   │   └── RegistrationAdapter.kt    → RecyclerView adapter
│   │   └── seminar/
│   │       └── SeminarResultActivity.kt  → Halaman Hasil Pendaftaran
│   ├── model/
│   │   ├── SeminarData.kt                → Daftar seminar + kredensial
│   │   └── Registration.kt               → Data class + JSON (de)serializer
│   ├── data/
│   │   └── RegistrationRepository.kt     → CRUD via SharedPreferences
│   └── utils/
│       └── ValidationUtils.kt            → Logika validasi input
├── res/
│   ├── layout/                           → Activity + Fragment + Item layouts
│   ├── menu/
│   │   ├── bottom_nav_menu.xml           → 3 menu bottom navigation
│   │   └── main_toolbar_menu.xml         → Menu toolbar (Logout)
│   ├── color/
│   │   └── bottom_nav_item_color.xml     → State color selector
│   ├── drawable/                         → Vector icons + gradient header
│   ├── values/                           → colors, strings, themes
│   └── mipmap-anydpi-v26/                → Adaptive launcher icons
└── AndroidManifest.xml
```

---

## Cara Menjalankan

1. Clone repository ini
2. Buka di **Android Studio** (Hedgehog 2023.1.1 atau lebih baru)
3. Tunggu **Gradle sync** selesai (memerlukan koneksi internet)
4. Jalankan di emulator atau device fisik (min Android 7.0 / API 24)
5. Login dengan kredensial di bawah

---

## Kredensial Login

```
Username : reyhan
Password : fathir123
```

---

## Penyimpanan Data

Seluruh pendaftaran disimpan di **SharedPreferences** dengan format JSON array. Data **tetap tersimpan** meskipun aplikasi ditutup jadi bisa buka kembali dan tetap muncul di Laporan. Gunakan tombol hapus untuk membersihkan data.
