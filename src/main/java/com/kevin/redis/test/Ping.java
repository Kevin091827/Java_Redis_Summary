package com.kevin.redis.test;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Ping {

    /**
     * 判断IP是否可用
     * @param ipAddress
     * @return
     * @throws Exception
     */
    public static boolean ping(String ipAddress) throws Exception {
        // 超时应该在3钞以上
        int  timeOut =  3000 ;
        // 当返回值是true时，说明host是可用的，false则不可。
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);
        log.info("该IP地址是可用的！");
        return status;
    }


    /**
     * 直接调用cmd命令行执行ping命令
     * @param ipAddress
     * @throws Exception
     */
    public static void cmdPing(String ipAddress) throws Exception {
        // 执行cmd命令后输出内容
        String line = null;
        try {
            // 直接调用cmd命令行执行ping命令
            Process pro = Runtime.getRuntime().exec("ping " + ipAddress);
            BufferedReader buf = new BufferedReader(new InputStreamReader(
                    pro.getInputStream(),"GBK"));
            // 输出cmd命令执行结果
            log.info("ping命令结果：");
            while ((line = buf.readLine()) != null) {
                log.info(line);
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }

    /**
     * cmd命令执行指定IP,指定ping次数，指定超时时间的ping命令
     * @param ipAddress
     * @param pingTimes
     * @param timeOut
     */
    public static void cmdPing(String ipAddress, int pingTimes, int timeOut) {

        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();
        // 将要执行的ping命令,此命令是windows格式的命令
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes    + " -w " + timeOut;
        try {
            log.info(pingCommand);
            // 执行命令并获取输出
            Process p = r.exec(pingCommand);
            if (p != null) {
                in = new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));
                // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
                String line = null;
                while ((line = in.readLine()) != null) {
                    log.info(line);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String ipAddress = "127.0.0.1";
        System.out.println(ping(ipAddress));
      //  cmdPing(ipAddress);
        cmdPing(ipAddress, -1, 5000);
    }
}