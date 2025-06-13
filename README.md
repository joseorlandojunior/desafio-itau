Este é um projeto desenvolvido para um desafio de desenvolvedor de software no Itaú;
A aplicação foi desenvolvida em Java v21 com SpringBoot3. Usa Gralde na versã 8.14 como gerenciador de dependências; 

<h1>Banco de dados</h1>
<h2>PostgreSQL</h2>
O banco de dados escolhido foi postgresSQL por ser altamente robusto, escalavel verticalmente e horizontalmente(com algumas limitações, mas é possível usar a estrategia de sharding e read replicas), open source
e com alta consistencia, pois é ACID compliance.

<h1>Nginx</h1>
A aplicação esta usando nginx como balanceador de carga para orquestrar as chamadas entre as 3 instancias que estão configuradas no arquivo <code>docker-stack.yml</code>. Este arquivo é a configuração de toda a stack da aplicação
que usa o docker swarm.

<h1>Docker-swarm</h1>
A escolha do docker-swarm foi feita com base em alguns tradeoffs importantes: 

<h3>Alta disponibilidade</h3>
Com Swarm, é possível rodar múltiplas réplicas do serviço em diferentes máquinas. Se uma falhar, outra assume automaticamente, garantindo e aumentando a disponibilidade da aplicação.

<h3>Escalabilidade</h3>
É possível aumentar ou diminuir o número de instancias de forma simples, usando comandos como <code>docker service scale</code> ou até mesmo modificar a quantidade de instancias no arquivo <code>docker-stack.yml</code>

<h3>Gerenciamento simplificado</h3>
É possível gerenciar todo o cluster de forma centralizada usando comandos do docker padrão. 

<h3>Rolling Updates</h3>
É possível atualizar a aplicação sem downtime, atualizando uma réplica de cada vez e garantindo que o serviço continue disponível durante o processo.

//ORientações gerais spbre a app
//adicionar imagem da proposta de estrutura da aplicação
//imagem dos logs junto da oriegnação de como ve-lo
