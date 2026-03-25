package dev.ubayd.lightsabersreborn.client;

import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import dev.ubayd.lightsabersreborn.LightsabersReborn;
import dev.ubayd.lightsabersreborn.item.LightsaberItem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LightsabersReborn.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientTickHandler {

    // We use this boolean so we don't restart the animation 20 times a second
    private static boolean isIdlePlaying = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        // Only run once per tick
        if (event.phase != TickEvent.Phase.END) return;

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        // Check if the player is holding an IGNITED lightsaber (not the hilt)
        boolean holdingLightsaber = player.getMainHandItem().getItem() instanceof LightsaberItem ||
                player.getOffhandItem().getItem() instanceof LightsaberItem;

        if (holdingLightsaber && !isIdlePlaying) {
            // Player just pulled out the lightsaber -> Start the animation
            var animation = PlayerAnimationRegistry.getAnimation(new ResourceLocation(LightsabersReborn.MODID, "pose_one_handed_lightsaber"));
            if (animation != null) {
                LightsaberAnimations.animationLayer.setAnimation(new KeyframeAnimationPlayer(animation));
                isIdlePlaying = true;
            }
        } else if (!holdingLightsaber && isIdlePlaying) {
            // Player put the lightsaber away -> Stop the animation
            LightsaberAnimations.animationLayer.setAnimation(null);
            isIdlePlaying = false;
        }
    }
}