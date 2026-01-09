package app.Controllers;

import app.App.Controller;
import app.App.Helper;
import app.Models.Admin;
import app.Models.Siswa;
import app.Models.Users;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HomeController extends Controller {

    protected Scanner input;
    public static int session_id_user;

    public HomeController() {
        this.input = new Scanner(System.in);
    }

    @Override
    public void indexCLI() {
        SiswaController siswaController = new SiswaController();
        GuruController guruController = new GuruController();
        AdminController adminController = new AdminController();
        Siswa SiswaModel = new Siswa();
        Admin adminModel = new Admin();
        Users usersModel = new Users();

        while (true) {
            Users.userdata dto = new Users.userdata();
            System.out.println("\n=== Selamat Datang Di Sistem Informasi Akademik ===");
            System.out.print("Username : ");
            String isName = input.nextLine();
            dto.setNama(isName);

            System.out.print("Password : ");
            String isPass = input.nextLine();
            dto.setPass(isPass);

                try {

                ResultSet rs = usersModel.ValidasiSiswa(dto);

                if (rs.next()) {
                    HomeController.session_id_user = rs.getInt("id_user");

                    String namaUser = rs.getString("nama");
                    int idRole = rs.getInt("id_role");

                    System.out.println("\nLogin Berhasil! Selamat datang, " + namaUser);
                    Helper.clearScreen();
                    //done
                    if (idRole == 1) {
                        while (true) {
                            Helper.clearScreen();
                            tampilanMenuGuru();
                            int pilihan = input.nextInt();
                            input.nextLine(); // Consume newline
                            switch (pilihan) {
                                case 1:
                                    Helper.clearScreen();
                                    guruController.indexCLI();
                                    pressEnterToContinue();
                                    break;
                                case 2:
                                    Helper.clearScreen();
                                    guruController.UpdateNilai();
                                    pressEnterToContinue();
                                    break;
                                case 3:
                                    Helper.clearScreen();
                                    guruController.DeleteNilai();
                                    pressEnterToContinue();
                                    break;
                                case 4:
                                    Helper.clearScreen();
                                    guruController.InputAbsensi();
                                    pressEnterToContinue();
                                    break;
                                case 5:
                                    Helper.clearScreen();
                                    guruController.UpdateAbsensi();
                                    pressEnterToContinue();
                                    break;
                                case 6:
                                    Helper.clearScreen();
                                    guruController.DeleteAbsensi();
                                    pressEnterToContinue();
                                    break;
                                case 7:
                                    Helper.clearScreen();
                                    guruController.TampilkanNilaiSiswa();
                                    pressEnterToContinue();
                                    break;
                                case 8:
                                    Helper.clearScreen();
                                    guruController.TampilkanNilaiSiswaById();
                                    pressEnterToContinue();
                                    break;
                                case 0:
                                    Helper.clearScreen();
                                    System.out.println("Logout berhasil.");
                                    break;
                                default:
                                    Helper.clearScreen();
                                    System.out.println("Pilihan tidak valid!");
                                    break;
                            }
                            if (pilihan == 0) {
                                break;
                            }
                        }
                        //done
                    } else if (idRole == 2) {
                        while (true) {
                            Helper.clearScreen();
                            tampilanSiswa();
                            int pilihanSiswa = input.nextInt();
                            input.nextLine(); // Consume newline
                            switch (pilihanSiswa) {
                                case 1:
                                    Helper.clearScreen();
                                    siswaController.BiodataSiswa();
                                    pressEnterToContinue();
                                    break;
                                case 2:
                                    Helper.clearScreen();
                                    siswaController.indexCLI();
                                    pressEnterToContinue();
                                    break;
                                case 3:
                                    Helper.clearScreen();
                                    siswaController.AbsensiSiswa();
                                    pressEnterToContinue();
                                    break;
                                case 0:
                                    Helper.clearScreen();
                                    System.out.println("Logout berhasil.");
                                    break;
                                default:
                                    Helper.clearScreen();
                                    System.out.println("Pilihan tidak valid!");
                                    break;
                            }
                            if (pilihanSiswa == 0) {
                                break;
                            }
                        }
                        // done
                    } else if (idRole == 3) {
                        while (true) {
                            tampilanAdmin();
                            System.err.print("Masukan Pilihan Anda: ");
                            int pilihanAdmin = input.nextInt();
                            input.nextLine(); // Consume newline
                            switch (pilihanAdmin) {
                                case 1:
                                    Helper.clearScreen();
                                    adminController.indexCLI();
                                    pressEnterToContinue();
                                    break;
                                case 2:
                                    Helper.clearScreen();
                                    adminController.UpdateDataSiswa();
                                    pressEnterToContinue();
                                    break;
                                case 3:
                                    Helper.clearScreen();
                                    adminController.DeleteSiswa();
                                    pressEnterToContinue();
                                    break;
                                case 4:
                                    Helper.clearScreen();
                                    adminController.GetDataSiswa();
                                    pressEnterToContinue();
                                    break;
                                case 5:
                                    Helper.clearScreen();
                                    adminController.InputDataGuru();
                                    pressEnterToContinue();
                                    break;
                                case 6:
                                    Helper.clearScreen();
                                    adminController.UpdateDataGuru();
                                    pressEnterToContinue();
                                    break;
                                case 7:
                                    Helper.clearScreen();
                                    adminController.DeleteDataGuru();
                                    pressEnterToContinue();
                                    break;
                                case 8:
                                    Helper.clearScreen();
                                    adminController.GetDataGuru();
                                    pressEnterToContinue();
                                    break;
                                case 9:
                                    Helper.clearScreen();
                                    adminController.CreateUser();
                                    pressEnterToContinue();
                                    break;
                                case 10:
                                    Helper.clearScreen();
                                    adminController.UpdateUser();
                                    pressEnterToContinue();
                                    break;
                                case 11:
                                    Helper.clearScreen();
                                    adminController.DeleteUser();
                                    pressEnterToContinue();
                                    break;
                                case 0:
                                    Helper.clearScreen();
                                    System.out.println("Logout berhasil.");
                                    break;
                                default:
                                    Helper.clearScreen();
                                    System.out.println("Pilihan tidak valid!");
                                    break;

                            }
                            if (pilihanAdmin == 0) {
                                break;
                            }
                        }
                    }
                } else {
                    Helper.clearScreen();
                    System.out.println(">> Gagal Login: Username atau Password salah!");
                }

                // Penting: Tutup ResultSet
                rs.close();

            } catch (SQLException e) {
                System.out.println("Terjadi kesalahan database: " + e.getMessage());
            }
        }
    }

    public void pressEnterToContinue() {
        System.out.println("\nTekan Enter untuk keluar...");
        input.nextLine();
    }

    static void tampilanMenuGuru() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                      MENU DASHBOARD GURU                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        System.out.println("┌───────────────────────────────┬──────────────────────────────┐");
        // Baris 1
        System.out.printf("│ %-2d. %-25s │ %-2d. %-24s │%n",
                1, "Input Nilai Siswa", 5, "Update Absensi Siswa");
        // Baris 2
        System.out.printf("│ %-2d. %-25s │ %-2d. %-24s │%n",
                2, "Update Nilai Siswa", 6, "Delete Absensi Siswa");
        // Baris 3
        System.out.printf("│ %-2d. %-25s │ %-2d. %-24s │%n",
                3, "Delete Nilai Siswa", 7, "Lihat Semua Nilai");
        // Baris 4
        System.out.printf("│ %-2d. %-25s │ %-2d. %-24s │%n",
                4, "Input Absensi Siswa", 8, "Cari Nilai (By ID)");
        System.out.println("├───────────────────────────────┴──────────────────────────────┤");
        // Baris Keluar
        System.out.printf("│ %-2d. %-56s │%n", 0, "Kembali ke Menu Utama");
        System.out.println("└──────────────────────────────────────────────────────────────┘");
        System.out.print("➤ Pilih Menu: ");
    }

    static void tampilanSiswa() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║              MENU DASHBOARD SISWA            ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("┌──────────────────────────────────────────────┐");
        System.out.printf("│ %-2d. %-40s │%n", 1, "Lihat Biodata Siswa");
        System.out.printf("│ %-2d. %-40s │%n", 2, "Lihat Nilai Siswa");
        System.out.printf("│ %-2d. %-40s │%n", 3, "Lihat Absensi Siswa");
        System.out.println("├──────────────────────────────────────────────┤");
        System.out.printf("│ %-2d. %-40s │%n", 0, "Kembali ke Menu Utama");
        System.out.println("└──────────────────────────────────────────────┘");
        System.out.print("➤ Pilih Menu: ");
    }

    static void tampilanAdmin() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     MENU DASHBOARD ADMIN                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // Section Data Akademik
        System.out.println("┌───────────────────────────────┬──────────────────────────────┐");
        System.out.printf("│ %-29s │ %-28s │%n", "[ DATA SISWA ]", "[ DATA GURU ]");
        System.out.println("├───────────────────────────────┼──────────────────────────────┤");
        System.out.printf("│ %-2d. %-25s │ %-2d. %-24s │%n", 1, "Input Biodata Siswa", 5, "Input Biodata Guru");
        System.out.printf("│ %-2d. %-25s │ %-2d. %-24s │%n", 2, "Update Biodata Siswa", 6, "Update Biodata Guru");
        System.out.printf("│ %-2d. %-25s │ %-2d. %-24s │%n", 3, "Delete Biodata Siswa", 7, "Delete Biodata Guru");
        System.out.printf("│ %-2d. %-25s │ %-2d. %-24s │%n", 4, "Lihat Biodata Siswa", 8, "Lihat Biodata Guru");

        // Section Management User
        System.out.println("├───────────────────────────────┴──────────────────────────────┤");
        System.out.printf("│ %-60s │%n", "[ SYSTEM & USER MANAGEMENT ]");
        System.out.println("├───────────────────────────────┬──────────────────────────────┤");
        System.out.printf("│ %-2d. %-25s │ %-2d. %-24s │%n", 9, "Create New User", 11, "Delete User");
        System.out.printf("│ %-2d. %-25s │ %-28s │%n", 10, "Update User", "");

        // Footer Navigasi
        System.out.println("├───────────────────────────────┴──────────────────────────────┤");
        System.out.printf("│ %-2d. %-56s │%n", 0, "Kembali ke Menu Utama");
        System.out.println("└──────────────────────────────────────────────────────────────┘");
        System.out.print("➤ Pilih Menu: ");
    }
}

//                 case 2:
//                     System.out.print("Masukan Nip Anda: ");
//                     String isNip = input.nextLine();
//                     System.out.print("Masukan Password Anda: ");
//                     String isPasss = input.nextLine();
//                     if (adminModel.ValidasiGuru(isNip, isPasss)) {
//                         tampilanGuru();
//                         System.out.print("Pilih menu: ");
//                         int ispilihanGuru = input.nextInt();
//                         input.nextLine();
//                         switch (ispilihanGuru) {
//                             case 1:
//                                 guruController.indexCLI();
//                                 break;
//                             case 2:
//                                 guruController.UpdateNilai();
//                                 break;
//                             case 3:
//                                 guruController.DeleteNilai();
//                                 break;
//                             case 4:
//                                 guruController.GetNilaiSiswa();
//                                 break;
//                             case 0:
//                                 break;
//                             default:
//                                 System.out.println("Pilihan tidak valid!");
//                                 break;
//                         }
//                     } else {
//                         System.out.println(">> Gagal Login: NIP atau Password salah!");
//                     }
//                     break;
//                 case 3:
//                     tampilanAdmin();
//                     System.out.print("Pilih menu: ");
//                     int ispilihanAdmin = input.nextInt();
//                     input.nextLine();
//                     switch (ispilihanAdmin) {
//                         case 1:
//                             adminController.indexCLI();
//                             break;
//                         case 2:
//                             adminController.UpdateDataSiswa();
//                             break;
//                         case 3:
//                             adminController.DeleteSiswa();
//                             break;
//                         case 4:
//                             adminController.GetDataSiswa();
//                             break;
//                         case 5:
//                             adminController.InputDataGuru();
//                             break;
//                         case 6:
//                             adminController.UpdateDataGuru();
//                             break;
//                         case 7:
//                             adminController.DeleteDataGuru();
//                             break;
//                         case 8:
//                             adminController.GetDataGuru();
//                             break;
//                         case 0:
//                             break;
//                         default:
//                             System.out.println("Pilihan tidak valid!");
//                             break;
//                     }
//                     break;
//                 case 4:
//                     siswaController.BiodataSiswa();
//                     break;
//                 // case 5:
//                 //     // lihatLaporanSiswa();
//                 //      siswaController.listSiswa();
//                 //     System.out.println("Fungsi lihat laporan lengkap siswa dipanggil.");
//                 //     break;
//                 // case 6:
//                 //     siswaController.Update();
//                 //     System.out.println("Update Biodata Siswa");
//                 //     break;
//                 // case 7:
//                 //     siswaController.Delete();
//                 //     System.out.println("Delete Biodata Siswa");
//                 //     break;
//                 // case 8:
//                 //     System.out.println("Update Biodata Guru");
//                 //     break;
//                 // case 9:
//                 //     System.out.println("Delete Biodata Guru");
//                 //     break;
//                 case 0:
//                     System.out.println("Keluar dari sistem...");
//                     // conn.close();
//                     System.exit(0);
//                 default:
//                     System.out.println("Pilihan tidak valid!");
//             }
//         }
//     }
//     static void tampilkanMenu() {
//         System.out.println("\n=== Sistem Manajemen Sekolah ===");
//         System.out.println("1. Login Sebagai Siswa ");
//         System.out.println("2. Login Sebagai Guru");
//         System.out.println("3. Login Sebagai Admin");
//         System.out.println("4. Lihat Laporan Lengkap Siswa");
//         System.out.println("0. Keluar");
//     }
//   
//     // static void tampilkanMenu1() {
//     //     System.out.println("\n=== SISTEM MANAJEMEN SEKOLAH ===");
//     //     System.out.println("1. Pendaftaran Siswa Baru");
//     //     System.out.println("2. Kelola Biodata Guru (Lihat/Tambah)");
//     //     System.out.println("3. Input Nilai Siswa");
//     //     System.out.println("4. Input Absensi Siswa");
//     //     System.out.println("5. Lihat Laporan Lengkap Siswa");
//     //     System.out.println("6. Update Biodata Siswa");
//     //     System.out.println("7. Delete Biodata Siswa");
//     //     System.out.println("8. Update Biodata Guru");
//     //     System.out.println("9. Delete Biodata Guru");
//     //     System.out.println("0. Keluar");
//     // }
//     // public void indexCLI() {
//     // tampilkanMenu();
//     //             System.out.print("Pilih menu: ");
//     //             int pilihan; 
//     //             // input.nextInt();
//     //             // input.nextLine();
//     //     switch (pilihan) {
//     //                 case 1:
//     //                     // daftarSiswaBaru();
//     //                     // break;
//     //                 case 2:
//     //                     // kelolaGuru();
//     //                     // break;
//     //                 case 3:
//     //                     // inputNilaiSiswa();
//     //                     // break;
//     //                 case 4:
//     //                     // inputAbsensiSiswa();
//     //                     // break;
//     //                 case 5:
//     //                     // lihatLaporanSiswa();
//     //                     // break;
//     //                 case 0:
//     //                     // System.out.println("Keluar dari sistem...");
//     //                     // conn.close();
//     //                     // System.exit(0);
//     //                 default:
//     //                     // System.out.println("Pilihan tidak valid!");
//     //             }
//     //             System.out.println("\n--------------------------------");
//     //         }
//     // }
// }

