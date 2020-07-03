package pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ProdutoPage {

	private WebDriver driver;
	private By nomeDoProduto = By.className("h1");
	private By precoDoProduto = By.cssSelector(".current-price span:nth-child(1)");
	private By tamanhoDoProduto = By.id("group_1");
	private By inputCorPreta = By.xpath("//ul[@id=\"group_2\"]//input[@value=11]");
	private By quantidadeProduto = By.id("quantity_wanted");
	private By botaoAddToCart = By.className("add-to-cart");
	
	public ProdutoPage(WebDriver driver) {
		this.driver = driver;
	}

	public String obterNomeProduto() {
		return driver.findElement(nomeDoProduto).getText();
	}
	
	public String obterPrecoProduto() {
		return driver.findElement(precoDoProduto).getText();
	}
	
	public void selecionarOpcaoDropDown(String opcao) {
		 encontrarDropDownSize().selectByVisibleText(opcao);
	}
	
	public List<String> obterOpcoesSelecionadas(){
		List<WebElement> elementosSelecionados = encontrarDropDownSize().getAllSelectedOptions();	
		List<String> listaOpcoesSelecionadas = new ArrayList<String>();
		
		for (WebElement elemento : elementosSelecionados) {
			listaOpcoesSelecionadas.add(elemento.getText());
		}
		
		return listaOpcoesSelecionadas;
	}
	
	public Select encontrarDropDownSize() {
		return new Select(driver.findElement(tamanhoDoProduto));
	}
	
	public void selecionarCorPreta() {
		driver.findElement(inputCorPreta).click();
	}
	
	public void alterarQuantidade(int quantidade) {
		driver.findElement(quantidadeProduto).clear();
		driver.findElement(quantidadeProduto).sendKeys(Integer.toString(quantidade));
	}
	
	public ModalProdutoPage clicarNoBotaoAddToCart() {
		driver.findElement(botaoAddToCart).click();
		return new ModalProdutoPage(driver);
	}
}
