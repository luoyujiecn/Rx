package com.kxj.rx.util;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * 流工具类
 *
 * @author 王小平
 */
public class StreamUtils {
    private static String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36";

    /**
     * 讲一个字节数组输出到指定文件
     *
     * @param datas
     * @param destFile
     * @throws IOException
     */
    public static void bytesToFile(byte[] datas, File destFile)
            throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(destFile));
        try {
            bos.write(datas, 0, datas.length);
            bos.flush();
        } finally {
            closeStream(null, bos);
        }
    }

    /**
     * 将一个输入流输出到文件，会自动关闭传进来的is
     *
     * @param is
     * @param destFile
     * @throws IOException
     */
    public static void inputToFile(InputStream is, File destFile)
            throws IOException {
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        if(!destFile.exists()){
            destFile.createNewFile();
        }
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(destFile));
            bis = new BufferedInputStream(is);
            byte[] buff = new byte[1024 * 2];
            int length = 0;
            while ((length = bis.read(buff)) != -1) {
                bos.write(buff, 0, length);
            }
            bos.flush();
        } finally {
            closeStream(is, bos);
        }
    }

    /**
     * 将一个zipFile解压到指定目录
     *
     * @param zipFile
     * @param outDirFile
     * @throws IOException
     */
    public static void zipEntryToFile(ZipFile zipFile, File outDirFile)
            throws IOException {
        try {
            if (!outDirFile.exists()) {
                outDirFile.mkdirs();
            }
            Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
            while (enumeration.hasMoreElements()) {
                ZipEntry zipEntry = enumeration.nextElement();
                File zipEntryFile = new File(outDirFile, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    if (!zipEntryFile.exists()) {
                        zipEntryFile.mkdirs();
                    }
                } else {
                    BufferedInputStream bis = null;
                    try {
                        bis = new BufferedInputStream(
                                zipFile.getInputStream(zipEntry));
                        StreamUtils.inputToFile(bis, zipEntryFile);
                    } finally {
                        closeStream(bis, null);
                    }
                }
            }
        } finally {
            zipFile.close();
        }
    }

    /**
     * 将一个文件流写入到一个输出流中去
     *
     * @param os   输出流
     * @param file 待写出的文件
     * @throws IOException io异常
     */
    public static void writeFileToOs(OutputStream os, File file)
            throws IOException {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            byte[] buff = new byte[1024 * 2];
            int length = 0;
            while ((length = bis.read(buff)) != -1) {
                os.write(buff, 0, length);
                os.flush();
            }
        } finally {
            closeStream(bis, null);
        }
    }

    /**
     * 关闭输入输出流
     *
     * @param is
     */
    public static void closeStream(Closeable... is) {
        if (is != null) {
            for (Closeable closeable : is) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 读取下载文件指令.从命令输入流中读取一条指令,命令以回车分割. 从输入流中逐个读取直到流末尾，或者遇到换行符,然后将读取出的字节组成一个字符串
     *
     * @param is 命令输入流
     * @return 返回读取到的指令
     * @throws IOException
     */
    public static String readGetTaskFileCommand(InputStream is)
            throws IOException {
        ByteArrayOutputStream byteArrayOs = new ByteArrayOutputStream();
        int readData = -1;
        while (true) {
            readData = is.read();
            if (readData == -1 || readData == '\n') {
                break;
            }
            if (readData != '\r') {
                byteArrayOs.write(readData);
            }
        }
        return byteArrayOs.toString();
    }

    /**
     * 把一个输入流按照默认编码方式转换成为一个字符串
     *
     * @param is 输入流
     * @return
     * @throws IOException
     */
    public static String inputToString(InputStream is) throws IOException {
        return inputToString(is, null);
    }

    /**
     * 把一个输入流按照指定编码方式转换成为一个字符串
     *
     * @param is      输入流
     * @param charset 编码放置
     * @return
     * @throws IOException
     */
    public static String inputToString(InputStream is, String charset)
            throws IOException {
        String result;
        byte[] bytesResult = inputToBytes(is);
        if (charset == null) {
            result = new String(bytesResult);
        } else {
            result = new String(bytesResult, charset);
        }
        return result;
    }

    /**
     * 把一个输入流转换成为一个字节数组
     *
     * @param is 输入流
     * @return
     * @throws IOException
     */
    public static byte[] inputToBytes(InputStream is) throws IOException {
        ByteArrayOutputStream arrayOutputStream = null;
        try {
            arrayOutputStream = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = -1;
            while ((len = is.read(buff)) != -1) {
                arrayOutputStream.write(buff, 0, len);
            }
            byte[] result = arrayOutputStream.toByteArray();
            return result;
        } finally {
            if (arrayOutputStream != null) {
                try {
                    arrayOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将一个字符串对应内容保存到指定文件
     *
     * @param str
     * @param file
     * @throws IOException
     */
    public static void stringToFile(String str, File file) throws IOException {
        InputStream is = null;
        try {
            is = new ByteArrayInputStream(str.getBytes());
            inputToFile(is, file);
        } finally {
            closeStream(is, null);
        }
    }

    /**
     * 将一个url对应内容返回为一个字符串
     *
     * @param urlStr
     * @throws IOException
     */
    public static String urlToString(String urlStr, String charset)
            throws IOException {
        String result = null;
        URL url = new URL(urlStr);
        InputStream is = null;
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", userAgent);
            conn.setReadTimeout(1000 * 10);
            if (conn instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn)
                        .setHostnameVerifier(new HostnameVerifier() {
                            @Override
                            public boolean verify(String arg0, SSLSession arg1) {
                                return true;
                            }
                        });
            }
            is = conn.getInputStream();

            boolean gzip = false;
            String ce = conn.getHeaderField("Content-Encoding");
            if (ce != null && ce.equalsIgnoreCase("gzip")) {
                gzip = true;
            }
            result = inputToString(is, gzip, charset);
        } finally {
            closeStream(is, null);
            if (conn != null) {
                conn.disconnect();
            }
        }
        //System.out.println(result);
        return result;
    }

    /**
     * 支持gzip的inputToString 方法
     *
     * @param is
     * @param gzip
     * @return
     * @throws IOException
     */
    private static String inputToString(InputStream is, boolean gzip,
                                        String charset) throws IOException {
        if (!gzip) {
            return inputToString(is, charset);
        } else {
            byte[] bytes = inputToBytes(is);
            GZIPInputStream gzipInputStream = null;
            try {
                gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(
                        bytes));
                return inputToString(gzipInputStream, charset);
            } finally {
                closeStream(gzipInputStream, null);
            }
        }
    }

    /**
     * 获取跳转之后的真实连接
     *
     * @param sourceUrl
     * @return
     */
    public static String urlTo3xxUrl(String sourceUrl) {

        HttpURLConnection conn = null;
        try {
            HttpURLConnection.setFollowRedirects(false);
            URL url = new URL(sourceUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (conn instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn)
                        .setHostnameVerifier(new HostnameVerifier() {
                            @Override
                            public boolean verify(String arg0, SSLSession arg1) {
                                return true;
                            }
                        });
            }
            int code = conn.getResponseCode();
            if (code >= 300 && code < 400) {
                String realUrl = conn.getHeaderField("Location");
                return realUrl;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HttpURLConnection.setFollowRedirects(true);
            if (conn != null) {
                conn.disconnect();
            }
        }
        return sourceUrl;
    }

    /**
     * 将一个url对应内容保存到指定文件
     *
     * @param urlStr
     * @param file
     * @throws IOException
     */
    public static boolean urlToFile(String urlStr, File file) throws IOException {
        boolean isOk = true;
        URL url = new URL(urlStr);
        InputStream is = null;
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", userAgent);
            conn.setReadTimeout(1000 * 10);
            is = conn.getInputStream();
            inputToFile(is, file);
        }catch (Exception e){
            isOk = false;
        }finally {
            closeStream(is, null);
            if (conn != null) {
                conn.disconnect();
            }
        }
        return isOk;
    }

    public static void writeEndLine(String url, String text){
        try{
            FileReader fr = new FileReader(new File(url));
            BufferedReader br = new BufferedReader(fr);
            String s;
            StringBuilder sb = new StringBuilder();
            int i = 0;
            //读文件内容
            while((s=br.readLine())!=null){
                sb.append(s).append("\n");
            }
            //对内容进行截取去掉最后一个\n,然后截取去掉最后一行，最后加入你想要的内容
            System.out.println(sb.substring(0, sb.toString()
                    .substring(0, sb.length()-1).lastIndexOf("\n"))+"\n"+text);
            fr.close();
            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 通过文件获得字符串
     * @param path
     * @return
     */
    public static String file2String(String path) {
        String text = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(path)));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                text = text + tempString;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return text;
    }

    /**
     * 将文件内容转为list 适用于内容为一行一行的文件
     * @param filePath
     * @return
     */
    public static ArrayList<String> file2ArrayList(String filePath){
        ArrayList<String> list = new ArrayList();
        BufferedReader br = null;
        String line = "";
        try{
            br = new BufferedReader(
                    new FileReader(new File(filePath)));
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 将list内容转为文件  适用于内容为一行一行的文件
     * @param list
     */
    public static void arrayList2File(ArrayList<String> list, String destPath){
        FileWriter fw = null;
        try{
            File file = new File(destPath);
            if(!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            fw = new FileWriter(file);
            for(String line:list){
                fw.write(line+"\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (fw != null) {
                    fw.flush();
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取Assets文件内容
     * @param fileName
     * @param context
     * @return
     */
    public static String getFromAssets(String fileName, Context context) {
        String line = "";
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            while ((line = bufReader.readLine()) != null) {
                Result += line + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }
}
