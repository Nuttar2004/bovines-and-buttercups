package net.merchantpug.bovinesandbuttercups.api.condition;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.RegistryAccess;

import java.util.function.Function;

public abstract class ConditionType<T, CC extends ConditionConfiguration<T>> {
    protected final Function<RegistryAccess, MapCodec<CC>> codecFunction;

    public ConditionType(Function<RegistryAccess, MapCodec<CC>> codecFunction) {
        this.codecFunction = codecFunction;
    }
}
