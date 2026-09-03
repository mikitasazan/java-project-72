package hexlet.code;

import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import io.javalin.testtools.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.http.HttpClient;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    private Javalin app;

    private static HttpClient buildFollowRedirectsClient() {
        var cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        return HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .cookieHandler(cookieManager)
                .build();
    }

    private static final TestConfig FOLLOW_REDIRECTS = new TestConfig(
            true,
            true,
            buildFollowRedirectsClient());

    @BeforeEach
    void setUp() throws Exception {
        app = App.getApp();
    }

    @Test
    void testIndex() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    void testCreateUrl() {
        JavalinTest.test(app, FOLLOW_REDIRECTS, (server, client) -> {
            var response = client.post("/urls", "url=https://ru.hexlet.io/path?x=1");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://ru.hexlet.io");
            assertThat(response.body().string()).contains("Страница успешно добавлена");

            var saved = UrlRepository.findByName("https://ru.hexlet.io");
            assertThat(saved).isPresent();
        });
    }

    @Test
    void testCreateUrlWithPort() {
        JavalinTest.test(app, FOLLOW_REDIRECTS, (server, client) -> {
            var response = client.post("/urls", "url=https://ru.hexlet.io:8080/path");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://ru.hexlet.io:8080");
        });
    }

    @Test
    void testCreateInvalidUrl() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/urls", "url=not a url");
            assertThat(response.code()).isEqualTo(422);
            assertThat(response.body().string()).contains("Некорректный URL");
        });
    }

    @Test
    void testCreateDuplicateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var first = client.post("/urls", "url=https://ru.hexlet.io");
            assertThat(first.code()).isEqualTo(302);
            var firstLocation = first.headers().get("Location").get(0);

            var second = client.post("/urls", "url=https://ru.hexlet.io");
            assertThat(second.code()).isEqualTo(302);
            var secondLocation = second.headers().get("Location").get(0);

            assertThat(secondLocation).isEqualTo(firstLocation);
            assertThat(UrlRepository.getEntities()).hasSize(1);
        });
    }

    @Test
    void testUrlsList() {
        JavalinTest.test(app, (server, client) -> {
            client.post("/urls", "url=https://ru.hexlet.io");

            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://ru.hexlet.io");
            assertThat(response.body().string()).contains("data-test=\"urls\"");
        });
    }

    @Test
    void testUrlShow() {
        JavalinTest.test(app, FOLLOW_REDIRECTS, (server, client) -> {
            client.post("/urls", "url=https://ru.hexlet.io");
            var saved = UrlRepository.findByName("https://ru.hexlet.io").orElseThrow();

            var response = client.get("/urls/" + saved.getId());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("data-test=\"url\"");
        });
    }

    @Test
    void testUrlShowNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }
}
