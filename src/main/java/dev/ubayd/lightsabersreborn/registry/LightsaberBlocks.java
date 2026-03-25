package dev.ubayd.lightsabersreborn.registry;

import dev.ubayd.lightsabersreborn.LightsabersReborn;
import dev.ubayd.lightsabersreborn.block.LightsaberSconce;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LightsaberBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LightsabersReborn.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LightsabersReborn.MODID);

    public static final RegistryObject<Block> sconce = BLOCKS.register("sconce",
            () -> new LightsaberSconce(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)
                    .noOcclusion()
                    .lightLevel((state) -> {
                        if (!state.getValue(LightsaberSconce.HAS_LIGHTSABER)) return 0; // Empty
                        return state.getValue(LightsaberSconce.DARKSABER) ? 7 : 13;     // Darksaber gets 7, others get 13
                    })
            )
    );

    public static final RegistryObject<Item> sconce_item = BLOCK_ITEMS.register("sconce",
            () -> new BlockItem(sconce.get(), new Item.Properties()));

}
