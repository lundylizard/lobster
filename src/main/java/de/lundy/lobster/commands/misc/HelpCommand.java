package de.lundy.lobster.commands.misc;

import de.lundy.lobster.commands.impl.Command;
import de.lundy.lobster.utils.BotUtils;
import de.lundy.lobster.utils.mysql.SettingsManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record HelpCommand(SettingsManager settingsManager) implements Command {

    @Override
    public void action(String[] args, @NotNull MessageReceivedEvent event) {

        var helpContent = """
                **LOBSTER MUSIC BOT**

                Made by lundylizard » [Twitch](https://twitch.tv/iundylizard) - [Twitter](https://twitter.com/lundylizard) - [GitHub](https://github.com/lundylizard/lobster) - [Discord](https://discord.gg/Hk5YP5AWhW)

                `%prefix%play <top> [link]` - Adds a song to the queue <at the top>.
                `%prefix%move [from] [to]` - Move songs in the queue.
                `%prefix%remove [index]` - Removes a song from the queue.
                `%prefix%skip [amount]` - Skips [amount] of songs in the queue.
                `%prefix%seek [mm:ss]` - Changes position in the song.
                `%prefix%shuffle` - Shuffles the queue.
                `%prefix%leave` - Leave the current voice channel.
                `%prefix%queue` - Shows current queue.
                `%prefix%join` - Join the voice channel you're in.
                `%prefix%stop` - Stops the playback and leaves vc.
                `%prefix%np` - Displays the current song.
                                
                __Misc. Commands:__
                `%prefix%prefix` - Change the prefix for this bot.
                `%prefix%invite` - Sends a bot invitation link into the channel.
                                
                A detailed list of commands can be found **[here](https://github.com/lundylizard/lobster/blob/master/docs/COMMANDS.md)**
                """;

        var serverId = event.getGuild().getIdLong();
        var prefix = settingsManager.getPrefix(serverId);
        var color = Objects.requireNonNull(event.getGuild().getMember(event.getJDA().getSelfUser())).getColor();

        event.getChannel().sendMessage(new EmbedBuilder().setColor(color).setDescription(helpContent.replace("%prefix%", prefix)).setFooter(BotUtils.randomFooter(prefix)).setThumbnail("https://raw.githubusercontent.com/lundylizard/lobster/master/src/main/resources/t_lobster_image_PixelForgeGames.png").build()).queue();

    }
}
