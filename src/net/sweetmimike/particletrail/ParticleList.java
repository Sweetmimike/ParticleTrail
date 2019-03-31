package net.sweetmimike.particletrail;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

public enum ParticleList {
	
	//1er ajout
	FLAME("flame", Particle.FLAME, Material.BLAZE_POWDER),
	HEART("heart", Particle.HEART, Material.APPLE),
	CRIT("crit", Particle.CRIT, Material.STONE_SWORD),
	ANGRYVILLAGER("angryvillager", Particle.VILLAGER_ANGRY, Material.MAGMA_CREAM),
	NOTE("note", Particle.NOTE, Material.NOTE_BLOCK),
	
	//2eme ajout
	BARRIER("barrier", Particle.BARRIER, Material.BARRIER),
	HAPPYVILLAGER("happyvillager", Particle.VILLAGER_HAPPY, Material.EMERALD),
	CLOUD("cloud", Particle.CLOUD, Material.ELYTRA),
	LAVA("lava", Particle.LAVA, Material.LAVA_BUCKET),
	
	TOTEM("totem", Particle.TOTEM, Material.TOTEM_OF_UNDYING),
	DRAGON_BREATH("dragon_breath", Particle.DRAGON_BREATH, Material.DRAGON_HEAD),
	
	//3eme ajout
	BLACKHEART("blackheart", Particle.DAMAGE_INDICATOR, Material.WITHER_SKELETON_SKULL),
	DRIP_WATER("dripwater", Particle.DRIP_WATER, Material.WATER_BUCKET),
	ENCHANTMENT("enchantment", Particle.ENCHANTMENT_TABLE, Material.ENCHANTING_TABLE),
	END_ROD("endrod", Particle.END_ROD, Material.END_ROD),
	FIREWORK("firework", Particle.FIREWORKS_SPARK, Material.FIREWORK_ROCKET),
	MAGIC_CRIT("magic_crit", Particle.CRIT_MAGIC, Material.DIAMOND_SWORD),
	SPELL("spell", Particle.SPELL, Material.POTION),
	PORTAL("portal", Particle.PORTAL, Material.ENDER_PEARL),
	SLIME("slime", Particle.SLIME, Material.SLIME_BALL),
	SNOWBALL("snowball", Particle.SNOWBALL, Material.SNOWBALL),
	WITCH("witch", Particle.SPELL_WITCH, Material.BREWING_STAND),
	
	
	;
	
	private String name = "";
	private Particle particle;
	private Material mat;
	private ItemStack item;
	
	
	private ParticleList(String name, Particle particle, Material mat) {
		this.name = name;
		this.particle = particle;
		this.mat = mat;
	}
	
	private ParticleList(String name, Particle particle, ItemStack item) {
		this.name = name;
		this.particle = particle;
		this.mat = item.getType();
		this.item = item;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Particle getParticle() {
		return particle;
	}

	public void setParticle(Particle particle) {
		this.particle = particle;
	}

	public Material getMat() {
		return mat;
	}

	public void setMat(Material mat) {
		this.mat = mat;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

}
