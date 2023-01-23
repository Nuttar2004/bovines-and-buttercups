package net.merchantpug.bovinesandbuttercups.data;

import com.mojang.serialization.Codec;
import net.merchantpug.bovinesandbuttercups.api.type.CowType;
import net.merchantpug.bovinesandbuttercups.registry.BovineBiomeModifierSerializers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

import java.util.List;
import java.util.Optional;

public record AddCowTypeSpawnsModifier(CowType<?> cowType, Optional<HolderSet<Biome>> excludedBiomes, List<MobSpawnSettings.SpawnerData> spawners) implements BiomeModifier {
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (ConfiguredCowTypeRegistry.asStream().anyMatch(entry -> entry.getValue().getCowType() == cowType && entry.getValue().getConfiguration().getSettings().naturalSpawnWeight() > 0 && entry.getValue().getConfiguration().getSettings().biomes().isPresent() && biome.is(entry.getValue().getConfiguration().getSettings().biomes().get()))) {
            if (phase == Phase.ADD && (this.excludedBiomes.isEmpty() || !this.excludedBiomes.get().contains(biome))) {
                MobSpawnSettingsBuilder spawns = builder.getMobSpawnSettings();
                for (MobSpawnSettings.SpawnerData spawner : this.spawners) {
                    EntityType<?> type = spawner.type;
                    spawns.addSpawn(type.getCategory(), spawner);
                }
            }
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return BovineBiomeModifierSerializers.ADD_COW_TYPE_SPAWNS_MODIFIER.get();
    }
}
