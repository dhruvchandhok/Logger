public class StdoutSink implements Sink{
    @Override
    public void writeMessage(String message) {
        System.out.println(message);
    }
}
