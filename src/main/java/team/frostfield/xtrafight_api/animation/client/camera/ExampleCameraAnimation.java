package team.frostfield.xtrafight_api.animation.client.camera;

import team.frostfield.xtrafight_api.animation.client.CameraAnimatorAPI;
import team.frostfield.xtrafight_api.events.xtrafight_api.registry.CameraAnimationRegistryDispatchEvent;
import team.frostfield.xtrafight_api.main.XtraFightAPI;
import team.frostfield.xtrafight_api.main.XtraFightAPIMod;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(value = Dist.CLIENT, modid = XtraFightAPIMod.MOD_ID)
public final class ExampleCameraAnimation {

    public static final List<CameraAnimatorAPI.CamAnim> CAM_ANIM_LIST = new ArrayList<>();

    public static CameraAnimatorAPI.CamAnim register(String name, ResourceLocation path) {

        // ResourceLocation, ResourceLocation?
        CameraAnimatorAPI.CamAnim anim = new CameraAnimatorAPI.CamAnim(XtraFightAPI.getInstance().identifier(name), path);

        for (CameraAnimatorAPI.CamAnim existing : CAM_ANIM_LIST) {
            if (existing.animationName().equals(anim.animationName())) {
                throw new IllegalStateException("CamAnimation named " + anim.animationName() + " already registered.");
            }
        }

        CAM_ANIM_LIST.add(anim);

        return anim;
    }

    public static CameraAnimatorAPI.CamAnim register(ResourceLocation id) {
        return register(id.getPath(), ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "camera/" + id.getPath() + ".json"));
    }

    public static final CameraAnimatorAPI.CamAnim TEST2 = register(XtraFightAPI.getInstance().identifier("testcam"));


    public static final CameraAnimatorAPI.CamAnim TEST = new CameraAnimatorAPI.CamAnim(XtraFightAPI.getInstance().identifier("testcamera_2"));


    @SubscribeEvent
    public static void registerExampleCamAnim(CameraAnimationRegistryDispatchEvent event) {
        // you can register the animation in these 3 ways:
        // First, is the array registry
        event.register(TEST2, TEST);
        // Second, register from the class, reading all of its static fields
        event.registerFromClass(ExampleCameraAnimation.class);
        // Third, registering from a list, like so:
        event.registerFromList(CAM_ANIM_LIST);
    }

}
