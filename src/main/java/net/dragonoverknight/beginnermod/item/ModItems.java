package net.dragonoverknight.beginnermod.item;

import net.dragonoverknight.beginnermod.BeginnerMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Arrays;
import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BeginnerMod.MOD_ID);

    public static final DeferredItem<Item> RUBY = ITEMS.register("ruby",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> UNCUT_RUBY = ITEMS.register("uncut_ruby",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> Sapphire = ITEMS.register("sapphire",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> UNCUT_SAPPHIRE = ITEMS.register("uncut_sapphire",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> AMETHYST = ITEMS.register("amethyst",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> UNCUT_AMETHYST = ITEMS.register("uncut_amethyst",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> JADE = ITEMS.register("jade",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> UNCUT_JADE = ITEMS.register("uncut_jade",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> TOPAZ = ITEMS.register("topaz",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> UNCUT_TOPAZ = ITEMS.register("uncut_topaz",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PEARL = ITEMS.register("pearl",
            () -> new Item(new Item.Properties()));

    public static final List<DeferredItem<Item>> GEMS = Arrays.asList(
            RUBY, UNCUT_RUBY,
            Sapphire, UNCUT_SAPPHIRE,
            AMETHYST, UNCUT_AMETHYST,
            JADE, UNCUT_JADE,
            TOPAZ, UNCUT_TOPAZ,
            PEARL
    );


    public static void register(IEventBus eventbus) {
        ITEMS.register(eventbus);
    }
}
