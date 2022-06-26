package com.yutadd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
//ffmpeg -passphrase passworddesu -i srt://localhost:3000?mode=listener -c:v copy -f hls -hls_time 2 -hls_playlist_type vod -hls_segment_filename "video%3d.ts" video.m3u8
public class Controller {
	Worker w=new Worker();

	@RequestMapping({ "/", "/index.html", "/index.php", "index.htm" })
	public String top() {

		return "index";
	}

	@RequestMapping("streamsdata")
	@ResponseBody
	public String createjson() {
		return "[{\"icon\":\"/icon.png\",\"title\":\"eyyyyyyyyyyyyy\",\"name\":\"ninja\",\"thumb\":\"/ninja0.jpg\",\"date\":\"2020/6/10\"},{\"icon\":\"/icon.png\",\"title\":\"eyyyyyyyyyyyyy\",\"name\":\"ninja\",\"thumb\":\"/ninja0.jpg\",\"date\":\"2020/6/10\"}]";
	}

	/* create m3u8 file from /videos/video{n}.ts */
	@RequestMapping("m3u8")
	@ResponseBody
	public String createm3u8() {
		System.out.println("ok");
		String m3u8 ="#EXTM3U\r\n"
				+ "#EXT-X-VERSION:3\r\n"
				+ "#EXT-X-ALLOW-CACHE:YES\r\n"
				+ "#EXT-X-DEFINITION:1080p\r\n"
				+ "#EXT-X-MEDIA-SEQUENCE:1236\r\n"
				+ "#EXT-X-TARGETDURATION:6\r\n";
		for(String s:w.videos.keySet()) {
			m3u8 +=w.videos.get(s);
		}
		return m3u8;
	}
	@RequestMapping("lives/{stream}/{file}")
	@ResponseBody
	public byte[] video(@PathVariable String stream,@PathVariable String file ) throws IOException {
		System.out.println("c:\\lives\\"+stream+"\\"+file);
		InputStream in =new FileInputStream(new File("c:\\lives\\"+stream+"\\"+file));
		return IOUtils.toByteArray(in);
	}
}
