package datadump;
import datadump.core.Server;
import datadump.core.Settings;
import datadump.exception.ConfigParamException;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    private static final String CONF_FILE = "conf.properties";

    public static void main(String[] args){
        File conf = new File(CONF_FILE);
        loadSettings(conf);
        Server server = Server.getInstance();
        server.start();
    }

    private static void loadSettings(File file) {
        if (file.exists()) {
        } else {
            logger.error(String.format("未找到系统配置文件[%s],系统终止...", file.getAbsolutePath()));
            System.exit(1);
        }

        //加载conf文件
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException ex) {
            logger.error("读取系统配置文件失败,请检查配置文件内容是否正确,系统终止!!!", ex);
            System.exit(1);
        }

        try {
            Settings.initial(properties);
        } catch (ConfigParamException ex) {
            logger.error(ex.getMessage());
            System.exit(1);
        }
    }
}


