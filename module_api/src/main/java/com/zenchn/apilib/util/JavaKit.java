package com.zenchn.apilib.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 作    者：wangr on 2017/5/25 14:36
 * 描    述： java常用的封装
 * 修订记录：
 */
public class JavaKit {

    private static final String TAG = "JavaKit";

    /**
     * 判断非空相关（字符串、数组、集合等）
     * --------------------------------------------------------------------
     */

    /**
     * 判断一个字符串是否为空
     *
     * @param charSequence
     * @return
     */
    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    /**
     * 判断多个字符串是否为空
     *
     * @param charSequences
     * @return
     */
    public static boolean isEmpty(CharSequence... charSequences) {
        for (CharSequence charSequence : charSequences) {
            if (isEmpty(charSequence))
                return true;
        }
        return false;
    }

    /**
     * 判断数组是否为空
     *
     * @param objects
     * @return
     */
    public static boolean isEmpty(Object[] objects) {
        return null == objects || objects.length == 0;
    }

    /**
     * 判断一个集合是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * 判断一个集合是否为空
     *
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }

    /**
     * 判断一个集合有内容(非空)
     *
     * @param collection
     * @return
     */
    public static boolean isNonNull(Collection<?> collection) {
        return null != collection && !collection.isEmpty();
    }

    /**
     * 判断一个集合有内容(非空)
     *
     * @param map
     * @return
     */
    public static boolean isNonNull(Map<?, ?> map) {
        return null != map && !map.isEmpty();
    }

    /**
     * 关闭相关
     * --------------------------------------------------------------------
     */

    /**
     * 关闭IO
     *
     * @param closeables closeables
     */
    public static void close(Closeable... closeables) {
        if (closeables == null)
            return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    /**
     * 安静关闭IO
     *
     * @param closeables closeables
     */
    public static void closeQuietly(Closeable... closeables) {
        if (closeables == null)
            return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 作    者：wangr on 2017/5/23 15:37
     * 描    述：生成随机长度字符串的封装类
     * 修订记录：
     */
    public static class Random {

        public static final String ARABIC_NUMBERS = "0123456789";
        public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
        public static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String ALL_LETTERS = CAPITAL_LETTERS + LOWER_CASE_LETTERS;
        public static final String NUMBERS_AND_LETTERS = ARABIC_NUMBERS + ALL_LETTERS;

        /**
         * 生成随机长度的字符串（包含大小写字母和数字）
         *
         * @param length
         * @return
         */
        public static String getRandomNumbersAndLetters(int length) {
            return getRandom(NUMBERS_AND_LETTERS, length);
        }

        /**
         * 生成随机长度的数字串
         *
         * @param length
         * @return
         */
        public static String getRandomNumbers(int length) {
            return getRandom(ARABIC_NUMBERS, length);
        }

        /**
         * 生成随机长度的字母串（包含大小写）
         *
         * @param length
         * @return
         */
        public static String getRandomLetters(int length) {
            return getRandom(ALL_LETTERS, length);
        }

        /**
         * 生成随机长度的字母串（大写）
         *
         * @param length
         * @return
         */
        public static String getRandomCapitalLetters(int length) {
            return getRandom(CAPITAL_LETTERS, length);
        }

        /**
         * 生成随机长度的字母串（小写）
         *
         * @param length
         * @return
         */
        public static String getRandomLowerCaseLetters(int length) {
            return getRandom(LOWER_CASE_LETTERS, length);
        }

        /**
         * 生成随机长度的字符串（指定字符范围）
         *
         * @param length
         * @return
         */
        public static String getRandom(String source, int length) {
            return isEmpty(source) ? null : getRandom(source.toCharArray(), length);
        }

        /**
         * 生成随机长度的字符串（指定字符范围）
         *
         * @param length
         * @return
         */
        public static String getRandom(char[] sourceChar, int length) {
            if (isEmpty(Arrays.asList(sourceChar)) || length < 0) {
                return null;
            }

            StringBuilder sb = new StringBuilder(length);
            java.util.Random random = new java.util.Random();
            for (int i = 0; i < length; i++) {
                sb.append(sourceChar[random.nextInt(sourceChar.length)]);
            }
            return sb.toString();
        }

        /**
         * 生成 0 - |max| 或者 -|max| - 0 之间的随机数
         *
         * @param max
         * @return
         */
        public static int getRandom(int max) {
            return getRandom(0, max);
        }

        /**
         * 生成两个数字之间的随机数
         *
         * @param lower
         * @param upper
         * @return
         */
        public static int getRandom(int lower, int upper) {
            int min = Math.min(lower, upper);
            int scope = Math.abs(upper - lower);
            return new java.util.Random().nextInt(scope) + min;
        }
    }

    /**
     * 作    者：wangr on 2017/5/23 16:16
     * 描    述：文件操作的封装类
     * 修订记录：
     */
    public static class File {

        private final static String DEFAULT_CHARSET_NAME = "UTF-8";
        private final static String FILE_EXTENSION_SEPARATOR = ".";

        /**
         * 读取文件
         *
         * @param filePath
         * @param charsetName
         * @return if file not exist, return null, else return content of file
         */
        public static StringBuilder readFile(String filePath, String charsetName) {

            java.io.File file = new java.io.File(filePath);
            StringBuilder fileContent = new StringBuilder("");

            if (!file.isFile()) {
                return null;
            }

            BufferedReader bufferedReader = null;
            try {

                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), isEmpty(charsetName) ? DEFAULT_CHARSET_NAME : charsetName);
                bufferedReader = new BufferedReader(inputStreamReader);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    if (!fileContent.toString().equals("")) {
                        fileContent.append("\r\n");
                    }
                    fileContent.append(line);
                }
                return fileContent;

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
                return fileContent;

            } finally {
                closeQuietly(bufferedReader);
            }

        }

        /**
         * 将字符串写入到文件（可选是否追加）
         *
         * @param filePath
         * @param content
         * @param append   is append, if true, write to the end of file, else clear
         *                 content of file and write into it
         * @return return false if content is empty or exception occurred, true otherwise
         */
        public static boolean writeFile(String filePath, String content, boolean append) {

            if (isEmpty(content)) {
                return false;
            }

            FileWriter fileWriter = null;
            try {
                makeDirs(filePath);
                fileWriter = new FileWriter(filePath, append);
                fileWriter.write(content);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
                return false;
            } finally {
                close(fileWriter);
            }
        }

        /**
         * 将集合写入到文件（可选是否追加）
         *
         * @param filePath
         * @param contentList
         * @param append
         * @return
         */
        public static boolean writeFile(String filePath, List<String> contentList, boolean append) {
            if (contentList == null || contentList.isEmpty()) {
                return false;
            }

            FileWriter fileWriter = null;
            try {
                makeDirs(filePath);
                fileWriter = new FileWriter(filePath, append);
                int i = 0;
                for (String line : contentList) {
                    if (i++ > 0) {
                        fileWriter.write("\r\n");
                    }
                    fileWriter.write(line);
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
                return false;
            } finally {
                close(fileWriter);
            }
        }

        /**
         * 将字符串写入到文件（从文件的开始写入）
         *
         * @param filePath
         * @param content
         * @return
         */
        public static boolean writeFile(String filePath, String content) {
            return writeFile(filePath, content, false);
        }

        /**
         * 将集合写入到文件（从文件的开始写入）
         *
         * @param filePath
         * @param contentList
         * @return
         */
        public static boolean writeFile(String filePath, List<String> contentList) {
            return writeFile(filePath, contentList, false);
        }

        /**
         * 将输入流写入到文件（从文件的开始写入）
         *
         * @param filePath
         * @param inputStream
         * @return
         */
        public static boolean writeFile(String filePath, InputStream inputStream) {
            return writeFile(filePath, inputStream, false);
        }

        /**
         * 将输入流写入到文件（可选是否追加）
         *
         * @param filePath
         * @param inputStream
         * @return
         */
        public static boolean writeFile(String filePath, InputStream inputStream, boolean append) {
            return writeFile(filePath != null ? new java.io.File(filePath) : null, inputStream, append);
        }

        /**
         * 将输入流写入到文件（从文件的开始写入）
         *
         * @param file
         * @param inputStream
         * @return
         */
        public static boolean writeFile(java.io.File file, InputStream inputStream) {
            return writeFile(file, inputStream, false);
        }

        /**
         * 将输入流写入到文件（可选是否追加）
         *
         * @param file
         * @param inputStream
         * @param append
         * @return
         */
        public static boolean writeFile(java.io.File file, InputStream inputStream, boolean append) {
            OutputStream outputStream = null;
            try {
                makeDirs(file.getAbsolutePath());
                outputStream = new FileOutputStream(file, append);
                byte data[] = new byte[1024];
                int length = -1;
                while ((length = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, length);
                }
                outputStream.flush();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
                return false;
            } finally {
                close(outputStream, inputStream);
            }
        }

        /**
         * 移动文件
         *
         * @param sourceFilePath
         * @param destFilePath
         */
        public static void moveFile(String sourceFilePath, String destFilePath) throws RuntimeException {
            if (isEmpty(sourceFilePath, destFilePath)) {
                Log.e(TAG,"Both sourceFilePath and destFilePath cannot be null.");
                throw new RuntimeException("Both sourceFilePath and destFilePath cannot be null.");
            }
            moveFile(new java.io.File(sourceFilePath), new java.io.File(destFilePath));
        }

        /**
         * 移动文件
         *
         * @param srcFile
         * @param destFile
         */
        public static void moveFile(java.io.File srcFile, java.io.File destFile) {
            boolean rename = srcFile.renameTo(destFile);
            if (!rename) {
                copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
                deleteFile(srcFile.getAbsolutePath());
            }
        }

        /**
         * 复制文件
         *
         * @param sourceFilePath
         * @param destFilePath
         * @return
         * @throws RuntimeException if an error occurs while operator FileOutputStream
         */
        public static boolean copyFile(String sourceFilePath, String destFilePath) {
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(sourceFilePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
                return false;
            }
            return writeFile(destFilePath, inputStream);
        }

        /**
         * 读取文件（每一行内容放到集合中）
         *
         * @param filePath
         * @param charsetName The name of a supported
         * @return if file not exist, return null, else return content of file
         * @throws RuntimeException if an error occurs while operator BufferedReader
         */
        public static List<String> readFileToList(String filePath, String charsetName) {
            java.io.File file = new java.io.File(filePath);
            List<String> fileContent = new ArrayList<>();
            if (!file.isFile()) {
                return null;
            }

            BufferedReader reader = null;
            try {
                InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
                reader = new BufferedReader(is);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    fileContent.add(line);
                }
                return fileContent;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
                return fileContent;
            } finally {
                close(reader);
            }

        }

        /**
         * 获取文件名（不带文件后缀）
         * <p/>
         * <p/>
         * <pre>
         *      getFileNameWithoutExtension(null)               =   null
         *      getFileNameWithoutExtension("")                 =   ""
         *      getFileNameWithoutExtension("   ")              =   "   "
         *      getFileNameWithoutExtension("abc")              =   "abc"
         *      getFileNameWithoutExtension("a.mp3")            =   "a"
         *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
         *      getFileNameWithoutExtension("c:\\")              =   ""
         *      getFileNameWithoutExtension("c:\\a")             =   "a"
         *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
         *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
         *      getFileNameWithoutExtension("/home/admin")      =   "admin"
         *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
         * </pre>
         *
         * @param filePath
         * @return file name from path, not include suffix
         * @see
         */
        public static String getFileNameWithoutExtension(String filePath) {
            if (isEmpty(filePath)) {
                return filePath;
            }

            int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            if (filePosi == -1) {
                return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
            }
            if (extenPosi == -1) {
                return filePath.substring(filePosi + 1);
            }
            return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
        }

        /**
         * 获取文件的后缀名
         * <p/>
         * <p/>
         * <pre>
         *      getFileExtension(null)               =   ""
         *      getFileExtension("")                 =   ""
         *      getFileExtension("   ")              =   "   "
         *      getFileExtension("a.mp3")            =   "mp3"
         *      getFileExtension("a.b.rmvb")         =   "rmvb"
         *      getFileExtension("abc")              =   ""
         *      getFileExtension("c:\\")              =   ""
         *      getFileExtension("c:\\a")             =   ""
         *      getFileExtension("c:\\a.b")           =   "b"
         *      getFileExtension("c:a.txt\\a")        =   ""
         *      getFileExtension("/home/admin")      =   ""
         *      getFileExtension("/home/admin/a.txt/b")  =   ""
         *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
         * </pre>
         *
         * @param filePath
         * @return
         */
        public static String getFileExtension(String filePath) {
            if (isEmpty(filePath)) {
                return filePath;
            }

            int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            if (extenPosi == -1) {
                return "";
            }
            return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
        }

        /**
         * 获取文件名（带文件后缀）
         * <p/>
         * <p/>
         * <pre>
         *      getFileName(null)               =   null
         *      getFileName("")                 =   ""
         *      getFileName("   ")              =   "   "
         *      getFileName("a.mp3")            =   "a.mp3"
         *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
         *      getFileName("abc")              =   "abc"
         *      getFileName("c:\\")              =   ""
         *      getFileName("c:\\a")             =   "a"
         *      getFileName("c:\\a.b")           =   "a.b"
         *      getFileName("c:a.txt\\a")        =   "a"
         *      getFileName("/home/admin")      =   "admin"
         *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
         * </pre>
         *
         * @param filePath
         * @return file name from path, include suffix
         */
        public static String getFileName(String filePath) {
            if (isEmpty(filePath)) {
                return filePath;
            }

            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
        }

        /**
         * 获取文件夹名称
         * <p/>
         * <p/>
         * <pre>
         *      getFolderName(null)               =   null
         *      getFolderName("")                 =   ""
         *      getFolderName("   ")              =   ""
         *      getFolderName("a.mp3")            =   ""
         *      getFolderName("a.b.rmvb")         =   ""
         *      getFolderName("abc")              =   ""
         *      getFolderName("c:\\")              =   "c:"
         *      getFolderName("c:\\a")             =   "c:"
         *      getFolderName("c:\\a.b")           =   "c:"
         *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
         *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
         *      getFolderName("/home/admin")      =   "/home"
         *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
         * </pre>
         *
         * @param filePath
         * @return
         */
        public static String getFolderName(String filePath) {

            if (isEmpty(filePath)) {
                return filePath;
            }

            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
        }

        /**
         * 创建文件夹
         *
         * @param filePath
         * @return
         */
        public static boolean makeDirs(String filePath) {
            if (isEmpty(filePath)) {
                return false;
            }
            java.io.File folder = new java.io.File(filePath);
            return (folder.exists() && folder.isDirectory()) || folder.mkdirs();
        }

        /**
         * 判断文件（非文件夹）是否存在
         *
         * @param file
         * @return
         */
        public static boolean isFileExist(java.io.File file) {
            return file != null && file.exists() && file.isFile();
        }

        /**
         * 判断文件夹（非文件）是否存在
         *
         * @param file
         * @return
         */
        public static boolean isFolderExist(java.io.File file) {
            return file != null && file.exists() && file.isDirectory();
        }


        /**
         * 判断文件夹（非文件）是否存在
         *
         * @param directoryPath
         * @return
         */
        public static boolean isFolderExist(String directoryPath) {
            if (isEmpty(directoryPath)) {
                return false;
            }

            java.io.File dire = new java.io.File(directoryPath);
            return (dire.exists() && dire.isDirectory());
        }

        /**
         * 判断文件（非文件夹）是否存在
         *
         * @param filePath
         * @return
         */
        public static boolean isFileExist(String filePath) {
            if (isEmpty(filePath)) {
                return false;
            }

            java.io.File file = new java.io.File(filePath);
            return (file.exists() && file.isFile());
        }

        /**
         * 删除文件夹
         *
         * @param path
         * @return
         */
        public static boolean deleteFile(String path) {
            if (isEmpty(path)) {
                return true;
            }

            java.io.File file = new java.io.File(path);
            if (!file.exists()) {
                return true;
            }
            if (file.isFile()) {
                return file.delete();
            }
            if (!file.isDirectory()) {
                return false;
            }
            for (java.io.File f : file.listFiles()) {
                if (f.isFile()) {
                    f.delete();
                } else if (f.isDirectory()) {
                    deleteFile(f.getAbsolutePath());
                }
            }
            return file.delete();
        }

        /**
         * 获取文件大小
         *
         * @param path
         * @return
         */
        public static long getFileSize(String path) {
            if (isEmpty(path)) {
                return -1;
            }

            java.io.File file = new java.io.File(path);
            return (file.exists() && file.isFile() ? file.length() : -1);
        }

    }

    /**
     * 作    者：wangr on 2017/5/24 14:33
     * 描    述： 日期工具类
     * 修订记录：
     */
    public static class Date {

        private static SimpleDateFormat m = new SimpleDateFormat("MM", Locale.CHINA);
        private static SimpleDateFormat d = new SimpleDateFormat("dd", Locale.CHINA);
        private static SimpleDateFormat md = new SimpleDateFormat("MM-dd", Locale.CHINA);
        private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        private static SimpleDateFormat ymdDot = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
        private static SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        private static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        private static SimpleDateFormat ymdhmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);
        private static SimpleDateFormat hm = new SimpleDateFormat("HH:mm", Locale.CHINA);
        private static SimpleDateFormat mdhm = new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);
        private static SimpleDateFormat mdhmLink = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);


        /**
         * 读取两个日期之间的天数
         *
         * @return
         */
        public static int getDaysBetween(java.util.Date beginDate, java.util.Date endDate) {
            if (beginDate == null || endDate == null)
                return 0;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(beginDate);
            int dStart = calendar.get(Calendar.DAY_OF_YEAR);
            calendar.setTime(endDate);
            int dEnd = calendar.get(Calendar.DAY_OF_YEAR);
            return Math.abs(dEnd - dStart);
        }

        /**
         * 月[05]
         *
         * @param milliseconds
         * @return
         */

        public static String getM(long milliseconds) {
            return m.format(new java.util.Date(milliseconds));
        }

        /**
         * 日[24]
         *
         * @param milliseconds
         * @return
         */
        public static String getD(long milliseconds) {
            return d.format(new java.util.Date(milliseconds));
        }

        /**
         * 时分[4:36]
         *
         * @param milliseconds
         * @return
         */
        public static String getHm(long milliseconds) {
            return hm.format(new java.util.Date(milliseconds));
        }

        /**
         * 月日[05-14]
         *
         * @param milliseconds
         * @return
         */
        public static String getMd(long milliseconds) {
            return md.format(new java.util.Date(milliseconds));
        }

        /**
         * 月日时分[05-14 14:36]
         *
         * @param milliseconds
         * @return
         */
        public static String getMdhmLink(long milliseconds) {
            return mdhmLink.format(new java.util.Date(milliseconds));
        }

        /**
         * 月日时分[05月14日 14:36]
         *
         * @param milliseconds
         * @return
         */
        public static String getMdhm(long milliseconds) {
            return mdhm.format(new java.util.Date(milliseconds));
        }

        /**
         * 年月日[2017-05-24]
         *
         * @param milliseconds
         * @return
         */
        public static String getYmd(long milliseconds) {
            return ymd.format(new java.util.Date(milliseconds));
        }

        /**
         * 年月日[2017.05.24]
         *
         * @param milliseconds
         * @return
         */
        public static String getYmdDot(long milliseconds) {
            return ymdDot.format(new java.util.Date(milliseconds));
        }

        /**
         * 年月日时分[2017-05-14 14:36]
         *
         * @param milliseconds
         * @return
         */
        public static String getYmdhm(long milliseconds) {
            return ymdhm.format(new java.util.Date(milliseconds));
        }

        /**
         * 年月日时分秒[2017-05-14 14:36:29]
         *
         * @param milliseconds
         * @return
         */
        public static String getYmdhms(long milliseconds) {
            return ymdhms.format(new java.util.Date(milliseconds));
        }

        /**
         * 年月日时分秒.毫秒数[2017-05-14 14:36:29.666]
         *
         * @param milliseconds
         * @return
         */
        public static String getYmdhmsS(long milliseconds) {
            return ymdhmss.format(new java.util.Date(milliseconds));
        }

        /**
         * 是否是今天
         *
         * @param milliseconds
         * @return
         */
        public static boolean isToday(long milliseconds) {
            String dest = getYmd(milliseconds);
            String now = getYmd(Calendar.getInstance().getTimeInMillis());
            return dest.equals(now);
        }

        /**
         * 是否是同一天
         *
         * @param aMilliseconds
         * @param bMilliseconds
         * @return
         */
        public static boolean isSameDay(long aMilliseconds, long bMilliseconds) {
            String aDay = getYmd(aMilliseconds);
            String bDay = getYmd(bMilliseconds);
            return aDay.equals(bDay);
        }

        /**
         * 获取年份
         *
         * @param milliseconds
         * @return
         */
        public static int getYear(long milliseconds) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            return calendar.get(Calendar.YEAR);
        }

        /**
         * 获取月份
         *
         * @param milliseconds
         * @return
         */
        public static int getMonth(long milliseconds) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            return calendar.get(Calendar.MONTH) + 1;
        }

        /**
         * 获取日期
         *
         * @param milliseconds
         * @return
         */
        public static int getDay(long milliseconds) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            return calendar.get(Calendar.DAY_OF_MONTH);
        }

        /**
         * 获取月份的天数
         *
         * @param milliseconds
         * @return
         */
        public static int getDaysInMonth(long milliseconds) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);

            switch (month) {
                case Calendar.JANUARY:
                case Calendar.MARCH:
                case Calendar.MAY:
                case Calendar.JULY:
                case Calendar.AUGUST:
                case Calendar.OCTOBER:
                case Calendar.DECEMBER:
                    return 31;
                case Calendar.APRIL:
                case Calendar.JUNE:
                case Calendar.SEPTEMBER:
                case Calendar.NOVEMBER:
                    return 30;
                case Calendar.FEBRUARY:
                    return (year % 4 == 0) ? 29 : 28;
                default:
                    throw new IllegalArgumentException("Invalid Month");
            }
        }

        /**
         * 获取星期,0-周日,1-周一，2-周二，3-周三，4-周四，5-周五，6-周六
         *
         * @param milliseconds
         * @return
         */
        public static int getWeekNO(long milliseconds) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            return calendar.get(Calendar.DAY_OF_WEEK) - 1;
        }

        /**
         * 获取星期,0-周日,1-周一，2-周二，3-周三，4-周四，5-周五，6-周六
         *
         * @param milliseconds
         * @return
         */
        public static String getWeek(long milliseconds) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.CHINA);
            return sdf.format(new java.util.Date(milliseconds));
        }

        /**
         * 获取当天0点0分0秒
         *
         * @return
         */
        public static long getFirstTimeOfDay(long milliseconds) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 1);
            return calendar.getTimeInMillis();
        }

        /**
         * 获取当天23点59分59秒
         *
         * @return
         */
        public static long getLastTimeOfDay(long milliseconds) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            return calendar.getTimeInMillis();
        }

        /**
         * 获取当月第一天的时间（毫秒值）
         *
         * @param milliseconds
         * @return
         */
        public static long getFirstOfMonth(long milliseconds) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            return getFirstTimeOfDay(calendar.getTimeInMillis());
        }

        /**
         * 获取当月最后一天的时间（毫秒值）
         *
         * @return
         */
        public static long getLastOfMonth(long milliseconds) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.roll(Calendar.DAY_OF_MONTH, -1);
            return getLastTimeOfDay(calendar.getTimeInMillis());
        }

    }

}
