
public class Main {

	public static void main(String[] args) {
		String message = "ce cours de mathematiques est tres interessant";
		String cle = "7 1 4 5 2 3 8 6";
		
		// Chiffrement
		System.out.println("Message encrypte:");
		String msgEncrype = Chiffrement.chiffrer(message, cle);
		System.out.println(msgEncrype);
		
		System.out.println("\n");
		
		// D�chiffrement
		System.out.println("Message decrypte:");
		String msgDecrypte = Chiffrement.dechiffrer(msgEncrype, cle);
		System.out.println(msgDecrypte);
		
	}

}
