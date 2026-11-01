package team.frostfield.xtrafight_api.client.sound.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import team.frostfield.xtrafight_api.cls.loadable.ILoadableClass;
import team.frostfield.xtrafight_api.cls.loadable.marks.Invoke;
import team.frostfield.xtrafight_api.events.bus.NeoBus;
import team.frostfield.xtrafight_api.main.XtraFightAPIMod;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class XtraFightAPISoundsManager extends SimpleJsonResourceReloadListener implements ILoadableClass {
    private static final Gson GSON = new Gson();
    private static final Map<ResourceLocation, SoundDataDefinition> SOUND_BY_NAME = new HashMap<>();
    private static final Map<Integer, SoundDataDefinition> SOUND_BY_ID = new HashMap<>();

    public XtraFightAPISoundsManager() {
        super(GSON, "advanced_sounds");
    }

    @Invoke
    private XtraFightAPISoundsManager(NeoBus neoBus) {
        this();
        neoBus.<AddReloadListenerEvent>addListener(event -> event.addListener(this));
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectMap, ResourceManager resourceManager, ProfilerFiller profiler) {
        SOUND_BY_NAME.clear();
        SOUND_BY_ID.clear();

        objectMap.forEach((location, jsonElement) -> {
            try {
                if (!jsonElement.isJsonObject()) return;
                JsonObject root = jsonElement.getAsJsonObject();

                if (root.has("sounds") && root.get("sounds").isJsonObject()) {
                    JsonObject soundsObj = root.getAsJsonObject("sounds");

                    for (String key : soundsObj.keySet()) {
                        if (!soundsObj.get(key).isJsonObject()) continue;

                        ResourceLocation soundId = ResourceLocation.tryParse(key);
                        if (soundId == null) {
                            XtraFightAPIMod.warn("Invalid sound entry key '{}' in file {}", key, location);
                            continue;
                        }

                        this.parseAndRegister(soundId, soundsObj.getAsJsonObject(key));
                    }
                } else {
                    this.parseAndRegister(location, root);
                }
            } catch (Exception e) {
                XtraFightAPIMod.err("Failed to load Sound API data file: {} ", location, e);
            }
        });
    }

    private void parseAndRegister(ResourceLocation soundId, JsonObject soundEntry) {
        AtomicInteger integer = new AtomicInteger();
        int sequence = integer.incrementAndGet();
        if (!soundEntry.has("sound_event")) {
            XtraFightAPIMod.warn("Missing 'sound_event' field for sound entry '{}'", soundId);
            return;
        }

        ResourceLocation soundEvent = ResourceLocation.tryParse(soundEntry.get("sound_event").getAsString());
        if (soundEvent == null) {
            XtraFightAPIMod.warn("Invalid 'sound_event' ResourceLocation in sound entry '{}'", soundId);
            return;
        }
        if (!BuiltInRegistries.SOUND_EVENT.containsKey(soundEvent)) {
            XtraFightAPIMod.warn("No sound {} in the sound entry {}", soundEvent, soundEntry);
        }

        String category = soundEntry.has("category") ? soundEntry.get("category").getAsString() : "master";
        float volume = soundEntry.has("volume") ? soundEntry.get("volume").getAsFloat() : 1.0f;
        float pitch = soundEntry.has("pitch") ? soundEntry.get("pitch").getAsFloat() : 1.0f;
        float speed = soundEntry.has("speed") ? soundEntry.get("speed").getAsFloat() : 1.0f;
        float fadeInSpeed = soundEntry.has("fade_in_speed") ? soundEntry.get("fade_in_speed").getAsFloat() : 0.05f;

        double start = 0.0;
        double end = -1.0;

        if (soundEntry.has("loop_bounds") && soundEntry.get("loop_bounds").isJsonObject()) {
            JsonObject bounds = soundEntry.getAsJsonObject("loop_bounds");
            start = bounds.has("start_seconds") ? bounds.get("start_seconds").getAsDouble() : 0.0;
            end = bounds.has("end_seconds") ? bounds.get("end_seconds").getAsDouble() : -1.0;
        }

        SoundDataDefinition def = new SoundDataDefinition(soundEvent, soundId, category, volume, pitch, speed, fadeInSpeed, start, end);
        if (containsSoundName(soundId) || containsSoundID(sequence)) {
            XtraFightAPIMod.err("a JSON with the name: {} already exists!", soundId);
        }
        SOUND_BY_NAME.put(soundId, def);
        SOUND_BY_ID.put(sequence, def);
    }

    public static SoundDataDefinition byName(ResourceLocation id) {
        return SOUND_BY_NAME.get(id);
    }

    public static SoundDataDefinition byID(int id) {
        return SOUND_BY_ID.get(id);
    }

    public static boolean containsSoundName(ResourceLocation id) {
        return SOUND_BY_NAME.containsKey(id);
    }

    public static boolean containsSoundID(int id) {
        return SOUND_BY_ID.containsKey(id);
    }

}