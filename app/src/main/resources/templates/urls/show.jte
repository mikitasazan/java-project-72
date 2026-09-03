@import hexlet.code.model.Url
@param Url url
@param String flash = null

@template.layout.main(
    flash = flash,
    content = @`
        <h1 class="mb-6 text-3xl font-light">Сайт: ${url.getName()}</h1>
        <table data-test="url" class="border-collapse text-left">
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
    `
)
