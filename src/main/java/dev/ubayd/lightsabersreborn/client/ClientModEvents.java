package dev.ubayd.lightsabersreborn.client;

import dev.ubayd.lightsabersreborn.LightsabersReborn;
import dev.ubayd.lightsabersreborn.registry.LightsaberBlockEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LightsabersReborn.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // This links the visual renderer to the data block
        event.registerBlockEntityRenderer(LightsaberBlockEntities.SCONCE_BE.get(), LightsaberSconceRenderer::new);
    }
}