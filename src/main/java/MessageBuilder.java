public class MessageBuilder {
    //MARK
    private static final String TAG_HEADER = "Get hint I am (TAG) :";
    private static final String PORT_HEADER = "This is hint, please response (Port) :";

    public static String buildWithPort(int port) {
        return PORT_HEADER + port;
    }

    public static int parsePort(String data) {
        if (data.startsWith(PORT_HEADER)) {
            return Integer.parseInt(data.substring(PORT_HEADER.length()));
        }
        return -1;
    }

    public static String buildWithTAG(String  tag) {
        return TAG_HEADER + tag;
    }

    public static String  parseTAG(String data) {
        if (data.startsWith(TAG_HEADER)) {
            return data.substring(TAG_HEADER.length());
        }
        return null;
    }


}
