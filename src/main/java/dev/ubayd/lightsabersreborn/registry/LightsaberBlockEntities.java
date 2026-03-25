package dev.ubayd.lightsabersreborn.registry;

import dev.ubayd.lightsabersreborn.LightsabersReborn;
import dev.ubayd.lightsabersreborn.block.LightsaberBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LightsaberBlockEntities {

    // Create the DeferredRegister for Block Entities
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, LightsabersReborn.MODID);

    // Register the Sconce Block Entity and bind it strictly to Sconce block
    public static final RegistryObject<BlockEntityType<LightsaberBlockEntity>> SCONCE_BE =
            BLOCK_ENTITIES.register("sconce",
                    () -> BlockEntityType.Builder.of(LightsaberBlockEntity::new, LightsaberBlocks.sconce.get()).build(null));
}
