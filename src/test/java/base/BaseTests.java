package base;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;

public class BaseTests {
	
	private static WebDriver driver;
	private static String url;
	protected HomePage homePage;
	
	@BeforeAll
	public static void inicializar() {
		System.setProperty("webdriver.chrome.driver", "C:\\webdriver\\chrome\\83\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@BeforeEach
	public void carregarPaginaInicial() {
		url = "https://marcelodebittencourt.com/demoprestashop/";
		driver.get(url);
		homePage = new HomePage(driver);
	}
	
	@AfterAll
	public static void finalizar() {
		driver.quit();
	}
	

}
