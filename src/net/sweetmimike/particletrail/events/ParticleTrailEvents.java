package net.sweetmimike.particletrail.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.sweetmimike.particletrail.Main;
import net.sweetmimike.particletrail.ParticleList;
import net.sweetmimike.particletrail.commands.CommandParticleTrail;

public class ParticleTrailEvents implements Listener {

	CommandParticleTrail commandParticleTrail;
	ParticleList pList;
	Main main;

	public static List<String> isRotate = new ArrayList<>();
	public static Map<String, Particle> playerParticle = new HashMap<>();	

	public ParticleTrailEvents(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onClickMenu(InventoryClickEvent e) {

		Player p = (Player) e.getWhoClicked();

		if(e.getInventory().getName().equalsIgnoreCase("§a§lParticle Trail")) {
			e.setCancelled(true);

			//le joueur clique sur la boussole et a déjà une particle d'activée
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.COMPASS && playerParticle.containsKey(p.getName())) {
				ItemStack compass = e.getCurrentItem();
				ItemMeta metaCompass = compass.getItemMeta();
				if(metaCompass.hasEnchants()) {
					isRotate.remove(p.getName());
					metaCompass.removeEnchant(Enchantment.DAMAGE_ALL);
					compass.setItemMeta(metaCompass);
					return;
				}
				metaCompass.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
				metaCompass.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				compass.setItemMeta(metaCompass);
				isRotate.add(p.getName());



				new BukkitRunnable() {
					Location loc;
					World world;
					/* ou = math.toRadian(90); */
					double angle = 2 * Math.PI / 16; 
					double radius = 1.5;
					double x;
					double z;
					@Override
					public void run() {
						loc = p.getLocation();
						world = p.getWorld();
						angle += 0.1;
						x = radius * Math.cos(angle);
						z = radius * Math.sin(angle);
						loc.add(x, 1, z);
						String pName = "";
						for(ParticleList pList : ParticleList.values()) {
							if(pList.getParticle() == playerParticle.get(p.getName()))
								pName = pList.getName();
						}
						if(playerParticle.containsKey(p.getName())) {
							world.spawnParticle(playerParticle.get(p.getName()), loc.getX(), loc.getY() + 0.15, loc.getZ(), main.getConfig().getInt("particle." + pName + ".count"), 0.001, 0.001, 0.001, main.getConfig().getDouble("particle." + pName + ".speed"));
						}
						loc.subtract(x, 1, z);
						if(!(isRotate.contains(p.getName()))) {
							cancel();
						}

					}
				}.runTaskTimer(main, 0, (long)0.5);
				return;
			}

			if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
				ItemStack itemClicked = e.getCurrentItem();
				ItemMeta meta = itemClicked.getItemMeta();
				for(ParticleList particle : ParticleList.values()) {
					if(meta.hasEnchants() && meta != null) {
						meta.removeEnchant(Enchantment.DAMAGE_ALL);
						itemClicked.setItemMeta(meta);
						p.performCommand("pt reset");

						p.closeInventory();
						return;
					}

					if(particle.getMat() == itemClicked.getType() || particle.getItem() == itemClicked) {
						p.performCommand("pt " + particle.getName().toLowerCase());
						System.out.println();
						p.closeInventory();

					}
				}
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		World world = p.getWorld();
		Particle particleName = playerParticle.get(p.getName());
		String pName = "";
		for(ParticleList pList : ParticleList.values()) {
			if(pList.getParticle() == particleName)
				pName = pList.getName();
		}

		if(playerParticle.containsKey(p.getName()) && !(isRotate.contains(p.getName()))) {
			// test si le joueur bouge d'un block entier
			if(e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()) {
				world.spawnParticle(particleName, loc.getX(), loc.getY() + 0.15, loc.getZ(), main.getConfig().getInt("particle." + pName + ".count"), 0.001, 0.001, 0.001, main.getConfig().getDouble("particle." + pName + ".speed"));
			}
		}
	}

	public Map<String, Particle> getPlayerParticle() {
		return playerParticle;
	}

	public void setPlayerParticle(Player player, Particle particle) {
		playerParticle.put(player.getName(), particle);
	}
}
