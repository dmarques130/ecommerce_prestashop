package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CarrinhoPage {
	
	private WebDriver driver;
	
	private By nomeDoProduto = By.cssSelector("div.product-line-info a");
	
	private By precoDoProduto = By.cssSelector("span.price");
	
	private By tamanhoDoProduto = By.cssSelector("div.product-line-info:nth-child(4) span.value");
	
	private By corDoProduto = By.cssSelector("div.product-line-info:nth-child(5) span.value");
	
	private By inputQuantidadeDoProduto = By.cssSelector("input.js-cart-line-product-quantity");
	
	private By subTotalDoProduto = By.cssSelector("span.product-price strong");
	
	private By numeroItensTotal = By.cssSelector("span.js-subtotal");
	
	private By subtotalTotal = By.cssSelector("#cart-subtotal-products span.value");
	
	private By shippingTotal = By.cssSelector("#cart-subtotal-shipping span.value");
	
	private By totalTaxExclTotal = By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(1) span.value");
	
	private By totalTaxInclTotal = By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(2) span.value");
	
	private By taxesTotal = By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(3) span.value");
	
	private By botaoProceedToCheckout = By.cssSelector("a.btn-primary");
	
	public CarrinhoPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String obter_nomeDoProduto() {
		return driver.findElement(nomeDoProduto).getText();
	}
	
	public String obter_precoDoProduto() {
		return driver.findElement(precoDoProduto).getText();
	}
	
	public String obter_tamanhoDoProduto() {
		return driver.findElement(tamanhoDoProduto).getText();
	}
	
	public String obter_corDoProduto() {
		return driver.findElement(corDoProduto).getText();
	}
	
	public String obter_inputQuantidadeDoProduto() {
		return driver.findElement(inputQuantidadeDoProduto).getAttribute("value");
	}
	
	public String obter_subTotalDoProduto() {
		return driver.findElement(subTotalDoProduto).getText();
	}
	
	public String obter_numeroItensTotal() {
		return driver.findElement(numeroItensTotal).getText();
	}
	
	public String obter_subtotalTotal() {
		return driver.findElement(subtotalTotal).getText();
	}
	
	public String obter_shippingTotal() {
		return driver.findElement(shippingTotal).getText();
	}
	
	public String obter_totalTaxExclTotal() {
		return driver.findElement(totalTaxExclTotal).getText();
	}
	
	public String obter_totalTaxInclTotal() {
		return driver.findElement(totalTaxInclTotal).getText();
	}
	
	public String obter_taxesTotal() {
		return driver.findElement(taxesTotal).getText();
	}
	
	public CheckoutPage ClicarBotaoProceedToCheckout() {
		driver.findElement(botaoProceedToCheckout).click();
		return new CheckoutPage(driver);
	}
	

}
