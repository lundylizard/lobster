package de.lundy.lobster.commands.music;

import de.lundy.lobster.commands.impl.Command;
import de.lundy.lobster.lavaplayer.PlayerManager;
import de.lundy.lobster.utils.ChatUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SkipCommand implements Command {

    @Override
    public void action(String[] args, @NotNull MessageReceivedEvent event) {

        var channel = event.getTextChannel();
        var self = Objects.requireNonNull(event.getMember()).getGuild().getSelfMember();
        var selfVoiceState = self.getVoiceState();

        assert selfVoiceState != null;
        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage(":warning: I am not playing anything.").queue();
            return;
        }

        var member = event.getMember();
        var memberVoiceState = member.getVoiceState();

        assert memberVoiceState != null;
        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage(":warning: You are not in a voice channel.").queue();
            return;
        }

        if (!Objects.equals(memberVoiceState.getChannel(), selfVoiceState.getChannel())) {
            channel.sendMessage(":warning: You need to be in the same voice channel as me.").queue();
            return;
        }

        var musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        var audioPlayer = musicManager.audioPlayer;

        if (audioPlayer.getPlayingTrack() == null) {
            channel.sendMessage(":warning: There is currently no track playing.").queue();
            return;
        }

        if (args.length != 0) {
            if (ChatUtils.checkIfNumber(args[0])) {
                channel.sendMessage(":warning: `" + args[0] + "` is not a valid number.").queue();
                return;
            }

            var amount = Integer.parseInt(args[0]);
            var queueSize = musicManager.scheduler.queue.size();

            if (amount > queueSize) {
                channel.sendMessage(":warning: The queue is " + queueSize + " songs long. You cannot skip more than that.").queue();
                return;
            }

            for (var i = 0; i < amount; i++) {
                musicManager.scheduler.nextTrack();
            }

            channel.sendMessage(":white_check_mark: Skipped `" + amount + "` songs from the queue.").queue();

        } else {

            var trackInfo = audioPlayer.getPlayingTrack().getInfo();
            channel.sendMessage(":white_check_mark: Skipped `" + trackInfo.title + "`").queue();
            musicManager.scheduler.nextTrack();

        }

    }
}


