package com.zenchn.apilib.util;


import android.util.Log;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 作    者：wangr on 2017/4/24 11:09
 * 描    述：ZIP压缩工具类
 * 修订记录：
 */
public class GZIPUtils {
    private static final String TAG = "GZIPUtils";

    private GZIPUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * GZIP解压
     *
     * @param sourcePath
     * @param targetPath
     * @return
     */
    public static boolean unZip(String sourcePath, String targetPath) {
        return unZip(new File(sourcePath), new File(targetPath));
    }

    /**
     * GZIP解压
     *
     * @param sourceFile
     * @param targetFile
     * @return
     */
    public static boolean unZip(File sourceFile, File targetFile) {
        boolean result = false;
        InputStream in = null;
        OutputStream os = null;
        try {
            in = new FileInputStream(sourceFile);
            os = new FileOutputStream(targetFile);
            result = unZip(in, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } finally {
            JavaKit.close(in, os);
        }
        return result;
    }

    /**
     * GZIP解压
     *
     * @param in
     * @param os
     */
    public static boolean unZip(InputStream in, OutputStream os) {
        boolean result = false;
        InputStream is = null;
        try {
            is = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.flush();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } finally {
            JavaKit.close(is, os);
        }
        return result;
    }

    /**
     * GZIP解压数据库到data
     *
     * @param in
     * @param packageName
     */
    public static boolean unZipDB(InputStream in, String packageName, String db_name) {
        String DB_PATH = "/data/data/" + packageName + "/databases/";
        boolean result = false;
        InputStream is = null;
        OutputStream myOutput = null;
        try {
            String outFileName = DB_PATH + db_name;
            File file = new File(outFileName);
            if (!file.mkdirs()) {
                file.mkdirs();
            }
            File dataFile = new File(outFileName);
            if (dataFile.exists()) {
                dataFile.delete();
            }
            myOutput = new FileOutputStream(outFileName);
            result=unZip(in,myOutput);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } finally {
            JavaKit.close(is, myOutput);
        }
        return result;
    }

    /**
     * GZIP压缩
     *
     * @param sourcePath
     * @param targetPath
     * @return
     */
    public static boolean zip(String sourcePath, String targetPath) {
        return zip(new File(sourcePath), new File(targetPath));
    }


    /**
     * GZIP压缩
     *
     * @param sourceFile
     * @param targetFile
     * @return
     */
    public static boolean zip(File sourceFile, File targetFile) {
        boolean result = false;
        InputStream in = null;
        OutputStream os = null;
        try {
            in = new FileInputStream(sourceFile);
            os = new FileOutputStream(targetFile);
            result = zip(in, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } finally {
            JavaKit.close(in, os);
        }
        return result;
    }

    /**
     * Gzip压缩
     * @param in
     * @param targetPath
     * @return
     */
    public static boolean zipDB(InputStream in,String targetPath){
        boolean result = false;
        OutputStream myOutput = null;
        try {
            File file = new File(targetPath);
            if (!file.mkdirs()) {
                file.mkdirs();
            }
            File dataFile = new File(targetPath);
            if (dataFile.exists()) {
                dataFile.delete();
            }

            myOutput = new FileOutputStream(targetPath);
            result=zip(in,myOutput);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } finally {
            JavaKit.close(myOutput);
        }
        return result;
    }


    /**
     * GZIP压缩
     *
     * @param in 要压缩的文件输入流
     * @param os 压缩后的文件的输出流
     * @throws Exception
     */
    public static boolean zip(InputStream in, OutputStream os) {
        boolean result = false;
        OutputStream out = null;
        try {
            out = new GZIPOutputStream(os);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } finally {
            JavaKit.close(out, in);
        }
        return result;
    }
}
