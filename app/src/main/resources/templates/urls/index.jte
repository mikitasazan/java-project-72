@import java.util.List
@import hexlet.code.model.Url
@param List<Url> urls
@param String flash = null

@template.layout.main(
    flash = flash,
    content = @`
        <h1 class="mb-6 text-3xl font-light">Сайты</h1>
        <table data-test="urls" class="w-full border-collapse text-left">
            <thead>
                <tr class="border-b border-gray-200">
                    <th class="py-2 pr-4">ID</th>
                    <th class="py-2 pr-4">Имя</th>
                    <th class="py-2 pr-4">Последняя проверка</th>
                    <th class="py-2">Код ответа</th>
                </tr>
            </thead>
            <tbody>
                @for(Url url : urls)
                    <tr class="border-b border-gray-100">
                        <td class="py-2 pr-4">${url.getId()}</td>
                        <td class="py-2 pr-4">
                            <a class="text-blue-600 hover:underline" href="/urls/${url.getId()}">${url.getName()}</a>
                        </td>
                        <td class="py-2 pr-4">
                            @if(url.getLastCheckCreatedAt() != null)
                                ${url.getLastCheckCreatedAt().toLocalDate().toString()}
                            @endif
                        </td>
                        <td class="py-2">
                            @if(url.getLastCheckStatusCode() != null)
                                ${url.getLastCheckStatusCode()}
                            @endif
                        </td>
                    </tr>
                @endfor
            </tbody>
        </table>
    `
)
