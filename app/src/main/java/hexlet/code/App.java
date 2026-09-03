package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.model.Url;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class App {

    public static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    public static String getDatabaseUrl() {
        return System.getenv().getOrDefault(
                "JDBC_DATABASE_URL",
                "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;");
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    private static String readResourceFile(String fileName) throws IOException {
        try (InputStream inputStream = App.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + fileName);
            }
            try (BufferedReader reader =
                    new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        }
    }

    // Keeps only scheme + host (+ port, if explicit) — the address we store for a page.
    private static String normalizeUrl(String rawUrl) throws Exception {
        var uri = new URI(rawUrl);
        var url = uri.toURL();
        var port = url.getPort();
        var normalized = url.getProtocol() + "://" + url.getHost();
        if (port != -1) {
            normalized += ":" + port;
        }
        return normalized;
    }

    public static Javalin getApp() throws IOException, SQLException {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getDatabaseUrl());
        var dataSource = new HikariDataSource(hikariConfig);

        var sql = readResourceFile("schema.sql");
        var connection = dataSource.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.setConn(connection);

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));

            config.staticFiles.add(staticFiles -> {
                staticFiles.directory = "/static";
                staticFiles.location = Location.CLASSPATH;
            });

            config.routes.get("/", ctx -> ctx.render("index.jte"));

            config.routes.post("/urls", ctx -> {
                var rawUrl = ctx.formParam("url");
                String normalized;
                try {
                    normalized = normalizeUrl(rawUrl == null ? "" : rawUrl);
                } catch (Exception e) {
                    ctx.status(422);
                    ctx.render("index.jte", Map.of("flash", "Некорректный URL"));
                    return;
                }

                var existing = UrlRepository.findByName(normalized);
                if (existing.isPresent()) {
                    ctx.sessionAttribute("flash", "Страница уже существует");
                    ctx.redirect("/urls/" + existing.get().getId());
                    return;
                }

                var url = new Url(normalized);
                UrlRepository.save(url);
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
                ctx.redirect("/urls/" + url.getId());
            });

            config.routes.get("/urls", ctx -> {
                var urls = UrlRepository.getEntities();
                ctx.render("urls/index.jte", Map.of("urls", urls));
            });

            config.routes.get("/urls/{id}", ctx -> {
                var id = ctx.pathParamAsClass("id", Long.class).get();
                var url = UrlRepository.find(id)
                        .orElseThrow(() -> new NotFoundResponse("Url with id = " + id + " not found"));

                var flash = (String) ctx.consumeSessionAttribute("flash");
                Map<String, Object> model = new HashMap<>();
                model.put("url", url);
                if (flash != null) {
                    model.put("flash", flash);
                }
                ctx.render("urls/show.jte", model);
            });
        });

        return app;
    }

    public static void main(String[] args) throws IOException, SQLException {
        var app = getApp();
        app.start(getPort());
    }
}
