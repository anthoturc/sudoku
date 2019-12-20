import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameTimer extends JTextField implements Runnable {

    private static final String timeZone = "GMT";
    private static final String format = "mm:ss";
    private static final String fontFam = "Monospace";
    private static final int fontSz = 30;
    private static final int sleepTime = 100; // ms

    private Date start;
    private SimpleDateFormat sdf;

    public GameTimer() {

        this.setFont(new Font(fontFam, Font.BOLD, fontSz));
        this.setEditable(false);
        this.setHorizontalAlignment(CENTER);
        this.setForeground(Color.BLACK);
        this.setBorder(BorderFactory.createEmptyBorder());

        this.start = new Date();
        this.sdf = new SimpleDateFormat(format);

        Thread watchThread = new Thread(this);
        watchThread.start();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            Date now = new Date();
            String time = this.sdf.format(now.getTime() - this.start.getTime());
            this.setText(time);

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
