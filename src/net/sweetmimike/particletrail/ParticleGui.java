package net.sweetmimike.particletrail;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ParticleGui {

	private static final String NAME = "§a§lParticle Trail";
	private Inventory inv;
	private int size = 54;


	public ParticleGui(Player owner) {
		inv = Bukkit.createInventory(owner, size, NAME);
		createMenu();
	}

	public void createMenu() {

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

		ItemStack totemIt = new ItemStack(Material.TOTEM_OF_UNDYING);
		rename(totemIt, "§5§lTotem");

		ItemStack dragonSkullIt = new ItemStack(Material.DRAGON_HEAD);
		rename(dragonSkullIt, "§1§lDragonBreath");

		ItemStack lavaBucketIt = new ItemStack(Material.LAVA_BUCKET);
		rename(lavaBucketIt, "§c§lLava");

		//
		ItemStack witherSkullIt = new ItemStack(Material.WITHER_SKELETON_SKULL);
		rename(witherSkullIt, "§0§lWither");

		ItemStack waterBucketIt = new ItemStack(Material.WATER_BUCKET);
		rename(waterBucketIt, "§3§lWater");

		ItemStack enchantmentTableIt = new ItemStack(Material.ENCHANTING_TABLE);
		rename(enchantmentTableIt, "§2§lEnchantment");

		ItemStack endRodIt = new ItemStack(Material.END_ROD);
		rename(endRodIt, "§d§lEnd Rod");

		ItemStack fireworkIt = new ItemStack(Material.FIREWORK_ROCKET);
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

		ItemStack snowballIt = new ItemStack(Material.SNOWBALL);
		rename(snowballIt, "§f§lSnowball");

		ItemStack brewingStandIt = new ItemStack(Material.BREWING_STAND);
		rename(brewingStandIt, "§8§lWitch");

		ItemStack panel = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
		
		ItemStack rotateItem = new ItemStack(Material.COMPASS);
		rename(rotateItem, "§e§lRotating Particle");

		//adding the glass panel
		for(int i = 0; i < inv.getSize(); i++)
			inv.setItem(i, panel);

		//adding the items to inv
		inv.setItem(10, elytraIt);
		inv.setItem(11, stoneSwordIt);
		inv.setItem(12, flameIt);
		inv.setItem(13, noteBlockIt);
		inv.setItem(14, heartIt);
		inv.setItem(15, fireChargeIt);
		inv.setItem(16, barrierIt);
		inv.setItem(19, emeraldIt);



		inv.setItem(20, totemIt);


		inv.setItem(21, dragonSkullIt);
		inv.setItem(22, lavaBucketIt);
		inv.setItem(23, witherSkullIt);
		inv.setItem(24, waterBucketIt);
		inv.setItem(25, enchantmentTableIt);
		inv.setItem(28, diamondSwordIt);
		inv.setItem(29, potionIt);
		inv.setItem(30, enderpearlIt);
		inv.setItem(31, slimeballIt);
		inv.setItem(32, snowballIt);
		inv.setItem(33, brewingStandIt);
		inv.setItem(34, fireworkIt);
		inv.setItem(37, endRodIt);
		inv.setItem(53, rotateItem);



	}

	public Inventory getInv() {
		return inv;
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

}
