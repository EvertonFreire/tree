# Dependencies

Dependências necessárias

* Java SE 8
* Mariadb-server

# How to

Logo após configurar os dados do banco de dados basta iniciar a api com o java:

Caso utilize linux pode ser utilizado o comando abaixo, onde $file_path é o local onde o arquivo jar está armazenado:

java -jar $file_path

Para o programa executar é aconselhável utilizar uma instância volátil do mysql, por exemplo o docker com o container mysql e atribuir a senha root para ele.

Caso seja utilizado outra maneira de disponibilizar o mysql, criar a tabela: *restapi*  e com a as permissões necessárias para acesso de usuário ao mesmo.
Assim também se faz necessário realizar a alteração do usuário de acesso ao banco e da sua respectiva senha, as quais se encontram em:
*tree/src/main/resources/application.properties*

# Run

Para realizar as operações de CRUD de cada um dos nós, segue-se abaixo o formato de request e o responde que será informado:

* Inserção de nó na árvore - Requisição POST com endereço: http://localhost:8080/node, inserindo os campos abaixo no formato JSON.
  
  *Ex: Method= POST, URL= http://localhost:8080/node, JSON= {	"code": "root",	"detail": "root node",	"description": "this is only a node of the main tree",	"parentId": "1"}*

* Atualização de nó da árvore - Requisição PUT com endereço: http://localhost:8080/node, inserindo os campos abaixo no formato JSON.
  
  *Ex: Method= PUT, URL= http://localhost:8080/node, JSON= {	"code": "node",	"detail": "it is only a node",	"description": "I don't have description for that node.",	"parentId": "1", "id": 10}*

* Remoção de nó da árvore - Requisição POST com endereço: http://localhost:8080/node/${id}
  
  *Ex: Method= POST, URL= http://localhost:8080/node/1*

* Busca por somente pelos filhos de um nó e se eles tem filhos - Requisição GET com endereço: http://localhost:8080/node/{parentId}
  
  *Ex: Method= GET, URL= http:localhost:8080/node/3*

* Apresentação de toda a árvore - Requisição GET com endereço: http://localhost:8080/node
  
  *Ex: Method= GET, URL= http://localhost:8080/node*


# Links

Para acessar aos files Jar e WAR, é preciso baixar os arquivos pelo GDrive com o link abaixo:

https://drive.google.com/open?id=1u5pNPu76xiD8pmWQGCOIjf5Y5b7bzxBJ

Para utilizar diretamente as opções rest da API utilize o site:
www.doler.com.br:8080/


