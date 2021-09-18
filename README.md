android-downloader
=====

English | [中文][14]

[![](https://jitpack.io/v/com.ixuea/android-downloader.svg)](https://jitpack.io/#com.ixuea/android-downloader)
![License](https://img.shields.io/github/license/ixuea/android-downloader)

[Report an issue][10], iOS and macOS use [CocoaDownloader][12].

Android Downloader is a open source multithread and mulitask downloadInfo framework for Android.

Try out the sample application [on the Apk file][20].

<img src="https://raw.githubusercontent.com/ixuea/android-downloader/master/app/art/home.png" width="30%" height="30%"><img src="https://raw.githubusercontent.com/ixuea/android-downloader/master/app/art/downloadAFile.png" width="30%" height="30%">
<img src="https://raw.githubusercontent.com/ixuea/android-downloader/master/app/art/useInList.png" width="30%" height="30%">
<img src="https://raw.githubusercontent.com/ixuea/android-downloader/master/app/art/downloading.png" width="30%" height="30%">
<img src="https://raw.githubusercontent.com/ixuea/android-downloader/master/app/art/downloaded.png" width="30%" height="30%">

Download
=======

Add it in your root build.gradle at the end of repositories:

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency:

```gradle
dependencies {
    implementation 'com.ixuea:android-downloader:latest'
}
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

1.Add network network permissions()
-------

```xml
<uses-permission android:name="android.permission.INTERNET" />
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
File targetFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "a.apk");
final DownloadInfo downloadInfo = new DownloadInfo.Builder().setUrl("http://example.com/a.apk")
    .setPath(targetFile.getAbsolutePath())
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
      bt_download_button.setText("Continue");
      tv_download_info.setText("Download fail:" + e.getMessage());
  }
});

//submit download info to download manager.
downloadManager.download(downloadInfo);
```

Compatibility
=======

* **Android SDK**: Android Downloader requires a minimum API level of 21.


Build
=======


Samples
=======

Follow the steps in the [Build][60] section to setup the project and then:

```gradle
./gradlew :app:run
```

You may also find precompiled APKs on the releases page.

## More

See the example code.

## Author

Smile - @ixueadev on GitHub, Email is ixueadev@163.com, See more ixuea([http://www.ixuea.com][100])

Android development QQ group: 702321063.

[10]: https://github.com/ixuea/android-downloader/issues/new
[12]: http://a.ixuea.com/8
[13]: https://github.com/ixuea/android-downloader
[14]: https://github.com/ixuea/android-downloader/blob/master/docs/zh.md
[20]: https://github.com/ixuea/android-downloader/releases

[30]: https://raw.github.com/ixuea/android-downloader/master/samples/art/download-a-file.png
[31]: https://raw.github.com/ixuea/android-downloader/master/samples/art/use-in-list.png
[32]: https://raw.github.com/ixuea/android-downloader/master/samples/art/download-manager-downloading.png
[33]: https://raw.github.com/ixuea/android-downloader/master/samples/art/download-manager-downloaded.png

[40]: https://github.com/ixuea/android-downloader/releases
[50]: https://github.com/ixuea/android-downloader/releases
[60]: https://github.com/ixuea/android-downloader#build

[100]: http://a.ixuea.com/3

[200]: https://github.com/ixuea/android-downloader/wiki
[201]: https://github.com/ixuea/android-downloader/wiki
