AndroidDownloader
=====

[English][13] | 中文

[![](https://jitpack.io/v/com.ixuea/AndroidDownloader.svg)](https://jitpack.io/#com.ixuea/AndroidDownloader)
![License](https://img.shields.io/github/license/ixuea/AndroidDownloader.svg)

[提交一个Issue][10], iOS和macOS平台使用[CocoaDownloader][12].

Android Downloader是一个开源的多线程，多任务下载框架。

更多功能和使用方法可以查看这个[APK][20].

<img src="https://raw.githubusercontent.com/ixuea/AndroidDownloader/master/samples/art/home.png" width="30%" height="30%"><img src="https://raw.githubusercontent.com/ixuea/AndroidDownloader/master/samples/art/downloadAFile.png" width="30%" height="30%">
<img src="https://raw.githubusercontent.com/ixuea/AndroidDownloader/master/samples/art/useInList.png" width="30%" height="30%"> 
<img src="https://raw.githubusercontent.com/ixuea/AndroidDownloader/master/samples/art/downloading.png" width="30%" height="30%">
<img src="https://raw.githubusercontent.com/ixuea/AndroidDownloader/master/samples/art/downloaded.png" width="30%" height="30%">


下载
=======

你能从Github[releases page][40]下载jar包.

或者使用Gradle:

在根项目的build.gradle文件中添加仓库地址：

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

添加依赖:

```gradle
dependencies {
  compile 'com.ixuea:AndroidDownloader:latest'
}
```

如果要使用Snapshot版本, 请查看[Snapshots][50]wiki页面.

ProGuard
=======

如果你的项目使用了ProGuard，你需要添加下面的配置信息到项目的proguard-rules.pro文件中

```pro
-keep public class * implements com.ixuea.android.downloader.db.DownloadDBController
```

如果使用?
=======

更多的信息可以查看[GitHub wiki][200]和[Javadocs][201].

0.添加网络访问权限
-------

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

1.配置下载服务
--------------------------------

```xml
<service android:name="com.ixuea.android.downloader.DownloadService">
  <intent-filter>
    <action android:name="com.ixuea.android.downloader.DOWNLOAD_SERVICE" />
  </intent-filter>
</service>
```

2.创建DownloadManager实例
-----------------------------------

```java
downloadManager = DownloadService.getDownloadManager(context.getApplicationContext());
```

3.下载一个文件
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

更多信息请查看Demo.

兼容
=======

* **Android SDK**: 要求最新Android SDK为API 10.


实例代码
=======

可以看到该[Build][60]文档配置开发环境:

```gradle
./gradlew :samples:run
```

你也可以在releases界面找到Demo APK.

## 更多帮助信息

请查看Demo项目.

## 作者

Smile - @ixueadev on GitHub, Email is ixueadev@163.com, See more ixuea([http://www.ixuea.com][100])

Android开发交流群QQ群: 702321063.


[10]: https://github.com/ixuea/AndroidDownloader/issues/new
[12]: http://a.ixuea.com/8
[13]: https://github.com/ixuea/AndroidDownloader
[14]: https://github.com/ixuea/AndroidDownloader/blob/master/docs/zh.md
[20]: https://i.woblog.cn

[30]: https://raw.github.com/ixuea/AndroidDownloader/master/samples/art/download-a-file.png
[31]: https://raw.github.com/ixuea/AndroidDownloader/master/samples/art/use-in-list.png
[32]: https://raw.github.com/ixuea/AndroidDownloader/master/samples/art/download-manager-downloading.png
[33]: https://raw.github.com/ixuea/AndroidDownloader/master/samples/art/download-manager-downloaded.png

[40]: https://github.com/ixuea/AndroidDownloader/releases
[50]: https://github.com/ixuea/AndroidDownloader/releases
[60]: https://github.com/ixuea/AndroidDownloader#build

[100]: http://a.ixuea.com/3

[200]: https://github.com/ixuea/AndroidDownloader/wiki
[201]: http://i.woblog.cn/AndroidDownloader/javadocs/2.0.0/
