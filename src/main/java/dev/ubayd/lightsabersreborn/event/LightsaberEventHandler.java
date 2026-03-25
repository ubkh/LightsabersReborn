package dev.ubayd.lightsabersreborn.event;

import dev.ubayd.lightsabersreborn.LightsabersReborn;
import dev.ubayd.lightsabersreborn.item.LightsaberItem;
import dev.ubayd.lightsabersreborn.registry.LightsaberItems;
import dev.ubayd.lightsabersreborn.registry.LightsaberSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.WetSpongeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.PlayLevelSoundEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = LightsabersReborn.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
@SuppressWarnings("resource")
public class LightsaberEventHandler {

    @SubscribeEvent
    public static void onplayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        CompoundTag entityData = player.getPersistentData();

        if (player.getServer() != null) {
            ServerAdvancementManager manager = player.getServer().getAdvancements();
            Advancement install = manager.getAdvancement(new ResourceLocation(LightsabersReborn.MODID, LightsabersReborn.MODID + "/install"));

            boolean isDone = (install != null && player.getAdvancements().getOrStartProgress(install).isDone());

            if (!entityData.getBoolean("lightsaber.firstJoin") && !isDone) {
                entityData.putBoolean("lightsaber.firstJoin", true);

                if (!player.level().isClientSide) {
                    MutableComponent installInfo = Component.translatable("advancements.lightsaber.install.display1");
                    installInfo.withStyle(ChatFormatting.GOLD);
                    player.sendSystemMessage(installInfo);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSoundPlay(PlayLevelSoundEvent.AtEntity event) {
        if (event.getSound() != null && event.getSound().value() == SoundEvents.SHIELD_BLOCK && event.getEntity() != null) {
            if (event.getEntity() instanceof LivingEntity living && living.getUseItem().getItem() instanceof LightsaberItem) {
                if (living.getUseItem().getItem() == LightsaberItems.darksaber.get()) {
                    event.setSound(LightsaberSoundEvents.darksaber_hit.getHolder().get());
                } else {
                    event.setSound(LightsaberSoundEvents.lightsaber_hit.getHolder().get());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLeftClick(LeftClickBlock event) {
        BlockState state = event.getLevel().getBlockState(event.getPos());
        Block block = state.getBlock();

        if (event.getItemStack().getItem() instanceof LightsaberItem) {
            if (block instanceof WetSpongeBlock) {
                event.getLevel().setBlock(event.getPos(), Blocks.SPONGE.defaultBlockState(), 3);
                event.getLevel().gameEvent(event.getEntity(), GameEvent.BLOCK_PLACE, event.getPos());
            } else if (CampfireBlock.canLight(state) || CandleBlock.canLight(state) || CandleCakeBlock.canLight(state)) {
                event.getLevel().setBlock(event.getPos(), state.setValue(BlockStateProperties.LIT, true), 11);
                event.getLevel().gameEvent(event.getEntity(), GameEvent.BLOCK_PLACE, event.getPos());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityDeath(LivingDropsEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            if (player.getMainHandItem().getItem() instanceof LightsaberItem) {
                List<ItemEntity> newList = new ArrayList<>(event.getDrops());

                for (int i = 0; i < newList.size(); i++) {
                    ItemStack itemstack = newList.get(i).getItem();
                    int count = itemstack.getCount();

                    List<SmokingRecipe> recipe = player.level().getRecipeManager().getRecipesFor(RecipeType.SMOKING, new SimpleContainer(itemstack), player.level());

                    if (!recipe.isEmpty()) {
                        ItemStack result = recipe.get(0).getResultItem(event.getEntity().level().registryAccess()).copy();
                        result.setCount(count);
                        event.getDrops().remove(newList.get(i));
                        event.getDrops().add(new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), result));
                    } else {
                        List<CampfireCookingRecipe> campfireRecipe = player.level().getRecipeManager().getRecipesFor(RecipeType.CAMPFIRE_COOKING, new SimpleContainer(itemstack), player.level());

                        if (!campfireRecipe.isEmpty()) {
                            ItemStack result = campfireRecipe.get(0).getResultItem(event.getEntity().level().registryAccess()).copy();
                            result.setCount(count);
                            event.getDrops().remove(newList.get(i));
                            event.getDrops().add(new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), result));
                        } else {
                            List<SmeltingRecipe> furnaceRecipe = player.level().getRecipeManager().getRecipesFor(RecipeType.SMELTING, new SimpleContainer(itemstack), player.level());

                            if (!furnaceRecipe.isEmpty()) {
                                ItemStack result = furnaceRecipe.get(0).getResultItem(event.getEntity().level().registryAccess()).copy();
                                result.setCount(count);
                                if (result.getItem().isEdible()) {
                                    event.getDrops().remove(newList.get(i));
                                    event.getDrops().add(new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), result));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDamage(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.getUseItem().getItem() instanceof LightsaberItem lightsaber) {
                if (lightsaber.getUseAnimation(player.getUseItem()) == UseAnim.BLOCK) {
                    if (event.getSource().is(DamageTypes.LIGHTNING_BOLT) ||
                            event.getSource().getDirectEntity() instanceof FireworkRocketEntity ||
                            event.getSource().getEntity() instanceof Guardian ||
                            event.getSource().is(DamageTypes.DRAGON_BREATH)) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}