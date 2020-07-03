package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import util.Funcoes;

public class CheckoutPage {
	
	private WebDriver driver;
	
	private By totalTaxInclTotal = By.cssSelector("div.cart-total span.value");
	
	private By nomeCliente = By.cssSelector("div.address");
	
	private By botaoContinueAddress = By.name("confirm-addresses");
	
	private By shippingValor = By.cssSelector("span.carrier-price");
	
	private By botaoContinueShipping = By.name("confirmDeliveryOption");
	
	private By radioPayByCheck = By.id("payment-option-1");
	
	private By amountPayByCheck = By.cssSelector("#payment-option-1-additional-information > section > dl > dd:nth-child(2)");
	
	private By checkboxIAgree = By.cssSelector("div.float-xs-left input.ps-shown-by-js");
	
	private By botaoConfirmarPedido = By.cssSelector("#payment-confirmation button.center-block");
	
	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
	}

	public Double obter_totalTaxInclTotal( ) {
		String textoComCifrao =  driver.findElement(totalTaxInclTotal).getText();
		return Funcoes.removeCifraoDevolveDouble(textoComCifrao);
	}
	
	public String obter_nomeCliente() {
		return driver.findElement(nomeCliente).getText();
	}
	
	public void ClicarBotaoContinueAddress() {
		driver.findElement(botaoContinueAddress).click();
	}
	
	public Double obter_shippingValor() {
		String textoAtual = driver.findElement(shippingValor).getText();
		String textoDepois = Funcoes.removeTexto(textoAtual, " tax excl.");
		return Funcoes.removeCifraoDevolveDouble(textoDepois);
	}
	
	public void ClicarBotaoContinueShipping() {
		driver.findElement(botaoContinueShipping).click();
	}

	public void selecionarRadioPayByCheck() {
		driver.findElement(radioPayByCheck).click();
	}
	
	public Double obter_amountPayByCheck( ) {
		String textoAtual = driver.findElement(amountPayByCheck).getText();
		String textoDepois = Funcoes.removeTexto(textoAtual, " (tax incl.)");
		return Funcoes.removeCifraoDevolveDouble(textoDepois);
	}
	
	public void selecionarCheckboxIAgree() {
		driver.findElement(checkboxIAgree).click();
	}
	
	public boolean estaSelecionadoCheckboxIAgree() {
		return driver.findElement(checkboxIAgree).isSelected();
	}
	
	public PedidoPage clicarBotaoConfirmarPedido() {
		driver.findElement(botaoConfirmarPedido).click();
		return new PedidoPage(driver);
	}
}
