package drjery.com.easyconnect.util;

import java.net.DatagramSocket;

/**
 * Created by DRJ on 2017/7/5.
 */

public class ConstUtil {

    public static String addr = "192.168.137.1";

    public static String url = "http://"+addr+":8080/EasyConnect/";

    public static int port = 9003;

    public static String MESSAGE_RECEIVE = "New Message Come";

    public static DatagramSocket mySocket;

    public static final int TYPE_RECEIVE = 2;

    public static final int TYPE_SEND = 1;

    public static final int TYPE_STRING = 1;

    public static final int TYPE_PIC= 2;

    public static final int CURRENT_COM_DEV = 0;
}
