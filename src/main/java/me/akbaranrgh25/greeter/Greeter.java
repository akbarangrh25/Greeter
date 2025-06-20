package me.akbaranrgh25.greeter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class Greeter extends JavaPlugin implements CommandExecutor, Listener {

    private final MiniMessage mini = MiniMessage.miniMessage();
    private final Random random = new Random();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("greeter").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("greeter.reload")) {
                sender.sendMessage(mini.deserialize("<red>You don't have permission.</red>"));
                return true;
            }
            reloadConfig();
            sender.sendMessage(mini.deserialize("<green>Greeter config reloaded!</green>"));
            return true;
        }
        sender.sendMessage(mini.deserialize("<gold>Usage: /greeter reload</gold>"));
        return true;
    }

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        Player newPlayer = event.getPlayer();
        var cfg = getConfig();

        int startDelay = cfg.getInt("delay-start", 3);
        long startTicks = startDelay * 20L;

        Bukkit.getScheduler()
                .runTaskLater(this, () -> scheduleGreetings(newPlayer), startTicks);
    }

    private void scheduleGreetings(@NotNull Player newPlayer) {
        var cfg = getConfig();

        List<Player> online = new ArrayList<>(Bukkit.getOnlinePlayers());
        online.remove(newPlayer);

        int count = (int) Math.ceil(online.size() * 0.25);
        Collections.shuffle(online);

        List<String> greetings = cfg.getStringList("greetings");
        int min = cfg.getInt("delay-min", 3);
        int max = cfg.getInt("delay-max", 10);

        for (int i = 0; i < count && i < online.size(); i++) {
            Player greeter = online.get(i);
            String raw = greetings.get(random.nextInt(greetings.size()));
            String filled = raw
                    .replace("%greeter%", greeter.getName())
                    .replace("%new_player%", newPlayer.getName());

            Component message = mini.deserialize(filled);
            int delaySeconds = min + random.nextInt(max - min + 1);
            long delayTicks = delaySeconds * 20L;

            Bukkit.getScheduler()
                    .runTaskLater(this, () -> newPlayer.sendMessage(message), delayTicks);
        }
    }
}
