package de.pa2.projects.audio.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;

public class VLCHeadlessController implements AudioController {
	private static final Logger LOG = LoggerFactory.getLogger(VLCHeadlessController.class);
	private Thread playerThread = null;
	private AudioMediaPlayerComponent mediaPlayerComponent;

	@Override
	public void init() throws IOException {
		// discover vlc
		boolean found = new NativeDiscovery().discover();
		if (!found) {
			LOG.error("could not discover vlc");
			throw new IOException("could not discover vlc");
		}
		// start player in thread
		playerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				mediaPlayerComponent = new AudioMediaPlayerComponent();
				mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
					@Override
					public void finished(MediaPlayer mediaPlayer) {
						LOG.info("finished play list");
					}

					@Override
					public void error(MediaPlayer mediaPlayer) {
						LOG.error("error in mdedia player");
					}
				});
				try {
					Thread.currentThread().join();
				} catch (InterruptedException e) {
					LOG.error("could not join player thread: {}", e.getMessage(), e);
				}
			}
		});
		playerThread.start();

		Thread initThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (mediaPlayerComponent == null) {
					try {
						Thread.currentThread().sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		initThread.run();
		try {
			initThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void shutdown() {
		mediaPlayerComponent.release();
	}

	void test() {
		mediaPlayerComponent.getMediaPlayer()
				.playMedia("http://wdr-kiraka-live.icecast.wdr.de/wdr/kiraka/live/mp3/128/stream.mp3");
	}

	@Override
	public void clear() {
		LOG.debug("clear");

		if (mediaPlayerComponent != null && mediaPlayerComponent.getMediaPlayer() != null) {
			// mediaPlayerComponent.getMediaPlayer().prepareMedia(null);
			// mediaPlayerComponent.getMediaPlayer().subItemsMediaList().clear();
		}

	}

	@Override
	public void play(String url) {
		LOG.debug("play: {}", url);

		MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
		MediaListPlayer mediaListPlayer = mediaPlayerFactory.newMediaListPlayer();
		MediaList mediaList = mediaPlayerFactory.newMediaList();
		mediaList.addMedia(url);
		mediaListPlayer.setMediaList(mediaList);
		mediaListPlayer.play();
	}

	@Override
	public void stop() {
		LOG.debug("stop");
		if (mediaPlayerComponent != null && mediaPlayerComponent.getMediaPlayer() != null) {
			mediaPlayerComponent.getMediaPlayer().stop();
		}
	}

	@Override
	public void pause() {
		LOG.debug("pause");
		mediaPlayerComponent.getMediaPlayer().pause();

	}

}
