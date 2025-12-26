package app.Models;

import app.App.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Biodata extends Database {

    public static class userdata {

        private int id_user;
        private int id_bio;
        private String nama;
        private String kategori;
        private String nip;
        private String nis;
        private String tgl_lahir;
        private String alamat;
        private String no_hp;
        private String nama_ibu;
        private String jenis_kelamin;

        // Getter dan Setter sebagai pintu akses data (Encapsulation)
        public int getId_user() {
            return id_user;
        }

        public void setId_user(int id_user) {
            this.id_user = id_user;
        }

        public int getId_bio() {
            return id_bio;
        }

        public void setId_bio(int id_bio) {
            this.id_bio = id_bio;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getKategori() {
            return kategori;
        }

        public void setKategori(String kategori) {
            this.kategori = kategori;
        }

        public String getNip() {
            return nip;
        }

        public void setNip(String nip) {
            this.nip = nip;
        }

        public String getNis() {
            return nis;
        }

        public void setNis(String nis) {
            this.nis = nis;
        }

        public String getTgl_lahir() {
            return tgl_lahir;
        }

        public void setTgl_lahir(String tgl_lahir) {
            this.tgl_lahir = tgl_lahir;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getNo_hp() {
            return no_hp;
        }

        public void setNo_hp(String no_hp) {
            this.no_hp = no_hp;
        }

        public String getNama_ibu() {
            return nama_ibu;
        }

        public void setNama_ibu(String nama_ibu) {
            this.nama_ibu = nama_ibu;
        }

        public String getJenis_kelamin() {
            return jenis_kelamin;
        }

        public void setJenis_kelamin(String jenis_kelamin) {
            this.jenis_kelamin = jenis_kelamin;
        }
    }

    public boolean CreateBiodataSiswa(userdata dto) {

// 1. Ambil ID User terbaru dari tabel users
        try {

            String sql = "INSERT INTO biodata (id_user, nama, kategori, nis, tgl_lahir, alamat, no_hp, nama_ibu, jenis_kelamin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            var stmt = prepare(sql);
            stmt.setInt(1, dto.getId_user());
            stmt.setString(2, dto.getNama());
            stmt.setString(3, dto.getKategori());
            stmt.setString(4, dto.getNis());
            stmt.setString(5, dto.getTgl_lahir());
            stmt.setString(6, dto.getAlamat());
            stmt.setString(7, dto.getNo_hp());
            stmt.setString(8, dto.getNama_ibu());
            stmt.setString(9, dto.getJenis_kelamin());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet ReadBiodataSiswa() {
        String sql = "SELECT users.id_user, users.nama, biodata.id_bio, biodata.kategori, biodata.nis, biodata.tgl_lahir, "
                + "biodata.alamat, biodata.no_hp, biodata.nama_ibu, biodata.jenis_kelamin "
                + "FROM biodata JOIN users ON biodata.id_user = users.id_user "
                + "WHERE users.id_role = '2' AND biodata.kategori = 'siswa' ";
        try {
            PreparedStatement stmt = prepare(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Kembalikan null jika error
        }
    }

    public boolean UpdateBiodataSiswa(userdata dto) {

        try {
            String sql = "UPDATE biodata SET nama = ?,kategori = ?,nis = ?,tgl_lahir = ?,alamat = ?,no_hp = ?,nama_ibu = ?,jenis_kelamin = ? WHERE id_bio = ?";
            var stmt = prepare(sql);
            stmt.setString(1, dto.getNama());
            stmt.setString(2, dto.getKategori());
            stmt.setString(3, dto.getNis());
            stmt.setString(4, dto.getTgl_lahir());
            stmt.setString(5, dto.getAlamat());
            stmt.setString(6, dto.getNo_hp());
            stmt.setString(7, dto.getNama_ibu());
            stmt.setString(8, dto.getJenis_kelamin());
            stmt.setInt(9, dto.getId_bio());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean DeleteBiodataSiswa(int id) {
        try {
            String sql = "DELETE FROM biodata WHERE id_bio = ?";
            var stmt = prepare(sql);
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //input data guru
    public boolean CreateBiodataGuru(userdata dto) {

        try {
            String sql = "INSERT INTO biodata (id_user, nama, kategori, nip, tgl_lahir, alamat, no_hp, nama_ibu, jenis_kelamin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            var stmt = prepare(sql);
            stmt.setInt(1, dto.getId_user());
            stmt.setString(2, dto.getNama());
            stmt.setString(3, dto.getKategori());
            stmt.setString(4, dto.getNip());
            stmt.setString(5, dto.getTgl_lahir());
            stmt.setString(6, dto.getAlamat());
            stmt.setString(7, dto.getNo_hp());
            stmt.setString(8, dto.getNama_ibu());
            stmt.setString(9, dto.getJenis_kelamin());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet ReadBiodataGuru() {

        String sql = "SELECT users.id_user, users.nama,biodata.id_bio, biodata.kategori, biodata.nip, biodata.tgl_lahir, "
                + "biodata.alamat,biodata.no_hp, biodata.nama_ibu, biodata.jenis_kelamin "
                + "FROM biodata JOIN users ON biodata.id_user = users.id_user "
                + " WHERE users.id_role = '1'";
        try {
            PreparedStatement stmt = prepare(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean UpdateBiodataGuru(userdata dto) {

        try {
            String sql = "UPDATE biodata SET nama = ?,kategori = ?,tgl_lahir = ?,alamat = ?,no_hp = ?,nama_ibu = ?,jenis_kelamin = ? WHERE id_bio = ?";
            var stmt = prepare(sql);
            stmt.setString(1, dto.getNama());
            stmt.setString(2, dto.getKategori());
            stmt.setString(3, dto.getTgl_lahir());
            stmt.setString(4, dto.getAlamat());
            stmt.setString(5, dto.getNo_hp());
            stmt.setString(6, dto.getNama_ibu());
            stmt.setString(7, dto.getJenis_kelamin());
            stmt.setInt(8, dto.getId_bio());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean DeleteBiodataGuru(int id) {
        try {
            String sql = "DELETE FROM biodata WHERE id_bio = ?";
            var stmt = prepare(sql);
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet Biodata() {
        String sql = "SELECT biodata.*, users.id_role "
                + "FROM biodata "
                + "JOIN users ON biodata.id_user = users.id_user";
        try {
            PreparedStatement stmt = prepare(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Kembalikan null jika error
        }
    }

    public boolean isBiodataExists(int id_user) {
        String sql = "SELECT id_user FROM biodata WHERE id_user = ?";
        try {
            var stmt = prepare(sql);
            stmt.setInt(1, id_user);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Mengembalikan true jika data sudah ada
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet ReadBiodataSelf(int id) {
        String sql = "SELECT * FROM biodata WHERE id_user = ?";
        try {
            var stmt = prepare(sql);
            stmt.setInt(1, id);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Fungsi ini melakukan "Mapping" dari ResultSet ke Objek DTO
    private userdata map(ResultSet rs) throws SQLException {
        userdata dto = new userdata();
        dto.setId_user(rs.getInt("id_user"));
        dto.setNip(rs.getString("nip"));
        dto.setNis(rs.getString("nis"));

        return dto;
    }

}
