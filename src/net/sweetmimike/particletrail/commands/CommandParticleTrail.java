package net.sweetmimike.particletrail.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.sweetmimike.particletrail.Main;
import net.sweetmimike.particletrail.ParticleGui;
import net.sweetmimike.particletrail.ParticleList;
import net.sweetmimike.particletrail.events.ParticleTrailEvents;

public class CommandParticleTrail implements CommandExecutor, TabCompleter {

	Main main;

	Map<String, Inventory> pInv = new HashMap<>();
	ArrayList<String> pName = new ArrayList<>();
	Boolean version = Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12");

	public CommandParticleTrail(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("reload")) {
				if(sender.hasPermission("pt.reload") ) {
					main.reloadConfig();
					System.out.println("Config file reloaded");
					return true;
				} else {
					sender.sendMessage("§4You do not have permission to do that");
					return true;
				} 
			}
		}

		if(sender instanceof Player) {
			Player p = (Player) sender;

			/**
			 * Creation of the inv when a player type "/pt" for the first time
			 */
			if(!pInv.containsKey(p.getName())) {

				ParticleGui pGui = new ParticleGui(p);

				pInv.put(p.getName(), pGui.getInv());

			}


			if(args.length == 0) {
				if(p.hasPermission("pt.open")) {

					p.openInventory(pInv.get(p.getName()));
					return true;
				} else {
					p.sendMessage("§4You do not have permission to do that");
					return true;
				}
			} 

			if(args.length == 1) {

				/**
				 * pt list
				 */
				if(args[0].equalsIgnoreCase("list")) {
					if(p.hasPermission("pt.list")) {
						List<String> list = new ArrayList<>();

						for(ParticleList str : ParticleList.values()) {
							String name = str.getName().substring(0, 1).toUpperCase() + str.getName().substring(1);
							list.add("§a" + name);
						}
						list.sort(String.CASE_INSENSITIVE_ORDER);
						String listToSend = list.toString().replace("[", "").replace("]", "").replace(",", "§r,");
						p.sendMessage(Main.PREFIX + " §aParticle available : " + listToSend);
						return true;
					} else {
						p.sendMessage("§4You do not have permission to do that");
						return true;
					}




					/**
					 * pt help
					 */
				} else if(args[0].equalsIgnoreCase("help")) {
					help(p); return true;



					/**
					 * pt reset | remove
					 */
				} else if(args[0].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("remove")) {
					if(p.hasPermission("pt.remove")) {

						if(!ParticleTrailEvents.playerParticle.containsKey(p.getName())) {
							p.sendMessage(Main.PREFIX + " §cYou currently have no particle trail"); return true;
						}
						p.sendMessage(Main.PREFIX + " §cParticle trail removed");
						ParticleTrailEvents.playerParticle.remove(p.getName());
						ParticleTrailEvents.isRotate.remove(p.getName());
						updateEnchantMenu(p);
						return true;
					} else {
						p.sendMessage("§4You do not have permission to do that");
						return true;
					}
				}

				//Search if args[0] is a particle name
				for(ParticleList str : ParticleList.values()) {
					if(args[0].equalsIgnoreCase(str.getName())) {
						if(p.hasPermission("pt." + str.getName())) {
							ParticleTrailEvents.playerParticle.put(p.getName(), str.getParticle());
							String name = str.getName().substring(0, 1).toUpperCase() + str.getName().substring(1);
							p.sendMessage(Main.PREFIX + " §a" + name + " Enable");

							//Inv update
							for(ItemStack is : pInv.get(p.getName()).getContents()) {
								if(is.getType() != Material.COMPASS) {
									ItemMeta meta = is.getItemMeta();
									meta.removeEnchant(Enchantment.DAMAGE_ALL);
									is.setItemMeta(meta);
									if(str.getMat() == is.getType()) {
										meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
										meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
										is.setItemMeta(meta);

									}
								}

							}
							return true;
						} else {
							p.sendMessage("§4You do not have permission to do that"); return true;
						}
					} 
				}
				p.performCommand("pt list"); return true;
			} else {
				help(p);
				return true;
			} 
		} else {
			sender.sendMessage("You are not a player !");
		}

		return false;
	}




	/**
	 * Send a help message to the player p
	 * @param p - A player
	 */
	public void help(Player p) {
		p.sendMessage("§c====== §2[§aParticleTrail§2] §c======");
		p.sendMessage("");
		p.sendMessage("§3> §a/pt §7- §aOpen the gui");
		p.sendMessage("§3> §a/pt <particle> §7- §aUse particles trails without opening the gui");
		p.sendMessage("§3> §a/pt list §7- §aAll the particles trails available");
		p.sendMessage("§3> §a/pt <remove|reset> §7- §aRemove your current particle trail");
		p.sendMessage("§3> §a/pt help §7- §aDisplay the help");
		p.sendMessage("");
		p.sendMessage("§c====== §2[§aParticleTrail§2] §c======");
	}

	public void updateEnchantMenu(Player p) {
		for(ItemStack is : pInv.get(p.getName()) ) {
			if(is.getType() != Material.COMPASS) {
				ItemMeta meta = is.getItemMeta();
				meta.removeEnchant(Enchantment.DAMAGE_ALL);
				is.setItemMeta(meta);
			}
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

		//Player p = (Player) sender;
		final String[] commandsArgs = {"list", "help", "remove", "reset", "reload"};


		if(cmd.getName().equalsIgnoreCase("pt")) {
			if(args.length == 1) {
				List<String> list = new ArrayList<>();
				for(ParticleList particle : ParticleList.values()) {
					if(particle.getName().startsWith(args[0]) && sender.hasPermission("pt." + particle.getName())) {
						list.add(particle.getName());
					}

				}

				for(String str : commandsArgs) {
					if(str.startsWith(args[0]) && sender.hasPermission("pt." + str)) {
						list.add(str);
					}
				}
				list.sort(String.CASE_INSENSITIVE_ORDER);
				return list;
			}
		}

		return null;
	}

	//	private Vector rotateAroundAxisX(Vector v, double angle) {
	//        angle = Math.toRadians(angle);
	//        double y, z, cos, sin;
	//        cos = Math.cos(angle);
	//        sin = Math.sin(angle);
	//        y = v.getY() * cos - v.getZ() * sin;
	//        z = v.getY() * sin + v.getZ() * cos;
	//        return v.setY(y).setZ(z);
	//    }
	//	
	//	private Vector rotateAroundAxisY(Vector v, double angle) {
	//        angle = -angle;
	//        angle = Math.toRadians(angle);
	//        double x, z, cos, sin;
	//        cos = Math.cos(angle);
	//        sin = Math.sin(angle);
	//        x = v.getX() * cos + v.getZ() * sin;
	//        z = v.getX() * -sin + v.getZ() * cos;
	//        return v.setX(x).setZ(z);
	//    }
	//
	//    private Vector rotateAroundAxisZ(Vector v, double angle) {
	//        angle = Math.toRadians(angle);
	//        double x, y, cos, sin;
	//        cos = Math.cos(angle);
	//        sin = Math.sin(angle);
	//        x = v.getX() * cos - v.getY() * sin;
	//        y = v.getX() * sin + v.getY() * cos;
	//        return v.setX(x).setY(y);
	//    }
}
