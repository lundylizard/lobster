package de.lundy.lobster.commands.music;

import de.lundy.lobster.commands.impl.Command;
import de.lundy.lobster.lavaplayer.PlayerManager;
import de.lundy.lobster.utils.BotUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class ShuffleCommand implements Command {

    @Override
    public void action(String[] args, @NotNull MessageReceivedEvent event) {

        var channel = event.getTextChannel();
        var self = Objects.requireNonNull(event.getMember()).getGuild().getSelfMember();
        var member = event.getMember();
        var memberVoiceState = member.getVoiceState();

        assert memberVoiceState != null;
        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage(":warning: You are not in a voice channel.").queue();
            return;
        }

        var selfVoiceState = self.getVoiceState();
        assert selfVoiceState != null;
        if (!Objects.equals(memberVoiceState.getChannel(), selfVoiceState.getChannel())) {
            channel.sendMessage(":warning: You need to be in the same voice channel as me.").queue();
            return;
        }

        var musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        var queue = musicManager.scheduler.queue;

        if (queue.isEmpty()) {
            channel.sendMessage(":warning: The queue is currently empty.").queue();
            return;
        }

        Random randomizer = new Random();

        if (args.length == 1) {
            randomizer = new Random(BotUtils.parseAsInt(args[0]));
        }

        var trackList = new LinkedList<>(queue);
        Collections.shuffle(trackList, randomizer);
        queue.clear();
        queue.addAll(trackList);
        channel.sendMessage("Successfully shuffled the queue.").queue();

    }
}
