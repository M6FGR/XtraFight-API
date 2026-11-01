package team.frostfield.xtrafight_api.events.subscribes;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import team.frostfield.xtrafight_api.animation.client.CameraAnimatorAPI;
import team.frostfield.xtrafight_api.animation.client.CameraAnimatorAPI.CameraFOVHelper;
import team.frostfield.xtrafight_api.client.gui.InteractionAPI;
import team.frostfield.xtrafight_api.events.xtrafight_api.registry.CameraAnimationRegistryDispatchEvent;
import team.frostfield.xtrafight_api.main.XtraFightAPIMod;
import yesman.epicfight.api.client.event.EpicFightClientEventHooks;

public class XtraFightAPISubscribeEvents {
    @EventBusSubscriber(modid = XtraFightAPIMod.MOD_ID, value = Dist.CLIENT)
    public static class Client {

        private static long lastFrameNanos = 0L;
        @SubscribeEvent
        public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
            if (event.getHand() == InteractionHand.MAIN_HAND && event.getLevel().isClientSide()) {
                InteractionAPI.getInstance().interactWithEntity(event.getEntity(), event.getTarget());
                event.setCanceled(true);
            }
        }

        @SubscribeEvent
        public static void onRenderFrame(RenderFrameEvent.Pre event) {
            InteractionAPI api = InteractionAPI.getInstance();
            if (!api.isInteracting()) {
                lastFrameNanos = 0L;
                return;
            }

            long now = System.nanoTime();
            if (lastFrameNanos == 0L) {
                lastFrameNanos = now;
                return;
            }

            float deltaSeconds = (float) ((now - lastFrameNanos) / 1_000_000_000.0);
            lastFrameNanos = now;

            api.updateInteractionLock(deltaSeconds);
        }

        @SubscribeEvent
        public static void onCameraAngles(ViewportEvent.ComputeCameraAngles event) {
            InteractionAPI api = InteractionAPI.getInstance();
            if (!api.isInteracting()) return;

            Entity target = api.getCurrentTarget();
            Player player = event.getRenderer().getMainCamera().getEntity() instanceof Player p ? p : null;
            if (player == null || target == null || !target.isAlive()) return;

            float partialTick = (float) event.getPartialTick();
            float interpolatedYaw = Mth.rotLerp(partialTick, player.yRotO, player.getYRot());
            float interpolatedPitch = Mth.lerp(partialTick, player.xRotO, player.getXRot());

            event.setYaw(interpolatedYaw);
            event.setPitch(interpolatedPitch);
        }
    }
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre ignoredEvent) {
        CameraAnimatorAPI.getInstance().tick();
    }

    //@SubscribeEvent
    public static void computeFOV(ViewportEvent.ComputeFov event) {
        if (CameraAnimatorAPI.getInstance().isPlaying()) {
            CameraAnimatorAPI.CamAnim current = CameraAnimatorAPI.getInstance().getCurrentCamAnim();
            //FIXME: This is calculated Default FOV from blender, do you guys really think it should be default
            if (current != null) {
                event.setFOV(CameraFOVHelper.focalLengthToFOV(10.3923f));
            }
        }
    }

    @SubscribeEvent
    public static void onCameraBuild(FMLClientSetupEvent evt) {
        EpicFightClientEventHooks.Camera.BUILD_TRANSFORM_PRE.registerContextAwareEvent((event, eventContext) -> {
            if (!CameraAnimatorAPI.getInstance().isPlaying()) return;

            // Apply Camera animation
            CameraAnimatorAPI.getInstance().applyToCamera(
                    event.getCamera(),
                    event.getPartialTick()
            );

            // Take full control of camera during animation
            event.setVanillaCameraSetupCanceled(true);

        });

        evt.enqueueWork(() -> {
            new CameraAnimationRegistryDispatchEvent().post();
        });

    }
}