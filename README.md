# XProgressView [![](https://jitpack.io/v/sunlightAndroid/XProgressView.svg)](https://jitpack.io/#sunlightAndroid/XProgressView)





### 可设置斜线的进度条
![image](http://m.qpic.cn/psc?/V535ockk4gKmll2vn6Xd1xdhbP1otnf7/ruAMsa53pVQWN7FLK88i5i*Pr38knpECE95Xei6*tPTR1WLLn1zkIwd4HVeZGR8Z1QIvBhmtNiTzsLMIEyemz2aMZR1dBv6lsTfqcJXUpR0!/mnull&bo=aQOABwAAAAABB80!&rf=photolist&t=5)


### Step 1. Add the JitPack repository to your build file

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

### Step 2. Add the dependency

```
	dependencies {
	        implementation 'com.github.sunlightAndroid:XProgressView:1.0.0'
	}
```

### xml中使用


```
             <me.eric.progress.XProgressView
                android:id="@+id/item_pk_progress"
                android:layout_width="match_parent"
                android:layout_height="52dp"
                android:layout_gravity="center"
                app:animation_enabled="true"
                app:background_color="#ff5657"
                app:c_progress="0.6"
                app:progress_color="#5a5cff"
                app:progress_style="SLASH" />
```



###   代码中设置


```
   progressView.setProgress(0.4f)   // 设置进度
   progressView.setBackgroundColor() // 设置背景色
   progressView.setProgressColor() // 设置进度条的背景
   progressView.enableAnimator()  // 是否使用动画
   progressView.setProgressStyle(XProgressView.ProgressStyle.SLASH)// 设置进度条样式  


```

  

