
public class Main {

	public static void main(String[] args) {
		String message = "ce cours de mathematiques est tres interessant";

		
		System.out.println("------------------------------------------------");
		System.out.println("Message utilisé :");
		System.out.println("------------------------------------------------");
		System.out.println(message + "\n");
		/*
		 * Exemple #1
		 */
		System.out.println("------------------------------------------------");
		System.out.println("Exemple avec 2 clés pareilles #1 :");
		System.out.println("------------------------------------------------");

		String cle = "7 1 4 5 2 3 8 6";
		
		//Clé
		System.out.println("Clé: " + cle + "\n");
		
		// Chiffrement
		System.out.println("Message encrypte:");
		String msgEncrype = Chiffrement.chiffrer(message, cle);
		System.out.println(msgEncrype);
		
		System.out.println("\n");
		
		// D�chiffrement
		System.out.println("Message decrypte:");
		String msgDecrypte = Chiffrement.dechiffrer(msgEncrype, cle);
		System.out.println(msgDecrypte);
		
		/*
		 * Exemple #2
		 */
		System.out.println("------------------------------------------------");
		System.out.println("Exemple avec 2 clés pareilles #2 :");
		System.out.println("------------------------------------------------");
		
		cle = "4 5 3 1 2";
		
		//Clé
		System.out.println("Clé: " + cle + "\n");
		
		// Chiffrement
		System.out.println("Message encrypte:");
		msgEncrype = Chiffrement.chiffrer(message, cle);
		System.out.println(msgEncrype);
		
		System.out.println("\n");
		
		// D�chiffrement
		System.out.println("Message decrypte:");
		msgDecrypte = Chiffrement.dechiffrer(msgEncrype, cle);
		System.out.println(msgDecrypte);
		/*
		 * Exemple #3
		 */
		System.out.println("------------------------------------------------");
		System.out.println("Exemple avec 2 clés différentes :");
		System.out.println("------------------------------------------------");
		
		//Clé
		System.out.println("Clé: " + cle + "\n");
		
		// Chiffrement
		System.out.println("Message encrypte:");
		msgEncrype = Chiffrement.chiffrer(message, cle);
		System.out.println(msgEncrype);
		
		System.out.println("\n");
		
		cle = "8 4 3 1 2 6 5 7";
		
		//Clé
		System.out.println("Clé: " + cle + "\n");
		
		// D�chiffrement
		System.out.println("Message decrypte:");
		msgDecrypte = Chiffrement.dechiffrer(msgEncrype, cle);
		System.out.println(msgDecrypte);
	}

}
