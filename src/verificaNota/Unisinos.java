package verificaNota;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class Unisinos {
	
	public void logAndPlay(String user, String passwd) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException, LineUnavailableException, UnsupportedAudioFileException {
		System.out.println("Vericando a nota...");
		//Cria o cliente
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		//O CookieManager vai gerenciar os dados da sessão
		CookieManager cookieMan = new CookieManager();
		cookieMan = webClient.getCookieManager();
		cookieMan.setCookiesEnabled(true);

		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

		HtmlPage pagina;
		try {
			webClient.getOptions().setCssEnabled(false);

			// Get the first page
			pagina = webClient.getPage("http://www.unisinos.br//component/autenticador/portaleducacional/true/continue/bm90YXM=");


			List<HtmlForm> formularios = pagina.getForms();
			HtmlForm formulario = null;

			for (HtmlForm htmlForm : formularios) {
				formulario = htmlForm;
			}

			final HtmlTextInput userId = formulario.getInputByName("username");
			final HtmlPasswordInput password = formulario.getInputByName("passwd");
			final HtmlButton botao = formulario.getButtonByName("btnLogin");
			// Change the value of the text field
			userId.type(user);
			password.type(passwd);
			final HtmlPage logando = botao.click();

			this.aguardarJavaScript(webClient);

			logando.getWebResponse();
								 
			this.getNota(webClient);
	 
			 
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}
	}

	private void getNota(WebClient webClient) throws FailingHttpStatusCodeException, MalformedURLException, IOException, LineUnavailableException, UnsupportedAudioFileException {
		final HtmlPage pagina = webClient.getPage("https://portal.asav.org.br/RM/web/app/edu/PortalEducacional/#/notas");
		this.aguardarJavaScript(webClient);   
		final HtmlRadioButtonInput button = (HtmlRadioButtonInput) pagina.getElementById("item11367334281198466");
	
	    	final HtmlButton buttonA = pagina.getHtmlElementById("btnConfirmar");
	    	buttonA.click(); 
			this.aguardarJavaScript(webClient);

			final HtmlPage pagina2 = webClient.getPage("https://portal.asav.org.br/RM/web/app/edu/PortalEducacional/#/notas");
			this.aguardarJavaScript(webClient);
			
			List<HtmlTableDataCell>  element = pagina2.getByXPath("//td[@class='gridRow textAlignCenter']");
			final String custo = element.get(9).asText(); // no meu caso a nota é o nono td da tabela de notas.
			System.out.println("GB S.O: " + custo);
			
			if(!"".equals(custo)) {
				System.out.println("Aew saiu a nota!! Tomara que vc esteja feliz :D");
		        URL oUrl = new URL("http://www.soundjay.com/button/beep-02.wav");
		        Clip oClip = AudioSystem.getClip();
		        AudioInputStream oStream = AudioSystem.getAudioInputStream(oUrl);
		        oClip.open(oStream);		        
		        oClip.loop(Clip.LOOP_CONTINUOUSLY);
			} else { 
				System.out.println("Segue sem nota...");
			}

	}
		
	private void aguardarJavaScript(WebClient webClient) {
		webClient.waitForBackgroundJavaScript(20000);
		webClient.waitForBackgroundJavaScriptStartingBefore(20000);
	}
	
}
