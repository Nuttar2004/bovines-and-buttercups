package net.merchantpug.bovinesandbuttercups.content.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;

import java.util.Optional;

public class PreventEffectTrigger extends SimpleCriterionTrigger<PreventEffectTrigger.TriggerInstance> {
    public static final ResourceLocation ID = BovinesAndButtercups.asResource("prevent_effect");
    public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                    BuiltInRegistries.MOB_EFFECT.byNameCodec().optionalFieldOf("effect").forGetter(TriggerInstance::effect)
            ).apply(inst, TriggerInstance::new)
    );


    public void trigger(ServerPlayer serverPlayer, MobEffect effect) {
        this.trigger(serverPlayer, (triggerInstance) -> triggerInstance.matches(effect));
    }

    @Override
    public Codec<TriggerInstance> codec() {
        return CODEC;
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<MobEffect> effect) implements SimpleInstance {
        public boolean matches(MobEffect value) {
            return effect.isEmpty() || effect.get() == value;
        }
    }
}
