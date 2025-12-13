package net.dragonoverknight.beginnermod.block;


import net.dragonoverknight.beginnermod.BeginnerMod;
import net.dragonoverknight.beginnermod.block.custom.GemPolisherBlock;
import net.dragonoverknight.beginnermod.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BeginnerMod.MOD_ID);

    public static final DeferredBlock<Block> GEM_POLISHER = registerBlock("tool_blocks/gem_polisher", "block_items/tool_blocks/gem_polisher",
            () -> new GemPolisherBlock(BlockBehaviour.Properties.of()
                    .strength(2.0F)
                    .sound(SoundType.DEEPSLATE_BRICKS)
                    .pushReaction(PushReaction.NORMAL)
                    .noOcclusion()));

    public static final DeferredBlock<Block> RUBY_ORE = registerBlock("gem_blocks/ruby_ore", "block_items/gem_blocks/ruby_ore",
            () -> new GemXpBlock(UniformInt.of(4, 7),
                    BlockBehaviour.Properties.of()
                    .strength(2.0F)
                    .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> RUBY_BLOCK = registerBlock("gem_blocks/ruby_block", "block_items/gem_blocks/ruby_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1.0F)
                    .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> SAPPHIRE_ORE = registerBlock("gem_blocks/sapphire_ore", "block_items/gem_blocks/sapphire_ore",
            () -> new GemXpBlock(UniformInt.of(4, 7),
                    BlockBehaviour.Properties.of()
                    .strength(2.0F)
                    .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> SAPPHIRE_BLOCK = registerBlock("gem_blocks/sapphire_block", "block_items/gem_blocks/sapphire_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1.0F)
                    .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> AMETHYST_ORE = registerBlock("gem_blocks/amethyst_ore", "block_items/gem_blocks/amethyst_ore",
            () -> new GemXpBlock(UniformInt.of(4, 7),
                    BlockBehaviour.Properties.of()
                    .strength(2.0F)
                    .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> AMETHYST_BLOCK = registerBlock("gem_blocks/amethyst_block", "block_items/gem_blocks/amethyst_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1.0F)
                    .sound(SoundType.AMETHYST)));;

    public static final DeferredBlock<Block> JADE_ORE = registerBlock("gem_blocks/jade_ore", "block_items/gem_blocks/jade_ore",
            () -> new GemXpBlock(UniformInt.of(4, 7),
                    BlockBehaviour.Properties.of()
                    .strength(2.0F)
                    .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> JADE_BLOCK = registerBlock("gem_blocks/jade_block", "block_items/gem_blocks/jade_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1.0F)
                    .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> TOPAZ_ORE = registerBlock("gem_blocks/topaz_ore", "block_items/gem_blocks/topaz_ore",
            () -> new GemXpBlock(UniformInt.of(4, 7),
                    BlockBehaviour.Properties.of()
                    .strength(2.0F)
                    .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> TOPAZ_BLOCK = registerBlock("gem_blocks/topaz_block", "block_items/gem_blocks/topaz_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1.0F)
                    .sound(SoundType.AMETHYST)));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, String itemName, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(itemName, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block)
    {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static final List<DeferredBlock<Block>> TOOLBLOCKS = Arrays.asList(
            GEM_POLISHER
    );

    public static final List<DeferredBlock<Block>> GEMORES = Arrays.asList(
            RUBY_ORE,
            SAPPHIRE_ORE,
            JADE_ORE,
            AMETHYST_ORE,
            TOPAZ_ORE
    );

    public static final List<DeferredBlock<Block>> GEMBLOCKS = Arrays.asList(
            RUBY_BLOCK,
            SAPPHIRE_BLOCK,
            JADE_BLOCK,
            AMETHYST_BLOCK,
            TOPAZ_BLOCK
    );

    public static void register(IEventBus eventbus) {BLOCKS.register(eventbus);
    }
}
