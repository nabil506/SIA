package app.Models;

import app.App.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Users extends Database {

    public static class userdata {

        private int id_user;
        private String nama;
        private String pass;
        private int id_role;

        // Getter dan Setter sebagai pintu akses data (Encapsulation)
        public int getId_user() {
            return id_user;
        }

        public void setId_user(int id_user) {
            this.id_user = id_user;
        }

        public void setId_role(int id_role) {
            this.id_role = id_role;
        }

        public int getId_role() {
            return id_role;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

    }

    public int createNewUser(userdata dto) {
        // 1. Definisikan SQL (Password otomatis default '1234')
        String sql = "INSERT INTO users (nama, pass, id_role) VALUES (?, '1234', ?)";

        try {
            // 2. Tambahkan flag RETURN_GENERATED_KEYS agar JDBC menangkap ID baru
            var stmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, dto.getNama());
            stmt.setInt(2, dto.getId_role());

            // 3. Jalankan perintah insert
            int affectedRows = stmt.executeUpdate();

            // 4. Jika ada baris yang bertambah, ambil ID-nya
            if (affectedRows > 0) {
                var rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // Mengembalikan ID angka (misal: 10, 11, dst)
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 5. Kembalikan -1 jika gagal agar Controller tahu ada masalah
        return -1;
    }
    //         // 2. AMBIL ID USER YANG BARU SAJA DIBUAT
    //         var rs = stmtUser.getGeneratedKeys();
    //         int idUserBaru = 0;
    //         if (rs.next()) {
    //             idUserBaru = rs.getInt(1); // Dapat ID baru, misal: 10
    //         }

    //         // 3. INSERT KE TABEL BIODATA (Pakai ID tadi biar nyambung)
    //         String sqlBio = "INSERT INTO biodata_siswa (id_user, nis, tgl_lahir, alamat, nama_ibu, jenis_kelamin) VALUES (?, ?, ?, ?, ?, ?)";
    //         var stmtBio = conn.prepareStatement(sqlBio);
    //         stmtBio.setInt(1, idUserBaru); // Sambungkan ID User disini
    //         stmtBio.setString(2, nis);
    //         stmtBio.setString(3, tgl_lahir);
    //         stmtBio.setString(4, alamat);
    //         stmtBio.setString(5, nama_ibu);
    //         stmtBio.setString(6, jenis_kelamin);
    //         stmtBio.executeUpdate(); // Jalankan insert kedua
    //         return true; // Sukses
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false; // Gagal
    //     }
    // }
    // public ResultSet ReadUser() {
    // }
    public boolean UpdateUser(userdata dto) {

        String sql = "UPDATE users SET pass = ? WHERE id_user = ?";

        try {
            var stmt = prepare(sql);
            stmt.setString(1, dto.getPass());
            stmt.setInt(2, dto.getId_user());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public ResultSet User(){
        String sql = "SELECT * FROM users";

        try {
            PreparedStatement stmt = prepare(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Kembalikan null jika error
        }

    }

    public boolean DeleteUser(userdata dto) {

        String sql = "DELETE FROM users WHERE id_user = ?";

        try {
            var stmt = prepare(sql);
            stmt.setInt(1, dto.getId_user());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public ResultSet ValidasiSiswa(userdata dto) {

        String sql = "SELECT * FROM users WHERE nama = ? AND pass = ?";
        try {
            var stmt = prepare(sql);
            stmt.setString(1, dto.getNama());
            stmt.setString(2, dto.getPass());

            return stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // iki tambahan lanek awkmu pingin nampilno detail e 1 siswa :
    public ResultSet findById(int id) {
        String sql = "SELECT users.id_user, users.nama, users.id_role, biodata.id_bio "
       + "FROM users LEFT JOIN biodata ON users.id_user = biodata.id_user WHERE users.id_user = ? ";
        try {
            var stmt = prepare(sql);
            stmt.setInt(1, id);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean ValidasiIdUsers(userdata dto) {
        String sql = "SELECT * FROM users WHERE id_user = ?";
        try {
            var stmt = prepare(sql);
            stmt.setInt(1, dto.getId_user());
            var rs = stmt.executeQuery();
            return rs.next(); // Mengembalikan true jika ada data
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // public ResultSet getUsersWithoutBiodata() {
    //     // Mencari user yang ID-nya TIDAK ADA di tabel biodata
    //     String sql = "SELECT id_user, nama, id_role FROM users "
    //             + "WHERE id_user NOT IN (SELECT id_user FROM biodata)";
    //     try {
    //         return prepare(sql).executeQuery();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }
}
// nggolek o nak chat gpt pokok e :
// buatkan saya method untuk menampilkan 1 detail dari user yang saya pilih

// wes all in o sek nak kabeh method iki......
// public boolean UpdateDataSiswa(String nis_kunci, String nama_siswa, String tgl_lahir, String alamat,
//         String nama_ibu, String jenis_kelamin) {
//     // --- QUERY 1: UPDATE NAMA (di tabel USERS) ---
//     String sql1 = "UPDATE users SET nama = ? WHERE id_user = (SELECT id_user FROM biodata_siswa WHERE nis = ?)";
//     // --- QUERY 2: UPDATE DETAIL (di tabel BIODATA_SISWA) ---
//     String sql2 = "UPDATE biodata_siswa SET tgl_lahir = ?, alamat = ?, nama_ibu = ?, jenis_kelamin = ? WHERE nis = ?";
//     try {
//         // Mulai Transaksi
//         conn.setAutoCommit(false);
//         // --- EKSEKUSI UPDATE USERS (NAMA) ---
//         var stmt1 = conn.prepareStatement(sql1);
//         stmt1.setString(1, nama_siswa);
//         stmt1.setString(2, nis_kunci); // Menggunakan NIS sebagai kunci Subquery
//         stmt1.executeUpdate();
//         // --- EKSEKUSI UPDATE BIODATA ---
//         var stmt2 = conn.prepareStatement(sql2);
//         stmt2.setString(1, tgl_lahir);
//         stmt2.setString(2, alamat);
//         stmt2.setString(3, nama_ibu);
//         stmt2.setString(4, jenis_kelamin);
//         stmt2.setString(5, nis_kunci); // Menggunakan NIS sebagai kunci WHERE
//         int rowsAffected = stmt2.executeUpdate();
//         // Commit Transaksi
//         conn.commit();
//         conn.setAutoCommit(true);
//         return rowsAffected > 0;
//     } catch (SQLException e) {
//         try {
//             // Rollback jika gagal
//             conn.rollback();
//             conn.setAutoCommit(true);
//         } catch (SQLException ex) {
//             ex.printStackTrace();
//         }
//         e.printStackTrace();
//         return false;
//     }
// }

