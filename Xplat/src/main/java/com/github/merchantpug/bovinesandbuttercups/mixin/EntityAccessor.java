package com.github.merchantpug.bovinesandbuttercups.mixin;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor("eyeHeight")
    void bovinesandbuttercups$setEyeHeight(float value);
}
