package app.Models;

import app.App.Database;

public class Admin extends Database {

    // CRUD DATA SISWA

    // Asumsi: Admin mengirimkan NIS sebagai kunci untuk mencari
    // public boolean UpdateDataSiswa(String nis_kunci, String nama_siswa, String tgl_lahir, String alamat, String nama_ibu, String jenis_kelamin) {


        // --- QUERY 2: UPDATE DETAIL (di tabel BIODATA_SISWA) ---

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


        // sek, iki opo sek sing di benakno ?? 
        //query nak bawah iki kan from user dee nah nek from user berarti masuk e file endi?
        //cobak deloen nak gone model siswa dan guru wes betul gak sesuai karo seng mok kei ero mau
        //maksudk nilai absensi siswa admin e dorong tak benakno

        // nah awkmu ben gampang ngene iki kan awkdewe fokus nak 1 tabel sek toh tabel users otomatis
        // awkmu nggawe ae UserModel.java
        // nggawe o file e sek
        // engkok query SELECT | INSERT | UPDATE | DELETE nak njero UserModel.java
        // wes a?? okee session chat ae wak nek ape chat





    // public boolean ValidasiGuru(String nip, String password) {
    //     try {
    //         String sql = "SELECT * FROM guru WHERE nip = ? AND pass = ?";
    //         var stmt = prepare(sql);
    //         stmt.setString(1, nip);
    //         stmt.setString(2, password);
    //         ResultSet rs = stmt.executeQuery();
    //         return rs.next();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }
}
