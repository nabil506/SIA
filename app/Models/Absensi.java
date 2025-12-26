package app.Models;

import app.App.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Absensi extends Database {

    public static class DataAbsensi {

        private int id_absensi;
        private int id_user;
        private String nama;
        private String tanggal;
        private String status;

        public int getId_absensi() {
            return id_absensi;
        }

        public void setId_absensi(int id_absensi) {
            this.id_absensi = id_absensi;
        }

        public int getId_user() {
            return id_user;
        }

        public void setId_user(int id_user) {
            this.id_user = id_user;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getTanggal() {
            return tanggal;
        }

        public void setTanggal(String tanggal) {
            this.tanggal = tanggal;
        }

        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
    }

    public boolean InsertAbsensiSiswa(DataAbsensi dto) {
        try {
            String sql = "INSERT INTO absensi (id_user,nama, tanggal, status) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = prepare(sql);
            stmt.setInt(1, dto.getId_user());
            stmt.setString(2, dto.getNama());
            stmt.setString(3, dto.getTanggal());
            stmt.setString(4, dto.getStatus());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdateAbsensiSiswa(DataAbsensi dto) {
        try {
            String sql = "UPDATE absensi SET tanggal = ?, status = ? WHERE id_absensi = ?";
            PreparedStatement stmt = prepare(sql);
            stmt.setString(1, dto.getTanggal());
            stmt.setString(2, dto.getStatus());
            stmt.setInt(3, dto.getId_absensi());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean DeleteAbsensiSiswa(int id_absensi) {
        try {
            String sql = "DELETE FROM absensi WHERE id_absensi = ?";
            PreparedStatement stmt = prepare(sql);
            stmt.setInt(1, id_absensi);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet AbsenSiswaById(int id_absensi) {
        String sql = "SELECT absensi.id_absensi, users.id_user, users.nama, absensi.tanggal, absensi.status "
                + "FROM absensi "
                + "JOIN users ON absensi.id_user = users.id_user "
                + "WHERE absensi.id_absensi = ?";
        try {
            PreparedStatement stmt = prepare(sql);
            stmt.setInt(1, id_absensi);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Kembalikan null jika error
        }

    }

    public ResultSet AbsenSiswaSelf(int id_user) {
        String sql = "SELECT users.id_user, users.nama, absensi.tanggal, absensi.status "
                + "FROM absensi "
                + "JOIN users ON absensi.id_user = users.id_user "
                + "WHERE absensi.id_user = ?"; // Tambahkan filter ini
        try {
            PreparedStatement stmt = prepare(sql);
            stmt.setInt(1, id_user);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet AllAbsensi(){
        String sql = "SELECT * FROM absensi";

        try {
            PreparedStatement stmt = prepare(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Kembalikan null jika error
        }
    }

}
