import javax.swing.JOptionPane;

public class DialogBox {

	public DialogBox(String Message) {
		// TODO Auto-generated constructor stub
		if(Message == "WIN") {
			JOptionPane.showMessageDialog(null, "You Won !", "Message", JOptionPane.INFORMATION_MESSAGE);
		}else if(Message == "LOSE") {
			JOptionPane.showMessageDialog(null, "You Lose !", "Message", JOptionPane.INFORMATION_MESSAGE);
		}else if(Message == "LOSE_LIFE") {
			JOptionPane.showMessageDialog(null, "You Lose A Life !", "Find the Xit", JOptionPane.ERROR_MESSAGE);
		}
	}

}
