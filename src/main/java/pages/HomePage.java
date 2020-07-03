package pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
	
	private WebDriver driver;
	List<WebElement> listaProdutos = new ArrayList<WebElement>();
	private By textoProdutosNoCarrinho = By.className("cart-products-count");
	private By produtos = By.className("product-description");
	private By botaoSignIn = By.cssSelector("#_desktop_user_info span.hidden-sm-down");
	private By usuarioLogado = By.cssSelector("#_desktop_user_info span.hidden-sm-down");
	private By descricoesDosProdutos = By.cssSelector(".product-description a");
	private By precoDosProdutos = By.cssSelector(".product-description span.price");
	
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public int contarProdutos() {
		listaProdutos = driver.findElements(produtos);
		return listaProdutos.size();
	}
	
	public int obterQuantidadeProdutosNoCarrinho() {
		// obtendo o elemento
		String quantidadeProdutosNoCarrinho = driver.findElement(textoProdutosNoCarrinho).getText();
		
		// substituindo strings
		quantidadeProdutosNoCarrinho = quantidadeProdutosNoCarrinho.replace("(", "");
		quantidadeProdutosNoCarrinho = quantidadeProdutosNoCarrinho.replace(")", "");
		
		// convertendo String para int
		int qtdProdutosNoCarrinho = Integer.parseInt(quantidadeProdutosNoCarrinho);
		
		// retornando quantidade de produtos no carrinho
		return qtdProdutosNoCarrinho;
	}
	
	public String obterNomeDoProduto(int indice) {
		String nomeDoProduto = driver.findElements(descricoesDosProdutos).get(indice).getText();
		return nomeDoProduto;
	}
	
	public String obterPrecoDoProduto(int indice) {
		String precoDoProduto = driver.findElements(precoDosProdutos).get(indice).getText();
		return precoDoProduto;
	}
	
	public LoginPage clicarBotaoSignIn() {
		driver.findElement(botaoSignIn).click();
		return new LoginPage(driver);
	}
	
	public boolean estaLogado(String texto) {
		return texto.contentEquals(driver.findElement(usuarioLogado).getText());
	}
	
	public ProdutoPage ClicarProduto(int indice) {
		driver.findElements(descricoesDosProdutos).get(indice).click();
		return new ProdutoPage(driver);
	}
}
