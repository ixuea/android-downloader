AndroidDownloader
=====


[![Build Status](https://travis-ci.org/lifengsofts/AndroidDownloader.svg?branch=master)](https://travis-ci.org/lifengsofts/AndroidDownloader)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6a72dfeec9f54c3187475aaa5ebafe73)](https://www.codacy.com/app/lifengsofts/AndroidDownloader?utm_source=github.com&utm_medium=referral&utm_content=lifengsofts/AndroidDownloader&utm_campaign=badger)
[![Issue Count](https://codeclimate.com/github/lifengsofts/AndroidDownloader/badges/issue_count.svg)](https://codeclimate.com/github/lifengsofts/AndroidDownloader)
![Maven-central Version](https://img.shields.io/maven-central/v/com.ixuea.android/downloader.svg)
![Release](https://img.shields.io/github/release/lifengsofts/AndroidDownloader.svg)
![License](https://img.shields.io/github/license/lifengsofts/AndroidDownloader.svg)

[Report an issue][10], iOS and macOS use [CocoaDownloader][12].

Android Downloader is a open source multithread and mulitask downloadInfo framework for Android.

Try out the sample application [on the Apk file][20].

![AndroidDownloader Sample Screenshots][30] ![AndroidDownloader Sample Screenshots][31]

![AndroidDownloader Sample Screenshots][32] ![AndroidDownloader Sample Screenshots][33]


Download
=======

You can download a jar from GitHub's [releases page][40].

Or use Gradle:

```gradle
dependencies {
  compile 'com.ixuea.android:downloader:1.0.1'
}
```

Or Maven:

```xml
<dependency>
  <groupId>com.ixuea.android</groupId>
  <artifactId>downloader</artifactId>
  <version>1.0.0</version>
</dependency>
```

For info on using the bleeding edge, see the [Snapshots][50] wiki page.

ProGuard
=======

If your project uses ProGuard, you need to add the following configuration to your project proguard-rules.pro file

```pro
-keep public class * implements com.ixuea.android.downloader.db.DownloadDBController
```

How do I use Android Downloader?
=======

For more information on [GitHub wiki][200] and [Javadocs][201].

0.Add network network permissions()
-------

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

1.Configuration download service
--------------------------------

```xml
<service android:name="com.ixuea.android.downloader.DownloadService">
  <intent-filter>
    <action android:name="com.ixuea.android.downloader.DOWNLOAD_SERVICE" />
  </intent-filter>
</service>
```

2.Create a DownloadManager instance
-----------------------------------

```java
downloadManager = DownloadService.getDownloadManager(context.getApplicationContext());
```

Simple use as follows

3.Download a file
-----------------

```java
//create download info set download uri and save path.
final DownloadInfo downloadInfo = new DownloadInfo.Builder().setUrl("http://example.com/a.apk")
    .setPath("/sdcard/a.apk")
    .build();

//set download callback.
downloadInfo.setDownloadListener(new DownloadListener() {

  @Override
  public void onStart() {
    tv_download_info.setText("Prepare downloading");
  }

  @Override
  public void onWaited() {
    tv_download_info.setText("Waiting");
    bt_download_button.setText("Pause");
  }

  @Override
  public void onPaused() {
    bt_download_button.setText("Continue");
    tv_download_info.setText("Paused");
  }

  @Override
  public void onDownloading(long progress, long size) {
    tv_download_info
        .setText(FileUtil.formatFileSize(progress) + "/" + FileUtil
            .formatFileSize(size));
    bt_download_button.setText("Pause");
  }

  @Override
  public void onRemoved() {
    bt_download_button.setText("Download");
    tv_download_info.setText("");
    downloadInfo = null;
  }

  @Override
  public void onDownloadSuccess() {
    bt_download_button.setText("Delete");
    tv_download_info.setText("Download success");
  }

  @Override
  public void onDownloadFailed(DownloadException e) {
    e.printStackTrace();
    tv_download_info.setText("Download fail:" + e.getMessage());
  }
});

//submit download info to download manager.
downloadManager.download(downloadInfo);
```

Compatibility
=======

* **Android SDK**: Android Downloader requires a minimum API level of 10.


Build
=======


Samples
=======

Follow the steps in the [Build][60] section to setup the project and then:

```gradle
./gradlew :samples:run
```

You may also find precompiled APKs on the releases page.

## More

See the example code.

## Author

Smile - @ixueadev on GitHub, Email is ixueadev@163.com, See more ixuea([http://www.ixuea.com][100])


[10]: https://github.com/lifengsofts/AndroidDownloader/issues/new
[12]: http://a.ixuea.com/8
[20]: https://i.woblog.cn

[30]: https://raw.github.com/lifengsofts/AndroidDownloader/master/samples/art/download-a-file.png
[31]: https://raw.github.com/lifengsofts/AndroidDownloader/master/samples/art/use-in-list.png
[32]: https://raw.github.com/lifengsofts/AndroidDownloader/master/samples/art/download-manager-downloading.png
[33]: https://raw.github.com/lifengsofts/AndroidDownloader/master/samples/art/download-manager-downloaded.png

[40]: https://github.com/lifengsofts/AndroidDownloader/releases
[50]: https://github.com/lifengsofts/AndroidDownloader/releases
[60]: https://github.com/lifengsofts/AndroidDownloader#build

[100]: http://a.ixuea.com/3

[200]: https://github.com/lifengsofts/AndroidDownloader/wiki
[201]: http://i.woblog.cn/AndroidDownloader/javadocs/2.0.0/
