package net.boat.industrialhellscape;

import net.boat.industrialhellscape.entity.ModEntities;
import net.boat.industrialhellscape.entity.SittableEntity.SittableEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = IndustrialHellscape.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
//@EventBusSubscriber(modid = IndustrialHellscape.MOD_ID, value = Dist.CLIENT) Uncomment only if there is a SubscribeEvent in this class
public class IndustrialHellscapeClient {
    public IndustrialHellscapeClient(ModContainer container) {

        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        IndustrialHellscape.LOGGER.info("InHell Client: Config Screen Registration Complete");
    }

    @EventBusSubscriber(modid = IndustrialHellscape.MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.CHAIR.get(), SittableEntityRenderer::new);
            IndustrialHellscape.LOGGER.info("InHell Client: Client-side Entity Renderer Registration Complete");
        }
    }
}