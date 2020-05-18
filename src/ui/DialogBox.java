package ui;

import javax.swing.JOptionPane;

class DialogBox {

    DialogBox(String Message) {
        switch (Message) {
            case "WIN":
                JOptionPane.showMessageDialog(null, "You Won !", "Message", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "LOSE":
                JOptionPane.showMessageDialog(null, "You Lose !", "Message", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "LOSE_LIFE":
                JOptionPane.showMessageDialog(null, "You Lose A Life !", "Find the Xit", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

}
