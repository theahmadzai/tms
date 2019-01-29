package immortal.util;

import javax.swing.JPanel;

public class ComboItem extends JPanel {
    public int value;
    public String label;

    public ComboItem(int value, String label){
        this.value = value;
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
