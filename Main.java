
public class Main {

	public static void main(String[] args) {
		String message = "ce cours de mathématiques est très intéressant";
		String cle = "7 1 4 5 2 3 8 6";
		
		Chiffrement chr = new Chiffrement();
		
		String msgEncrype = chr.chiffrer(message, cle);
		System.out.println(msgEncrype);
	}

}
