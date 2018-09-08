package net.sweetmimike.particletrail;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.sweetmimike.particletrail.commands.CommandParticleTrail;
import net.sweetmimike.particletrail.events.ParticleTrailEvents;

public class Main extends JavaPlugin {
	
	public static final String PREFIX = "§2[§aParticleTrail§2]";

	@Override
	public void onEnable() {

		getCommand("pt").setExecutor(new CommandParticleTrail(this));

		Bukkit.getPluginManager().registerEvents(new ParticleTrailEvents(this), this);

		saveDefaultConfig();

		super.onEnable();
	}

	public Main getInstance() {
		return this;
	}

}
