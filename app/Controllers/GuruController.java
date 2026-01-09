package app.Controllers;

import app.Models.Absensi;
import app.Models.Biodata;
import app.Models.Guru;
import app.Models.Nilai;
import app.Models.Users;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuruController extends HomeController {

    private final Guru guruModel;
    private final Nilai nilaiModel;
    private final Absensi absensiModel;
    private final Users usersModel;
    private final Biodata biodataModel;

    public GuruController() {
        this.guruModel = new Guru();
        this.nilaiModel = new Nilai();
        this.absensiModel = new Absensi();
        this.usersModel = new Users();
        this.biodataModel = new Biodata();
    }

    @Override
    public void indexCLI() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    INPUT NILAI SISWA                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        try {
            ResultSet listRs = this.usersModel.User();
            // Bingkai Tabel Daftar User sesuai request
            System.out.println("╔══════════╦══════════════════════════════╦════════════════════╗");
            System.out.printf("║ %-8s ║ %-28s ║ %-18s ║%n", "ID USER", "NAMA SISWA", "ROLE");
            System.out.println("╠══════════╬══════════════════════════════╬════════════════════╣");

            boolean adaData = false;
            while (listRs != null && listRs.next()) {
                if (listRs.getInt("id_role") == 2) {
                    adaData = true;
                    System.out.printf("║ %-8d ║ %-28s ║ %-18s ║%n",
                            listRs.getInt("id_user"),
                            listRs.getString("nama"),
                            "Siswa");
                }
            }
            if (!adaData) {
                System.out.printf("║ %-59s ║%n", "Data siswa tidak ditemukan.");
            }
            System.out.println("╚══════════╩══════════════════════════════╩════════════════════╝");
        } catch (SQLException e) {
            System.out.println(">> Gagal memuat daftar user: " + e.getMessage());
        }

        Nilai.nilaidata dto = new Nilai.nilaidata();

        System.out.println("\n--- Silakan Masukkan Data ---");
        System.out.print("➤ ID Siswa      : ");
        int id_siswa = input.nextInt();
        input.nextLine();
        dto.setId_user(id_siswa);

        try {
            ResultSet rsUser = this.usersModel.findById(id_siswa);
            if (rsUser != null && rsUser.next()) {
                // Validasi Role
                if (rsUser.getInt("id_role") != 2) {
                    System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                    System.out.println("║ GAGAL: User tersebut ditemukan, namun BUKAN SISWA!           ║");
                    System.out.println("╚══════════════════════════════════════════════════════════════╝");
                    return;
                }

                dto.setNama(rsUser.getString("nama"));
                System.out.println("  Nama Siswa    : " + dto.getNama());
                
                System.out.print("➤ Mata Pelajaran: ");
                dto.setMapel(input.nextLine());
                
                System.out.print("➤ Nilai (0-100) : ");
                dto.setNilai(input.nextDouble());
                input.nextLine();

                // Validasi Nilai
                if (dto.getNilai() < 0 || dto.getNilai() > 100) {
                    System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                    System.out.println("║ GAGAL: Nilai harus berada di rentang 0 - 100!                ║");
                    System.out.println("╚══════════════════════════════════════════════════════════════╝");
                    return;
                }

                // Eksekusi Simpan
                boolean isAddNilai = this.nilaiModel.InputNilaiSiswa(dto);

                // Bingkai Status Hasil Akhir
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                if (isAddNilai) {
                    System.out.printf("║ SUCCESS: Berhasil input nilai untuk %-23s ║%n", dto.getNama());
                } else {
                    System.out.println("║ FAILED : Terjadi kesalahan saat menyimpan data ke sistem.   ║");
                }
                System.out.println("╚══════════════════════════════════════════════════════════════╝");

            } else {
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                System.out.println("║ GAGAL: ID Siswa tidak ditemukan di database!                 ║");
                System.out.println("╚══════════════════════════════════════════════════════════════╝");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateNilai() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    UPDATE NILAI SISWA                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // 1. Tampilkan daftar nilai yang tersedia sebagai referensi
        ResultSet listRs = this.nilaiModel.Nilai();
        try {
            System.out.println("\n[ Daftar Nilai Saat Ini ]");
            System.out.println("╔══════════╦══════════╦═════════════════╦══════════════╦═══════╗");
            System.out.printf("║ %-8s ║ %-8s ║ %-15s ║ %-12s ║ %-5s ║%n",
                    "ID NILAI", "ID USER", "NAMA SISWA", "MAPEL", "NILAI");
            System.out.println("╠══════════╬══════════╬═════════════════╬══════════════╬═══════╣");

            boolean adaData = false;
            while (listRs != null && listRs.next()) {
                adaData = true;
                System.out.printf("║ %-8d ║ %-8d ║ %-15s ║ %-12s ║ %-5.2f ║%n",
                        listRs.getInt("id_nilai"),
                        listRs.getInt("id_user"),
                        listRs.getString("nama"),
                        listRs.getString("mapel"),
                        listRs.getDouble("nilai"));
            }

            if (!adaData) {
                System.out.printf("║ %-59s ║%n", " Data nilai masih kosong.");
            }
            System.out.println("╚══════════╩══════════╩═════════════════╩══════════════╩═══════╝");
        } catch (SQLException e) {
            System.out.println(">> Gagal memuat daftar: " + e.getMessage());
        }

        Nilai.nilaidata dto = new Nilai.nilaidata();

        System.out.println("\n--- Masukkan Perubahan Data ---");
        System.out.print("➤ Masukkan ID Nilai yang akan diupdate: ");
        int id_nilai = input.nextInt();
        input.nextLine();
        dto.setId_nilai(id_nilai);

        try {
            // 2. Validasi apakah ID Nilai tersebut ada di database
            ResultSet rsCek = this.nilaiModel.NilaiById(id_nilai);
            if (rsCek != null && rsCek.next()) {
                dto.setNama(rsCek.getString("nama"));
                System.out.println("  Siswa Terdeteksi: " + dto.getNama());
                System.out.println("  Mata Pelajaran  : " + rsCek.getString("mapel"));

                System.out.print("➤ Masukkan Nilai Baru (0-100): ");
                double nilaiBaru = input.nextDouble();
                input.nextLine();
                dto.setNilai(nilaiBaru);

                // 3. Validasi rentang nilai
                if (dto.getNilai() < 0 || dto.getNilai() > 100) {
                    System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                    System.out.println("║ GAGAL: Nilai baru harus berada di rentang 0 - 100!           ║");
                    System.out.println("╚══════════════════════════════════════════════════════════════╝");
                    return;
                }

                // 4. Eksekusi Update ke Model
                boolean isUpdated = this.nilaiModel.UpdateNilaiSiswa(dto);

                // Bingkai Status Hasil Akhir
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                if (isUpdated) {
                    System.out.printf("║ SUCCESS: Nilai %-15s berhasil diperbarui.      ║%n", dto.getNama());
                } else {
                    System.out.println("║ FAILED : Gagal memperbarui data ke database.                 ║");
                }
                System.out.println("╚══════════════════════════════════════════════════════════════╝");

            } else {
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                System.out.println("║ GAGAL: ID Nilai tidak ditemukan dalam sistem!                ║");
                System.out.println("╚══════════════════════════════════════════════════════════════╝");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteNilai() {

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    DELETE NILAI SISWA                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        ResultSet listRs = this.nilaiModel.Nilai();
        try {
            System.out.println("\n╔══════════╦══════════╦═════════════════╦══════════════╦═══════╗");
            System.out.printf("║ %-8s ║ %-8s ║ %-15s ║ %-12s ║ %-5s ║%n",
                    "ID NILAI", "ID USER", "NAMA SISWA", "MAPEL", "NILAI");
            System.out.println("╠══════════╬══════════╬═════════════════╬══════════════╬═══════╣");
            boolean adaData = false;
            while (listRs != null && listRs.next()) {
                adaData = true;
                System.out.printf("║ %-8d ║ %-8d ║ %-15s ║ %-12s ║ %-5.2f ║%n",
                        listRs.getInt("id_nilai"),
                        listRs.getInt("id_user"),
                        listRs.getString("nama"),
                        listRs.getString("mapel"),
                        listRs.getDouble("nilai"));
            }
            if (!adaData) {
                System.out.printf("║ %-67s ║%n", "Data masih kosong.");
            }
            System.out.println("╚══════════╩══════════╩═════════════════╩══════════════╩═══════╝");
        } catch (SQLException e) {
            System.out.println("Gagal memuat daftar user: " + e.getMessage());
        }

        System.out.print("ID Nilai yang akan dihapus: ");
        int id = input.nextInt();
        input.nextLine(); // Consume newline

        boolean isDeleteNilai = this.nilaiModel.DeleteNilaiSiswa(id);

        if (!isDeleteNilai) {
            System.out.println(">> Gagal delete nilai! Pastikan ID Siswa benar dan Terdaftar!");
        } else {
            System.out.println(">> Berhasil menghapus nilai siswa!");
        }
    }

    public void InputAbsensi() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    INPUT ABSENSI SISWA                       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        try {
            ResultSet listRs = this.usersModel.User();
            // Bingkai Tabel Daftar User
            System.out.println("╔══════════╦══════════════════════════════╦════════════════════╗");
            System.out.printf("║ %-8s ║ %-28s ║ %-18s ║%n", "ID USER", "NAMA SISWA", "ROLE");
            System.out.println("╠══════════╬══════════════════════════════╬════════════════════╣");

            boolean adaData = false;
            while (listRs != null && listRs.next()) {
                if (listRs.getInt("id_role") == 2) {
                    adaData = true;
                    System.out.printf("║ %-8d ║ %-28s ║ %-18s ║%n",
                            listRs.getInt("id_user"),
                            listRs.getString("nama"),
                            "Siswa");
                }
            }
            if (!adaData) {
                System.out.printf("║ %-59s ║%n", "Data siswa tidak ditemukan.");
            }
            System.out.println("╚══════════╩══════════════════════════════╩════════════════════╝");
        } catch (SQLException e) {
            System.out.println(">> Gagal memuat daftar user: " + e.getMessage());
        }

        Absensi.DataAbsensi dto = new Absensi.DataAbsensi();

        System.out.println("\n--- Silakan Isi Data Absensi ---");
        System.out.print("➤ ID Siswa      : ");
        int id_siswa = input.nextInt();
        input.nextLine();
        dto.setId_user(id_siswa);

        try {
            ResultSet rsUser = this.usersModel.findById(id_siswa);
            if (rsUser != null && rsUser.next()) {
                // Validasi Role (Harus Siswa)
                if (rsUser.getInt("id_role") != 2) {
                    System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                    System.out.println("║ GAGAL: User tersebut ditemukan, namun BUKAN SISWA!           ║");
                    System.out.println("╚══════════════════════════════════════════════════════════════╝");
                    return;
                }

                dto.setNama(rsUser.getString("nama"));
                System.out.println("  Nama Siswa    : " + dto.getNama());

                System.out.print("➤ Tanggal       : ");
                dto.setTanggal(input.nextLine());

                System.out.print("➤ Status Absen  : ");
                dto.setStatus(input.nextLine());

                // Proses Simpan ke Model
                boolean isSuccess = this.absensiModel.InsertAbsensiSiswa(dto);

                // Bingkai Status Hasil Akhir
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                if (isSuccess) {
                    System.out.printf("║ SUCCESS: Berhasil input absensi untuk %-22s ║%n", dto.getNama());
                } else {
                    System.out.println("║ FAILED : Terjadi kesalahan saat menyimpan data absensi.     ║");
                }
                System.out.println("╚══════════════════════════════════════════════════════════════╝");

            } else {
                // Jika ID Siswa tidak ditemukan
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                System.out.println("║ GAGAL: ID Siswa tidak ditemukan di database!                 ║");
                System.out.println("╚══════════════════════════════════════════════════════════════╝");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateAbsensi() {

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     UPDATE ABSENSI SISWA                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        // 1. Tampilkan daftar nilai yang tersedia sebagai referensi
        ResultSet listRs = this.absensiModel.AllAbsensi();
        try {
            System.out.println("╔══════════╦══════════╦═════════════════╦══════════════╦═══════╗");
            System.out.printf("║ %-8s ║ %-8s ║ %-15s ║ %-12s ║ %-5s ║%n",
                    "ID ABSENSI", "ID USER", "NAMA SISWA", "TANGGAL", "STATUS");
            System.out.println("╠══════════╬══════════╬═════════════════╬══════════════╬═══════╣");

            boolean adaData = false;
            while (listRs != null && listRs.next()) {
                adaData = true;
                System.out.printf("║ %-8d ║ %-8d ║ %-15s ║ %-12s ║ %-5s ║%n",
                        listRs.getInt("id_absensi"),
                        listRs.getInt("id_user"),
                        listRs.getString("nama"),
                        listRs.getString("tanggal"),
                        listRs.getString("status"));
            }

            if (!adaData) {
                System.out.printf("║ %-59s ║%n", " Data nilai masih kosong.");
            }
            System.out.println("╚══════════╩══════════╩═════════════════╩══════════════╩═══════╝");
        } catch (SQLException e) {
            System.out.println(">> Gagal memuat daftar: " + e.getMessage());
        }

        Absensi.DataAbsensi dto = new Absensi.DataAbsensi();

        System.out.println("\n--- Masukkan Perubahan Data ---");
        System.out.print("➤ Masukkan ID Absensi yang akan diupdate: ");
        int id_absensi = input.nextInt();
        input.nextLine();
        dto.setId_absensi(id_absensi);

        try {
            // 2. Validasi apakah ID Absensi tersebut ada di database
            ResultSet rsCek = this.absensiModel.AbsenSiswaById(id_absensi);
            if (rsCek != null && rsCek.next()) {
                dto.setNama(rsCek.getString("nama"));
                System.out.println("  Siswa Terdeteksi: " + dto.getNama());

                System.out.print("➤ Masukkan Tanggal Baru: ");
                String tanggal = input.nextLine();
                dto.setTanggal(tanggal);
                System.out.print("➤ Masukkan Status Baru: ");
                String status = input.nextLine();
                dto.setStatus(status);

                // 4. Eksekusi Update ke Model
                boolean isUpdated = this.absensiModel.UpdateAbsensiSiswa(dto);

                // Bingkai Status Hasil Akhir
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                if (isUpdated) {
                    System.out.printf("║ SUCCESS: Absensi %-15s berhasil diperbarui.      ║%n", dto.getNama());
                } else {
                    System.out.println("║ FAILED : Gagal memperbarui data ke database.                 ║");
                }
                System.out.println("╚══════════════════════════════════════════════════════════════╝");

            } else {
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                System.out.println("║ GAGAL: ID Absensi tidak ditemukan dalam sistem!                ║");
                System.out.println("╚══════════════════════════════════════════════════════════════╝");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteAbsensi() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    DELETE ABSENSI SISWA                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // 1. Tampilkan daftar absensi sebagai referensi agar tidak salah ID
        try {
            // Asumsi Anda memiliki method di model untuk mengambil semua data absensi
            ResultSet listRs = this.absensiModel.AllAbsensi();

            System.out.println("╔══════════╦══════════╦══════════════════════╦═════════════════╗");
            System.out.printf("║ %-8s ║ %-8s ║ %-20s ║ %-15s ║%n",
                    "ID ABSEN", "ID USER", "NAMA SISWA", "STATUS");
            System.out.println("╠══════════╬══════════╬══════════════════════╬═════════════════╣");

            boolean adaData = false;
            while (listRs != null && listRs.next()) {
                adaData = true;
                System.out.printf("║ %-8d ║ %-8d ║ %-20s ║ %-15s ║%n",
                        listRs.getInt("id_absensi"),
                        listRs.getInt("id_user"),
                        listRs.getString("nama"),
                        listRs.getString("status"));
            }

            if (!adaData) {
                System.out.printf("║ %-59s ║%n", " Data absensi masih kosong.");
            }
            System.out.println("╚══════════╩══════════╩══════════════════════╩═════════════════╝");
        } catch (SQLException e) {
            System.out.println(">> Gagal memuat daftar absensi: " + e.getMessage());
        }

        System.out.print("\n➤ Masukkan ID Absensi yang akan dihapus: ");
        int id = input.nextInt();
        input.nextLine(); // Konsumsi newline

        try {
            // 2. Cari data untuk konfirmasi sebelum benar-benar dihapus
            ResultSet rsCek = this.absensiModel.AbsenSiswaById(id);
            if (rsCek != null && rsCek.next()) {
                String namaSiswa = rsCek.getString("nama");
                String status = rsCek.getString("status");
                System.out.println("  Data Ditemukan: " + namaSiswa + " [" + status + "]");
                System.out.print("➤ Apakah Anda yakin ingin menghapus data ini? (y/n): ");
                String konfirmasi = input.nextLine();

                if (konfirmasi.equalsIgnoreCase("y")) {
                    // 3. Panggil method Delete di Model
                    boolean isDeleted = this.absensiModel.DeleteAbsensiSiswa(id);

                    System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                    if (isDeleted) {
                        System.out.println("║ SUCCESS: Data absensi berhasil dihapus dari sistem.          ║");
                    } else {
                        System.out.println("║ FAILED : Terjadi kesalahan saat proses penghapusan data.     ║");
                    }
                    System.out.println("╚══════════════════════════════════════════════════════════════╝");
                } else {
                    System.out.println(">> Penghapusan dibatalkan.");
                }
            } else {
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                System.out.println("║ GAGAL: ID Absensi tidak ditemukan dalam sistem!              ║");
                System.out.println("╚══════════════════════════════════════════════════════════════╝");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void TampilkanNilaiSiswa() {
        // 1. Panggil Model untuk dapatkan datanya
        ResultSet rs = this.nilaiModel.NilaiSiswa();

        // 2. Cek apakah datanya ada (tidak null)
        if (rs == null) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                GAGAL MENGAMBIL DATA SISWA                    ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            return;
        }

        // 3. Lakukan Looping & Print di sini
        try {
            // Judul disesuaikan agar pas di tengah tabel
            System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                            DAFTAR NILAI SISWA                             ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════╝");

            System.out.println("╔══════════╦══════════╦══════════════════════╦══════════════════╦═══════╗");
            System.out.printf("║ %-8s ║ %-8s ║ %-20s ║ %-16s ║ %-5s ║%n",
                    "ID NILAI", "ID USER", "NAMA SISWA", "MAPEL", "NILAI");
            System.out.println("╠══════════╬══════════╬══════════════════════╬══════════════════╬═══════╣");

            boolean adaData = false;
            while (rs.next()) {
                adaData = true;
                System.out.printf("║ %-8d ║ %-8d ║ %-20s ║ %-16s ║ %-5.1f ║%n",
                        rs.getInt("id_nilai"),
                        rs.getInt("id_user"),
                        rs.getString("nama"),
                        rs.getString("mapel"),
                        rs.getDouble("nilai"));
            }

            if (!adaData) {
                // Lebar disesuaikan dengan total kolom tabel (75 karakter isi)
                System.out.printf("║ %-75s ║%n", " Data nilai masih kosong.");
            }

            System.out.println("╚══════════╩══════════╩══════════════════════╩══════════════════╩═══════╝");

            // Tutup statement untuk kebersihan memori
            rs.getStatement().close();

        } catch (SQLException e) {
            System.err.println("Error saat menampilkan data: " + e.getMessage());
        }
    }

    public void TampilkanNilaiSiswaById() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                CARI DATA NILAI SISWA (BY ID)                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // 1. Tampilkan referensi ID Nilai yang tersedia (Tabel Referensi)
        try {
            ResultSet listRs = this.nilaiModel.Nilai();
            System.out.println("╔════════════╦════════════╦════════════════════════════════╗");
            System.out.printf("║ %-10s ║ %-10s ║ %-30s ║%n", "ID NILAI", "ID USER", "NAMA SISWA");
            System.out.println("╠════════════╬════════════╬════════════════════════════════╣");

            boolean adaData = false;
            while (listRs != null && listRs.next()) {
                adaData = true;
                System.out.printf("║ %-10d ║ %-10d ║ %-30s ║%n",
                        listRs.getInt("id_nilai"),
                        listRs.getInt("id_user"),
                        listRs.getString("nama"));
            }
            if (!adaData) {
                System.out.printf("║ %-58s ║%n", "         DATA NILAI MASIH KOSONG");
            }
            System.out.println("╚════════════╩════════════╩════════════════════════════════╝");
        } catch (SQLException e) {
            System.out.println(">> Gagal memuat daftar: " + e.getMessage());
        }

        // 2. Input ID Pencarian
        System.out.print("\n➤ Masukkan ID Nilai yang dicari: ");
        int id = input.nextInt();
        input.nextLine(); // Membersihkan buffer

        ResultSet rs = this.nilaiModel.NilaiById(id);

        try {
            if (rs != null && rs.next()) {
                // --- BAGIAN HASIL PENCARIAN (SATU BINGKAI UTUH) ---
                System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
                System.out.println("║                     HASIL PENCARIAN NILAI                        ║");
                System.out.println("╠══════════════════════════════════════════════════════════════════╣");

                // Informasi Identitas
                System.out.printf("║ ID NILAI   : %-51d ║%n", rs.getInt("id_nilai"));
                System.out.printf("║ NAMA SISWA : %-51s ║%n", rs.getString("nama").toUpperCase());
                System.out.printf("║ ID USER    : %-51d ║%n", rs.getInt("id_user"));

                // Garis Pemisah Tengah (Double Side Connector)
                System.out.println("╟──────────────────────────────────┬───────────────────────────────╢");

                // Header Kolom Mata Pelajaran
                System.out.printf("║ %-32s │ %-29s ║%n", "MATA PELAJARAN", "NILAI AKHIR");
                System.out.println("╟──────────────────────────────────┼───────────────────────────────╢");

                // Isi Data Nilai
                System.out.printf("║ %-32s │ %-29.2f ║%n",
                        rs.getString("mapel"),
                        rs.getDouble("nilai"));

                // Penutup Bingkai Bawah
                System.out.println("╚══════════════════════════════════╧═══════════════════════════════╝");

                rs.getStatement().close();
            } else {
                // Pesan Jika Tidak Ditemukan (Juga menggunakan bingkai agar seragam)
                System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
                System.out.printf("║ GAGAL: Data Nilai ID [%-3d] tidak ditemukan di database!      ║%n", id);
                System.out.println("╚══════════════════════════════════════════════════════════════════╝");
            }
        } catch (SQLException e) {
            System.err.println(">> Error saat menampilkan data: " + e.getMessage());
        }
    }
}
