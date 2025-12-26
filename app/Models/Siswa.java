package app.Models;

import app.App.Database;

public class Siswa extends Database {

    // public ResultSet GetNilaiSiswa() {
    //     String sql = "SELECT users.id_user, users.nama, nilai.mapel, nilai.nilai" + "FROM nilai"
    //             + "JOIN users ON nilai.id_user = users.id_user"
    //             + "WHERE users.id_role = 2"; 
    //     try {
    //         PreparedStatement stmt = prepare(sql);
    //         return stmt.executeQuery();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return null; // Kembalikan null jika error
    //     }
    // }

    // public ResultSet DataSiswa() {
    //     String sql = "SELECT * FROM biodata Where kategori = 'siswa'";
    //     try {
    //         PreparedStatement stmt = prepare(sql);
    //         return stmt.executeQuery();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return null; // Kembalikan null jika error
    //     }
    // }


}
