# Agro Drone

Criado projeto simulando um drone enviando informações para duas filas SaaS (Criadas em https://www.cloudamqp.com/) com informações de latitude, longitude, umidade e temperatura.

## Justificativas da composição do projeto

As linguagens escolhidas para o desenvolvimento foram: Javascript React(front-end) & Java (back-end).
Foram criadas as duas filas abaixo no Rabbit MQ:

- drone.allinfo: fila alimentada pelo front-end para o consumo do microserviço A responsável pelo envio de alertas por e-mail;
- drone.locationInfo: fila alimentada pelo front-end somente quando a opção de rastreamento está ativa e consumida pelo microserviço B responsável pelo rastreamento.

Foi utilizada a estrategia direct exchange na fila Rabbit MQ para o envio das mensagens entre o front e os microservicos, pois a solucao foi pensada para a utilizacao de roteamento unicast.

Para facilitar e melhorar a produtividade no back-end foi utilizado o spring framework e no front-end o NextJS.
A documentaçao da API do servico de monitoramento foi feita atraves do swagger.


## Justificativas da composição do projeto

## Frontend (pasta agro-drone)

Para iniciar o projeto são necessários alguns passos:

- Executar comando: **npm install** (instalar as dependecias do projeto)
- Criar a variável de ambiente **CLOUDAMQP_URL** com a url da instância criada no https://www.cloudamqp.com/

    Para criar uma instância é necessário criar uma conta pelo link https://customer.cloudamqp.com/signup, feito isso o site irá te enviar para url https://customer.cloudamqp.com/instance e com isso podemos criá-la.
    
    Após criar a instância ela aparecerá na sua lista, clicando encima conseguimos ver as informações detalhadas e lá terá a AMQP URL que deve ser colocada na variável de ambiente.
    
- Criar exchange, binds e queues que serão utilizadas

    Para criar é necessário entrar no RabbitMQ Manager da sua instância. 
    
    Vamos criar o Exchange, para isso clicar na aba Exchanges e logo abaixo terá uma sessão **Add new exchange**, no nome colocar **exchange.drone** o restante é só deixar como está, feito isso é só clicar em **Add Exchange**.
    
    Para criar as queues entraremos na aba Queues e logo abaixo terá a sessão **Add a new queue**, como vamos criar duas o processo precisará ser repetido uma segunda vez, para primeira queue no nome colocar **drone.allinfo** (Fila que será consumida pelo microserviço A) os demais parâmetros ficam como estão, feito isso clicar em **Add queue** e está criada, para a segunda queue o nome será **drone.locationInfo** (Fila que será consumida pelo microserviço B) repetindo os passos acima.
    
    Com tudo criado, precisamos configurar nossos Binds com as routingKeys e suas respectivas filas, para fazer isso na aba Exchange clicar no **exchange.drone** que foi criado e na sessão **Bind > Add binding from this exchange** criar os seguintes binds:
    - to_queue: drone.allinfo, RoutingKey: allinfo, arguments não mexer; (Após preencher clicar no botão **Bind**)
    - to_queue: drone.locationInfo, RoutingKey: locationinfo, arguments não mexer; (Após preencher clicar no botão **Bind**)
    
    Feito todos esses passos nossa configuração da menssageria está feita.
    
- Para rodar o projeto frontend temos duas maneiras, para rodar local em modo de desenvolvimento executar o comando **npm run dev**, para rodar com uma versão pronta para deploy em ambientes executar os comandos **npm run build** para gerar o build do projeto e **npm start**, em ambos os casos o projeto erá subir na url http://localhost:3000/

### Observações

- Ambas as queues somente serão alimentadas se todas as informações do formulario forem preenchidas (ID Drone, Latitude, Longitude) e válidas (latitude, longitude);
- A queue drone.allinfo será alimentada de 10 em 10 segundos;
- A queue drone.locationInfo será alimentada somente se o botão de ativar rastreamento for ativado sendo o intervalo de 10 em 10 segundos;


## Backend (pasta localiza-drone)

   O **localiza-done** é um microsserviço responsável pela exibição da localização do drone de maneira visual no mapa. Através de um serviço node(agro-drone), quando a opção de ratreamento está ativada, ocorre o envio de mensagens para a fila  **drone.locationInfo** (RabbitMQ) que são consumidas permitindo a construção dinâmica do mapa.
   
### Inicialização   
  A inicialização é feita de maneira simplificada pelo uso do spring boot sendo necessário apenas executar a classe **LocalizaDroneApplication**. Contudo, para que ocorra o correto funcionamento do microsserviço é necessário o preenchimento da propriedade **google.key** a fim de a aplicação conseguir autenticar no serviço de mapas do google. 
  O mapa possúi uma coordenada padrão para que seja renderizado pela primeira vez que é recuperada das propriedades **drone.lat** e **drone.lng**. Dessa forma, caso haja a necessidade de alteração das coordenadas de inicialização basta apenas alterar seus valores no arquivo **applications.properties** localizada a pasta resource do projeto.  
  
### Documentação
  As informações referentes as rotas e funcionalidades do microsserviço podem ser acessadas através do swagger(http://localhost:8080/localiza-drone/swagger-ui.html) configurado no projeto.
  
## Backend (pasta localiza-drone)
