### Alunos
- Edson Pimenta de Almeida
- Luís Augusto Starling Toledo
- Luiz Gabriel Milione Assis

### Objetivo do TP 1
Implementar a criação de um CRUD genérico capaz de criar, ler, modificar e deletar registros de qualquer entidade, dado um arquivo.

### Implementação

#### Classes e Interfaces
- **Arquivo**
- **HashExtensivel**
- **ParIDEndereco**
- **Main**
- **Tarefa**

Além disso, há as interfaces:
- **Registro**
- **RegistroHashExtensivel**

#### Interface Registro
Define que as classes devem implementar os seguintes métodos:
- `setId`
- `getId`
- `toByteArray`
- `fromByteArray`

#### Classe Arquivo
Responsável pelo CRUD de objetos do tipo genérico `T`, que devem estender a interface `Registro`.

##### Atributos
- `int` representando o tamanho do cabeçalho.
- `RandomAccessFile arquivo`: o arquivo a ser manipulado.
- `String nome_arquivo`: nome do arquivo a ser manipulado.
- `Constructor construtor`: construtor da classe `T`.
- `HashExtensivel indice_direto`: arquivo com índices diretos.

##### Métodos
- **Construtor:** Recebe uma `String` e um `Constructor` para abrir ou criar o arquivo `RandomAccessFile` e o construtor da classe `T`. Inicializa o cabeçalho com o número 0, se necessário.
- **`create`**: Recebe um objeto `T`, atribui um novo ID, escreve o registro no arquivo e o índice direto. Retorna o ID.
- **`read`**: Recebe o ID e retorna o objeto ou `null`, verificando a validade da lápide.
- **`delete`**: Recebe o ID e marca o registro como inválido, retornando verdadeiro ou falso.
- **`update`**: Atualiza o registro no arquivo, comparando o tamanho do novo registro com o original. Se o tamanho for maior, o novo registro é escrito no final.
- **`close`**: Fecha o arquivo.

#### Classe Tarefa
Representa uma tarefa com os seguintes atributos:
- **Nome** (`String`)
- **Data de criação e conclusão** (`LocalDate`)
- **Status** (`String`)
- **Prioridade** (`byte`)

##### Métodos
Possui construtores, getters, setters, e os métodos:
- `toByteArray`
- `fromByteArray`
- `toString`

#### Classe ParIDEndereco
Implementa a interface `RegistroHashExtensivel`, com os atributos:
- **`int id`**
- **`long endereço`**

##### Métodos
- Construtores, getters, setters, e os métodos:
  - `toByteArray`
  - `fromByteArray`

#### Classe HashExtensivel
Serve como índice direto para objetos `T` que implementem a interface `RegistroHashExtensivel`.

##### Métodos
- `create`
- `read`
- `update`
- `delete`

#### Classe Main
Responsável por testar a aplicação. Cria as tarefas, manipula os arquivos `tarefas.bd`, `hash_c.bd` e `hash_d.bd`, realiza operações de CRUD e imprime os resultados no console.

---

### Relato dos Alunos
- Todos os requisitos foram implementados sem problemas ou dificuldades.
- Os resultados foram alcançados.

#### Perguntas
- O trabalho possui um índice direto implementado com a tabela hash extensível? **SIM**
- A operação de inclusão insere um novo registro no fim do arquivo e no índice, retornando o ID? **SIM**
- A operação de busca retorna os dados do registro após localizá-lo pelo índice direto? **SIM**
- A operação de alteração trata corretamente reduções e aumentos no espaço do registro? **SIM**
- A operação de exclusão marca o registro como excluído e o remove do índice direto? **SIM**
- O trabalho está funcionando corretamente? **SIM**
- O trabalho está completo? **SIM**
- O trabalho é original e não é cópia de outro grupo? **SIM**
