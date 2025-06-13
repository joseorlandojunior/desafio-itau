Este é um projeto desenvolvido para um desafio de desenvolvedor de software no Itaú;</br>
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

<h1>Proposta de arquitetura da app.</h1>

![arch-app](https://github.com/user-attachments/assets/2a2a5980-6bcc-4cc8-884e-4cabac49bec5)

<h1>Orientações gerais</h1>
Para subir a aplicação é necessário rodar o docker swarm com o comando <code>docker swarm init</code>; </br>
Em seguida, faça o build da imagem localmente com <code>docker build -t jjosejunior/desafio-itau:latest .</code>; </br>
E por fim, o deploy com o comando <code> docker stack deploy -c docker-stack.yml desafio-itau </code> </br>
PAra verificar se app subiu com a quantidade esperada de instancias de maquina, execute <code>docker stack services desafio-itau</code></br></br>


<h2> Visualizando logs</h2>
Uma vez que o deploy foi realizado com sucesso, é possível ver os logs acessando o Elastic diretamente na porta 5602do localhost. A principio é necessário gerar um novo index pattern para visualiza-los. A interface deve ser a seguinte:

![logs_kibana](https://github.com/user-attachments/assets/a5632f61-02bb-4c61-9670-b9f58c04e2a6)

<h2> Monitorar o Circuit Breaker - metricas </h2>
Para verificar as informações referentes ao circuit breaker geradas pelo actguator basta acessar a URL <code>http://localhost:8080/actuator/metrics/resilience4j.circuitbreaker.calls</code> </br>
É possível realizar inumeras chamadas consecultivas para atingr a taxa de falhas abrir o circuit breaker. (se necessário, abaixe a taxa de falhas em <code>application-prod.properties</code> 

<h1>Proposta de pipeline</h1>

```yaml
name: Proposta de CI/CD com docker swarm

on:
  push:
    branches:
      - master

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout repository
        uses: actions/checkout@v2

      - name: Install SSH key
        uses: webfactory/ssh-agent@v0.5.3
        with:
          ssh-private-key: SSH_PRIVATE_KEY

      - name: Copy docker-stack.yml to remote server
        run: |
          scp -o StrictHostKeyChecking=no docker-stack.yml ec2-user@IP_DO_SERVIDOR_EC2:/home/ec2-user/

      - name: SSH into remote server, build image and deploy stack
        run: |
          ssh -o StrictHostKeyChecking=no ec2-user@IP_DO_SERVIDOR_EC2 "
            cd /home/ec2-user && \
            docker build -t jjosejunior/desafio-itau:latest . && \
            docker stack deploy -c docker-stack.yml desafio-itau
          "
```
<h3> Explicação </h3>

Esse é um workflow do GitHub Actions que automatiza o processo de deploy desta app. </br>
Ele é disparado automaticamente sempre que houver um push na branch master.</br>
O workflow roda em uma máquina virtual Ubuntu.</br>
Ele faz o checkout do código do repositório.</br>
Instala uma chave SSH para acessar um servidor remoto (EC2 da AWS). </br>
Copia o arquivo docker-stack.yml para o servidor remoto. </br>
Conecta via SSH no servidor, onde Constrói a imagem Docker da aplicação.
Implanta ou atualiza o stack Docker Swarm usando o arquivo docker-stack.yml. </br>
OBS: Os valores como o ip da maquina EC2 e o ssh não são reais, pois a app não foi hospedada, esta é uma proposta baseada no nosso serviço. 

<h1> Avisos/observações gerais sobre a aplicação</h1>
Todos os arquivos foram comitados e nenhum esta no gitignore justamente para que seja possivel executa-la sem problemas. Geralmente arquivos com senhas como os da pasta env são deixados de fora por conta de segurança. </br></br>
Foi exposto o endpoint <code>/fist-manager</code> com o intuito de criar o primeiro gerente da aplicação quando executado em produção, e em seguida, esse endpoint pode ser removido sem ter necessidade de grande refatoração, pois o controller e implementação esta separado do restante. Isso é necessário, pois toda a app da incio com ações que exigem um gerente. 
