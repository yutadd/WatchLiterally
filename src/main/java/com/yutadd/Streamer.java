package com.yutadd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Streamer {
	String name = "";
	HashMap<String, Stream> streams = new HashMap<String, Stream>();

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof String) {
			return name.equals((String) obj);
		} else if (obj instanceof Streamer) {
			return name.equals(((Streamer) obj).name);
		}
		System.out.println("Streamer class's equals method has error with :" + obj.toString());
		return false;
	}

	public Streamer(String streamerName) {
		name = streamerName;
		Path p = Paths.get("C:\\lives\\" + streamerName);
		File f = new File("c:\\lives\\" + streamerName + "\\.cache");
		try {
			if (!f.exists())
				f.createNewFile();
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"utf-8"));
			String line = "";
			while ((line = br.readLine()) != null)
				streams.put(line, new Stream(streamerName, line));
			br.close();
				for (File f2 : p.toFile().listFiles()) {
					if (f2.isDirectory()) {
						if (!streams.containsKey(f2.getName())) {
							streams.put(f2.getName(), new Stream(streamerName, f2.getName()));
							OutputStreamWriter osw  = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
							osw.append(f2.getName() + "\r\n");
							osw.close();
						}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
