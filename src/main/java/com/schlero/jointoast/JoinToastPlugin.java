package com.schlero.jointoast;

import org.powernukkitx.Player;
import org.powernukkitx.event.EventPriority;
import org.powernukkitx.event.Listener;
import org.powernukkitx.event.player.PlayerJoinEvent;
import org.powernukkitx.plugin.PluginBase;

public class JoinToastPlugin extends PluginBase {

    @Override
    public void onEnable() {
        // Save default config.yml from resources if it doesn't exist
        this.saveDefaultConfig();

        // Create a dummy Listener object to pass to PNX
        Listener dummyListener = new Listener() {};

        // Direct EventExecutor bypasses MethodEventExecutor reflection completely
        this.getServer().getPluginManager().registerEvent(
            PlayerJoinEvent.class,
            dummyListener,
            EventPriority.NORMAL,
            (listener, event) -> {
                if (event instanceof PlayerJoinEvent joinEvent) {
                    Player player = joinEvent.getPlayer();

                    // Fetch title and message from config
                    String title = getConfig().getString("toast.title", "Welcome to the server!");
                    String message = getConfig().getString("toast.message", "Hello, {player.name}! Welcome to our server. Please make sure to follow our rules!");

                    // Replace placeholders
                    title = title.replace("{player.name}", player.getName());
                    message = message.replace("{player.name}", player.getName());

                    // Send Toast
                    player.sendToast(title, message);
                }
            },
            this
        );

        this.getLogger().info("JoinToast has been enabled!");
    }
}
