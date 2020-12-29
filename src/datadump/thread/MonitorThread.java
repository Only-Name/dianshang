package datadump.thread;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MonitorThread implements Runnable {
    private static final Logger logger = Logger.getLogger(MonitorThread.class);
    private String processName;//监测进程的名字
    private Connection conn;
    public Lock lock;

    public void init(String processName, Lock lock, Connection conn) {
        this.processName = processName;
        this.lock = lock;
        this.conn = conn;
    }

    public void run() {
        logger.info("MonitorThread监控进程启动");
        BufferedReader bufferedReader = null;
        String line = null;
        //Process proc = null;
        Session sess = null;
        InputStream stdout = null;
        while (true) {
            try {
                sess = conn.openSession();
                sess.execCommand("ps aux | grep "+processName);
                stdout = new StreamGobbler(sess.getStdout());
                bufferedReader = new BufferedReader(new InputStreamReader(stdout));

                while((line = bufferedReader.readLine()) != null) {
                    if (line.contains("java -jar")) {
                        lock.setRun(true);
                        //logger.info("监控进程正在执行");
                        break;
                    } else {
                        lock.setRun(false);
                        //logger.info("监控进程未执行");
                    }
                }
                sess.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stdout != null)
                        stdout.close();
                    if (bufferedReader != null)
                        bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}









