AndroidDownloader
=====


[![Build Status](https://travis-ci.org/lifengsofts/AndroidDownloader.svg?branch=master)](https://travis-ci.org/lifengsofts/AndroidDownloader)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6a72dfeec9f54c3187475aaa5ebafe73)](https://www.codacy.com/app/lifengsofts/AndroidDownloader?utm_source=github.com&utm_medium=referral&utm_content=lifengsofts/AndroidDownloader&utm_campaign=badger)
<!-- [![Code Climate](https://codeclimate.com/github/lifengsofts/AndroidDownloader/badges/gpa.svg)](https://codeclimate.com/github/lifengsofts/AndroidDownloader) -->
<!-- [![Test Coverage](https://codeclimate.com/github/lifengsofts/AndroidDownloader/badges/coverage.svg)](https://codeclimate.com/github/lifengsofts/AndroidDownloader/coverage) -->
[![Issue Count](https://codeclimate.com/github/lifengsofts/AndroidDownloader/badges/issue_count.svg)](https://codeclimate.com/github/lifengsofts/AndroidDownloader)
<!-- ![downloads Size](https://img.shields.io/github/downloads/lifengsofts/AndroidDownloader/latest/total.svg) -->
![Maven-central Version](https://img.shields.io/maven-central/v/cn.woblog.android/downloader.svg)
![Release](https://img.shields.io/github/release/lifengsofts/AndroidDownloader.svg)
![License](https://img.shields.io/github/license/lifengsofts/AndroidDownloader.svg)

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

For more information on [GitHub wiki][200] and [Javadocs][201].

0.Add network network permissions()
-------

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

1.Configuration download service
--------------------------------

```xml
<service android:name="cn.woblog.android.downloader.DownloadService">
  <intent-filter>
    <action android:name="cn.woblog.android.downloader.DOWNLOAD_SERVICE" />
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

4.Use in list
-------------

The default is to use the RecyclerView.

```java
class ViewHolder extends RecyclerView.ViewHolder {

  private final ImageView iv_icon;
  private final TextView tv_size;
  private final TextView tv_status;
  private final ProgressBar pb;
  private final TextView tv_name;
  private final Button bt_action;
  private DownloadInfo downloadInfo;

  public ViewHolder(View view) {
    super(view);

    iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
    tv_size = (TextView) view.findViewById(R.id.tv_size);
    tv_status = (TextView) view.findViewById(R.id.tv_status);
    pb = (ProgressBar) view.findViewById(R.id.pb);
    tv_name = (TextView) view.findViewById(R.id.tv_name);
    bt_action = (Button) view.findViewById(R.id.bt_action);
  }

  @SuppressWarnings("unchecked")
  public void bindData(final MyDownloadInfo data, int position, final Context context) {
    Glide.with(context).load(data.getIcon()).into(iv_icon);
    tv_name.setText(data.getName());

    // Get download task status
    downloadInfo = downloadManager.getDownloadById(data.getUrl().hashCode());

    // Set a download listener
    if (downloadInfo != null) {
      downloadInfo
          .setDownloadListener(new MyDownloadListener(new SoftReference(ViewHolder.this)) {
            //  Call interval about one second
            @Override
            public void onRefresh() {
              if (getUserTag() != null && getUserTag().get() != null) {
                ViewHolder viewHolder = (ViewHolder) getUserTag().get();
                viewHolder.refresh();
              }
            }
          });

    }

    refresh();

//      Download button
    bt_action.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (downloadInfo != null) {

          switch (downloadInfo.getStatus()) {
            case DownloadInfo.STATUS_NONE:
            case DownloadInfo.STATUS_PAUSED:
            case DownloadInfo.STATUS_ERROR:

              //resume downloadInfo
              downloadManager.resume(downloadInfo);
              break;

            case DownloadInfo.STATUS_DOWNLOADING:
            case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
            case STATUS_WAIT:
              //pause downloadInfo
              downloadManager.pause(downloadInfo);
              break;
            case DownloadInfo.STATUS_COMPLETED:
              downloadManager.remove(downloadInfo);
              break;
          }
        } else {
//            Create new download task
          File d = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "d");
          if (!d.exists()) {
            d.mkdirs();
          }
          String path = d.getAbsolutePath().concat("/").concat(data.getName());
          downloadInfo = new Builder().setUrl(data.getUrl())
              .setPath(path)
              .build();
          downloadInfo
              .setDownloadListener(new MyDownloadListener(new SoftReference(ViewHolder.this)) {

                @Override
                public void onRefresh() {
                  if (getUserTag() != null && getUserTag().get() != null) {
                    ViewHolder viewHolder = (ViewHolder) getUserTag().get();
                    viewHolder.refresh();
                  }
                }
              });
          downloadManager.download(downloadInfo);
        }
      }
    });

  }

  private void refresh() {
    if (downloadInfo == null) {
      tv_size.setText("");
      pb.setProgress(0);
      bt_action.setText("Download");
      tv_status.setText("not downloadInfo");
    } else {
      switch (downloadInfo.getStatus()) {
        case DownloadInfo.STATUS_NONE:
          bt_action.setText("Download");
          tv_status.setText("not downloadInfo");
          break;
        case DownloadInfo.STATUS_PAUSED:
        case DownloadInfo.STATUS_ERROR:
          bt_action.setText("Continue");
          tv_status.setText("paused");
          try {
            pb.setProgress((int) (downloadInfo.getProgress() * 100.0 / downloadInfo.getSize()));
          } catch (Exception e) {
            e.printStackTrace();
          }
          tv_size.setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/" + FileUtil
              .formatFileSize(downloadInfo.getSize()));
          break;

        case DownloadInfo.STATUS_DOWNLOADING:
        case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
          bt_action.setText("Pause");
          try {
            pb.setProgress((int) (downloadInfo.getProgress() * 100.0 / downloadInfo.getSize()));
          } catch (Exception e) {
            e.printStackTrace();
          }
          tv_size.setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/" + FileUtil
              .formatFileSize(downloadInfo.getSize()));
          tv_status.setText("downloading");
          break;
        case STATUS_COMPLETED:
          bt_action.setText("Delete");
          try {
            pb.setProgress((int) (downloadInfo.getProgress() * 100.0 / downloadInfo.getSize()));
          } catch (Exception e) {
            e.printStackTrace();
          }
          tv_size.setText(FileUtil.formatFileSize(downloadInfo.getProgress()) + "/" + FileUtil
              .formatFileSize(downloadInfo.getSize()));
          tv_status.setText("success");
          break;
        case STATUS_REMOVED:
          tv_size.setText("");
          pb.setProgress(0);
          bt_action.setText("Download");
          tv_status.setText("not downloadInfo");
        case STATUS_WAIT:
          tv_size.setText("");
          pb.setProgress(0);
          bt_action.setText("Pause");
          tv_status.setText("Waiting");
          break;
      }

    }
  }
}
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

Author
=======

Patsy - @Patsysoft on GitHub, Email is patsysoft@gmail.com.


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
[60]: https://github.com/lifengsofts/AndroidDownloader#build

[200]: https://github.com/lifengsofts/AndroidDownloader/wiki
[201]: http://i.woblog.cn/AndroidDownloader/javadocs/1.0.1/
