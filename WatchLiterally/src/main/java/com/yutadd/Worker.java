package com.yutadd;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameGrabber.Exception;

public class Worker {
	static int number;
	TreeMap<String,String> videos=new TreeMap<String,String>();
	Path f = Paths.get("C:\\lives\\test\\");
	public Worker(){
		number++;
		System.out.println(number);
		Thread th=new Thread() {
			@Override
			public void run() {
				while(true) {
					for (File f2 : f.toFile().listFiles()) {
						if (f2.isFile() && f2.getName().endsWith(".ts")) {
							if(!videos.containsKey(f2.getName())) {
								FFmpegFrameGrabber mInputGrabber = new FFmpegFrameGrabber(f2);
								try {mInputGrabber.start();} catch (Exception e) {e.printStackTrace();}
								String m3u8="";
								m3u8 +="#EXTINF:"+(mInputGrabber.getLengthInFrames()/(float)mInputGrabber.getFrameRate())+", title\r\n";
								m3u8 += "lives/test/" +f2.getName() + "\r\n";
								videos.put(f2.getName(), m3u8);
								try {mInputGrabber.close();} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {e.printStackTrace();}
							}
						}
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
				}
			}
		};
		th.start();
	}
}
