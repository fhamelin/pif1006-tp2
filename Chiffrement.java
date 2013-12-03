import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

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
	// MÈthodes privÈs pour le chiffrement
	// =======================================================================================
	
	// Transforme la string dans un tableau 2 dimentions de char
	private static char[][] GenererCrypteTabChar(String _message, int _longCle) {
		
		int longMsgs, nbLnChar, posMsg;
		longMsgs = _message.length();
		nbLnChar = (longMsgs / _longCle) + 1;
		posMsg   = 0;
		
		char[][] cMsgs = new char[nbLnChar][_longCle];
		
		// On remplie le tableau de caract√®re par caract√®re
		for (int ligne = 0; ligne < nbLnChar; ligne++)
			for (int colonne = 0; colonne < _longCle; colonne++) 
				if (posMsg < _message.length()) {
					cMsgs[ligne][colonne] = _message.charAt(posMsg);
					posMsg++;
				}
				else 
					cMsgs[ligne][colonne] = '"'; // Caract√®re de msg vide
		return cMsgs;
	}
	
	// g√©n√®re un mapping: clef => code de chiffrement; valeur => position dans la Matrice
	private static Map GenererMapCrypte(String _cles, int _longCle) {
		Map<Integer, Integer> posClefs = new HashMap<Integer,Integer>();
		
		for (int i = 0; i < _longCle; i++)
			posClefs.put(Character.getNumericValue(_cles.charAt(i)), i);
		
		return posClefs;
	}

	// Chiffrement par transposition
	private static String ChiffrerClefTransposition(String _message, String _cle) {
		String sCles, msgCodee = "";
		int longCles, colMatrice;
		char[][] messOrigine;
		
		sCles    = _cle.replaceAll("\\s","");
		longCles = sCles.length();
		
		Map<Integer,Integer> posClefs = GenererMapCrypte(sCles, longCles);
		messOrigine = GenererCrypteTabChar(_message, longCles);
		
		for (int colonne = 0; colonne < messOrigine[0].length; colonne++) {
			// Se positionne sur la bonne colonne de la matrice 
			colMatrice = posClefs.get(colonne+1);
		
			for (int ligne = 0; ligne < messOrigine.length; ligne++) 
				if (messOrigine[ligne][colMatrice] != '"')
					msgCodee += messOrigine[ligne][colMatrice];		
		}
		
		return msgCodee;
	}

	// Chiffrement par bloc CBC
	private static String ChiffrementCBC(String _msgTranspose) {
		
		byte[] bMsgTrans = _msgTranspose.getBytes(); 
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
	// MÈthodes privÈs pour le dÈcriffrement
	// =======================================================================================
	
	// DÈchiffrement par CBC
	private static String DechiffrementCBC(String _message) {
		
		byte[] bMsgCBC   = _message.getBytes();
		byte[] bMsgTrans = new byte[bMsgCBC.length];
		
		for (int i = 0; i < bMsgCBC.length; i++){
			if (i == 0)
				bMsgTrans[i] = (byte)(((int)bMsgCBC[i]) ^ ((int)VI));
			else
				bMsgTrans[i] = (byte)(((int)bMsgCBC[i]) ^ ((int)bMsgCBC[i-1]));
		}
		
		return new String(bMsgTrans);
	}
	
	// DÈchiffrement par transposÈe
	private static String DechiffrementTranspose(String _msgTrans, String _cle) {
		
		String sCles, msgDecodee = "";
		int longCles, colMatrice;
		char[][] msgTrans;
		char[][] msgInitial;
		
		sCles    = _cle.replaceAll("\\s","");
		longCles = sCles.length();
		
		Map<Integer,Integer> posClefs = GenererMapDecrypte(sCles, longCles);
		msgTrans = GenererDecrypteTabChar(_msgTrans, longCles);
		msgInitial = new char[msgTrans.length][msgTrans[0].length];

		/*
		for (int colonne = 0; colonne < msgTrans.length; colonne++) {
			// Se positionne sur la bonne colonne de la matrice 
			colMatrice = posClefs.get(colonne);
		
			for (int ligne = 0; ligne < msgTrans[0].length; ligne++) 
				if (msgTrans[ligne][colMatrice] != '"')
					msgInitial[ligne][colMatrice-1] += msgTrans[ligne][colMatrice];		
		}
		*/
		return msgDecodee;
	}
	
	private static char[][] GenererDecrypteTabChar(String _msgTrans, int _longCle) {
		
		int longMsgs, nbLnChar, posMsg;
		longMsgs = _msgTrans.length();
		nbLnChar = (longMsgs / _longCle) + 1;
		posMsg   = 0;
		
		char[][] cMsgs = new char[nbLnChar][_longCle];
		
		// On remplie le tableau de caract√®re par caract√®re
		for (int colonne = 0; colonne < _longCle; colonne++) 
			for (int ligne = 0; ligne < nbLnChar; ligne++) 
				if (posMsg < _msgTrans.length()) {
					cMsgs[ligne][colonne] = _msgTrans.charAt(posMsg);
					posMsg++;
				}
				else 
					cMsgs[ligne][colonne] = '"'; // Caract√®re de msg vide
		
		return cMsgs;
	}
	
	// GÈnÈrÈ un mapping: clef => code de chiffrement; valeur => position dans la Matrice
	private static Map GenererMapDecrypte(String _cles, int _longCle) {
		Map<Integer, Integer> posClefs = new HashMap<Integer,Integer>();
		
		for (int i = 0; i < _longCle; i++)
			posClefs.put(i, Character.getNumericValue(_cles.charAt(i)));
		
		return posClefs;
	}
}
