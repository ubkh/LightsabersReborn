package dev.ubayd.lightsabersreborn.item;

public enum LightsaberTier {
//    Lightsaber(3, 10.0F, (float) LightsaberConfig.generalLightsaberDamage, (float) LightsaberConfig.generalLightsaberSpeed, 24),
//    Darksaber(3, 10.0F, (float) LightsaberConfig.darksaberDamage, (float) LightsaberConfig.darksaberSpeed, 24),
//    White_Lightsaber(3, 10.0F, (float) LightsaberConfig.whiteLightsaberDamage, (float) LightsaberConfig.whiteLightsaberSpeed, 24),
//    Purple_Lightsaber(3, 10.0F, (float) LightsaberConfig.purpleLightsaberDamage, (float) LightsaberConfig.purpleLightsaberSpeed, 24);

    // TIER(HarvestLevel, Efficiency, AttackDamage, AttackSpeed, Enchantability)

    // Standard Lightsabers: Slightly stronger than Netherite (8.5 Damage, 2.0 Speed)
    Lightsaber(3, 10.0F, 8.5F, 2.0F, 24),

    // Darksaber: Heavy hitter (10.0 Damage, 1.8 Speed)
    Darksaber(3, 10.0F, 10.0F, 1.8F, 24),

    // White Lightsaber: Fast and precise (8.0 Damage, 2.4 Speed)
    White_Lightsaber(3, 10.0F, 8.0F, 2.4F, 24),

    // Purple Lightsaber: Balanced champion (9.0 Damage, 2.0 Speed)
    Purple_Lightsaber(3, 10.0F, 9.0F, 2.0F, 24);

    private final int harvestLevel;
    private final float efficiency;
    private final float attackDamage;
    private final float attackSpeed;
    private final int enchantability;

    LightsaberTier(int harvestLevelIn, float efficiencyIn, float attackDamageIn, float attackSpeedIn, int enchantabilityIn) {
        this.harvestLevel = harvestLevelIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.attackSpeed = attackSpeedIn;
        this.enchantability = enchantabilityIn;
    }

    //@Override
    public float getAttackDamage() {
        // TODO Auto-generated method stub
        return this.attackDamage;
    }

    //@Override
    public float getAttackSpeed() {
        // TODO Auto-generated method stub
        return this.attackSpeed;
    }

    //@Override
    public float getEfficiency() {
        // TODO Auto-generated method stub
        return this.efficiency;
    }

    //@Override
    public int getEnchantability() {
        // TODO Auto-generated method stub
        return this.enchantability;
    }

    //@Override
    public int getHarvestLevel() {
        // TODO Auto-generated method stub
        return this.harvestLevel;
    }
}
