package net.sweetmimike.rotatingparticletrail;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RotatingParticleGui {

	public static final String NAME = "§a§lRotating Particle Trail";
	private Inventory inv;


	public RotatingParticleGui(int size, Player owner) {
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

		ItemStack panel = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0);

		//adding the glass panel
		for(int i = 0; i < inv.getSize(); i++)
			inv.setItem(i, panel);

		//adding the items to inv
		
		inv.setItem(12, flameIt);
		inv.setItem(13, noteBlockIt);
		inv.setItem(14, heartIt);

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
