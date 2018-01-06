import org.bff.javampd.server.MPD;

public class PlayKiraka {
	public static void main(String... args) {
		String server = "localhost";
		int port = 6600;
		String playListUrl = "http://www.wdr.de/wdrlive/media/kiraka.m3u";
		MPD.Builder builder = new MPD.Builder();
		builder.server(server);
		builder.port(port);
		MPD mpd = builder.build();
		mpd.getPlaylist().loadPlaylist(playListUrl);
		mpd.close();
	}
}
