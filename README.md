Alunos: Edson Pimenta de Almeida, Luís Augusto Starling Toledo, Luiz Gabriel Milione Assis

O TP 1 tem como objetivo implementar a criação de um CRUD genérico, que consegue criar, ler, modificar e deletar registros de qualquer entidade dado um arquivo.
Nossa implementação possui as classes Arquivo, HashExtensivel, ParIDEndereco, Main e Tarefa. Há também as interfaces Registro e RegistroHashExtensivel.
Interface Registro: Informa que as classes implementarão os métodos setId, getId, toByteArray e fromByteArray


Classe Arquivo: A classe é a responsavel por fazer o CRUD dos objetos de tipo genérico T que devem extender a interface Registro. Possui como atributos um int representando o tamanho do cabeçalho, um RandomAccessFile arquivo, que será o arquivo a ser manipulado, uma String nome_arquivo com o nome do arquivo a ser manipulado, um Constructor construtor que será o construtor da classe T e uma HashExtensivel indice_direto que será um arqsuivo com índices diretos.

O construtor da classe recebe uma String e um construtor que serão usados para abrir ou criar o arquivo RandomAccessFile e o construtor da classe T. Além disso ela verifica se o arquivo já possui dados, caso não possua ela escreve o cabeçalho com o número 0.

O método create recebe um objeto da classe T e retorna seu ID. O método busca no cabeçalho o último ID usado no banco e atribui o número mais 1 como ID do objeto passado. No final do arquivo o método escreve o byte que marca o registro como válido, um short com o tamanho do registro e todo o registro na forma de array de bytes. Cria uma entrada no índice direto e retorna o ID.

O método read recebe o id do objeto a ser lido e retorna um objeto ou null. O método recupera o endereço do registro a partir do índice direto e faz a leitura no arquivo caso o id exista e a lápide esteja válida. Caso uma dessas condições seja falsa, retorna null.

O método delete recebe a id do objeto a ser excluido e retorna verdadeiro ou falso. O método recupera o endereço no arquivo a partir do índice direto e verifica o id, atribuindo a lápide como inválida e retorna verdadeiro caso o id seja o mesmo. Caso não ache o id retorna falso.

O método update recebe um objeto e retorna verdadeiro ou falso. O método recupera o id do objeto a partir do índice direto e verifica sua lápide. Caso o id não exista ou a lápide seja inválida, retorna falso. Caso contrário verifica o tamanho do registro no arquivo e compara com o tamanho do registro atualizado. Caso o atualizado seja menor ou do mesmo tamanho, o registro é sobrescrito no arquivo. Caso contrário o registro é marcado como inválido, e o atualizado é escrito no final, com sua lápide e tamanho. O endereço na tabela direta é atualizado

O método close fecha o arquivo.


A classe Tarefa representa uma tarefa que possúi nome (String), data de criação e conlusão (LocalDate), status (String) e prioridade (byte). Possui construtores vazio, com todos os parâmetros exeto id e completo. Possuí getters e setters além de toByteArray, fromByteArray e toString.


A classe ParIDEndereço implementa a interface RegistroHashExtensivel e possuí um int id e um long endereço. Possuí construtor, getters e setters além dos métodos toByteArray e fromByteArray


A classe HashExtensivel possui como objetivo ser um índice direto para objetos T que implementem a interface RegistroHashExtensivel. Seus métodos create, read, update e delete devem receber ou retornar pares de informações sobre ids e seus respectivos endereços em um arquivo.

A classe Main possui o método main do programa e é responsável por testar se tudo está funcionando como o planejado. Cria as tarefas, os arquivos tarefas.bd, hash_c.bd e hash_d.bd, cria as tarefas no banco de dados, faz a leitura, alteração e deleção e imprime tudo no console para a verificação do usuário.

--------------------------------------------------------------------------------------------------------------------------

Relato dos alunos: Todos os requisitos foram implementados sem problemas ou dificuldades. Os resultados foram alcançados.

O trabalho possui um índice direto implementado com a tabela hash extensível? SIM
A operação de inclusão insere um novo registro no fim do arquivo e no índice e retorna o ID desse registro? SIM
A operação de busca retorna os dados do registro, após localizá-lo por meio do índice direto? SIM
A operação de alteração altera os dados do registro e trata corretamente as reduções e aumentos no espaço do registro? SIM
A operação de exclusão marca o registro como excluído e o remove do índice direto? SIM
O trabalho está funcionando corretamente? SIM
O trabalho está completo? SIM
O trabalho é original e não a cópia de um trabalho de outro grupo? SIM
