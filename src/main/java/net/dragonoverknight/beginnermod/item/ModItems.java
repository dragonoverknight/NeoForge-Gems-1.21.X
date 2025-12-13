package net.dragonoverknight.beginnermod.item;

import net.dragonoverknight.beginnermod.BeginnerMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BeginnerMod.MOD_ID);

    public static final DeferredItem<Item> RUBY = ITEMS.register("gems/ruby",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RUBY_GEODE = ITEMS.register("raw_gems/ruby_geode",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> Sapphire = ITEMS.register("gems/sapphire",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SAPPHIRE_GEODE = ITEMS.register("raw_gems/sapphire_geode",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> AMETHYST = ITEMS.register("gems/amethyst",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> AMETHYST_GEODE = ITEMS.register("raw_gems/amethyst_geode",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> JADE = ITEMS.register("gems/jade",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> JADE_GEODE = ITEMS.register("raw_gems/jade_geode",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> TOPAZ = ITEMS.register("gems/topaz",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> TOPAZ_GEODE = ITEMS.register("raw_gems/topaz_geode",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PEARL = ITEMS.register("pearl",
            () -> new Item(new Item.Properties()));

    public static final List<DeferredItem<Item>> GEMS = Arrays.asList(
            RUBY, Sapphire, AMETHYST, JADE, TOPAZ
    );

    public static final List<DeferredItem<Item>> RAW_GEMS = Arrays.asList(
            RUBY_GEODE, SAPPHIRE_GEODE, AMETHYST_GEODE, JADE_GEODE, TOPAZ_GEODE
    );

    public static final List<DeferredItem<Item>> INGREDIENTS = Stream.concat(
            GEMS.stream(),
            RAW_GEMS.stream()
            ).collect(Collectors.toList());


    public static void register(IEventBus eventbus) {
        ITEMS.register(eventbus);
    }
}
