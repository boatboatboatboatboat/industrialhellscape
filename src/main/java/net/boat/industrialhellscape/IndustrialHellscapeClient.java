package net.boat.industrialhellscape;

import net.boat.industrialhellscape.block.block_entities.ModBlockEntities;
import net.boat.industrialhellscape.entity.ModEntities;
import net.boat.industrialhellscape.entity.SittableEntity.SittableEntityRenderer;
import net.minecraft.client.renderer.blockentity.BrushableBlockRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = IndustrialHellscape.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
//@EventBusSubscriber(modid = IndustrialHellscape.MOD_ID, value = Dist.CLIENT) Uncomment only if there is a SubscribeEvent in this class
public class IndustrialHellscapeClient {
    public IndustrialHellscapeClient(ModContainer modContainer) {

        //Client-side configs
        modContainer.registerConfig(ModConfig.Type.CLIENT, ModClientConfig.SPEC);

        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

    }

    /*
        Event hooks outside ClientModEvents will NOT execute
        - Including renderers
    */
    @EventBusSubscriber(modid = IndustrialHellscape.MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.CHAIR.get(), SittableEntityRenderer::new);
            //EntityRenderers.register(ModEntities.LANDMINE.get(), LandmineEntityRenderer::new);
        }

        @SubscribeEvent // on the mod event bus only on the physical client
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(
                    // The block entity type to register the renderer for.
                    ModBlockEntities.BRUSHABLE_BE.get(),
                    // A function of BlockEntityRendererProvider.Context to BlockEntityRenderer.
                    BrushableBlockRenderer::new
            );
        }

//        @SubscribeEvent
//        public static void onPlayerJoinWorld(PlayerEvent.PlayerLoggedInEvent event) {
//            if(ModClientConfig.enableWorldJoinMessage()) { //not implemented yet
//                Player player = event.getEntity();
//
//                int paleGoldenRod = 0xEEE8AA;
//
//                MutableComponent message = Component.literal("");
//
//                message.append(Component.translatable("industrialhellscape.client_chat_message.title")
//                        .withStyle(style -> style.withColor(paleGoldenRod).withBold(true)));
//
//                //Instruct on soft mod dependencies (CTM or Fusion) & Resource Pack use
//                message.append(Component.translatable("industrialhellscape.client_chat_message.body1")
//                        .withStyle(style -> style.withColor(paleGoldenRod)));
//                message.append(Component.translatable("industrialhellscape.client_chat_message.body2")
//                        .withStyle(style -> style.withColor(paleGoldenRod)));
//                message.append(Component.translatable("industrialhellscape.client_chat_message.body3")
//                        .withStyle(style -> style.withColor(paleGoldenRod)));
//
//                //Instruct on Client Configs (to disable this message)
//                message.append(Component.translatable("industrialhellscape.client_chat_message.body4")
//                        .withStyle(style -> style.withColor(paleGoldenRod)));
//                message.append(Component.translatable("industrialhellscape.client_chat_message.body5")
//                        .withStyle(style -> style.withColor(paleGoldenRod)));
//
//                //Instruct on Common Configs (client-side block placement preferences)
//                message.append(Component.translatable("industrialhellscape.client_chat_message.body6")
//                        .withStyle(style -> style.withColor(paleGoldenRod)));
//
//
//                message.append(Component.translatable("industrialhellscape.client_chat_message.eom")
//                        .withStyle(style -> style.withColor(paleGoldenRod).withBold(true)));
//
//                player.sendSystemMessage(message);
//            }
//        }
    }
}