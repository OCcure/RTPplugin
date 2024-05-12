package me.occure.rtpplugin;


import me.occure.rtpplugin.command.RTPcommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RTPplugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getLogger().warning("플러그인 활성화");

        Bukkit.getCommandMap().register("RTP_Plugin",new RTPcommand("rtp"));
    }

    @Override
    public void onDisable() {

    }
}
