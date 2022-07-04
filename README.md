# PokedexApp

<h3 align = "center"> Aplicativo android desenvolvido em Kotlin</h3>


<h3 align = "center"> Apresentação </h3>

<p align="center">







<img src="https://user-images.githubusercontent.com/94938103/177215315-0e30ada4-73fa-41ed-a4cf-e2a7efa014fd.png" width="220" >
 
<img src="https://user-images.githubusercontent.com/94938103/177215199-8538eafb-153a-4f75-8b93-a891e85e7e35.png" width="220"> 
  
<img src="https://user-images.githubusercontent.com/94938103/177193696-d9d290cc-fc76-4d34-8607-d595fb2b65b7.png" width="220">   
 
<img src="https://user-images.githubusercontent.com/94938103/177193750-697817ab-c47b-4dba-8276-8323734234ec.png" width="220">     

<img src="https://user-images.githubusercontent.com/94938103/177193861-c0e640e6-41d9-48bc-bacb-a14fef9df0cd.png" width="220">     


  
  

</p>

<h3> Funcionalidades </h3>
<h4> Home </h4> 

- [x] Listagem de todos os pokemons
- [x] Pesquisa por nome do pokémon
- [x] Botão na barra de pesquisa para exibir a lista de pokemons por tipo
- [x] Botão na barra inferior para sortear um pokemon aleátorio
- [x] Ao clicar na imagem ao topo do app a página de favoritos é exibida
- [x] Ao clicar em um pokémon a página de detalhes é exibida

<h4> Detalhes </h4> 

- [x] Informações do pokémon selecionado
- [x] Botão para adicionar aos favoritos
- [x] Botão para compartilhar a página de detalhes como imagem

<h4> Filtrar </h4> 

- [x] Listagem de pokemons pelo tipo selecionado
- [x] Pesquisa por nome do pokémon
- [x] Ao clicar em um pokémon a página de detalhes é exibida

<h4> Favoritos </h4> 

- [x] Listagem dos pokemons adicionados aos favoritos
- [x] Pesquisa por nome do pokémon
- [x] Ao clicar em um pokémon a página de detalhes é exibida



<h3> Desenvolvimento </h3>

- Arquitetura MVVM.
- Dependências via construtor para facilitar a execução de testes.
- Retrofit para a busca dos dados na API.
- Corountines para executar as chamadas de dados de forma assíncrona.
- Room para salvar pokemons aos favoritos em banco de dados local.
- Kotlin Flow para receber atualizações do banco de dados local.
- Glide para carregamento de imagens.
- ViewBinding para vinculação de visualizações de forma segura.
- LiveData para observar o retorno dos dados do repositório respeitando o ciclo de vida.
- ViewModel para armazenar e gerenciar dados relacionados à interface considerando o ciclo de vida.
- Jetpack Navigation Component para a navegação entre os Fragments
- SafeArgs para transmitir dados com segurança entre os Fragments

<h3> Testes </h3>

- Espresso para testes de interface
