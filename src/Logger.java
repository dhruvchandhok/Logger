import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Logger {
    private static Logger logger;
    private LogLevel minimumLogLevel;
    private int maxBufferSize;
    private Sink sink;
    private String[] messageBuffer;
    private int currentBufferSize;
    private DateTimeFormatter formatter;


    private final ReadWriteLock readWriteLock
            = new ReentrantReadWriteLock();

    private Logger(LogLevel logLevel,
                   String loggerType,
                   int bufferSize,
                   SinkType sinkType,
                   String timestampFormat) {
        minimumLogLevel = logLevel;
        if (loggerType.equals("SYNC")) {
            this.maxBufferSize = 0;
        } else {
           this.maxBufferSize = bufferSize;
        }

        if (sinkType == SinkType.STDOUT) {
            sink = new StdoutSink();
        }
        this.formatter = DateTimeFormatter.ofPattern(timestampFormat);
        this.currentBufferSize = 0;

    }

    private static void initializeLogger() throws IOException {
        // TODO: parse LogConfiguration
        FileReader fileReader = new FileReader("C:\\Users\\dhruv\\IdeaProjects\\Logger\\src\\LogConfiguration");
        BufferedReader br =  new BufferedReader(fileReader);
        String line;
        // set defaults
        LogLevel logLevel = LogLevel.DEBUG;
        String loggerType = "SYNC";
        int bufferSize = 0;
        SinkType sinkType = SinkType.STDOUT;
        String timestampFormat = "DDMMYYYY";
        while ((line = br.readLine()) != null) {
            // line.split(": ");
            // Override defaults
        }
        logger = new Logger(logLevel, loggerType, bufferSize, sinkType, timestampFormat);
    }

    public static Logger getLogger() throws IOException {

        if (logger == null) {
            synchronized (new Object()) {
                initializeLogger();
            }
        }
        return logger;
    }

    public void debug(String message) {
        if (minimumLogLevel == LogLevel.DEBUG) {
            log(message, LogLevel.DEBUG);
        }
    }

    public void info(String message) {
        if (minimumLogLevel == LogLevel.DEBUG || minimumLogLevel == LogLevel.INFO) {
            log(message, LogLevel.INFO);
        }
    }

    public void warn(String message) {
        if (minimumLogLevel == LogLevel.DEBUG || minimumLogLevel == LogLevel.INFO || minimumLogLevel == LogLevel.WARN) {
            log(message, LogLevel.WARN);
        }
    }

    public void error(String message) {
        if (minimumLogLevel == LogLevel.DEBUG || minimumLogLevel == LogLevel.INFO || minimumLogLevel == LogLevel.WARN
                || minimumLogLevel == LogLevel.ERROR) {
            log(message, LogLevel.ERROR);
        }
    }

    public void fatal(String message) {
        if (minimumLogLevel == LogLevel.DEBUG || minimumLogLevel == LogLevel.INFO || minimumLogLevel == LogLevel.WARN
                || minimumLogLevel == LogLevel.ERROR || minimumLogLevel == LogLevel.FATAL) {
            log(message, LogLevel.FATAL);
        }
    }

    private void log(String message, LogLevel messageLogLevel) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logLevelString = "[" + messageLogLevel.toString() + "]";
        String formattedMessage = timestamp + logLevelString +  message;

        if (maxBufferSize > 0 ) {
            synchronized (logger) {

                if (ifBufferFull() || isBufferTimeout()) {
                    for (String bufferMessage : messageBuffer) {
                        sink.writeMessage(bufferMessage);
                    }
                    currentBufferSize = 0;
                }
            }
        }

        // lock - with priority
        if (maxBufferSize > 0) {
            messageBuffer[currentBufferSize ++] = formattedMessage;
            // Ideal Solution: via conditions - if (currentBufferSize == maxBufferSize) signalBufferFull;
        } else {
            sink.writeMessage(formattedMessage);
        }
        // unlock
    }



    private boolean isBufferTimeout() {
        // TODO
        return false;
    }

    private boolean ifBufferFull() {
        //TODO
        return false;
    }


}
