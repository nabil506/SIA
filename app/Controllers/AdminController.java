package app.Controllers;

import app.Models.Admin;
import app.Models.Biodata;
import app.Models.Users;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminController extends HomeController {

    private final Admin adminModel;
    // private final Guru guruModel;
    private final Biodata biodataModel;
    private final Users usersModel;

    public AdminController() {
        this.adminModel = new Admin();
        // this.guruModel = new Guru();
        this.biodataModel = new Biodata();
        this.usersModel = new Users();
    }

    @Override
    public void indexCLI() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                PENDAFTARAN BIODATA SISWA BARU                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        System.out.println("\n[ Daftar User Siswa Belum Berbiodata ]");
        try {
            ResultSet listRs = this.usersModel.User();
            // Bingkai Tabel Daftar User
            System.out.println("╔══════════╦══════════════════════════════╦════════════════════╗");
            System.out.printf("║ %-8s ║ %-28s ║ %-18s ║%n", "ID USER", "NAMA SISWA", "ROLE");
            System.out.println("╠══════════╬══════════════════════════════╬════════════════════╣");

            boolean adaData = false;
            while (listRs != null && listRs.next()) {
                // Hanya tampilkan yang role-nya Siswa (Role 2)
                if (listRs.getInt("id_role") == 2) {
                    adaData = true;
                    System.out.printf("║ %-8d ║ %-28s ║ %-18s ║%n",
                            listRs.getInt("id_user"),
                            listRs.getString("nama"),
                            listRs.getInt("id_role") == 2 ? "Siswa" : "Guru");
                }
            }
            if (!adaData) {
                System.out.printf("║ %-59s ║%n", "Data user siswa tidak ditemukan.");
            }
            System.out.println("╚══════════╩══════════════════════════════╩════════════════════╝");
        } catch (SQLException e) {
            System.out.println(">> Gagal memuat daftar user: " + e.getMessage());
        }

        // 1. Admin memilih ID User secara manual
        System.out.print("\n➤ Masukkan ID User untuk diisi biodatanya: ");
        int idUserPilihan = input.nextInt();
        input.nextLine(); // Membersihkan buffer

        try {
            ResultSet rs = this.usersModel.findById(idUserPilihan);
            if (rs != null && rs.next()) {
                int role = rs.getInt("id_role");
                String namaUser = rs.getString("nama");

                // VALIDASI 1: Cek apakah benar Siswa
                if (role != 2) {
                    System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                    System.out.println("║ GAGAL: ID " + idUserPilihan + " adalah GURU, bukan SISWA.              ║");
                    System.out.println("╚══════════════════════════════════════════════════════════════╝");
                    return;
                }

                // VALIDASI 2: Cek Duplikat
                if (this.biodataModel.isBiodataExists(idUserPilihan)) {
                    System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                    System.out.printf("║ GAGAL: User %-17s sudah memiliki biodata!  ║%n", namaUser);
                    System.out.println("╚══════════════════════════════════════════════════════════════╝");
                    return;
                }

                // 3. Form Input Detail Biodata
                System.out.println("\n--- Melengkapi Biodata Untuk: " + namaUser + " ---");
                Biodata.userdata siswaBaru = new Biodata.userdata();
                siswaBaru.setId_user(idUserPilihan);
                siswaBaru.setNama(namaUser);
                siswaBaru.setKategori("siswa");

                System.out.print("➤ NIS            : ");
                siswaBaru.setNis(input.nextLine());
                System.out.print("➤ Tgl Lahir      : ");
                siswaBaru.setTgl_lahir(input.nextLine());
                System.out.print("➤ Alamat         : ");
                siswaBaru.setAlamat(input.nextLine());
                System.out.print("➤ Nama Ibu       : ");
                siswaBaru.setNama_ibu(input.nextLine());
                System.out.print("➤ No HP          : ");
                siswaBaru.setNo_hp(input.nextLine());
                System.out.print("➤ Jenis Kelamin  : ");
                siswaBaru.setJenis_kelamin(input.nextLine());

                // 4. Kirim ke Model
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                if (this.biodataModel.CreateBiodataSiswa(siswaBaru)) {
                    System.out.println("║ SUCCESS: Berhasil melengkapi biodata siswa!                  ║");
                } else {
                    System.out.println("║ FAILED : Terjadi kesalahan/duplikasi pada database.          ║");
                }
                System.out.println("╚══════════════════════════════════════════════════════════════╝");

            } else {
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                System.out.println("║ GAGAL: ID User tidak ditemukan di database!                  ║");
                System.out.println("╚══════════════════════════════════════════════════════════════╝");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void UpdateDataSiswa() {

        // Header Utama (Disesuaikan lebarnya agar simetris dengan tabel di bawah)
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                    UPDATE DATA BIODATA SISWA                                                                          ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
        try {
            ResultSet listRs = this.biodataModel.Biodata();

            // Header Tabel dengan 10 Kolom (Menambahkan No HP dan Memperlebar Jenis Kelamin)
            System.out.println("╔════════╦════════╦══════════════════════╦══════════╦══════════╦════════════╦══════════════════════╦══════════════════════╦═══════════════╦═══════════════╗");
            System.out.printf("║ %-6s ║ %-6s ║ %-20s ║ %-8s ║ %-8s ║ %-10s ║ %-20s ║ %-20s ║ %-13s ║ %-13s ║%n",
                    "ID BIO", "ID USR", "NAMA SISWA", "KATEGORI", "NIS", "TGL LAHIR", "ALAMAT", "NAMA IBU", "NO HP", "JENIS KELAMIN");
            System.out.println("╠════════╬════════╬══════════════════════╬══════════╬══════════╬════════════╬══════════════════════╬══════════════════════╬═══════════════╬═══════════════╣");

            boolean adaData = false;
            while (listRs != null && listRs.next()) {
                if (listRs.getInt("id_role") == 2) {
                    adaData = true;
                    System.out.printf("║ %-6d ║ %-6d ║ %-20s ║ %-8s ║ %-8s ║ %-10s ║ %-20s ║ %-20s ║ %-13s ║ %-13s ║%n",
                            listRs.getInt("id_bio"),
                            listRs.getInt("id_user"),
                            listRs.getString("nama"),
                            listRs.getString("kategori"),
                            listRs.getString("nis"),
                            listRs.getString("tgl_lahir"),
                            listRs.getString("alamat"),
                            listRs.getString("nama_ibu"),
                            listRs.getString("no_hp"),
                            listRs.getString("jenis_kelamin"));
                }
            }

            if (!adaData) {
                System.out.printf("║ %-165s ║%n", "Data siswa tidak ditemukan.");
            }
            System.out.println("╚════════╩════════╩══════════════════════╩══════════╩══════════╩════════════╩══════════════════════╩══════════════════════╩═══════════════╩═══════════════╝");

        } catch (SQLException e) {
            System.out.println(">> Gagal memuat daftar user: " + e.getMessage());
        }

        System.out.print("\n➤ Masukkan ID Bio yang akan diupdate biodatanya: ");
        int id_bio = input.nextInt();
        input.nextLine(); // Membersihkan buffer

        try {
            ResultSet rs = this.biodataModel.Biodata();
            if (rs != null && rs.next()) {
                int role = rs.getInt("id_role");
                String namaUser = rs.getString("nama");

                // VALIDASI: Pastikan yang dipilih adalah siswa
                if (role != 2) {
                    System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                    System.out.printf("║ GAGAL: ID %-3d (%-15s) adalah GURU/ADMIN!         ║%n", id_bio, namaUser);
                    System.out.println("╚══════════════════════════════════════════════════════════════╝");
                    return;
                }

                System.out.println("\n--- Perbarui Biodata Untuk: " + namaUser + " ---");

                // 3. Masukkan data ke DTO
                Biodata.userdata updateBaru = new Biodata.userdata();
                updateBaru.setId_bio(id_bio);
                updateBaru.setNama(namaUser);
                updateBaru.setKategori("siswa");

                System.out.print("➤ NIS            : ");
                updateBaru.setNis(input.nextLine());
                System.out.print("➤ Tgl Lahir      : ");
                updateBaru.setTgl_lahir(input.nextLine());
                System.out.print("➤ Alamat         : ");
                updateBaru.setAlamat(input.nextLine());
                System.out.print("➤ Nama Ibu       : ");
                updateBaru.setNama_ibu(input.nextLine());
                System.out.print("➤ No HP          : ");
                updateBaru.setNo_hp(input.nextLine());
                System.out.print("➤ Jenis Kelamin  : ");
                updateBaru.setJenis_kelamin(input.nextLine());

                // 4. Kirim ke Model (Menggunakan method Create/Update sesuai logika model Anda)
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                if (this.biodataModel.UpdateBiodataSiswa(updateBaru)) {
                    System.out.println("║ SUCCESS: Biodata siswa berhasil diperbarui!                  ║");
                } else {
                    System.out.println("║ FAILED : Gagal memperbarui biodata siswa.                    ║");
                }
                System.out.println("╚══════════════════════════════════════════════════════════════╝");

            } else {
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                System.out.println("║ GAGAL: ID User tidak ditemukan di database!                  ║");
                System.out.println("╚══════════════════════════════════════════════════════════════╝");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void DeleteSiswa() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                    DELETE DATA BIODATA SISWA                                                                  ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");

        // 1. Tampilkan Daftar Biodata agar Admin tidak salah ID
        try {
            ResultSet listRs = this.biodataModel.Biodata();

            // Header Tabel 10 Kolom
            System.out.println("╔════════╦════════╦══════════════════════╦══════════╦══════════╦════════════╦══════════════════════╦══════════════════════╦═══════════════╦═══════════════╗");
            System.out.printf("║ %-6s ║ %-6s ║ %-20s ║ %-8s ║ %-8s ║ %-10s ║ %-20s ║ %-20s ║ %-13s ║ %-13s ║%n",
                    "ID BIO", "ID USR", "NAMA SISWA", "KATEGORI", "NIS", "TGL LAHIR", "ALAMAT", "NAMA IBU", "NO HP", "JENIS KELAMIN");
            System.out.println("╠════════╬════════╬══════════════════════╬══════════╬══════════╬════════════╬══════════════════════╬══════════════════════╬═══════════════╬═══════════════╣");

            boolean adaData = false;
            while (listRs != null && listRs.next()) {
                if (listRs.getInt("id_role") == 2) {
                    adaData = true;
                    System.out.printf("║ %-6d ║ %-6d ║ %-20s ║ %-8s ║ %-8s ║ %-10s ║ %-20s ║ %-20s ║ %-13s ║ %-13s ║%n",
                            listRs.getInt("id_bio"),
                            listRs.getInt("id_user"),
                            listRs.getString("nama"),
                            listRs.getString("kategori"),
                            listRs.getString("nis"),
                            listRs.getString("tgl_lahir"),
                            listRs.getString("alamat"),
                            listRs.getString("nama_ibu"),
                            listRs.getString("no_hp"),
                            listRs.getString("jenis_kelamin"));
                }
            }

            if (!adaData) {
                System.out.printf("║ %-165s ║%n", " Data biodata masih kosong.");
            }
            System.out.println("╚════════╩════════╩══════════════════════╩══════════╩══════════╩════════════╩══════════════════════╩══════════════════════╩═══════════════╩═══════════════╝");

        } catch (SQLException e) {
            System.out.println(">> Gagal memuat daftar: " + e.getMessage());
        }

        // 2. Proses Input ID dan Konfirmasi
        System.out.print("\n➤ Masukkan ID Biodata yang akan dihapus: ");
        int id = input.nextInt();
        input.nextLine(); // Consume newline

        System.out.print("➤ Apakah Anda yakin ingin menghapus biodata ID [" + id + "] secara permanen? (y/n): ");
        String konfirmasi = input.nextLine();

        if (konfirmasi.equalsIgnoreCase("y")) {
            boolean isDeleteDataSiswa = this.biodataModel.DeleteBiodataSiswa(id);

            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            if (isDeleteDataSiswa) {
                System.out.println("║ SUCCESS: Biodata siswa berhasil dihapus dari sistem.         ║");
            } else {
                System.out.println("║ FAILED : Gagal menghapus biodata. Pastikan ID Bio benar.     ║");
            }
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
        } else {
            System.out.println(">> Penghapusan dibatalkan.");
        }
    }

    public void GetDataSiswa() {
        // 1. Panggil Model untuk mendapatkan data
        ResultSet rs = this.biodataModel.ReadBiodataSiswa();

        // 2. Header Judul
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                    LAPORAN BIODATA SISWA                                                                 ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");

        if (rs == null) {
            System.out.println(">> Gagal mengambil data atau koneksi bermasalah.");
            return;
        }

        try {
            // 3. Header Tabel (10 Kolom: Termasuk No HP dan Jenis Kelamin yang diperlebar)
            System.out.println("╔════════╦════════╦══════════════════════╦══════════╦══════════╦════════════╦══════════════════════╦══════════════════════╦═══════════════╦═══════════════╗");
            System.out.printf("║ %-6s ║ %-6s ║ %-20s ║ %-8s ║ %-8s ║ %-10s ║ %-20s ║ %-20s ║ %-13s ║ %-13s ║%n",
                    "ID BIO", "ID USR", "NAMA SISWA", "KATEGORI", "NIS", "TGL LAHIR", "ALAMAT", "NAMA IBU", "NO HP", "JENIS KELAMIN");
            System.out.println("╠════════╬════════╬══════════════════════╬══════════╬══════════╬════════════╬══════════════════════╬══════════════════════╬═══════════════╬═══════════════╣");

            boolean adaData = false;
            while (rs.next()) {
                adaData = true;
                // Pastikan method ReadBiodataSiswa() di Model mengembalikan kolom-kolom ini
                System.out.printf("║ %-6d ║ %-6d ║ %-20s ║ %-8s ║ %-8s ║ %-10s ║ %-20s ║ %-20s ║ %-13s ║ %-13s ║%n",
                        rs.getInt("id_bio"),
                        rs.getInt("id_user"),
                        rs.getString("nama"),
                        rs.getString("kategori"),
                        rs.getString("nis"),
                        rs.getString("tgl_lahir"),
                        rs.getString("alamat"),
                        rs.getString("nama_ibu"),
                        rs.getString("no_hp"),
                        rs.getString("jenis_kelamin"));
            }

            if (!adaData) {
                System.out.printf("║ %-165s ║%n", " Data biodata siswa masih kosong.");
            }

            System.out.println("╚════════╩════════╩══════════════════════╩══════════╩══════════╩════════════╩══════════════════════╩══════════════════════╩═══════════════╩═══════════════╝");

            // Tutup statement untuk kebersihan memori
            rs.getStatement().close();

        } catch (SQLException e) {
            System.err.println(">> Error saat menampilkan data: " + e.getMessage());
        }
    }

    // controller untuk guru
    public void InputDataGuru() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                PENDAFTARAN BIODATA GURU BARU                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        try {
            ResultSet listRs = this.usersModel.User();
            // Tabel Referensi User (Hanya Role 1)
            System.out.println("╔══════════╦══════════════════════════════╦════════════════════╗");
            System.out.printf("║ %-8s ║ %-28s ║ %-18s ║%n", "ID USER", "NAMA GURU", "ROLE");
            System.out.println("╠══════════╬══════════════════════════════╬════════════════════╣");

            boolean adaData = false;
            while (listRs != null && listRs.next()) {
                if (listRs.getInt("id_role") == 1) {
                    adaData = true;
                    System.out.printf("║ %-8d ║ %-28s ║ %-18s ║%n",
                            listRs.getInt("id_user"),
                            listRs.getString("nama"),
                            listRs.getInt("id_role") == 1 ? "Guru" : "Siswa");
                }
            }
            if (!adaData) {
                System.out.printf("║ %-59s ║%n", "Data user guru tidak ditemukan.");
            }
            System.out.println("╚══════════╩══════════════════════════════╩════════════════════╝");
        } catch (SQLException e) {
            System.out.println(">> Gagal memuat daftar user: " + e.getMessage());
        }

        // 1. Admin memilih ID User
        System.out.print("\n➤ Masukkan ID User Guru untuk diisi biodatanya: ");
        int idUserPilihan = input.nextInt();
        input.nextLine(); // Clear buffer

        try {
            ResultSet rs = this.usersModel.findById(idUserPilihan);
            if (rs != null && rs.next()) {
                int role = rs.getInt("id_role");
                String namaUser = rs.getString("nama");

                // VALIDASI 1: Cek Role (Harus Guru)
                if (role != 1) {
                    System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                    System.out.printf("║ GAGAL: ID %-3d (%-15s) adalah SISWA!              ║%n", idUserPilihan, namaUser);
                    System.out.println("╚══════════════════════════════════════════════════════════════╝");
                    return;
                }

                // VALIDASI 2: Cek Duplikat
                if (this.biodataModel.isBiodataExists(idUserPilihan)) {
                    System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                    System.out.printf("║ GAGAL: Guru %-17s sudah memiliki biodata!  ║%n", namaUser);
                    System.out.println("╚══════════════════════════════════════════════════════════════╝");
                    return;
                }

                // 3. Form Input Detail Biodata Guru
                System.out.println("\n--- Melengkapi Biodata Untuk: " + namaUser + " ---");
                Biodata.userdata guruBaru = new Biodata.userdata();
                guruBaru.setId_user(idUserPilihan);
                guruBaru.setNama(namaUser);
                guruBaru.setKategori("guru");

                System.out.print("➤ NIP            : ");
                guruBaru.setNip(input.nextLine()); // Pastikan menggunakan setNip untuk Guru
                System.out.print("➤ Tgl Lahir      : ");
                guruBaru.setTgl_lahir(input.nextLine());
                System.out.print("➤ Alamat         : ");
                guruBaru.setAlamat(input.nextLine());
                System.out.print("➤ Nama Ibu       : ");
                guruBaru.setNama_ibu(input.nextLine());
                System.out.print("➤ No HP          : ");
                guruBaru.setNo_hp(input.nextLine());
                System.out.print("➤ Jenis Kelamin  : ");
                guruBaru.setJenis_kelamin(input.nextLine());

                // 4. Kirim ke Model
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                if (this.biodataModel.CreateBiodataGuru(guruBaru)) {
                    System.out.println("║ SUCCESS: Berhasil melengkapi biodata guru!                  ║");
                } else {
                    System.out.println("║ FAILED : Terjadi kesalahan saat menyimpan data ke database.  ║");
                }
                System.out.println("╚══════════════════════════════════════════════════════════════╝");

            } else {
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                System.out.println("║ GAGAL: ID User tidak ditemukan di database!                  ║");
                System.out.println("╚══════════════════════════════════════════════════════════════╝");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void UpdateDataGuru() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                    UPDATE DATA BIODATA GURU                                                                   ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");

        // 1. Tampilkan Daftar Guru yang ada agar admin bisa melihat ID nya
        try {
            ResultSet listRs = this.biodataModel.Biodata();

            System.out.println("╔════════╦════════╦══════════════════════╦══════════╦══════════╦════════════╦══════════════════════╦══════════════════════╦═══════════════╦═══════════════╗");
            System.out.printf("║ %-6s ║ %-6s ║ %-20s ║ %-8s ║ %-8s ║ %-10s ║ %-20s ║ %-20s ║ %-13s ║ %-13s ║%n",
                    "ID BIO", "ID USR", "NAMA GURU", "KATEGORI", "NIP", "TGL LAHIR", "ALAMAT", "NAMA IBU", "NO HP", "JENIS KELAMIN");
            System.out.println("╠════════╬════════╬══════════════════════╬══════════╬══════════╬════════════╬══════════════════════╬══════════════════════╬═══════════════╬═══════════════╣");

            boolean adaData = false;
            while (listRs != null && listRs.next()) {
                if (listRs.getInt("id_role") == 1) {
                    adaData = true;
                    System.out.printf("║ %-6d ║ %-6d ║ %-20s ║ %-8s ║ %-8s ║ %-10s ║ %-20s ║ %-20s ║ %-13s ║ %-13s ║%n",
                            listRs.getInt("id_bio"),
                            listRs.getInt("id_user"),
                            listRs.getString("nama"),
                            listRs.getString("kategori"),
                            listRs.getString("nip"),
                            listRs.getString("tgl_lahir"),
                            listRs.getString("alamat"),
                            listRs.getString("nama_ibu"),
                            listRs.getString("no_hp"),
                            listRs.getString("jenis_kelamin"));
                }
            }

            if (!adaData) {
                System.out.printf("║ %-165s ║%n", " Data guru tidak ditemukan.");
            }
            System.out.println("╚════════╩════════╩══════════════════════╩══════════╩══════════╩════════════╩══════════════════════╩══════════════════════╩═══════════════╩═══════════════╝");

        } catch (SQLException e) {
            System.out.println(">> Gagal memuat daftar guru: " + e.getMessage());
        }

        System.out.print("\n➤ Masukkan ID Biodata Guru yang akan diupdate: ");
        int idBio = input.nextInt();
        input.nextLine(); // Clear buffer

        try {
            ResultSet rs = this.biodataModel.Biodata();
            if (rs != null && rs.next()) {
                int role = rs.getInt("id_role");
                String namaUser = rs.getString("nama");

                // VALIDASI: Pastikan yang dipilih adalah siswa
                if (role != 2) {
                    System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                    System.out.printf("║ GAGAL: ID %-3d (%-15s) adalah GURU/ADMIN!         ║%n", idBio, namaUser);
                    System.out.println("╚══════════════════════════════════════════════════════════════╝");
                    return;
                }

                System.out.println("\n--- Perbarui Biodata Untuk: " + namaUser + " ---");
                String nama = namaUser; // Simpan nama sebelum input ID
                // 2. Input ID dan Data Baru

                Biodata.userdata updateguruBaru = new Biodata.userdata();
                updateguruBaru.setId_bio(idBio);
                updateguruBaru.setNama(nama); // Nama tidak diubah di sini
                updateguruBaru.setKategori("guru");

                System.out.println("\n--- Masukkan Perubahan Data Guru ---");
                System.out.print("➤ NIP            : ");
                updateguruBaru.setNip(input.nextLine());
                System.out.print("➤ Tanggal Lahir  : ");
                updateguruBaru.setTgl_lahir(input.nextLine());
                System.out.print("➤ Alamat         : ");
                updateguruBaru.setAlamat(input.nextLine());
                System.out.print("➤ No HP          : ");
                updateguruBaru.setNo_hp(input.nextLine());
                System.out.print("➤ Nama Ibu       : ");
                updateguruBaru.setNama_ibu(input.nextLine());
                System.out.print("➤ Jenis Kelamin  : ");
                updateguruBaru.setJenis_kelamin(input.nextLine());
                // 3. Eksekusi ke Model
                // Disarankan menggunakan objek DTO agar parameter tidak terlalu panjang
                boolean isUpdate = this.biodataModel.UpdateBiodataSiswa(updateguruBaru); // Jika logic SQL sama, bisa pakai method update yang ada

                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                if (isUpdate) {
                    System.out.println("║ SUCCESS: Data Biodata Guru berhasil diperbarui!              ║");
                } else {
                    System.out.println("║ FAILED : Gagal memperbarui data guru. Cek ID Biodata.        ║");
                }
                System.out.println("╚══════════════════════════════════════════════════════════════╝");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void DeleteDataGuru() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                    DELETE  BIODATA GURU                                                                   ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");

        // 1. Tampilkan Daftar Guru agar Admin bisa memverifikasi ID BIO
        try {
            ResultSet listRs = this.biodataModel.ReadBiodataGuru();

            // Header Tabel 10 Kolom
            System.out.println("╔════════╦════════╦══════════════════════╦══════════╦══════════╦════════════╦══════════════════════╦══════════════════════╦═══════════════╦═══════════════╗");
            System.out.printf("║ %-6s ║ %-6s ║ %-20s ║ %-8s ║ %-8s ║ %-10s ║ %-20s ║ %-20s ║ %-13s ║ %-13s ║%n",
                    "ID BIO", "ID USR", "NAMA GURU", "KATEGORI", "NIP", "TGL LAHIR", "ALAMAT", "NAMA IBU", "NO HP", "JENIS KELAMIN");
            System.out.println("╠════════╬════════╬══════════════════════╬══════════╬══════════╬════════════╬══════════════════════╬══════════════════════╬═══════════════╬═══════════════╣");

            boolean adaData = false;
            while (listRs != null && listRs.next()) {
                adaData = true;
                System.out.printf("║ %-6d ║ %-6d ║ %-20s ║ %-8s ║ %-8s ║ %-10s ║ %-20s ║ %-20s ║ %-13s ║ %-13s ║%n",
                        listRs.getInt("id_bio"),
                        listRs.getInt("id_user"),
                        listRs.getString("nama"),
                        listRs.getString("kategori"),
                        listRs.getString("nip"),
                        listRs.getString("tgl_lahir"),
                        listRs.getString("alamat"),
                        listRs.getString("nama_ibu"),
                        listRs.getString("no_hp"),
                        listRs.getString("jenis_kelamin"));
            }

            if (!adaData) {
                System.out.printf("║ %-165s ║%n", " Data biodata guru masih kosong.");
            }
            System.out.println("╚════════╩════════╩══════════════════════╩══════════╩══════════╩════════════╩══════════════════════╩══════════════════════╩═══════════════╩═══════════════╝");

        } catch (SQLException e) {
            System.out.println(">> Gagal memuat daftar: " + e.getMessage());
        }

        // 2. Input ID dan Konfirmasi Penghapusan
        System.out.print("\n➤ Masukkan ID Biodata Guru yang akan dihapus: ");
        int id = input.nextInt();
        input.nextLine(); // Membuang sisa enter

        System.out.print("➤ Apakah Anda yakin ingin menghapus biodata guru ID [" + id + "]? (y/n): ");
        String konfirmasi = input.nextLine();

        if (konfirmasi.equalsIgnoreCase("y")) {
            // Memanggil method di model
            boolean isDeleteDataGuru = this.biodataModel.DeleteBiodataGuru(id);

            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            if (isDeleteDataGuru) {
                System.out.println("║ SUCCESS: Data biodata guru berhasil dihapus dari sistem.     ║");
            } else {
                System.out.println("║ FAILED : Gagal menghapus biodata. Pastikan ID Bio benar.     ║");
            }
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
        } else {
            System.out.println(">> Penghapusan dibatalkan.");
        }
    }

    public void GetDataGuru() {
        // 1. Panggil Model untuk mendapatkan data guru
        ResultSet rs = this.biodataModel.ReadBiodataGuru();

        // 2. Header Judul Laporan
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                     LAPORAN DATA BIODATA GURU                                                                 ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");

        // 3. Cek apakah datanya ada (tidak null)
        if (rs == null) {
            System.out.println("\n>> Gagal mengambil data atau data guru memang kosong.");
            return;
        }

        try {
            // 4. Header Tabel (10 Kolom: NIP digunakan menggantikan NIS)
            System.out.println("╔════════╦════════╦══════════════════════╦══════════╦══════════╦════════════╦══════════════════════╦══════════════════════╦═══════════════╦═══════════════╗");
            System.out.printf("║ %-6s ║ %-6s ║ %-20s ║ %-8s ║ %-8s ║ %-10s ║ %-20s ║ %-20s ║ %-13s ║ %-13s ║%n",
                    "ID BIO", "ID USR", "NAMA GURU", "KATEGORI", "NIP", "TGL LAHIR", "ALAMAT", "NAMA IBU", "NO HP", "JENIS KELAMIN");
            System.out.println("╠════════╬════════╬══════════════════════╬══════════╬══════════╬════════════╬══════════════════════╬══════════════════════╬═══════════════╬═══════════════╣");

            boolean adaData = false;
            while (rs.next()) {
                adaData = true;
                // Mengambil data sesuai dengan kolom yang ada di tabel biodata guru
                System.out.printf("║ %-6d ║ %-6d ║ %-20s ║ %-8s ║ %-8s ║ %-10s ║ %-20s ║ %-20s ║ %-13s ║ %-13s ║%n",
                        rs.getInt("id_bio"),
                        rs.getInt("id_user"),
                        rs.getString("nama"),
                        rs.getString("kategori"),
                        rs.getString("nip"), // Menggunakan NIP untuk guru
                        rs.getString("tgl_lahir"),
                        rs.getString("alamat"),
                        rs.getString("nama_ibu"),
                        rs.getString("no_hp"), // Kolom No HP
                        rs.getString("jenis_kelamin"));
            }

            if (!adaData) {
                System.out.printf("║ %-165s ║%n", " Data biodata guru masih kosong.");
            }

            System.out.println("╚════════╩════════╩══════════════════════╩══════════╩══════════╩════════════╩══════════════════════╩══════════════════════╩═══════════════╩═══════════════╝");

            // Tutup statement untuk kebersihan memori
            rs.getStatement().close();

        } catch (SQLException e) {
            System.err.println(">> Error saat menampilkan data guru: " + e.getMessage());
        }
    }

    public void CreateUser() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     CREATE NEW SYSTEM USER                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // 1. Tampilkan Referensi Role agar Admin tidak salah input
        System.out.println("\n[ Referensi Hak Akses / Role ]");
        System.out.println("┌──────────┬───────────────────────────────────────────────────┐");
        System.out.printf("│ %-8s │ %-49s │%n", "ID ROLE", "DESKRIPSI KATEGORI");
        System.out.println("├──────────┼───────────────────────────────────────────────────┤");
        System.out.printf("│ %-8d │ %-49s │%n", 1, "GURU (Akses Manajemen Nilai & Absensi)");
        System.out.printf("│ %-8d │ %-49s │%n", 2, "SISWA (Akses Lihat Biodata & Nilai)");
        System.out.println("└──────────┴───────────────────────────────────────────────────┘");

        // 2. Proses Input Data
        System.out.println("\n--- Masukkan Informasi Akun Baru ---");
        System.out.print("➤ Nama Lengkap   : ");
        String nama = input.nextLine();

        System.out.print("➤ ID Role (1/2)  : ");
        int id_role = input.nextInt();
        input.nextLine(); // Membersihkan buffer enter

        // Validasi input role secara lokal sebelum kirim ke model
        if (id_role != 1 && id_role != 2) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║ GAGAL: ID Role tidak valid! Gunakan hanya angka 1 atau 2.    ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            return;
        }

        // 3. Eksekusi ke Model
        int isCreateUser = this.usersModel.createNewUser(nama, id_role);

        // 4. Tampilkan Status Berhasil/Gagal
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        if (isCreateUser == -1) {
            System.out.println("║ FAILED : Gagal membuat user baru ke database.                ║");
        } else {
            System.out.printf("║ SUCCESS: User [%-15s] berhasil didaftarkan!      ║%n", nama);
        }
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

public void UpdateUser() {
   System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     UPDATE USER PASSWORD                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        try {
            ResultSet listRs = this.usersModel.User();
            System.out.println("╔══════════╦══════════════════════════════╦════════════════════╗");
            System.out.printf("║ %-8s ║ %-28s ║ %-18s ║%n", "ID USER", "NAMA LENGKAP", "ROLE");
            System.out.println("╠══════════╬══════════════════════════════╬════════════════════╣");
            
            while (listRs != null && listRs.next()) {
                int idUserDB = listRs.getInt("id_user");
                int roleID = listRs.getInt("id_role");

                // FILTER: Jangan tampilkan admin yang sedang login
                if (idUserDB == HomeController.session_id_user) continue; 

                String roleName = (roleID == 1) ? "Guru" : (roleID == 2) ? "Siswa" : "Admin";
                System.out.printf("║ %-8d ║ %-28s ║ %-18s ║%n", idUserDB, listRs.getString("nama"), roleName);
            }
            System.out.println("╚══════════╩══════════════════════════════╩════════════════════╝");
        } catch (SQLException e) { e.printStackTrace(); }

        System.out.print("\n➤ Masukkan ID User yang akan diupdate: ");
        int id_user = input.nextInt(); input.nextLine();

        // VALIDASI: Cegah input ID admin yang sedang login secara manual
        if (id_user == HomeController.session_id_user) {
            System.out.println(">> GAGAL: Anda tidak diperbolehkan mengupdate akun Anda sendiri!");
            return;
        }

        System.out.print("➤ Masukkan Password Baru: ");
        String password = input.nextLine();

        if (this.usersModel.UpdateUser(id_user, password)) {
            System.out.println(">> SUCCESS: Password berhasil diperbarui!");
        } else {
            System.out.println(">> FAILED: Gagal update password.");
        }
}
public void DeleteUser() {
    System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
    System.out.println("║                       DELETE USER                            ║");
    System.out.println("╚══════════════════════════════════════════════════════════════╝");

    // 1. Tampilkan Daftar User agar Admin tidak salah hapus ID
    try {
        ResultSet listRs = this.usersModel.User();
        System.out.println("╔══════════╦══════════════════════════════╦════════════════════╗");
        System.out.printf("║ %-8s ║ %-28s ║ %-18s ║%n", "ID USER", "NAMA LENGKAP", "ROLE");
        System.out.println("╠══════════╬══════════════════════════════╬════════════════════╣");
        
        boolean adaData = false;
        while (listRs != null && listRs.next()) {
            adaData = true;
            String roleName = (listRs.getInt("id_role") == 1) ? "Guru" : "Siswa";
            System.out.printf("║ %-8d ║ %-28s ║ %-18s ║%n",
                    listRs.getInt("id_user"),
                    listRs.getString("nama"),
                    roleName);
        }
        if (!adaData) {
            System.out.printf("║ %-59s ║%n", "Data user tidak ditemukan.");
        }
        System.out.println("╚══════════╩══════════════════════════════╩════════════════════╝");
    } catch (SQLException e) {
        System.out.println(">> Gagal memuat daftar user: " + e.getMessage());
    }

    // 2. Proses Input ID
    System.out.print("\n➤ Masukkan ID User yang akan dihapus : ");
    int id_user = input.nextInt();
    input.nextLine(); // Membersihkan buffer enter

    // 3. Konfirmasi Penghapusan (Sangat Penting untuk Delete)
    System.out.println("\n PERINGATAN: Menghapus user juga dapat berdampak pada data biodatanya.");
    System.out.print("➤ Apakah Anda yakin ingin menghapus user ID [" + id_user + "]? (y/n): ");
    String konfirmasi = input.nextLine();

    if (konfirmasi.equalsIgnoreCase("y")) {
        // 4. Eksekusi ke Model
        boolean isDeleteUser = this.usersModel.DeleteUser(id_user);

        // 5. Tampilkan Status Berhasil/Gagal
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        if (!isDeleteUser) {
            System.out.println("║ FAILED : Gagal menghapus user. Pastikan ID User sudah benar. ║");
        } else {
            System.out.println("║ SUCCESS: User tersebut telah berhasil dihapus dari sistem.   ║");
        }
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    } else {
        System.out.println(">> Penghapusan user dibatalkan.");
    }
}
}
