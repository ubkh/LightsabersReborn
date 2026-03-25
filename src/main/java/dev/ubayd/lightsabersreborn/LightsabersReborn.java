package dev.ubayd.lightsabersreborn;

import com.mojang.logging.LogUtils;
import dev.ubayd.lightsabersreborn.registry.LightsaberBlockEntities;
import dev.ubayd.lightsabersreborn.registry.LightsaberBlocks;
import dev.ubayd.lightsabersreborn.registry.LightsaberItems;
import dev.ubayd.lightsabersreborn.registry.LightsaberSoundEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(LightsabersReborn.MODID)
public class LightsabersReborn {

    public static final String MODID = "lightsabersreborn";
    private static final Logger LOGGER = LogUtils.getLogger();

    public LightsabersReborn() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register all custom DeferredRegisters to the mod event bus
        LightsaberItems.ITEMS.register(modEventBus);
        LightsaberBlocks.BLOCKS.register(modEventBus);
        LightsaberBlocks.BLOCK_ITEMS.register(modEventBus);
        LightsaberSoundEvents.SOUND_EVENTS.register(modEventBus);
        LightsaberBlockEntities.BLOCK_ENTITIES.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the addCreative method to add items to vanilla tabs
        modEventBus.addListener(this::addCreative);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Lightsabers Reborn: Common Setup Complete!");
    }

    // Add custom items to the Vanilla Minecraft Creative Tabs
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            // Add Hilts
            event.accept(LightsaberItems.red_lightsaber_hilt.get());
            event.accept(LightsaberItems.blue_lightsaber_hilt.get());
            event.accept(LightsaberItems.green_lightsaber_hilt.get());
            event.accept(LightsaberItems.yellow_lightsaber_hilt.get());
            event.accept(LightsaberItems.purple_lightsaber_hilt.get());
            event.accept(LightsaberItems.white_lightsaber_hilt.get());
            event.accept(LightsaberItems.darksaber_hilt.get());

            event.accept(LightsaberBlocks.sconce_item.get());

            // Add Ignited Lightsabers
//            event.accept(LightsaberItems.red_lightsaber.get());
//            event.accept(LightsaberItems.blue_lightsaber.get());
//            event.accept(LightsaberItems.green_lightsaber.get());
//            event.accept(LightsaberItems.yellow_lightsaber.get());
//            event.accept(LightsaberItems.purple_lightsaber.get());
//            event.accept(LightsaberItems.white_lightsaber.get());
//            event.accept(LightsaberItems.darksaber.get());

        } else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            // Add Crafting Parts
            event.accept(LightsaberItems.pommel_cap.get());
            event.accept(LightsaberItems.focusing_lens.get());
            event.accept(LightsaberItems.blade_emitter.get());
            event.accept(LightsaberItems.emitter_matrix.get());
            event.accept(LightsaberItems.inert_power_insulator.get());
            event.accept(LightsaberItems.activation_stud.get());
            event.accept(LightsaberItems.lightsaber_hilt.get());
            event.accept(LightsaberItems.darksaber_lens.get());
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Lightsabers Reborn: Server Starting!");
    }
}