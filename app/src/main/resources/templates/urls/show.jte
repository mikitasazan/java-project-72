@import java.util.List
@import hexlet.code.model.Url
@import hexlet.code.model.UrlCheck
@param Url url
@param List<UrlCheck> checks
@param String flash = null

@template.layout.main(
    flash = flash,
    content = @`
        <h1 class="mb-6 text-3xl font-light">Сайт: ${url.getName()}</h1>
        <table data-test="url" class="mb-10 border-collapse text-left">
            <tbody>
                <tr class="border-b border-gray-100">
                    <td class="py-2 pr-6 font-medium">ID</td>
                    <td class="py-2">${url.getId()}</td>
                </tr>
                <tr class="border-b border-gray-100">
                    <td class="py-2 pr-6 font-medium">Имя</td>
                    <td class="py-2">${url.getName()}</td>
                </tr>
                <tr>
                    <td class="py-2 pr-6 font-medium">Дата создания</td>
                    <td class="py-2">${url.getCreatedAt().toLocalDate().toString()}</td>
                </tr>
            </tbody>
        </table>

        <form method="post" action="/urls/${url.getId()}/checks" class="mb-6">
            <input type="submit"
                class="cursor-pointer rounded bg-blue-600 px-6 py-2 text-lg font-medium text-white uppercase hover:bg-blue-700"
                value="Запустить проверку">
        </form>

        <h2 class="mb-4 text-2xl font-light">Проверки</h2>
        <table data-test="checks" class="w-full border-collapse text-left">
            <thead>
                <tr class="border-b border-gray-200">
                    <th class="py-2 pr-4">ID</th>
                    <th class="py-2 pr-4">Код ответа</th>
                    <th class="py-2 pr-4">Дата создания</th>
                </tr>
            </thead>
            <tbody>
                @for(UrlCheck check : checks)
                    <tr class="border-b border-gray-100">
                        <td class="py-2 pr-4">${check.getId()}</td>
                        <td class="py-2 pr-4">${check.getStatusCode()}</td>
                        <td class="py-2 pr-4">${check.getCreatedAt().toLocalDate().toString()}</td>
                    </tr>
                @endfor
            </tbody>
        </table>
    `
)
