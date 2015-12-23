package com.nan.projectcollection.Utils;

import android.content.Context;

import com.lidroid.xutils.util.LogUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;


public class FileUtils {

    public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

    /**
     * 读取源文件字符数组
     *
     * @param file 获取字符数组的文件
     * @return 字符数组
     */
    public static byte[] readFileByte(File file) {
        FileInputStream fis = null;
        FileChannel fc = null;
        byte[] data = null;
        try {
            fis = new FileInputStream(file);
            fc = fis.getChannel();
            data = new byte[(int) (fc.size())];
            fc.read(ByteBuffer.wrap(data));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fc != null) {
                try {
                    fc.close();
                } catch (IOException e) {
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
        return data;
    }

    /**
     * 字符数组写入文件
     *
     * @param bytes 被写入的字符数组
     * @param file  被写入的文件
     * @return 字符数组
     */
    public static boolean writeByteFile(byte[] bytes, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 复制文件
     *
     * @param srcFile
     * @param destFile
     * @return
     */
    public static boolean copyFile(File srcFile, File destFile) {
        boolean result = false;
        if (srcFile != null && srcFile.exists()) {
            InputStream in = null;
            try {
                in = new FileInputStream(srcFile);
                result = copyToFile(in, destFile);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception ex) {
                    }
                }
            }
        }
        return result;
    }

    /**
     * 流存为文件
     *
     * @param inputStream
     * @param destFile
     * @return
     */
    public static boolean copyToFile(InputStream inputStream, File destFile) {
        try {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            } else if (destFile.exists()) {
                destFile.delete();
            }
            OutputStream out = new FileOutputStream(destFile);
            try {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) >= 0) {
                    out.write(buffer, 0, bytesRead);
                }
            } finally {
                out.close();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除目录以及子目录
     *
     * @param filepath 要删除的目录地址
     * @throws IOException
     */
    public static void deleteDir(String filepath) {
        File f = new File(filepath);
        if (f.exists() && f.isDirectory()) {
            if (f.listFiles().length == 0) {
                f.delete();
            } else {
                File[] delFiles = f.listFiles();
                for (File delFile : delFiles) {
                    if (delFile.isDirectory()) {
                        deleteDir(delFile.getAbsolutePath());
                    }
                    delFile.delete();
                }
                f.delete();
            }
        }
    }

    public static void deleteFile(String filepath) {
        File file = new File(filepath);
        if (file.exists()) {
            if (file.isDirectory()) {
                if (file.listFiles().length > 0) {
                    File[] delFiles = file.listFiles();
                    for (File delFile : delFiles) {
                        deleteFile(delFile.getAbsolutePath());
                    }
                }
            }
            file.delete();
        }
    }

    /**
     * 改名
     *
     * @param src
     * @param dst
     * @return
     */
    public static boolean safeRenameTo(File src, File dst) {
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        boolean ret = src.renameTo(dst);
        if (!ret) {
            ret = copyFile(src, dst);
            if (ret) {
                if (src.isDirectory()) {
                    deleteDir(src.getAbsolutePath());
                } else {
                    src.delete();
                }
            }
        }
        return ret;
    }

    /**
     * 文件夹下的文件个数
     *
     * @param aDir
     * @return
     */
    public static long countDirSize(File aDir) {
        long ret = 0L;
        File[] files = aDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                ret += countDirSize(file);
            } else {
                ret += file.length();
            }
        }
        return ret;
    }

    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                if (file.listFiles().length > 0) {
                    File[] delFiles = file.listFiles();
                    for (File delFile : delFiles) {
                        deleteFile(delFile);
                    }
                }
            }
            file.delete();
        }
    }

    /**
     * 将文件读取为String
     *
     * @param path
     * @param charsetName
     * @return
     */
    public static String readFile(String path, String charsetName) {
        StringBuffer contents = new StringBuffer("");
        try {
            File urlFile = new File(path);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(
                    urlFile), charsetName);
            @SuppressWarnings("resource")
            BufferedReader breader = new BufferedReader(isr);
            String mimeTypeLine = null;
            while ((mimeTypeLine = breader.readLine()) != null) {
                contents.append(mimeTypeLine);
            }
            breader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents.toString();
    }

    /**
     * 从工程的asset中读取文件
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return 返回文件中的内容(字符串)
     */
    public static String readFileFromAssets(Context context, String fileName) {
        String contents = "";
        try {
            contents = readFileFromAssets(context,
                    context.getResources().getAssets().open(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents;
    }

    /**
     * 从工程的asset中读取文件
     *
     * @param context 上下文
     * @param fileIns 文件的内容(InputStream)
     * @return 返回文件中的内容(字符串)
     */
    public static String readFileFromAssets(Context context, InputStream fileIns) {
        StringBuffer contents = new StringBuffer("");
        try {
            InputStreamReader isr = new InputStreamReader(fileIns);
            BufferedReader breader = new BufferedReader(isr);
            String mimeTypeLine = null;
            while ((mimeTypeLine = breader.readLine()) != null) {
                contents.append(mimeTypeLine);
            }
            breader.close();
            if (isr != null)
                isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents.toString();
    }

    /**
     * 判断是否有这个文件
     *
     * @param str
     * @return
     */
    public static boolean fileIsExists(String str) {
        try {
            File f = new File(str);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("获取失败!");
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 获取指定文件大小
     *
     * @param
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
//            file.createNewFile();
            LogUtils.e("文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.0");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df
                        .format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.0");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小MB
     *
     * @param fileS
     * @return
     */
    public static String FormetFileSizeMB(long fileS) {
        DecimalFormat df = new DecimalFormat("0.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        }
        return fileSizeString;
    }

}

