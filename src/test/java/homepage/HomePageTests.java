package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import base.BaseTests;
import pages.CarrinhoPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.PedidoPage;
import pages.ProdutoPage;
import util.Funcoes;

public class HomePageTests extends BaseTests {
	
	@Test
	public void testContarProdutos_oitoProdutosDiferentes() {
		carregarPaginaInicial();
		assertThat(homePage.contarProdutos(), is(8));
	}
	
	@Test
	public void testValidarCarrinhoZerado_ZeroItensNoCarrinho() {
		int produtosNoCarrinho = homePage.obterQuantidadeProdutosNoCarrinho();
		//System.out.println(produtosNoCarrinho);
		assertThat(produtosNoCarrinho, is(0));
	}
	
	LoginPage loginPage;
	
	@Test
	public void testLoginComSucesso_UsuarioLogado() {
		//Clicar no bot�o Sign in na Home Page
		loginPage = homePage.clicarBotaoSignIn();
		
		//Preencher usu�rio e senha
		loginPage.preencherEmail("teste@teste.com");
		loginPage.preencherPassword("teste");
		
		//Clicar no bot�o Sign in para logar
		loginPage.clicarBotaoSignIn();
		
		//Validar se usu�rio est� de fato logado
		assertThat(homePage.estaLogado("Teste Testador"), is(true));
		
		carregarPaginaInicial();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/massaTeste_Login.csv", numLinesToSkip = 1, delimiter = ';')
	public void testLogin_UsuarioLogadoComDadosValidos(String nomeTeste, String email, String password, String nomeUsuario, String resultado) {
		
		//Clicar no bot�o Sign in na Home Page
		loginPage = homePage.clicarBotaoSignIn();
		
		//Preencher usu�rio e senha
		loginPage.preencherEmail(email);
		loginPage.preencherPassword(password);
		
		//Clicar no bot�o Sign in para logar
		loginPage.clicarBotaoSignIn();
		
		boolean esperado_LoginOK;	
		
		if (resultado.equals("positivo"))
			esperado_LoginOK = true;
		else
			esperado_LoginOK = false;
		
		//Validar se usu�rio est� de fato logado
		assertThat(homePage.estaLogado(nomeUsuario), is(esperado_LoginOK));
		
		capturarTela(nomeTeste, resultado);
		
		if (esperado_LoginOK == true)
			homePage.clicarBotaoSignOut();
		
		carregarPaginaInicial();
	}
	
	ProdutoPage produtoPage;
	String nomeProduto_ProdutoPage;
	
	@Test
	public void testValidarDetalhesDoProduto_DescricaoEValorIguais() {
		int indice = 0;
		
		String nomeProduto_HomePage = homePage.obterNomeDoProduto(indice);
		double precoProduto_HomePage = homePage.obterPrecoDoProduto(indice);
		
		//System.out.println(nomeProduto_HomePage.toUpperCase());
		//System.out.println(precoProduto_HomePage);
		
		produtoPage = homePage.ClicarProduto(indice);
		
		nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		double precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();
		
		//System.out.println(nomeProduto_ProdutoPage);
		//System.out.println(precoProduto_ProdutoPage);
		
		assertThat(nomeProduto_HomePage.toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));
		assertThat(precoProduto_HomePage, is(precoProduto_ProdutoPage));	
	}
	
	ModalProdutoPage modalProdutoPage;
	
	@Test
	public void testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso() {
		
		String tamanhoDoProduto = "M";
		String corDoProduto = "Black";
		int quantidadeDoProduto = 2;
		
		// Pre-condicoes
		// O usuario deve estar logado
		if(!homePage.estaLogado("Teste Testador")) {
			testLoginComSucesso_UsuarioLogado();
		}
		
		// Teste
		// Selecionando o produto e validando descri��o e valor
		testValidarDetalhesDoProduto_DescricaoEValorIguais();
		
		// Selecionar o tamanho do produto		
		produtoPage.selecionarOpcaoDropDown(tamanhoDoProduto);
		//List<String> listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		
		// Selecionar a cor do produto
		produtoPage.selecionarCorPreta();
		
		// Selecionar a quantidade do produto
		produtoPage.alterarQuantidade(quantidadeDoProduto);
		
		// Adicionar o produto no carrinho
		modalProdutoPage = produtoPage.clicarNoBotaoAddToCart();
		
		// Validacoes
		assertTrue(modalProdutoPage.obterMensagemProdutoAdicionado().endsWith("Product successfully added to your shopping cart"));
		assertThat(modalProdutoPage.obterDescricaoDoProduto().toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));		
		assertThat(modalProdutoPage.obterTamanhoDoProduto(), is(tamanhoDoProduto));
		assertThat(modalProdutoPage.obterCorDoProduto(), is(corDoProduto));
		assertThat(modalProdutoPage.obterQuantidadeDoProduto(), is(Integer.toString(quantidadeDoProduto)));
		
		Double subTotalCalculado = (modalProdutoPage.obterPrecoDoProduto() * quantidadeDoProduto);	
		
		assertThat(modalProdutoPage.obterSubTotal(), is(subTotalCalculado));
	}
	
	CarrinhoPage carrinhoPage;
	
	// Valores esperados
	String esperado_nomeDoProduto = "Hummingbird printed t-shirt";
	Double esperado_precoDoProduto = 19.12;
	String esperado_tamanhoDoProduto = "M";
	String esperado_corDoProduto = "Black";
	int esperado_inputQuantidadeDoProduto = 2;
	Double esperado_subTotalDoProduto = esperado_precoDoProduto * esperado_inputQuantidadeDoProduto;
	
	int esperado_numeroItensTotal = esperado_inputQuantidadeDoProduto;
	double esperado_subtotalTotal = esperado_subTotalDoProduto;
	double esperado_shippingTotal = 7.0;
	double esperado_totalTaxExclTotal = esperado_subtotalTotal + esperado_shippingTotal;
	double esperado_totalTaxInclTotal = esperado_totalTaxExclTotal;
	double esperado_taxesTotal = 0.0;
	
	String esperado_nomeCliente = "Teste Testador";
	
	@Test
	public void testIrParaCarrinho_InformacoesPersistidas() {
		// Pre-Condicoes
		// Produto incluido na tela ModalProdutoPage
		testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso();
		carrinhoPage = modalProdutoPage.clicarBotaoProceedToCheckout();
			
		assertEquals(esperado_nomeDoProduto, carrinhoPage.obter_nomeDoProduto());
		assertEquals(esperado_precoDoProduto, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoDoProduto()));
		assertEquals(esperado_tamanhoDoProduto, carrinhoPage.obter_tamanhoDoProduto());
		assertEquals(esperado_corDoProduto, carrinhoPage.obter_corDoProduto());
		assertEquals(esperado_inputQuantidadeDoProduto, Integer.parseInt(carrinhoPage.obter_inputQuantidadeDoProduto()));
		assertEquals(esperado_subTotalDoProduto, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subTotalDoProduto()));
		
		assertEquals(esperado_numeroItensTotal, Funcoes.removeTextoItemsDevolveInt(carrinhoPage.obter_numeroItensTotal()));
		assertEquals(esperado_subtotalTotal, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotal()));
		assertEquals(esperado_shippingTotal, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()));
		assertEquals(esperado_totalTaxExclTotal, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()));
		assertEquals(esperado_totalTaxInclTotal, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxInclTotal()));
		assertEquals(esperado_taxesTotal, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesTotal()));
		
	}
	
	CheckoutPage checkoutPage;
	
	@Test
	public void testIrPraCheckout_FreteMeioPagamentoEnderecoListadosOk() {
		// Pre-condicoes
		// Produto disponivel no carrinho de compras
		testIrParaCarrinho_InformacoesPersistidas();
	
		// Teste
		
		//Clicar no botao
		checkoutPage = carrinhoPage.ClicarBotaoProceedToCheckout();
		//Preencher informacoes
		
		//Validar informacoes na tela
		assertThat(checkoutPage.obter_totalTaxInclTotal(), is(esperado_totalTaxInclTotal));
		assertTrue(checkoutPage.obter_nomeCliente().startsWith(esperado_nomeCliente));
		
		checkoutPage.ClicarBotaoContinueAddress();
		
		assertThat(checkoutPage.obter_shippingValor(), is(esperado_shippingTotal));
		
		checkoutPage.ClicarBotaoContinueShipping();
		
		//Selecionar opcao "Pay By Check"
		checkoutPage.selecionarRadioPayByCheck();
		
		//Validar valor do cheque (amount)
		assertThat(checkoutPage.obter_amountPayByCheck(), is(esperado_totalTaxInclTotal));
		
		//Clicar na opcao "I Agree"
		checkoutPage.selecionarCheckboxIAgree();
		assertTrue(checkoutPage.estaSelecionadoCheckboxIAgree());
		
	}
	
	PedidoPage pedidoPage;
	
	@Test
	public void testFinalizarPedido_pedidoFinalizadoComSucesso() {
		//Pre-condicoes
		//Checkout completamente conclu�do
		testIrPraCheckout_FreteMeioPagamentoEnderecoListadosOk();
		
		//Teste
		//Clicar no botao para confirmar o pedido
		pedidoPage = checkoutPage.clicarBotaoConfirmarPedido();
		//Validar valores da tela
		
		//System.out.println(pedidoPage.obter_textoPedidoConfirmado());
		assertTrue(pedidoPage.obter_textoPedidoConfirmado().endsWith("YOUR ORDER IS CONFIRMED"));	
		assertThat(pedidoPage.obter_email(), is("teste@teste.com "));
		assertThat(pedidoPage.obter_subTotalProdutos(), is(esperado_subtotalTotal));
		assertThat(pedidoPage.obter_totalTaxIncl(), is(esperado_totalTaxInclTotal));
		assertThat(pedidoPage.obter_metodoPagamento(), is("check"));
	}
	
}
