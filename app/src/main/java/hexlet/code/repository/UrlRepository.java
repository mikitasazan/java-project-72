package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {

    public static void save(Url url) throws SQLException {
        url.setCreatedAt(LocalDateTime.now());
        var sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, url.getName());
            stmt.setTimestamp(2, Timestamp.valueOf(url.getCreatedAt()));
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    url.setId(keys.getLong(1));
                }
            }
        }
    }

    public static Optional<Url> find(Long id) throws SQLException {
        var sql = "SELECT * FROM urls WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(buildUrl(resultSet));
                }
                return Optional.empty();
            }
        }
    }

    public static Optional<Url> findByName(String name) throws SQLException {
        var sql = "SELECT * FROM urls WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(buildUrl(resultSet));
                }
                return Optional.empty();
            }
        }
    }

    public static List<Url> getEntities() throws SQLException {
        var sql = "SELECT * FROM urls ORDER BY created_at DESC";
        var result = new ArrayList<Url>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                result.add(buildUrl(resultSet));
            }
        }
        return result;
    }

    private static Url buildUrl(ResultSet resultSet) throws SQLException {
        var url = new Url();
        url.setId(resultSet.getLong("id"));
        url.setName(resultSet.getString("name"));
        var createdAt = resultSet.getTimestamp("created_at");
        if (createdAt != null) {
            url.setCreatedAt(createdAt.toLocalDateTime());
        }
        return url;
    }
}
