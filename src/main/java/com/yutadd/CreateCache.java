package com.yutadd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class CreateCache {
	//検索用にやっぱhashmapである必要がある
	HashMap<String, Streamer> streamers = new HashMap<String, Streamer>();

	CreateCache() {
		Thread th = new Thread() {
			public void run() {
				Path p = Paths.get("C:\\lives\\");
				File f = new File("c:\\lives\\.cache");
				try {
					if (!f.exists())
						f.createNewFile();

					BufferedReader br = new BufferedReader(new FileReader(f));
					String line = "";
					while ((line = br.readLine()) != null)
						streamers.put(line, new Streamer(line));
					br.close();
						for (File f2 : p.toFile().listFiles()) {
							if (f2.isDirectory()) {
								if (!streamers.containsKey(f2.getName())) {
									streamers.put(f2.getName(), new Streamer(f2.getName()));
									FileWriter fw = new FileWriter(f, true);
									fw.write(f2.getName() + "\r\n");
									fw.close();
								}
							}
						}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		th.start();
	}
}
