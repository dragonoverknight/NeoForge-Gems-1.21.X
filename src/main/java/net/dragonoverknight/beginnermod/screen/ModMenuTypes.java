package net.dragonoverknight.beginnermod.screen;

import net.dragonoverknight.beginnermod.BeginnerMod;
import net.dragonoverknight.beginnermod.screen.custom.GemPolisherMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, BeginnerMod.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<GemPolisherMenu>> GEM_POLISHER_MENU =
            MENUS.register("tool_blocks/gem_polisher", () -> IMenuTypeExtension.create(GemPolisherMenu::new));

    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name,
                                                                                                              IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
