package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import util.Funcoes;

public class PedidoPage {
	
	private WebDriver driver;
	
	private By textoPedidoConfirmado = By.cssSelector("#content-hook_order_confirmation h3");
	
	private By email = By.cssSelector("#content-hook_order_confirmation p");
	
	private By subTotalProdutos = By.cssSelector("div.order-confirmation-table div.order-line div.row div.bold");
	
	private By totalTaxIncl = By.cssSelector("tr.font-weight-bold td:nth-child(2)");
	
	private By metodoPagamento = By.cssSelector("#order-details ul li:nth-child(2)");
	
	public PedidoPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String obter_textoPedidoConfirmado() {
		return driver.findElement(textoPedidoConfirmado).getText();
	}
	
	public String obter_email() {
		//teste@teste.com
		String texto =  driver.findElement(email).getText();
		texto = Funcoes.removeTexto(texto, "An email has been sent to the ");
		texto = Funcoes.removeTexto(texto,  "address.");
		return texto;
	}
	
	public Double obter_subTotalProdutos() {
		String texto = driver.findElement(subTotalProdutos).getText();
		return Funcoes.removeCifraoDevolveDouble(texto);
	}
	
	public Double obter_totalTaxIncl() {
		String texto = driver.findElement(totalTaxIncl).getText();
		return Funcoes.removeCifraoDevolveDouble(texto);
	}
	
	public String obter_metodoPagamento() {
		String texto = driver.findElement(metodoPagamento).getText();
		return Funcoes.removeTexto(texto, "Payment method: Payments by ");
	}

}
