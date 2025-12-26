package app.Models;

import app.App.Database;
import java.sql.PreparedStatement;;

public class Guru extends Database {

//         // CRUD DATA GURU
//     public boolean InputDataGuru(int id_guru, String mata_pelajaran, String nama_guru, String no_telp, String nip, String password) {
//         try {
//             String sql = "INSERT INTO guru (id, nama, mapel, no_telp, nip, pass) VALUES(?, ?, ?, ? , ?, ?)";
//             var stmt = prepare(sql);
//             stmt.setInt(1, id_guru);
//             stmt.setString(2, nama_guru);
//             stmt.setString(3, mata_pelajaran);
//             stmt.setString(4, no_telp);
//             stmt.setString(5, nip);
//             stmt.setString(6, password);

//             int rowsAffected = stmt.executeUpdate();
//             return rowsAffected > 0;
//         } catch (SQLException e) {
//             e.printStackTrace();
//             return false;
//         }

//     }

//     public boolean UpdateDataGuru(String mata_pelajaran, String nama_guru, String no_telp) {
//         try {
//             String sql = "UPDATE guru SET id_siswa = ?, mata_pelajaran = ?, nilai = ? WHERE id = ?";
//             var stmt = prepare(sql);
//             stmt.setString(2, mata_pelajaran);
//             stmt.setString(3, nama_guru);
//             stmt.setString(4, no_telp);

//             int rowsAffected = stmt.executeUpdate();
//             return rowsAffected > 0;
//         } catch (SQLException e) {
//             e.printStackTrace();
//             return false;
//         }

//     }

//     public boolean DeleteNilaiGuru(int id) {
//         try {
//             String sql = "DELETE FROM guru WHERE id = ?";
//             var stmt = prepare(sql);
//             stmt.setInt(1, id);

//             int rowsAffected = stmt.executeUpdate();
//             return rowsAffected > 0;
//         } catch (SQLException e) {
//             e.printStackTrace();
//             return false;
//         }
//     }

//     public ResultSet GetDataGuru() {
//         String sql = "SELECT * FROM guru";
//         try {
//             PreparedStatement stmt = prepare(sql);
//             return stmt.executeQuery();
//         } catch (SQLException e) {
//             e.printStackTrace();
//             return null; // Kembalikan null jika error
//         }
//     }


//     // public boolean cekApakahSiswaAda(int id) {
//     //     try {
//     //         String sql = "SELECT id FROM users WHERE id = ?";
//     //         var stmt = prepare(sql);
//     //         stmt.setInt(1, id);
//     //         ResultSet rs = stmt.executeQuery();
//     //         // rs.next() akan bernilai TRUE jika data ditemukan
//     //         return rs.next(); 
//     //     } catch (SQLException e) {
//     //         return false;
//     //     }
//     // }
// }
}