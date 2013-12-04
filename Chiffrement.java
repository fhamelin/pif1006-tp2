import java.util.HashMap;
import java.util.Map;
import java.nio.charset.Charset;

public abstract class Chiffrement 
{
	private static byte VI = 111;
	
	public static String chiffrer(String message, String cle)
	{
		String msgTransposee;
		String msgCBC;
		
		msgTransposee = ChiffrerClefTransposition(message, cle);
		msgCBC = ChiffrementCBC(msgTransposee);
		
		return msgCBC;
	}
	
	public static String dechiffrer(String message, String cle)
	{
		String msgTransposee;
		String msgInitial;
		
		msgTransposee = DechiffrementCBC(message);
		msgInitial    = DechiffrementTranspose(msgTransposee, cle);
		
		return msgInitial;
	}
	
	// =======================================================================================
	// M�thodes priv�s pour le chiffrement
	// =======================================================================================
	
	// Chiffrement par transposition
	private static String ChiffrerClefTransposition(String _message, String _cle) {
		String cle[] = _cle.split(" "); //Clé de chiffrement sous forme de tableau
		char[][] messCode = new char[(int)Math.ceil((double)(_message.length()) / (double)(cle.length))][cle.length]; //Message codé sous forme de tableau compatible avec la clé
		
		//Ajout des caractères du message dans le tableau du bon format
		for (int ligne = 0;ligne < messCode.length;ligne++) {
			for (int colonne = 0;colonne < messCode[ligne].length;colonne++) {
				if (ligne * cle.length + colonne >= _message.length()) break;
				messCode[ligne][Integer.parseInt(cle[colonne]) - 1] = _message.charAt(ligne * cle.length + colonne);
			}
		}
		
		String strMessCode = ""; // Message codé sous forme de string
		
		//On remet le message codé dans la string
		for (int colonne = 0; colonne < cle.length; colonne++) {
			for (int ligne = 0;ligne < messCode.length; ligne++) {
				if (messCode[ligne][colonne] != '\u0000') {
					strMessCode += messCode[ligne][colonne];
				}
			}
		}
		
		return strMessCode;
	}

	// Chiffrement par bloc CBC
	private static String ChiffrementCBC(String _msgTranspose) {
		byte[] bMsgTrans = _msgTranspose.getBytes(Charset.forName("US-ASCII")); 
		byte[] bMsgCBC   = new byte[bMsgTrans.length];
		
		for (int i = 0; i < bMsgTrans.length; i++){ // bMsgTrans.length
			if (i == 0) 
				bMsgCBC[i] = (byte)(((int)bMsgTrans[i]) ^ ((int)VI));
			else
				bMsgCBC[i] = (byte)(((int)bMsgTrans[i]) ^ ((int)bMsgCBC[i-1]));
		}
		
		return new String(bMsgCBC);
	}
	
	// =======================================================================================
	// M�thodes priv�s pour le d�criffrement
	// =======================================================================================
	
	// D�chiffrement par CBC
	private static String DechiffrementCBC(String _message) {
		
		byte[] bMsgCBC   = _message.getBytes(Charset.forName("US-ASCII"));
		byte[] bMsgTrans = new byte[bMsgCBC.length];
		
		for (int i = bMsgCBC.length - 1; i >= 0 ; i--){
			if (i == 0)
				bMsgTrans[i] = (byte)(((int)bMsgCBC[i]) ^ ((int)VI));
			else
				bMsgTrans[i] = (byte)(((int)bMsgCBC[i]) ^ ((int)bMsgCBC[i-1]));
		}
		
		return new String(bMsgTrans);
	}
	
	// D�chiffrement par transpos�e
	private static String DechiffrementTranspose(String _message, String _cle) {
		String cle[] = _cle.split(" "); //Clé de chiffrement sous forme de tableau
		char[][] messOrigine = new char[(int)Math.ceil((double)(_message.length()) / (double)(cle.length))][cle.length]; //Message codé sous forme de tableau compatible avec la clé
		
		int colNonNull = _message.length() % cle.length; //Nombre de colonne du tableau qui ne possèdent pas de caractère null
		int colonneADechiffrer = 0; //Colonne à déchiffrer
		int charAt = 0; //Position courante dans la chaîne de caractères encodée
		
		//On répète l'opération jusqu'à ce que toutes les colonnes aient été déchiffrées
		while (colonneADechiffrer < cle.length) {
			for (int colonne = 0; colonne < cle.length; colonne++) {
				if (Integer.parseInt(cle[colonne]) - 1 != colonneADechiffrer) {
					continue;
				}
				
				int nbLignes = colonne < colNonNull ? messOrigine.length : messOrigine.length - 1; //Nombre de lignes pour la colonne en cours
				
				for (int ligne = 0;ligne < nbLignes; ligne++) {
					messOrigine[ligne][colonne] = _message.charAt(charAt++);
				}
			}
			colonneADechiffrer++;
		}
		
		String strMessOrigine = ""; //Message d'origine 
		
		//Ajout des caractères du tableau dans le string
		for (int ligne = 0;ligne < messOrigine.length;ligne++) {
			for (int colonne = 0;colonne < messOrigine[ligne].length;colonne++) {
				if (ligne * cle.length + colonne >= _message.length()) break;
				strMessOrigine += messOrigine[ligne][colonne];
			}
		}
		
		return strMessOrigine;
	}
}
