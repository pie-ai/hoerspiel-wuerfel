package de.pa2.projects.audio.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.medialist.MediaListItem;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventAdapter;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

/**
 * audio controller implementation that uses vlc using vlcj
 * 
 * best usage example was
 * https://github.com/caprica/vlcj/blob/master/src/test/java/uk/co/caprica/vlcj/test/list/TestMediaListPlayer.java
 */
public class VLCHeadlessController implements AudioController {
	private static final Logger LOG = LoggerFactory.getLogger(VLCHeadlessController.class);
	private Thread playerThread = null;
	private MediaPlayerFactory mediaPlayerFactory;
	private MediaListPlayer mediaListPlayer;

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
				mediaPlayerFactory = new MediaPlayerFactory();
				mediaListPlayer = mediaPlayerFactory.newMediaListPlayer();
				mediaListPlayer.addMediaListPlayerEventListener(new MediaListPlayerEventAdapter() {
					@Override
					public void nextItem(MediaListPlayer mediaListPlayer, libvlc_media_t item, String itemMrl) {
						LOG.debug("nextItem()");
					}

					@Override
					public void stopped(MediaListPlayer mediaListPlayer) {
						LOG.debug("stopped");
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
				while (mediaListPlayer == null) {
					try {
						Thread.currentThread().sleep(100);
					} catch (InterruptedException e) {
						LOG.error("could not wait for initialization to be done: {}", e.getMessage(), e);
					}
				}

			}
		});
		initThread.run();
		try {
			initThread.join();
		} catch (InterruptedException e) {
			LOG.error("could not wait for initialization to be done: {}", e.getMessage(), e);
		}

	}

	@Override
	public void shutdown() {
		mediaListPlayer.release();
	}

	void test() {
		MediaList mediaList = mediaPlayerFactory.newMediaList();
		mediaList.addMedia("http://wdr-kiraka-live.icecast.wdr.de/wdr/kiraka/live/mp3/128/stream.mp3");

		mediaListPlayer.setMediaList(mediaList);
		mediaListPlayer.setMode(MediaListPlayerMode.LOOP);

		mediaListPlayer.play();

	}

	@Override
	public void clear() {
		LOG.debug("clear");
		MediaList mediaList = mediaPlayerFactory.newMediaList();
		mediaListPlayer.setMediaList(mediaList);
	}

	@Override
	public void play(String url) {
		LOG.debug("play: {}", url);

		MediaList mediaList = mediaPlayerFactory.newMediaList();
		mediaList.addMedia(url);

		mediaListPlayer.setMediaList(mediaList);
		// mediaListPlayer.setMode(MediaListPlayerMode.LOOP);

		mediaListPlayer.play();
	}

	public void enqueue(String url) {
		LOG.debug("enqueue: {}", url);
		if (mediaListPlayer.getMediaList() == null) {
			MediaList mediaList = mediaPlayerFactory.newMediaList();
			mediaListPlayer.setMediaList(mediaList);
		}

		mediaListPlayer.getMediaList().addMedia(url);

		if (LOG.isDebugEnabled()) {
			LOG.debug("current media list:");
			for (MediaListItem item : mediaListPlayer.getMediaList().items()) {
				LOG.debug("item: {}", item);
			}
		}
	}

	@Override
	public void stop() {
		LOG.debug("stop");
		mediaListPlayer.stop();
	}

	@Override
	public void pause() {
		LOG.debug("pause");
		mediaListPlayer.pause();
	}

}
