import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Chiffrement 
{
	public String chiffrer(String message, String cle)
	{
		String msgTransposee;
		String msgCBC;
		
		msgTransposee = ChiffrerClefTransposition(message, cle);
		msgCBC = ChiffrementCBC(msgTransposee);
		
		return msgCBC;
	}
	
	public String dechiffrer(String message, String cle)
	{
		return "";
	}
	
	// Transforme la string dans un tableau 2 dimentions de char
	private char[][] GenererTabChar(String _message, int _longCle) {
		
		int longMsgs, nbLnChar, posMsg;
		longMsgs = _message.length();
		nbLnChar = (longMsgs / _longCle) + 1;
		posMsg   = 0;
		
		char[][] cMsgs = new char[nbLnChar][_longCle];
		
		// On remplie le tableau de caractère par caractère
		for (int ligne = 0; ligne < nbLnChar; ligne++)
			for (int colonne = 0; colonne < _longCle; colonne++) 
				if (posMsg < _message.length()) {
					cMsgs[ligne][colonne] = _message.charAt(posMsg);
					posMsg++;
				}
				else 
					cMsgs[ligne][colonne] = '"'; // Caractère de msg vide
		return cMsgs;
	}
	
	// génère un mapping: clef => code de chiffrement; valeur => position dans la Matrice
	private Map GenererTabAsso(String _cles, int _longCle) {
		Map<Integer, Integer> posClefs = new HashMap<Integer,Integer>();
		
		for (int i = 0; i < _longCle; i++)
			posClefs.put(Character.getNumericValue(_cles.charAt(i)), i);
		
		return posClefs;
	}

	// Chiffrement par transposition
	private String ChiffrerClefTransposition(String _message, String _cle) {
		String sCles, msgCodee = "";
		int longCles, colMatrice;
		char[][] messOrigine;
		
		sCles    = _cle.replaceAll("\\s","");
		longCles = sCles.length();
		
		Map<Integer,Integer> posClefs = GenererTabAsso(sCles, longCles);
		messOrigine = GenererTabChar(_message, longCles);
		
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
	private String ChiffrementCBC(String _msgTranspose) {
		byte VI = 111;
		
		byte[] bMsgTrans = _msgTranspose.getBytes();
		byte[] bMsgCBC   = new byte[bMsgTrans.length];
		
		for (int i = 0; i < bMsgTrans.length; i++){
			if (i == 0) 
				bMsgCBC[i] = (byte)(((int)bMsgTrans[i]) ^ ((int)VI));
			else
				bMsgCBC[i] = (byte)(((int)bMsgTrans[i]) ^ ((int)bMsgTrans[i-1]));
		}
		
		return new String(bMsgCBC);
	}
	
}
