//--------------------------------------------------
// C'est une classe que j'ai créé pour limiter le nombre
// de caractère qu'un JTextField peut avoir
// Exemple d'utilisation: JTextField1.setDocument(new JTextFieldCharLimit(10));
// Le (10) est le nombre de caractère maximum que tu veux avoir
// Les nombres ne doivent pas être inférieur à 0
//--------------------------------------------------
package Paquet_Client;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Andy
 */
public class JTextFieldCharLimit extends PlainDocument{
    
    private int limit;
    
    public JTextFieldCharLimit(int limitation )
    {
        this.limit = limitation;
    }
    public void insertString(int offset, String str, AttributeSet set) throws BadLocationException
    {
        if(str == null)
        {
            return;
        }
        else if((getLength() + str.length()) <= limit)
        {
            super.insertString(offset, str, set);
        }
    }
}
