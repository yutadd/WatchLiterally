package com.yutadd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/* server:
ffmpeg -passphrase passworddesu -i srt://localhost:3000?mode=listener -c:v copy -f hls -hls_time 2 -hls_playlist_type vod -hls_segment_filename "video%3d.ts" video.m3u8
client:
srt://localhost:3000?passphrase=passworddesu
*/
@org.springframework.stereotype.Controller
public class Controller {
	// キャッシュの作成
	static CreateCache streamers = new CreateCache();
	static HashMap<String, String> onlines = new HashMap<String, String>();

	@RequestMapping({ "/", "/index.html", "/index.php", "index.htm" })
	public String top() {
		System.out.println(System.getProperty("file.encoding"));
		return "index";
	}

	@RequestMapping("/{streamer}/{stream}/")
	public String videopage() {
		return "video-page";
	}

	@RequestMapping("enumstreams")
	@ResponseBody
	public String createjson() {
		String json = "[";
		boolean first = true;
		for (String s : onlines.keySet()) {
			if (first) {
				json += onlines.get(s);
				first = false;
			} else {
				json += "," + onlines.get(s);
			}
		}
		return json + "]";
	}

	/* create m3u8 file from /videos/video{n}.ts */
	@RequestMapping("/{streamer}/{stream}/m3u8")
	@ResponseBody
	public String createm3u8(@PathVariable String streamer, @PathVariable String stream) {
		// キャッシュを読み込んで返す
		// 0番目のtsはなぜかエラー因子になる。
		try {
			File f = new File("c:\\lives\\" + streamer + "\\" + stream + "\\video.m3u8");
			if (f.exists()) {

				BufferedReader br = new BufferedReader(new FileReader(f));
				String line = "";
				String m3u8 = "";
				while ((line = br.readLine()) != null) {
					if (!line.equals("video000.ts")) {
						m3u8 += line + "\r\n";
					}
				}
				br.close();
				return m3u8;
			} else {
				String m3u8 = "#EXTM3U\r\n"
						+ "#EXT-X-VERSION:3\r\n"
						+ "#EXT-X-TARGETDURATION:2\r\n"
						+ "#EXT-X-MEDIA-SEQUENCE:0\r\n"
						+ "#EXT-X-PLAYLIST-TYPE:EVENT\r\n";
				f = new File("c:\\lives\\" + streamer + "\\" + stream + "\\.cache");
				BufferedReader br = new BufferedReader(new FileReader(f));
				String line = "";
				while ((line = br.readLine()) != null) {
					String[] str = line.split("§");
					m3u8 += "#EXTINF:" + str[1] + "," + stream + "\r\n";
					m3u8 += str[0] + "\r\n";
				}
				br.close();
				return m3u8;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/{streamer}/{stream}/chat")
	@ResponseBody
	public String getchat(@PathVariable String streamer, @PathVariable String stream, @RequestParam String time) {
		ArrayList<String> comment = new ArrayList<String>();
		BigDecimal time_ = new BigDecimal(time);
		try {
			File f = new File("c:\\lives\\" + streamer + "\\" + stream + "\\chat");
			if (!f.exists()) {
				f.createNewFile();
			}
			BufferedReader fr = new BufferedReader(new FileReader(f));
			String result = "[";
			boolean first = true;
			String line = "";
			while ((line = fr.readLine()) != null) {
				if (new BigDecimal(line.split("§")[0]).compareTo(time_) <=0) {
					comment.add("{\"name\":\"" + line.split("§")[2] + "\",\"message\":\"" + line.split("§")[1]
							+ "\",\"time\":\"" + line.split("§")[0] + "\"}");
				} else {
					break;
				}
			}
			for (String s : comment) {
				if (first) {
					result += s;
					first = false;
				} else {
					result += "," + s;
				}
			}
			fr.close();
			return result + "]";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping("/{streamer}/{stream}/chat")
	@ResponseBody
	public String postchat(HttpServletRequest request, @PathVariable String streamer, @PathVariable String stream,
			@RequestParam("message") String message, @RequestParam("time") String time) {
		System.out.println(message + "\t" + time);
		try {
			new BigDecimal(time);
			File f = new File("c:\\lives\\" + streamer + "\\" + stream + "\\chat");
			if (!f.exists()) {
				f.createNewFile();
			}
			FileWriter fw = new FileWriter(f, true);
			// validate name and message
			if (message.length() >= 1) {
				// decode CSS string
				message = message.replace("&", "&amp;");
				message = message.replace("<", "&lt;");
				message = message.replace(">", "&gt;");
				message = message.replace("\"", "&quot;");
				message = message.replace("'", "&#39;");
				message = message.replace("/", "&#47;");
				message = message.replace("\\", "&#92;");
				message = message.replace("\n", "<br>");
				message = message.replace("\r", "<br>");
				message = message.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
				message = message.replace(" ", "&nbsp;");
				fw.write(time + "§" + message + "§" + request.getRemoteAddr() + "\r\n");
				fw.close();
				return "200";
			} else {
				fw.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/{streamer}/{stream}/{file}")
	@ResponseBody
	public byte[] video(HttpServletRequest request, @PathVariable String streamer, @PathVariable String stream,
			@PathVariable String file)
			throws IOException {

		try {
			InputStream in = new FileInputStream(new File("c:\\lives\\" + streamer + "\\" + stream + "\\" + file));
			return IOUtils.toByteArray(in);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(
					"↑c:\\lives\\" + streamer + "\\" + stream + "\\" + file + "    (" + request.getRemoteAddr() + ")↑");
		}
		return null;
	}

	@RequestMapping("/{streamer}/{file}")
	@ResponseBody
	public byte[] icon(@PathVariable String streamer, @PathVariable String file) {
		try {
			InputStream in = new FileInputStream(new File("c:\\lives\\" + streamer + "\\" + file));
			return IOUtils.toByteArray(in);
		} catch (Exception e) {
			System.out.println("c:\\lives\\" + streamer + "\\" + file);
		}
		return null;
	}
}
