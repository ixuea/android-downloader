AndroidDownloader
=====

[![Build Status](https://travis-ci.org/lifengsofts/AndroidDownloader.svg?branch=master)](https://travis-ci.org/lifengsofts/AndroidDownloader)

Android Downloader is a open source multithread and mulitask downloadInfo framework for Android.

Try out the sample application [on the Apk file][20].

![AndroidDownloader Sample Screenshots][30]

Download
=======

You can download a jar from GitHub's [releases page][40].

Or use Gradle:

```gradle
dependencies {
  compile 'cn.woblog.android:downloader:1.0.0'
}
```

Or Maven:

```xml
<dependency>
  <groupId>cn.woblog.android</groupId>
  <artifactId>downloader</artifactId>
  <version>1.0.0</version>
</dependency>
```

For info on using the bleeding edge, see the [Snapshots][50] wiki page.

ProGuard
=======

If your project uses ProGuard, you need to add the following configuration to your project proguard-rules.pro file

```pro
-keep public class * implements cn.woblog.android.downloader.db.DownloadDBController
```

How do I use Android Downloader?
=======

1.Create a DownloadManager instance
-------

```java
downloadManager = DownloadService.getDownloadManager(context.getApplicationContext());
```

Simple use as follows

2.Download a file
-------

```java
//create download info set download uri and save path.
final DownloadInfo downloadInfo = new DownloadInfo.Builder().setUrl("http://example.com/a.apk")
    .setPath("/sdcard/a.apk")
    .build();

//set download callback.
downloadInfo
    .setDownloadListener(new DownloadListener() {

      @Override
      public void onStart() {
        tv_download_info.setText("Prepare downloading");
      }

      @Override
      public void onWaited() {
        tv_download_info.setText("Waiting");
      }

      @Override
      public void onPaused() {
        tv_download_info.setText("Paused");
      }

      @Override
      public void onDownloading() {
        tv_download_info.setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/" + FileUtil
            .formatFileSize(downloadInfo.getSize()));
      }

      @Override
      public void onRemoved() {
      }

      @Override
      public void onDownloadSuccess() {
        tv_download_info.setText("Download success");
      }

      @Override
      public void onDownloadFailed() {
        tv_download_info.setText("Download fail");
      }
    });

//submit download info to download manager.
downloadManager.download(downloadInfo);
```

License
=======

    Copyright 2016 Renpingqing, All Rights Reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



[20]: https://i.woblog.cn
[30]: https://raw.github.com/lifengsofts/AndroidDownloader/master/samples/art/screenshot.png
[40]: https://github.com/lifengsofts/AndroidDownloader/releases
[50]: https://github.com/lifengsofts/AndroidDownloader/releases