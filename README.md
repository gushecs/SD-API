# SD-API
##### API para o processo seletivo da SD Conecta

## Instalação:

Basta fazer o *pull* do projeto, instalar as dependências
com o *maven* e rodar o *SDAPIApplication* usando o *Spring Boot*.

Você deve ainda inserir as seguintes variáveis de sistema:

- SDAPI_URL = https://beta.sdconecta.com/
- SDAPI_CLIENT_SECRET = {sua client_secret}
- SDAPI_CLIENT_ID = {sua client_id}

Alternativamente, você pode colocar esses valores diretamente no
programa editando esses campos para a variável *sdConectaPartnerRQ*
 em *sdapi/security/JWTAuthenticationController.java*.

## Informações básicas:

A API utiliza H2 e o console deste está disponível no endpoint aberto */h2-console*.
Caso deseje realizar a iteração com a API via *Postman*, o projeto inclui 
a coleção *SDAPI.postman_collection.json* na pasta raíz. Todas as requisições
devem ser feitas em *localhost:8080* e utilizam *Content-Type = application/json*
.A API prevê as seguintes iterações:

## Métodos:

### POST - login

#### Endpoint: /sdapi/login

#### Header:

- "Authorization": "Bearer {partner_token}"

#### Request Body:
```
{
    "email": "email",
    "password": "password"
}
```


#### Responses:

**_200 OK:_**
```
{
    "sdapi_token": "sdapi_token",
    "partner_authorization_status": "partner_authorization_status",
    "partner_token": "partner_token",
    "sd_conecta_user": {
        "access_token": "access_token",
        "refresh_token": "refresh_token",
        "authorization_status": "authorization_status"
    }
}
```
- sdapi_token: token para validação de todos os métoddos (exceto métodos get) desta API;
- partner_authorization_status: Informação de se a token do associado da SD Conecta ainda está válida;
- partner_token: token do associado à SD Conecta, obtida com as credenciais *client_secret* e *client_id*;
- sd_conecta_user: Informações de login de usuário da SD Conecta retornada pelo endpoint *api/v2/partners/generate-user-token*.

**_401 UNAUTHORIZED:_** Informações fornecidas no Request Body inválidas.

### GET - findAll

#### Endpoint: /sdapi/users

#### Params:

- name (opicional): {name} -> Filtra apenas usuários que tenham o nome {name};
- specialty(opcional): {specialty} -> Filtra apenas os usuários que tenham pelo menos um CRM com a especialidade {specialty}.

#### Response:

Lista com todos os usuários no formato:
```
[
    {
        "email": "email",
        "name": "name",
        "surname": "surname",
        "mobile_phone": "mobile_phone",
        "authorization_status": "authorization_status",
        "crms": [
            {
                "crm": "crm",
                "uf": "uf",
                "specialty": "specialty"
            }
        ]
    }
]
```

### GET - findById

#### Endpoint: /sdapi/users/{id}

#### Responses:

**_200 OK:_** Retorna o usuário de id {id} no formato:
```
{
    "email": "email",
    "name": "name",
    "surname": "surname",
    "mobile_phone": "mobile_phone",
    "authorization_status": "authorization_status",
    "crms": [
        {
            "crm": "crm",
            "uf": "uf",
            "specialty": "specialty"
        }
    ]
}
```
**_404 NOT FOUND:_** Caso o {id} não corresponda a usuário nenhum.

### POST - register User

#### Endpoint: /sdapi/users

#### Header:

- "Authorization": "Bearer {sdapi_token}"

#### Request Body:
```
{
    "name": "name",
    "surname": "surname", (optional)
    "email": "email",
    "password": "password",
    "mobile_phone": "mobile_phone", (optional)
    "profile": "profile", (optional)
    "crms":[{ (optional)
        "crm":"CRM",
        "uf":"UF",
        "specialty":"specialty"
    }]
}
```

O profile deve ser 1, para usuários admin, ou 2 para usuários comuns. Caso não seja especificado, 
o status de usuário comum é atribuído automaticamente.

Ao ser criado, um usuário ganha um *authorization_status* com valor de *UNREGISTERED* automaticamente. O valor é atualizado
 após a primeira tentativa de login.

Caso o usuário seja inserido com um CRM inexistente, o CRM é cadastrado automaticamente. Caso o CRM já 
esteja atribuído a outro usuário, o dono do registro é modificado.

#### Responses:

**_200 OK:_** Retorna o usuário cadastrado.

**_403 FORBIDDEN:_** Caso o usuário não tenha credencial de admin ou o token esteja vencido.

**_400 BAD REQUEST:_** Caso o email fornecido já esteja cadastrado ou algum campo tenha valor inválido.

### PUT - update User

#### Endpoint: /sdapi/users/{id}

#### Header:

- "Authorization": "Bearer {sdapi_token}"

#### Request Body:
```
{
    "email": "email",
    "name": "name",
    "surname": "surname",
    "mobile_phone": "mobile_phone",
    "authorization_status": "authorization_status",
    "crms": [
        {
            "crm": "crm",
            "uf": "uf",
            "specialty": "specialty"
        }
    ]
}
```
O profile deve ser 1, para usuários admin, ou 2 para usuários comuns. Caso não seja especificado,
o status de usuário comum é atribuído automaticamente.

Caso o usuário seja atualizado com um CRM inexistente, o CRM é cadastrado automaticamente. Caso o CRM já
esteja atribuído a outro usuário, o dono do registro é modificado. Se algum CRM do usuário deixar de pertencer a ele, ele é deletado do banco de dados automaticamente. 
O método pode ser utilizado para atualizar o campo *specialty* do CRM.

#### Responses:

**_200 OK:_** Retorna o usuário atualizado, correspondente ao {id}.

**_403 FORBIDDEN:_** Caso o usuário não tenha credencial de admin ou o token esteja vencido.

**_400 BAD REQUEST:_** Caso o email fornecido já esteja cadastrado em outro usuário ou algum campo tenha valor inválido.

**_404 NOT FOUND:_** Caso o {id} não corresponda a usuário nenhum.


### DELETE - delete User

#### Endpoint: /sdapi/users/{id}

#### Header:

- "Authorization": "Bearer {sdapi_token}"

#### Responses:

**_200 OK:_** Deleta o usuário correspondente ao {id}.

**_403 FORBIDDEN:_** Caso o usuário não tenha credencial de admin ou o token esteja vencido.

**_404 NOT FOUND:_** Caso o {id} não corresponda a usuário nenhum.

### POST - register CRM

#### Endpoint: /sdapi/crm

#### Header:

- "Authorization": "Bearer {sdapi_token}"

#### Request Body:
```
{
"crm": "CRM",
"uf": "UF",
"specialty": "specialty", (opcional)
"user_id": user_id
}
```

#### Responses:

**_200 OK:_** Retorna o CRM cadastrado.

**_403 FORBIDDEN:_** Caso o usuário não tenha credencial de admin ou o token esteja vencido.

**_400 BAD REQUEST:_** Caso já haja um CRM cadastrado coom  memso número e UF ou algum campo tenha valor inválido.

