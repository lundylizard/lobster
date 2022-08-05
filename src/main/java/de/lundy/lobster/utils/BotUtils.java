package de.lundy.lobster.utils;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.concurrent.TimeUnit;

public class BotUtils {

    private BotUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String formatTime(long millis) {

        var hours = millis / TimeUnit.HOURS.toMillis(1);
        var minutes = millis / TimeUnit.MINUTES.toMillis(1) % 60;
        var seconds = millis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);

    }

    public static String getTrackPosition(AudioTrack track) {

        var current = new long[]{(track.getPosition() / 1000) / 60, (track.getPosition() / 1000) % 60};
        var duration = new long[]{(track.getDuration() / 1000) / 60, (track.getDuration() / 1000) % 60};

        return String.format("%02d:%02d / %02d:%02d", current[0], current[1], duration[0], duration[1]);

    }

}
