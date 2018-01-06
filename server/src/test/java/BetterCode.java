import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class BetterCode {
	private final AudioMediaPlayerComponent mediaPlayerComponent;

	public static void main(String[] args) {
		boolean found = new NativeDiscovery().discover();
		new BetterCode("http://wdr-kiraka-live.icecast.wdr.de/wdr/kiraka/live/mp3/128/stream.mp3");
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
		}
	}

	public BetterCode(String mrl) {
		mediaPlayerComponent = new AudioMediaPlayerComponent();
		mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
			@Override
			public void finished(MediaPlayer mediaPlayer) {
				exit(0);
			}

			@Override
			public void error(MediaPlayer mediaPlayer) {
				exit(1);
			}
		});
		mediaPlayerComponent.getMediaPlayer().playMedia(mrl);
	}

	private void exit(int result) {
		mediaPlayerComponent.release();
		System.exit(result);
	}
}
