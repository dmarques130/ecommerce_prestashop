package steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.es.Dado;
import io.cucumber.java.it.Quando;
import io.cucumber.java.pt.Entao;
import pages.HomePage;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.ProdutoPage;

public class ComprarProdutosSteps {
	
	private static WebDriver driver;
	private HomePage homePage = new HomePage(driver);	
	
	@Before
	public static void inicializar() {
		System.setProperty("webdriver.chrome.driver", "C:\\webdriver\\chrome\\83\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Dado("que estou na pagina inicial")
	public void que_estou_na_pagina_inicial() {
		homePage.carregarPaginalInicial();
		assertThat(homePage.obterTituloPagina(), is("Loja de Teste"));
	}

	@Quando("nao estou logado")
	public void nao_estou_logado() {
		assertFalse(homePage.estaLogado());
		//assertThat(homePage.estaLogado(), is(false));
	}

	@Entao("visualizo {int} produtos disponiveis")
	public void visualizo_produtos_disponiveis(Integer qtdProdutos) {
		assertThat(homePage.contarProdutos(), is(qtdProdutos));
	}

	@Entao("carrinho deve estar com {int} produtos")
	public void carrinho_esta_zerado(Integer qtdProdutos) {
		assertThat(homePage.obterQuantidadeProdutosNoCarrinho(), is(qtdProdutos));
	}
	
	
	LoginPage loginPage;
	
	@Quando("estou logado")
	public void estou_logado() {
		
		loginPage = homePage.clicarBotaoSignIn();		
		loginPage.preencherEmail("teste@teste.com");
		loginPage.preencherPassword("teste");	
		loginPage.clicarBotaoSignIn();
		
		assertThat(homePage.estaLogado("Teste Testador"), is(true));
		
		homePage.carregarPaginalInicial();
		
	}

	ProdutoPage produtoPage;
	String nomeProduto_HomePage;
	double precoProduto_HomePage;
	String nomeProduto_ProdutoPage;
	double precoProduto_ProdutoPage;
	
	@Quando("seleciono o produto da posição {int}")
	public void seleciono_o_produto_da_posição(Integer indice) {

		nomeProduto_HomePage = homePage.obterNomeDoProduto(indice);
		precoProduto_HomePage = homePage.obterPrecoDoProduto(indice);

		produtoPage = homePage.ClicarProduto(indice);
		
		nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();

	}

	@Quando("o nome do produto na tela principal e na tela do produto eh {string}")
	public void o_nome_do_produto_eh(String nomeProduto) {
		
		assertThat(nomeProduto_HomePage.toUpperCase(), is(nomeProduto.toUpperCase()));
		assertThat(nomeProduto_ProdutoPage.toUpperCase(), is(nomeProduto.toUpperCase()));
	}

	@Quando("o preco do produto na tela principal e na tela do produto eh {double}")
	public void o_preco_do_produto_eh(double precoProduto) {
		
		assertThat(precoProduto_HomePage, is(precoProduto));
		assertThat(precoProduto_ProdutoPage, is(precoProduto));
	}

	ModalProdutoPage modalProdutoPage;
	List<String> listaOpcoes;
	
	@Quando("adiciono o produto no carrinho com tamanho {string} cor {string} e quantidade {int}")
	public void adiciono_o_produto_no_carrinho_com_tamanho_cor_e_quantidade(String tamanhoProduto, String corProduto, Integer qtdProduto) {
		
		
		produtoPage.selecionarOpcaoDropDown(tamanhoProduto);	
		listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		
		if (!corProduto.equals("N/A")) {
			produtoPage.selecionarCorPreta();
		}
				
		produtoPage.alterarQuantidade(qtdProduto);
		
		modalProdutoPage = produtoPage.clicarNoBotaoAddToCart();
			
		assertTrue(modalProdutoPage.obterMensagemProdutoAdicionado().endsWith("Product successfully added to your shopping cart"));
		
	}

	@Entao("o produto aparece na confirmacao com o nome {string} preco {double} tamanho {string} cor {string} e quantidade {int}")
	public void o_produto_aparece_na_confirmacao_com_o_nome_preco_tamanho_cor_e_quantidade(String nomeProduto, double precoProduto, String tamanhoProduto, String corProduto, Integer qtdProduto) {
					
		assertThat(modalProdutoPage.obterDescricaoDoProduto().toUpperCase(), is(nomeProduto.toUpperCase()));
		assertThat(modalProdutoPage.obterPrecoDoProduto(), is(precoProduto));
		assertThat(modalProdutoPage.obterTamanhoDoProduto(), is(tamanhoProduto));
		
		if (!corProduto.equals("N/A")) {
			assertThat(modalProdutoPage.obterCorDoProduto(), is(corProduto));
		}
			
		assertThat(modalProdutoPage.obterQuantidadeDoProduto(), is(Integer.toString(qtdProduto)));
		
		Double subTotalCalculado = (precoProduto * qtdProduto);	
		
		assertThat(modalProdutoPage.obterSubTotal(), is(subTotalCalculado));
		
	}
	
	@After
	public static void finalizar() {
		driver.quit();
	}

}
