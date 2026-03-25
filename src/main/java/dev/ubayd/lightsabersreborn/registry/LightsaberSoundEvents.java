package dev.ubayd.lightsabersreborn.registry;

import dev.ubayd.lightsabersreborn.LightsabersReborn;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LightsaberSoundEvents {

    // Create the DeferredRegister for Sound Events
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LightsabersReborn.MODID);

    // Register each sound as a RegistryObject
    public static final RegistryObject<SoundEvent> lightsaber_on = register("item.lightsaber.lightsaber_on");
    public static final RegistryObject<SoundEvent> lightsaber_off = register("item.lightsaber.lightsaber_off");
    public static final RegistryObject<SoundEvent> lightsaber_swing = register("item.lightsaber.lightsaber_swing");
    public static final RegistryObject<SoundEvent> lightsaber_hit = register("item.lightsaber.lightsaber_hit");

    public static final RegistryObject<SoundEvent> darksaber_on = register("item.lightsaber.darksaber_on");
    public static final RegistryObject<SoundEvent> darksaber_off = register("item.lightsaber.darksaber_off");
    public static final RegistryObject<SoundEvent> darksaber_swing = register("item.lightsaber.darksaber_swing");
    public static final RegistryObject<SoundEvent> darksaber_hit = register("item.lightsaber.darksaber_hit");

    public static final RegistryObject<SoundEvent> hello_there = register("misc.hello_there");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () ->
                SoundEvent.createVariableRangeEvent(new ResourceLocation(LightsabersReborn.MODID, name)));
    }
}