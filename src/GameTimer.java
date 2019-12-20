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
    private Date curr;
    private SimpleDateFormat sdf;
    private volatile boolean stopThread = false;
    private Thread stopWatchThread;

    public GameTimer() {

        this.setFont(new Font(fontFam, Font.BOLD, fontSz));
        this.setEditable(false);
        this.setHorizontalAlignment(CENTER);
        this.setForeground(Color.BLACK);
        this.setBorder(BorderFactory.createEmptyBorder());

        this.start = new Date();
        this.curr = new Date();

        this.sdf = new SimpleDateFormat(format);

        this.stopWatchThread = new Thread(this);
    }

    @Override
    public void run() {
        while (!stopThread) {
            this.curr = new Date();
            String time = this.sdf.format(this.curr.getTime() - this.start.getTime());
            this.setText(time);

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // TODO: find a better way to do this
    // I have a feeling its behavior is not consistent
    public String totalTime() {
        return this.sdf.format(this.curr.getTime() - this.start.getTime());
    }

    public void startTimer() {
        this.stopWatchThread.start();
    }

    public void stopTimer() {
        this.stopThread = true;
    }
}
