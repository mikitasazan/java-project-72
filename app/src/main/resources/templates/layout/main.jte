@import gg.jte.Content
@param Content content
@param String flash = null

<!doctype html>
<html lang="ru">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Анализатор страниц</title>
        <link rel="stylesheet" href="/app.css">
    </head>
    <body class="bg-white text-gray-900">
        <nav class="border-b border-gray-200">
            <div class="mx-auto max-w-5xl px-4 py-4">
                <a href="/" class="text-xl font-semibold">Анализатор страниц</a>
            </div>
        </nav>
        <main class="mx-auto max-w-5xl px-4 py-10">
            @if(flash != null)
                <div role="alert" class="mb-6 rounded border border-blue-200 bg-blue-50 px-4 py-3 text-blue-800">${flash}</div>
            @endif
            ${content}
        </main>
    </body>
</html>
