# language: pt
Funcionalidade: Comprar produto
  Como um usuario logado
  Eu quero escolher um produto
  E visualizar esse produto no carrinho
  Para conluir o pedido

  @validacaoinicial
  Cenario: Deve mostrar uma lista de oito produtos na pagina inicial
    Dado que estou na pagina inicial
    Quando nao estou logado
    Entao visualizo 8 produtos disponiveis
    E carrinho deve estar com 0 produtos

  @fluxopadrao
  Esquema do Cenario: Deve mostrar produto escolhido confirmado
    Dado que estou na pagina inicial
    Quando estou logado
    E seleciono o produto da posição <posicao>
    E o nome do produto na tela principal e na tela do produto eh <nomeProduto>
    E o preco do produto na tela principal e na tela do produto eh <precoProduto>
    E adiciono o produto no carrinho com tamanho <tamanhoProduto> cor <corProduto> e quantidade <qtdProduto>
    Entao o produto aparece na confirmacao com o nome <nomeProduto> preco <precoProduto> tamanho <tamanhoProduto> cor <corProduto> e quantidade <qtdProduto>

    Exemplos: 
      | posicao | nomeProduto                   | precoProduto | tamanhoProduto | corProduto | qtdProduto |
      |       0 | "Hummingbird printed t-shirt" |        19.12 | "M"            | "Black"    |          2 |
      |       1 | "Hummingbird printed sweater" |        28.72 | "L"            | "N/A"      |          3 |
