package net.dragonoverknight.beginnermod;

import net.dragonoverknight.beginnermod.block.ModBlocks;
import net.dragonoverknight.beginnermod.screen.custom.GemPolisherScreen;
import net.dragonoverknight.beginnermod.block.entity.renderer.ModBlockEntities;
import net.dragonoverknight.beginnermod.item.ModItems;
import net.dragonoverknight.beginnermod.screen.ModMenuTypes;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(BeginnerMod.MOD_ID)
public class BeginnerMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "beginnermod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public BeginnerMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            for (DeferredItem<Item> gem : ModItems.GEMS) {
                event.accept(gem);
            }
            for (DeferredItem<Item> uncut_gem : ModItems.RAW_GEMS) {
                event.accept(uncut_gem);
            }

            event.accept(ModItems.PEARL);
        }

        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            for (DeferredBlock<Block> toolblock : ModBlocks.TOOLBLOCKS) {
                event.accept(toolblock);
            }
        }

        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            for (DeferredBlock<Block> gemore : ModBlocks.GEMORES) {
                event.accept(gemore);
            }
        }

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            for (DeferredBlock<Block> gemblock : ModBlocks.GEMBLOCKS) {
                event.accept(gemblock);
            }
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = BeginnerMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.GEM_POLISHER_MENU.get(), GemPolisherScreen::new);
        }
    }
}
