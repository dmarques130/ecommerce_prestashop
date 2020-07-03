package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

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
		//Clicar no botão Sign in na Home Page
		loginPage = homePage.clicarBotaoSignIn();
		
		//Preencher usuário e senha
		loginPage.preencherEmail("teste@teste.com");
		loginPage.preencherPassword("teste");
		
		//Clicar no botão Sign in para logar
		loginPage.clicarBotaoSignIn();
		
		//Validar se usuário está de fato logado
		assertThat(homePage.estaLogado("Teste Testador"), is(true));
		
		carregarPaginaInicial();
	}
	
	ProdutoPage produtoPage;
	String nomeProduto_ProdutoPage;
	
	@Test
	public void testValidarDetalhesDoProduto_DescricaoEValorIguais() {
		int indice = 0;
		
		String nomeProduto_HomePage = homePage.obterNomeDoProduto(indice);
		String precoProduto_HomePage = homePage.obterPrecoDoProduto(indice);
		
		//System.out.println(nomeProduto_HomePage.toUpperCase());
		//System.out.println(precoProduto_HomePage);
		
		produtoPage = homePage.ClicarProduto(indice);
		
		nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		String precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();
		
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
		
		// Pré-condições
		// O usuário deve estar logado
		if(!homePage.estaLogado("Teste Testador")) {
			testLoginComSucesso_UsuarioLogado();
		}
		
		// Teste
		// Selecionando o produto e validando descrição e valor
		testValidarDetalhesDoProduto_DescricaoEValorIguais();
		
		// Selecionar o tamanho do produto
			
		List<String> listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		//System.out.println(listaOpcoes.get(0));
		//System.out.println("Tamanho da lista" + listaOpcoes.size());
		
		produtoPage.selecionarOpcaoDropDown(tamanhoDoProduto);
		listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		
		// Selecionar a cor do produto
		produtoPage.selecionarCorPreta();
		
		// Selecionar a quantidade do produto
		produtoPage.alterarQuantidade(quantidadeDoProduto);
		
		// Adicionar o produto no carrinho
		modalProdutoPage = produtoPage.clicarNoBotaoAddToCart();
		//String mensagem = modalProdutoPage.obterMensagemProdutoAdicionado();
		
		// Validações
		assertTrue(modalProdutoPage.obterMensagemProdutoAdicionado().endsWith("Product successfully added to your shopping cart"));
		assertThat(modalProdutoPage.obterDescricaoDoProduto().toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));
		
		String precoProdutoString = modalProdutoPage.obterPrecoDoProduto();
		precoProdutoString = precoProdutoString.replace("$", "");
		Double precoProduto = Double.parseDouble(precoProdutoString);
			
		assertThat(modalProdutoPage.obterTamanhoDoProduto(), is(tamanhoDoProduto));
		assertThat(modalProdutoPage.obterCorDoProduto(), is(corDoProduto));
		assertThat(modalProdutoPage.obterQuantidadeDoProduto(), is(Integer.toString(quantidadeDoProduto)));
		
		String subTotalString = modalProdutoPage.obterSubTotal();
		subTotalString = subTotalString.replace("$", "");
		Double subTotal = Double.parseDouble(subTotalString);
		
		Double subTotalCalculado = (precoProduto * quantidadeDoProduto);	
		assertThat(subTotal, is(subTotalCalculado));	
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
		// Pré-Condições
		// Produto incluído na tela ModalProdutoPage
		testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso();
		carrinhoPage = modalProdutoPage.clicarBotaoProceedToCheckout();
			
		//Teste
		//Validar todos os elementos
//		System.out.println("*** TELA DO CARRINHO ***");
//		
//		System.out.println(carrinhoPage.obter_nomeDoProduto());
//		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoDoProduto()));
//		System.out.println(carrinhoPage.obter_tamanhoDoProduto());
//		System.out.println(carrinhoPage.obter_corDoProduto());
//		System.out.println(carrinhoPage.obter_inputQuantidadeDoProduto());
//		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subTotalDoProduto()));
//		
//		System.out.println("*** ITENS DE TOTAIS ***");
//		
//		System.out.println(Funcoes.removeTextoItemsDevolveInt(carrinhoPage.obter_numeroItensTotal()));
//		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotal()));
//		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()));
//		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()));
//		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxInclTotal()));
//		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesTotal()));
		
		// Asserções Hamcrest
//		assertThat(carrinhoPage.obter_nomeDoProduto(), is(esperado_nomeDoProduto));
//		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoDoProduto()), is(esperado_precoDoProduto));
//		assertThat(carrinhoPage.obter_tamanhoDoProduto(), is(esperado_tamanhoDoProduto));
//		assertThat(carrinhoPage.obter_corDoProduto(), is(esperado_corDoProduto));
//		assertThat(Integer.parseInt(carrinhoPage.obter_inputQuantidadeDoProduto()), is(esperado_inputQuantidadeDoProduto));
//		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subTotalDoProduto()), is(esperado_subTotalDoProduto));
//		
//		assertThat(Funcoes.removeTextoItemsDevolveInt(carrinhoPage.obter_numeroItensTotal()), is(esperado_numeroItensTotal));
//		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotal()), is(esperado_subtotalTotal));
//		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()), is(esperado_shippingTotal));
//		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()), is(esperado_totalTaxExclTotal));
//		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxInclTotal()), is(esperado_totalTaxInclTotal));
//		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesTotal()), is(esperado_taxesTotal));
//		
		// Asserções JUnit
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
	public void IrPraCheckout_FreteMeioPagamentoEnderecoListadosOk() {
		// Pré condições
		// Produto disponivel no carrinho de compras
		testIrParaCarrinho_InformacoesPersistidas();
	
		// Teste
		
		//Clicar no botão
		checkoutPage = carrinhoPage.ClicarBotaoProceedToCheckout();
		//Preencher informações
		
		//Validar informações na tela
		assertThat(checkoutPage.obter_totalTaxInclTotal(), is(esperado_totalTaxInclTotal));
		assertTrue(checkoutPage.obter_nomeCliente().startsWith(esperado_nomeCliente));
		
		checkoutPage.ClicarBotaoContinueAddress();
		
		assertThat(checkoutPage.obter_shippingValor(), is(esperado_shippingTotal));
		
		checkoutPage.ClicarBotaoContinueShipping();
		
		//Selecionar opção "Pay By Check"
		checkoutPage.selecionarRadioPayByCheck();
		
		//Validar valor do cheque (amount)
		assertThat(checkoutPage.obter_amountPayByCheck(), is(esperado_totalTaxInclTotal));
		
		//Clicar na opção "I Agree"
		checkoutPage.selecionarCheckboxIAgree();
		assertTrue(checkoutPage.estaSelecionadoCheckboxIAgree());
		
	}
	
	PedidoPage pedidoPage;
	
	@Test
	public void testFinalizarPedido_pedidoFinalizadoComSucesso() {
		//Pré-condições
		//Checkout completamente concluído
		IrPraCheckout_FreteMeioPagamentoEnderecoListadosOk();
		
		//Teste
		//Clicar no botão para confirmar o pedido
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
