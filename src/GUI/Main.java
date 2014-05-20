
package GUI;

import BLL.BLLError;
import javax.swing.JFrame;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BLLError.getInstance().register(MessageDialog.getInstance());
        JFrame main = GUIAdmin.getInstance();
        main.setVisible(true);
    }
}
