package verificaNota;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException; 


public class Main {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException, LineUnavailableException, UnsupportedAudioFileException {	 
		
		Unisinos classPlay = new Unisinos();
	    Scanner s = new Scanner(System.in);
	    System.out.println("Digite o periodo de horas que o sistema verificará a nota: ");
	    int horas = s.nextInt();
	    System.out.println("Digite seu user: ");
	    String user = s.next();
	    System.out.println("Digite sua senha: ");
	    String passwd = s.next();
		while(true) {
			
			classPlay.logAndPlay(user, passwd);
			Thread.sleep(3600000 * horas);
		}
		
	}
	
}
