# 🎵 jQueryByHumming

Sistema de recuperação de músicas a partir de solfejos (Query by Humming), com foco em canções brasileiras.
Projeto pioneiro da COPPE/UFRJ, desenvolvido por Alex Libório Caranha (2013) como dissertação de mestrado em Engenharia Elétrica – Processamento Digital de Sinais.

Este projeto foi desenvolvido como parte de uma pesquisa de mestrado com foco em sistemas de recuperação musical por consulta vocal. Ele combina técnicas de extração de características musicais com buscas eficientes utilizando Elasticsearch.

📄 [`docs/Dissertacao_AlexCaranha_2013.pdf`](./docs/Dissertacao_AlexCaranha_2013.pdf)

---

## 🔍 Visão Geral

Este projeto permite a pesquisa de músicas através de entrada vocal — como solfejo, canto ou assobio — identificando a melodia mais próxima dentro de uma base de dados com canções brasileiras. Ele implementa etapas clássicas de um sistema Query by Humming:

1. **Detecção de pitch (altura de nota)**;
2. **Detecção de onsets (início das notas)**;
3. **Conversão simbólica para representação melódica**;
4. **Comparação melódica usando algoritmos de similaridade**;
5. **Retorno das músicas mais similares ranqueadas por distância melódica**.

---

## 🧱 Arquitetura do Sistema

```text
Entrada WAV (solfejo) 
  └──► Pitch Tracking + Onset Detection
        └──► Representação Melódica
              └──► Comparação Melódica
                    └──► Retorno de músicas mais similares
```

---

## 🧠 Tecnologias e Algoritmos

- **Java SE** (JDK 6+)
- **Bibliotecas**: JAudio, JTransforms, JMusic
- **Pitch Estimators**: ACF, HPS, CEPS, YIN
- **Onset Detectors**: CD, SF, HFC, entre outros
- **Comparadores de Melodia**: Levenshtein, DTW
- **Representação de Melodia**: Intervalos relativos, Código de Parsons, vetores de pitch
- **Elasticsearch**: Motor de busca full-text para indexação e recuperação melódica
- **Maven**: Automação de builds e gerenciamento de dependências

---

🧪 Avaliação Experimental
O sistema foi avaliado com base em um banco de dados contendo músicas brasileiras tradicionais (MPB, samba, choro, etc.) e solfejos gravados por não músicos. As métricas de desempenho utilizadas foram:

- Top-N Accuracy (acurácia nos N primeiros resultados)

- Precision / Recall

- Análise qualitativa de similaridade melódica

Resultados indicaram que, mesmo com entrada imperfeita, o sistema é capaz de recuperar versões corretas ou altamente similares com eficácia.

---

🧑‍🎓 Origem Acadêmica
Este projeto foi desenvolvido como parte da dissertação de mestrado de Alex Libório Caranha, defendida em 2013 na COPPE/UFRJ (Programa de Engenharia Elétrica). O foco principal foi viabilizar a busca por música baseada em conteúdo (Content-Based Music Retrieval), adaptada à cultura brasileira.

---

## ⚙️ Requisitos

Antes de executar o projeto pela primeira vez, instale os seguintes softwares:

1. Java JDK 6 ou superior;

2. [Git](https://git-scm.com/book/pt-br/Primeiros-passos-Instalando-Git): Controle de versão distribuído. Necessário para clonar o repositório.

3. [Apache Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html): Ferramenta de automação de compilação Java.

4. [Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/current/install-elasticsearch.html): 
Servidor de busca necessário para indexação e consulta da base de músicas.

---

## 🚀 Primeira Execução

### 1. Clone o repositório
```bash
git clone https://github.com/AlexCaranha/jQueryByHumming
cd jQueryByHumming
```

### 2. Compile o projeto com Maven

Gere a versão .jar com todas as dependências:
```bash
mvn install
```

Após a conclusão, será criada a pasta target com os artefatos gerados.uy8\

### 3. Inicie o Elasticsearch: 

Certifique-se de que o servidor Elasticsearch está em execução localmente. Para instruções detalhadas, consulte a [documentação oficial](https://www.elastic.co/guide/en/elasticsearch/reference/current/install-elasticsearch.html).


### 4. Inicialize o banco de dados (indexação)

Execute o seguinte comando para limpar e popular as músicas no Elasticsearch:
```bash
cd target
java -jar jQueryByHumming-1.0-jar-with-dependencies.jar db:reset
```

Como resultado, você verá logs indicando a criação dos documentos (id, type, index).

### 5. Execute o aplicativo

Por fim, execute a aplicação com:
```bash
java -jar jQueryByHumming-1.0-jar-with-dependencies.jar
```

---

## 📘 Referência Acadêmica

Dissertação:  
**Sistema de Pesquisa de Músicas através de Solfejo com Foco em Músicas Brasileiras**  
Alex Libório Caranha, COPPE/UFRJ, 2013  
📄 [`docs/Dissertacao_AlexCaranha_2013.pdf`](./docs/Dissertacao_AlexCaranha_2013.pdf)

---

## 📫 Contato

Alex Libório Caranha  
[🌐 alexcaranha.com](https://alexcaranha.com)
[🔗 LinkedIn](https://linkedin.com/in/alexcaranha)

---

## 📌 Possíveis Extensões Futuras

- ⬜ Adição de algoritmos de comparação melódica;
- ⬜ Adição de algoritmos de detecção de início de notas;
- ⬜ Ampliação da base de músicas para incluir outros estilos;
- ⬜ Módulo educacional para aprendizado de melodia.

---

## ⚖️ Licença

Uso acadêmico e educacional permitido.

Para uso comercial, entrar em contato com o autor.
