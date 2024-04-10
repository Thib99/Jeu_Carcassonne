package JeuTuilesGenerique.Vue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.* ;

public class ButtonImage extends JButton{

    ButtonImage(String NameImage){
        this(NameImage, new Rectangle(15,15, 50 , 50)) ;
    }

    ButtonImage(String NameImage, Rectangle d){

        try {
            Image img;
            img = ImageIO.read(getClass().getResource("Images/" + NameImage));
            this.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setBounds(d);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setVisible(true);
    }
}