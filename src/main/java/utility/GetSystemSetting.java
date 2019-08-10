package utility;

import com.sap.conn.jco.support.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetSystemSetting {
    private static Properties pro;

    public static String getValueFromSetting(String property) throws IOException {
        InputStream inputStream = GetSystemSetting.class.getClassLoader().getResourceAsStream("start.properties");
        try {
            pro = new Properties();
            pro.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pro.getProperty(property);
    }

}
