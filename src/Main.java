import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger();
        logger.info("First Log");
        logger.debug("is it really logging fine?");
    }
}