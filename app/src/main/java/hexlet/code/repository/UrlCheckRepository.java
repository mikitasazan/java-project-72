package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlCheckRepository extends BaseRepository {

    public static void save(UrlCheck check) throws SQLException {
        check.setCreatedAt(LocalDateTime.now());
        var sql = "INSERT INTO url_checks (url_id, status_code, h1, title, description, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, check.getUrlId());
            stmt.setInt(2, check.getStatusCode());
            stmt.setString(3, check.getH1());
            stmt.setString(4, check.getTitle());
            stmt.setString(5, check.getDescription());
            stmt.setTimestamp(6, Timestamp.valueOf(check.getCreatedAt()));
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    check.setId(keys.getLong(1));
                }
            }
        }
    }

    public static List<UrlCheck> findByUrlId(Long urlId) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY id DESC";
        var result = new ArrayList<UrlCheck>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    result.add(buildUrlCheck(resultSet));
                }
            }
        }
        return result;
    }

    public static Optional<UrlCheck> findLatestByUrlId(Long urlId) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY id DESC LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(buildUrlCheck(resultSet));
                }
                return Optional.empty();
            }
        }
    }

    private static UrlCheck buildUrlCheck(ResultSet resultSet) throws SQLException {
        var check = new UrlCheck();
        check.setId(resultSet.getLong("id"));
        check.setUrlId(resultSet.getLong("url_id"));
        check.setStatusCode(resultSet.getInt("status_code"));
        check.setH1(resultSet.getString("h1"));
        check.setTitle(resultSet.getString("title"));
        check.setDescription(resultSet.getString("description"));
        var createdAt = resultSet.getTimestamp("created_at");
        if (createdAt != null) {
            check.setCreatedAt(createdAt.toLocalDateTime());
        }
        return check;
    }
}
