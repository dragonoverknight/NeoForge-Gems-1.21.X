package net.dragonoverknight.beginnermod.block.entity.renderer;

import net.dragonoverknight.beginnermod.BeginnerMod;
import net.dragonoverknight.beginnermod.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BeginnerMod.MOD_ID);

    public static final Supplier<BlockEntityType<GemPolisherBlockEntity>> GEM_POLISHER =
            BLOCK_ENTITIES.register("tool_blocks/gem_polisher",
                    () -> BlockEntityType.Builder.of(
                            GemPolisherBlockEntity::new, ModBlocks.GEM_POLISHER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
