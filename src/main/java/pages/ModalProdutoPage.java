package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import util.Funcoes;


public class ModalProdutoPage {
	
	WebDriver driver;
	private By mensagemProdutoAdicionado = By.id("myModalLabel");
	private By descricaoDoProduto = By.cssSelector("h6.product-name");
	private By precoDoProduto = By.cssSelector("div.modal-body p.product-price");
	private By listaValoresInformados = By.cssSelector(".divide-right .col-md-6:nth-child(2) span strong");
	private By subTotal = By.cssSelector(".cart-content p:nth-child(2) span.value");
	private By botaoProceedToCheckout = By.cssSelector("a.btn-primary");
	
	public ModalProdutoPage(WebDriver driver) {
		this.driver = driver;
	}

	public String obterMensagemProdutoAdicionado() {
		
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(30)) // vai esperar por 30 segundos
				.pollingEvery(Duration.ofSeconds(1)) // vai tentar novamente a cada 1 segundo
				.ignoring(NoSuchElementException.class); // vai ignorar a mengagem
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(mensagemProdutoAdicionado));
		
		return driver.findElement(mensagemProdutoAdicionado).getText();
	}
	
	public String obterDescricaoDoProduto() {
		return driver.findElement(descricaoDoProduto).getText();
	}
	
	public Double obterPrecoDoProduto() {
		String texto = driver.findElement(precoDoProduto).getText();
		return Funcoes.removeCifraoDevolveDouble(texto);
	}
	
	public String obterTamanhoDoProduto() {
		return driver.findElements(listaValoresInformados).get(0).getText();
	}
	
	public String obterCorDoProduto() {
		
		if (driver.findElements(listaValoresInformados).size() == 3)
			return driver.findElements(listaValoresInformados).get(1).getText();
		else
			return "N/A";		
	}
	
	public String obterQuantidadeDoProduto() {
		
		if (driver.findElements(listaValoresInformados).size() == 3)
			return driver.findElements(listaValoresInformados).get(2).getText();
		else
			return driver.findElements(listaValoresInformados).get(1).getText();		
	}
	
	public Double obterSubTotal() {
		String texto = driver.findElement(subTotal).getText();
		return Funcoes.removeCifraoDevolveDouble(texto);
	}

	public CarrinhoPage clicarBotaoProceedToCheckout() {
		driver.findElement(botaoProceedToCheckout).click();
		return new CarrinhoPage(driver);
	}
	
}
