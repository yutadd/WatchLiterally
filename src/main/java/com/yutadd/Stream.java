package com.yutadd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameGrabber.Exception;
import org.bytedeco.javacv.FFmpegLogCallback;

public class Stream {
	//#EXTINF:8.7623,title
	//:/ninja/test/video01.ts
	//ファイル追加時にすでに含まれていないか判定するためにHashMapである必要がある。
	ArrayList<String> tsFilesInfo = new ArrayList<String>();

	public Stream(String streamerName, String streamName) {
		File f = new File("c:\\lives\\" + streamerName + "\\" + streamName + "\\.cache");
		Controller.onlines.put(streamerName, "{\"link\":\"/"+streamerName+"/"+streamName+"/\",\"icon\":\"/"+streamerName+"/icon.png\",\"title\":\""+streamName+"\",\"name\":\""+streamerName+"\",\"thumb\":\"/"+streamerName+"/"+streamName+"/thumb.png\",\"date\":\"最近追加\"}");
		Thread th = new Thread() {
			@Override
			public void run() {
				
				try {
					TreeSet<String> sortset=new TreeSet<String>();
					File f0 = new File("c:\\lives\\" + streamerName + "\\" + streamName + "\\chat");
					if(!f0.exists()) {f0.createNewFile();}
					BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream(f0),"utf-8"));
					String line = "";
					while ((line = fr.readLine()) != null) {
						sortset.add(line);
					}
					fr.close();
					FileWriter fw0 = new FileWriter(f);
					for(String s:sortset) {
						fw0.write(s+"\r\n");
					}
					fw0.close();
					
					if(!f.exists())f.createNewFile();
					Path p = Paths.get("C:\\lives\\" + streamerName + "\\" + streamName);
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"utf-8"));
					while ((line = br.readLine()) != null)
						tsFilesInfo.add(line.split("§")[0]);
					br.close();
					FFmpegLogCallback.setLevel(avutil.AV_LOG_ERROR);
					while (true) {
						for (File f2 : p.toFile().listFiles()) {
							if (f2.isFile() && f2.getName().endsWith(".ts")) {
								if (!tsFilesInfo.contains(f2.getName())) {
									//新しいファイルが追加され続けているということはライブ中。なので、本来はここで、Controller.onlinesに追加する。
									FFmpegFrameGrabber mInputGrabber = new FFmpegFrameGrabber(f2);
									mInputGrabber.start();
									OutputStreamWriter fw  = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
									fw.append(f2.getName() + "§" + (mInputGrabber.getLengthInFrames()
											/ (float) mInputGrabber.getFrameRate()) + "\r\n");
									fw.close();
									tsFilesInfo.add(f2.getName());
									mInputGrabber.close();
								}
							}
						}
					}
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		};
		th.start();
	}
}
