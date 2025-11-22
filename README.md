# EasyRecipes APP
EasyRecipes é um aplicativo Android que permite aos usuários explorar receitas em tempo real, obtendo informações diretamente da API [Spoonacular](https://spoonacular.com/food-api). Com uma interface amigável, os usuários podem visualizar detalhes de receitas, incluindo ingredientes e muito mais.

## Funcionalidades
- Receitas aleatórias: Explore receitas de forma aleatória para descobrir novas maneiras de cozinhar.
- Detalhes da receita: Obtenha informações detalhadas sobre cada receita, como título, e sumário.
- Pesquisa por receitas: Não encontrou o que estava buscando? Não tem problema, faça uma busca para obter exatamente o que precisa.

## 🧩 Arquitetura
O projeto segue o padrão **MVVM (Model-View-ViewModel)** junto com o **Repository Pattern**, garantindo uma melhor separação de responsabilidades e facilidade de manutenção.  
Além disso, o app utiliza uma abordagem **Offline First**, permitindo que os dados sejam armazenados localmente para acesso mesmo sem conexão.

## 🧪 Testes
O projeto inclui testes unitários utilizando coroutines test, Flow testing e Fake Services para simular comportamentos da API, garantindo previsibilidade e isolamento durante os testes.

Exemplos de cenários testados:

- Quando a resposta da API é um sucesso, o ViewModel retorna o estado da UI corretamente

- Quando ocorre erro no serviço, o estado de erro é emitido

## :camera_flash: Screenshots
<!-- You can add more screenshots here if you like -->
<img src="https://github.com/user-attachments/assets/689adb1c-e3b1-4b3f-876b-370810793b4e" width=180/> 
<img src="https://github.com/user-attachments/assets/a5031e70-01b0-42d3-9947-245be4ee4af2" width=180/>
<img src="https://github.com/user-attachments/assets/cfd3d23f-5e2d-421d-9e5f-c6fc696c592a" width=180/>
<img src="https://github.com/user-attachments/assets/3b8e6bf6-bf44-4076-ad88-f48daa782423" width=180/>
<img src="https://github.com/user-attachments/assets/126b0dc9-1e16-4665-8a4e-52ce79f26c77" width=180/>





## 🛠️ Tecnologias
- **Kotlin 100%**
- **Jetpack Compose**
  - Column, Row, Modifier, Spacer, LazyColumn, OutlineTextField, ComposePreview, NavHostController, AsyncImage
- **API**
  - Retrofit, OkHttp3, Query, Path
- **Arquitetura**
  - MVVM
  - Repository Pattern
  - Offline First (Cache local com Room)

## Documentação API
[Postman](https://www.postman.com/spoonacular-api/)
## License
```
The MIT License (MIT)

Copyright (c) 2025 Marcus Vinícius de Sá Pereira

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
