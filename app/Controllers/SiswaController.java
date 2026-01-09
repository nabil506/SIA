package app.Controllers;

import app.Models.Absensi;
import app.Models.Biodata;
import app.Models.Nilai;
import app.Models.Siswa;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SiswaController extends HomeController {

    private final Siswa siswaModel;
    private final Absensi absensiModel;
    private final Nilai nilaiModel;
    private final Biodata biodataModel;

    public SiswaController() {
        this.absensiModel = new Absensi();
        this.siswaModel = new Siswa();
        this.nilaiModel = new Nilai();
        this.biodataModel = new Biodata();
    }

    @Override
    // pemanggilan nilai siswa
    public void indexCLI() {
        // 1. Ambil ID dari session login
        int id_login = HomeController.session_id_user;
        ResultSet rs = this.nilaiModel.NilaiSiswaSelf(id_login);

        // Header Utama
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    LAPORAN NILAI HASIL BELAJAR                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");

        try {
            if (rs != null && rs.next()) {
                // Bingkai Tabel Mulai
                System.out.println("┌──────────────────────────────────────────────────────────────────┐");
                // Baris Nama dan ID dimasukkan ke dalam tabel
                System.out.printf("│ NAMA SISWA : %-51s │%n", rs.getString("nama").toUpperCase());
                System.out.printf("│ ID SISWA   : %-51d │%n", rs.getInt("id_user"));
                System.out.println("├──────────────────────────────────┬───────────────────────────────┤");

                // Header Kolom
                System.out.printf("│ %-32s │ %-29s │%n", "MATA PELAJARAN", "NILAI AKHIR");
                System.out.println("├──────────────────────────────────┼───────────────────────────────┤");

                // 2. Looping Data Nilai
                do {
                    System.out.printf("│ %-32s │ %-29.2f │%n",
                            rs.getString("mapel"),
                            rs.getDouble("nilai"));
                } while (rs.next());

                System.out.println("└──────────────────────────────────┴───────────────────────────────┘");

                rs.getStatement().close();
            } else {
                System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
                System.out.println("║ INFO: Data nilai Anda belum tersedia di sistem.                  ║");
                System.out.println("╚══════════════════════════════════════════════════════════════════╝");
            }

        } catch (SQLException e) {
            System.err.println(">> Error saat memuat rapor: " + e.getMessage());
        }
    }

    public void BiodataSiswa() {
        // 1. Ambil ID dari session login
        int id_login = HomeController.session_id_user;
        ResultSet rs = this.biodataModel.ReadBiodataSelf(id_login);

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    PROFIL BIODATA SISWA                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        try {
            // 2. Cek apakah datanya ada
            if (rs != null && rs.next()) {
                System.out.println("┌──────────────────────────────────────────────────────────────┐");
                System.out.printf("│ %-20s : %-37s │%n", "ID SISWA", rs.getInt("id_user"));
                System.out.printf("│ %-20s : %-37s │%n", "NAMA LENGKAP", rs.getString("nama"));
                System.out.printf("│ %-20s : %-37s │%n", "NIS", rs.getString("nis"));
                System.out.printf("│ %-20s : %-37s │%n", "KATEGORI", rs.getString("kategori").toUpperCase());
                System.out.printf("│ %-20s : %-37s │%n", "TANGGAL LAHIR", rs.getString("tgl_lahir"));
                System.out.printf("│ %-20s : %-37s │%n", "JENIS KELAMIN", rs.getString("jenis_kelamin"));
                System.out.printf("│ %-20s : %-37s │%n", "NAMA IBU", rs.getString("nama_ibu"));
                System.out.printf("│ %-20s : %-37s │%n", "ALAMAT", rs.getString("alamat"));
                System.out.println("└──────────────────────────────────────────────────────────────┘");

                // Penutup Memori
                rs.getStatement().close();
            } else {
                // Jika biodata belum diisi oleh Admin
                System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
                System.out.println("║ INFO: Biodata Anda belum dilengkapi oleh Admin.              ║");
                System.out.println("║       Harap segera melapor ke bagian Tata Usaha.             ║");
                System.out.println("╚══════════════════════════════════════════════════════════════╝");
            }
        } catch (SQLException e) {
            System.out.println(">> Error saat menampilkan data: " + e.getMessage());
        }
    }

    public void AbsensiSiswa() {
        // 1. Ambil ID dari session login
        int id_login = HomeController.session_id_user;
        ResultSet rs = this.absensiModel.AbsenSiswaSelf(id_login);

        // Header Menu
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    REKAP ABSENSI KEHADIRAN SISWA                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");

        try {
            // 2. Cek apakah datanya ada
            if (rs != null && rs.next()) {
                // Bingkai Tabel Mulai
                System.out.println("┌──────────────────────────────────────────────────────────────────┐");
                // Baris Nama dan ID dimasukkan ke dalam tabel
                System.out.printf("│ NAMA SISWA : %-51s │%n", rs.getString("nama").toUpperCase());
                System.out.printf("│ ID SISWA   : %-51d │%n", rs.getInt("id_user"));
                System.out.println("├──────────────────────────────────┬───────────────────────────────┤");

                // Header Kolom
                System.out.printf("│ %-32s │ %-29s │%n", "TANGGAL KEHADIRAN", "STATUS");
                System.out.println("├──────────────────────────────────┼───────────────────────────────┤");

                // 3. Looping Data (Menggunakan do-while agar data pertama tidak terlewat)
                do {
                    String status = rs.getString("status").toUpperCase();
                    System.out.printf("│ %-32s │ %-29s │%n",
                            rs.getString("tanggal"),
                            status);
                } while (rs.next());

                System.out.println("└──────────────────────────────────┴───────────────────────────────┘");

                // Menutup result set
                rs.getStatement().close();
            } else {
                // Jika data absensi belum ada
                System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
                System.out.println("║ INFO: Belum ada data absensi yang tercatat untuk Anda.           ║");
                System.out.println("╚══════════════════════════════════════════════════════════════════╝");
            }
        } catch (SQLException e) {
            System.out.println(">> Error Absensi: " + e.getMessage());
        }
    }
}
