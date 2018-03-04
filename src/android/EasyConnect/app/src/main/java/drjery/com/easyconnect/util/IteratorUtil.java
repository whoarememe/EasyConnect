package drjery.com.easyconnect.util;

import java.util.Iterator;

/**
 * Created by DRJ on 2017/7/10.
 */

public class IteratorUtil {

    public static int getSize(Iterator<String> keys)
    {
        int i = 0;
        while (keys.hasNext())
        {
            i ++;
        }
        return i;
    }
}
