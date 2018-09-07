package net.sweetmimike.particletrail.events;

import java.util.HashMap;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.sweetmimike.particletrail.Main;
import net.sweetmimike.particletrail.ParticleList;
import net.sweetmimike.particletrail.commands.CommandParticleTrail;

public class ParticleTrailEvents implements Listener {

	CommandParticleTrail commandParticleTrail;
	ParticleList pList;
	Main main;

	public static Map<String, Particle> playerParticle = new HashMap<>();	

	public ParticleTrailEvents(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onClickMenu(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		if(e.getInventory().getName().equalsIgnoreCase("§a§lParticle Trail")) {
			e.setCancelled(true);

			if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
				ItemStack itemClicked = e.getCurrentItem();
				ItemMeta meta = itemClicked.getItemMeta();
				for(ParticleList mat : ParticleList.values()) {
					if(meta.hasEnchants() && meta != null) {
						meta.removeEnchant(Enchantment.DAMAGE_ALL);
						itemClicked.setItemMeta(meta);
						p.performCommand("pt reset");
						
						p.closeInventory();
						return;
					}
						
					if(mat.getMat() == itemClicked.getType() || mat.getItem() == itemClicked) {
						p.performCommand("pt " + mat.getName().toLowerCase());
						System.out.println();
						p.closeInventory();
					}
				}
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = (Player) e.getPlayer();
		Location loc = p.getLocation();
		World world = p.getWorld();
		Particle particleName = playerParticle.get(p.getName());
		String pName = "";
		for(ParticleList pList : ParticleList.values()) {
			if(pList.getParticle() == particleName)
				pName = pList.getName();
		}

		if(playerParticle.containsKey(p.getName())) {
			// test si le joueur bouge d'un block entier
			if(e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()) {
				world.spawnParticle(particleName, loc.getX(), loc.getY() + 0.15, loc.getZ(), main.getConfig().getInt("particle." + pName + ".count"), 0.001, 0.001, 0.001, main.getConfig().getDouble("particle." + pName + ".speed"));
			}
		}
	}
	
	public void sendNotifEnable(Player p, String particleName) {
		p.sendMessage("§2[§aParticleTrail§2] §b" + particleName + " Enable");
	}
	
	public Map<String, Particle> getPlayerParticle() {
		return playerParticle;
	}

	public void setPlayerParticle(Player player, Particle particle) {
		playerParticle.put(player.getName(), particle);
	}
	
	public ParticleTrailEvents getInstance() {
		return this;
	}
}
