import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static final String errorLog = "." + File.separator + "logs" + File.separator;
    private static final String errorDateFormat = "yyyy-MM-dd 'at' HH:mm:ss z";
    private static final String errorEntryDelimiter = "\t";

    public static void errorLog(StackTraceElement l, String error) throws IOException {
        String f = errorLog + l.getClassName().toLowerCase() + "-errors.txt";

        /* Check that file exists, if so append */
        File toMake = new File(f);
        FileWriter fw;
        if (toMake.exists()) {
            fw = new FileWriter(f, true);
        } else {
            toMake.createNewFile();
            fw = new FileWriter(f);
        }

        /* Get current date and time for the log */
        SimpleDateFormat sdf = new SimpleDateFormat(errorDateFormat);
        Date date = new Date(System.currentTimeMillis());
        String dateTimeStr = sdf.format(date);

        BufferedWriter buf = new BufferedWriter(fw);

        buf.write(dateTimeStr);
        buf.write(errorEntryDelimiter);
        buf.write(error);
        buf.write(errorEntryDelimiter);
        buf.write(" @" + l.getClassName() + ".java: " + l.getLineNumber() + "\r\n");

        buf.close();
    }
}
