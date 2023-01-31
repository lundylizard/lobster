package me.lundy.lobster.listeners;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuildJoinListener extends ListenerAdapter {

    private final Logger logger = LoggerFactory.getLogger(GuildJoinListener.class);

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        logger.info("+ Joined guild {} ({})", event.getGuild().getName(), event.getJDA().getGuilds().size());
    }
}
