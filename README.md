# consuming-github
### Consumindo uma API com estado de cache para buscas offline. #CLEAN-ARCHITECTURE
<p align="center">Api consumida</p>
<p align="center">https://api.github.com/search/repositories?q=language:kotlin&sort=stars</p>


| Consuming API | Consuming CACHE |
| :---------------: | :---------------: |
| <img src="https://raw.githubusercontent.com/jvictororiz/consuming-github/master/screens/carregando_dados_network.gif" align="center" width="70%"/> | <img src="https://raw.githubusercontent.com/jvictororiz/consuming-github/master/screens/carregando_dados_cache.gif" align="center" width="70%"/> |

----
### Tecnologias utilizados

* Linguagem: **Kotlin**
* Arquitetura: **MVVM**
* Bibliotecas: **Room database, Navigation, Courotines, View Binding, Glide , Retrofit , Koin**
* Bibliotecas de teste : **JUnit , MockK , Espresso, MockWebServer,Robot Pattern.**


### Modularização

<img src="https://raw.githubusercontent.com/jvictororiz/consuming-github/master/screens/modules.png" align="center" width="70%"/>

