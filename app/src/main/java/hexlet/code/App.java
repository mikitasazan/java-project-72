package hexlet.code;

import io.javalin.Javalin;

public class App {

    public static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    public static Javalin getApp() {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.routes.get("/", ctx -> ctx.result("Hello World"));
        });

        return app;
    }

    public static void main(String[] args) {
        var app = getApp();
        app.start(getPort());
    }
}
