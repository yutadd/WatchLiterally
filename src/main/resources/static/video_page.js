$(function() {
	var video = document.getElementById("video");
	var videoSrc = 'm3u8';
	if (video.canPlayType('application/vnd.apple.mpegurl')) {
		video.src = videoSrc;
	} else if (Hls.isSupported()) {
		var hls = new Hls();
		hls.loadSource(videoSrc);
		hls.attachMedia(video);
	}
	video.play();
});