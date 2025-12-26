package app.Models;

import app.App.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Nilai extends Database {

    public static class nilaidata {

        private int id_nilai;
        private int id_user;
        private String nama;
        private String mapel;
        private double nilai;

        // Getter dan Setter sebagai pintu akses data (Encapsulation)
        public int getId_nilai() {
            return id_nilai;
        }

        public void setId_nilai(int id_nilai) {
            this.id_nilai = id_nilai;
        }

        public int getId_user() {
            return id_user;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public void setId_user(int id_user) {
            this.id_user = id_user;
        }

        public String getMapel() {
            return mapel;
        }

        public void setMapel(String mapel) {
            this.mapel = mapel;
        }

        public double getNilai() {
            return nilai;
        }

        public void setNilai(double nilai) {
            this.nilai = nilai;
        }
    }

    public boolean InputNilaiSiswa(nilaidata dto) {

        if (dto.getNilai() < 0 || dto.getNilai() > 100) {
            return false; // Berhenti di sini, jangan lanjut ke simpan data
        }

        try {
            // 2. Query untuk mengecek apakah ID User tersebut adalah Siswa (id_role = 2)
            String checkRoleSql = "SELECT id_role FROM users WHERE id_user = ?";
            var checkStmt = prepare(checkRoleSql);
            checkStmt.setInt(1, dto.getId_user());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int role = rs.getInt("id_role");

                if (role != 2) {
                    System.out.println(">> Gagal: User tersebut bukan siswa!");
                    return false;
                }
            } else {
                System.out.println(">> Gagal: ID User tidak ditemukan!");
                return false;
            }

            // 4. Jika validasi lolos, lakukan INSERT (Tanpa klausa WHERE)
            String sql = "INSERT INTO nilai (id_user,nama, mapel, nilai) VALUES (?, ?, ?, ?)";
            var stmt = prepare(sql);
            stmt.setInt(1, dto.getId_user());
            stmt.setString(2, dto.getNama());
            stmt.setString(3, dto.getMapel());
            stmt.setDouble(4, dto.getNilai());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdateNilaiSiswa(nilaidata dto) {

        if (dto.getNilai() < 0 || dto.getNilai() > 100) {
            return false; 
        }

        try {
            String sql = "UPDATE nilai SET nilai = ? WHERE id_nilai = ?";
            var stmt = prepare(sql);
            stmt.setDouble(1, dto.getNilai());
            stmt.setInt(2, dto.getId_nilai());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean DeleteNilaiSiswa(int id) {

        try {
            String sql = "DELETE FROM nilai WHERE id_nilai = ?";
            var stmt = prepare(sql);
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet NilaiSiswa() {
        String sql = "SELECT users.id_user, users.nama, nilai.id_nilai, nilai.mapel, nilai.nilai "
                + "FROM nilai "
                + "JOIN users ON nilai.id_user = users.id_user";
        try {
            // JANGAN pakai try(stmt) di sini, pakai try biasa saja
            // agar koneksi tidak tertutup otomatis sebelum dibaca Controller
            PreparedStatement stmt = prepare(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Kembalikan null jika error
        }
    }

    public ResultSet NilaiSiswaSelf(int id_user) {
        String sql = "SELECT users.id_user, users.nama,nilai.id_nilai, nilai.mapel, nilai.nilai "
                + "FROM nilai "
                + "JOIN users ON nilai.id_user = users.id_user "
                + "WHERE nilai.id_user = ?"; // Filter berdasarkan ID login
        try {
            PreparedStatement stmt = prepare(sql);
            stmt.setInt(1, id_user);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet NilaiById(int id_nilai) {
        String sql = "SELECT users.id_user, users.nama,nilai.id_nilai, nilai.mapel, nilai.nilai "
                + "FROM nilai "
                + "JOIN users ON nilai.id_user = users.id_user "
                + "WHERE nilai.id_nilai = ?"; // Filter berdasarkan ID nilai
        try {
            PreparedStatement stmt = prepare(sql);
            stmt.setInt(1, id_nilai);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet Nilai(){ 
        String sql = "SELECT * FROM nilai";

        try {
            PreparedStatement stmt = prepare(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Kembalikan null jika error
        }
    }
}
