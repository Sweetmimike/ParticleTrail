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
import net.sweetmimike.particletrail.ParticleList;
import net.sweetmimike.particletrail.events.ParticleTrailEvents;

public class CommandParticleTrail implements CommandExecutor, TabCompleter {

	Main main;

	Inventory menu;
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
				ItemStack flameIt = new ItemStack(Material.BLAZE_POWDER);
				rename(flameIt, "§6§lFlame");

				ItemStack heartIt = new ItemStack(Material.APPLE);
				rename(heartIt, "§c§lHeart");

				ItemStack noteBlockIt = new ItemStack(Material.NOTE_BLOCK);
				rename(noteBlockIt, "§b§lNote");

				ItemStack fireChargeIt = new ItemStack(Material.MAGMA_CREAM);
				rename(fireChargeIt, "§7§lVillager Angry");

				ItemStack stoneSwordIt = new ItemStack(Material.STONE_SWORD);
				rename(stoneSwordIt, "§8§lCrit");

				ItemStack barrierIt = new ItemStack(Material.BARRIER);
				rename(barrierIt, "§4§lBarrier");

				ItemStack emeraldIt = new ItemStack(Material.EMERALD);
				rename(emeraldIt, "§e§lVillager Happy");

				//for cloud
				ItemStack elytraIt = new ItemStack(Material.ELYTRA);
				rename(elytraIt, "§f§lCloud");

				ItemStack totemIt = new ItemStack(Material.TOTEM);
				rename(totemIt, "§5§lTotem");

				ItemStack dragonSkullIt = new ItemStack(Material.SKULL_ITEM, 1, (byte)5);
				rename(dragonSkullIt, "§1§lDragonBreath");

				ItemStack lavaBucketIt = new ItemStack(Material.LAVA_BUCKET);
				rename(lavaBucketIt, "§c§lLava");

				//
				ItemStack witherSkullIt = new ItemStack(Material.SKULL_ITEM, 1, (byte)1);
				rename(witherSkullIt, "§0§lWither");

				ItemStack waterBucketIt = new ItemStack(Material.WATER_BUCKET);
				rename(waterBucketIt, "§3§lWater");

				ItemStack enchantmentTableIt = new ItemStack(Material.ENCHANTMENT_TABLE);
				rename(enchantmentTableIt, "§2§lEnchantment");

				ItemStack endRodIt = new ItemStack(Material.END_ROD);
				rename(endRodIt, "§d§lEnd Rod");

				ItemStack fireworkIt = new ItemStack(Material.FIREWORK);
				rename(fireworkIt, "§1§lFirework");

				ItemStack diamondSwordIt = new ItemStack(Material.DIAMOND_SWORD);
				rename(diamondSwordIt, "§9§lMagic Crit");

				ItemStack potionIt = new ItemStack(Material.POTION);
				ItemMeta m = potionIt.getItemMeta();
				m.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				potionIt.setItemMeta(m);
				rename(potionIt, "§e§lPotion");

				ItemStack enderpearlIt = new ItemStack(Material.ENDER_PEARL);
				rename(enderpearlIt, "§5§lPortal");

				ItemStack slimeballIt = new ItemStack(Material.SLIME_BALL);
				rename(slimeballIt, "§a§lSlime");

				ItemStack snowballIt = new ItemStack(Material.SNOW_BALL);
				rename(snowballIt, "§f§lSnowball");

				ItemStack brewingStandIt = new ItemStack(Material.BREWING_STAND_ITEM);
				rename(brewingStandIt, "§8§lWitch");

				ItemStack panel = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0);

				menu = Bukkit.createInventory(p, 54, "§a§lParticle Trail");

				//adding the glass panel
				for(int i = 0; i < menu.getSize(); i++)
					menu.setItem(i, panel);

				//adding the items to menu
				menu.setItem(10, elytraIt);
				menu.setItem(11, stoneSwordIt);
				menu.setItem(12, flameIt);
				menu.setItem(13, noteBlockIt);
				menu.setItem(14, heartIt);
				menu.setItem(15, fireChargeIt);
				menu.setItem(16, barrierIt);
				menu.setItem(19, emeraldIt);



				menu.setItem(20, totemIt);


				menu.setItem(21, dragonSkullIt);
				menu.setItem(22, lavaBucketIt);
				menu.setItem(23, witherSkullIt);
				menu.setItem(24, waterBucketIt);
				menu.setItem(25, enchantmentTableIt);
				menu.setItem(28, diamondSwordIt);
				menu.setItem(29, potionIt);
				menu.setItem(30, enderpearlIt);
				menu.setItem(31, slimeballIt);
				menu.setItem(32, snowballIt);
				menu.setItem(33, brewingStandIt);
				menu.setItem(34, fireworkIt);
				menu.setItem(37, endRodIt);




				pInv.put(p.getName(), menu);
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
						p.sendMessage("§2[§aParticleTrail§2] Particle available : " + listToSend);
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
							p.sendMessage("§2[§aParticleTrail§2] §cYou currently have no particle trail"); return true;
						}
						p.sendMessage("§2[§aParticleTrail§2] §cParticle trail removed");
						ParticleTrailEvents.playerParticle.remove(p.getName());
						updateEnchantMenu(p);
						return true;
					} else {
						p.sendMessage("§4You do not have permission to do that");
						return true;
					}
				}

				//					else if(args[0].equalsIgnoreCase("rotate")) {
				//					new BukkitRunnable() {
				//						
				//						
				//			            double t = 5;
				//			            double r = 0.5;
				//						
				//						@Override
				//						public void run() {
				//							Location loc = p.getLocation();
				//							
				//							t = t + Math.PI / 16;
				//			                double x = r * Math.cos(t);
				//			                double y = 0;
				//			                double z = r * Math.sin(t);
				//							Vector vec = new Vector(x, 0, z);
				//							vec = rotateAroundAxisY(vec, 10);
				//			                loc.add(vec.getX(), vec.getY(), vec.getZ());
				//							World world = p.getWorld();
				//							
				//							world.spawnParticle(Particle.FLAME, loc.getX(), loc.getY() + 2, loc.getZ(), 1, 0.01, 0.01, 0.01, 0.001);
				//							 loc.subtract(vec.getX(), vec.getY(), vec.getZ());
				//							 if (t > Math.PI * 8) {
				//				                    this.cancel();
				//				                }
				//							
				//						}
				//					}.runTaskTimer(main, 0, 1);
				//				}

				//Search if args[0] is a particle name
				for(ParticleList str : ParticleList.values()) {
					if(args[0].equalsIgnoreCase(str.getName())) {
						if(p.hasPermission("pt." + str.getName())) {
							ParticleTrailEvents.playerParticle.put(p.getName(), str.getParticle());
							String name = str.getName().substring(0, 1).toUpperCase() + str.getName().substring(1);
							p.sendMessage("§2[§aParticleTrail§2] §a" + name + " Enable");

							//Inv update
							for(ItemStack is : pInv.get(p.getName()).getContents()) {
								ItemMeta meta = is.getItemMeta();
								meta.removeEnchant(Enchantment.DAMAGE_ALL);
								is.setItemMeta(meta);
								if(str.getMat() == is.getType()) {
									meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
									meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
									is.setItemMeta(meta);

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
	 * Rename your item
	 * @param item - Item to rename
	 * @param name - Name that will be applied on the item
	 */
	public void rename(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
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
			ItemMeta meta = is.getItemMeta();
			meta.removeEnchant(Enchantment.DAMAGE_ALL);
			is.setItemMeta(meta);
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
