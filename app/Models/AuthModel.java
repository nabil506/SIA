package app.Models;

import app.App.Database;

public class AuthModel extends Database {
    public boolean login(String username, String password) {
        try {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            var stmt = prepare(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            var rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
