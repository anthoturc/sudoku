import javax.swing.*;
import java.awt.*;

public class SudokuCell extends JTextField {

    private static final int fontSz = 25;
    private static final String fontFam = "Arial";
    private static final String defaultVal = "";

    private char solution;

    public SudokuCell(char solution) {
        this.solution = solution;
        this.setFont(new Font(fontFam, Font.BOLD, fontSz));
        this.setText(defaultVal);
        this.setEditable(true);
        this.setHorizontalAlignment(JTextField.CENTER);
        this.setForeground(Color.BLACK);
    }

    public String getSolution() {
        return "" + this.solution;
    }
}
