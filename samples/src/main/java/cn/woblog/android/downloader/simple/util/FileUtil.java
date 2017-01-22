package cn.woblog.android.downloader.simple.util;

/**
 * Created by renpingqing on 17/1/23.
 */

public class FileUtil {

  public static String formatFileSize(long size) {
    String sFileSize = "";
    if (size > 0) {
      double dFileSize = (double) size;

      double kiloByte = dFileSize / 1024;
      if (kiloByte < 1 && kiloByte > 0) {
        return size + "Byte";
      }
      double megaByte = kiloByte / 1024;
      if (megaByte < 1) {
        sFileSize = String.format("%.2f", kiloByte);
        return sFileSize + "K";
      }

      double gigaByte = megaByte / 1024;
      if (gigaByte < 1) {
        sFileSize = String.format("%.2f", megaByte);
        return sFileSize + "M";
      }

      double teraByte = gigaByte / 1024;
      if (teraByte < 1) {
        sFileSize = String.format("%.2f", gigaByte);
        return sFileSize + "G";
      }

      sFileSize = String.format("%.2f", teraByte);
      return sFileSize + "T";
    }
    return "0K";
  }

}
