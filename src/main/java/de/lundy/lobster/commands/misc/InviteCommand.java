package de.lundy.lobster.commands.misc;

import de.lundy.lobster.commands.impl.Command;
import de.lundy.lobster.utils.BotUtils;
import de.lundy.lobster.utils.mysql.SettingsManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public record InviteCommand(SettingsManager settingsManager) implements Command {

    @Override
    public void action(String[] args, @NotNull MessageReceivedEvent event) {

        var permissions = new ArrayList<Permission>();
        permissions.add(Permission.MESSAGE_WRITE);          // Permission to send messages
        permissions.add(Permission.MESSAGE_EMBED_LINKS);    // Permission to embed links
        permissions.add(Permission.MESSAGE_READ);           // Receive messages to process commands
        permissions.add(Permission.VIEW_CHANNEL);           // See text/voice-channels
        permissions.add(Permission.VOICE_CONNECT);          // Connect to VC
        permissions.add(Permission.VOICE_SPEAK);            // Send music
        permissions.add(Permission.MANAGE_CHANNEL);         // Modify voice channel size
        permissions.add(Permission.VOICE_MOVE_OTHERS);      // Join full channel

        var inviteUrl = event.getJDA().getInviteUrl(permissions);

        event.getTextChannel().sendMessage(new EmbedBuilder().setFooter(BotUtils.randomFooter(settingsManager.getPrefix(event.getGuild().getIdLong())))
                .setDescription("**INVITE LOBSTER BOT**\n\n[Click here](" + inviteUrl + ") to invite this bot to your server.")
                .setColor(Objects.requireNonNull(event.getGuild().getMember(event.getJDA().getSelfUser())).getColor())
                .build()).queue();

    }

}
