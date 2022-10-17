package net.merchantpug.bovinesandbuttercups.util;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.BovineRegistryUtil;
import net.merchantpug.bovinesandbuttercups.api.ConfiguredCowType;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.data.entity.MushroomCowConfiguration;
import net.merchantpug.bovinesandbuttercups.registry.BovineCowTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class MushroomCowSpawnUtil {
    public static int getTotalSpawnWeight(LevelAccessor level, BlockPos pos) {
        int totalWeight = 0;

        Registry<Biome> registry = level.registryAccess().ownedRegistryOrThrow(Registry.BIOME_REGISTRY);

        HolderSet<Biome> entryList = null;

        for (ConfiguredCowType<?, ?> cowType : BovineRegistryUtil.configuredCowTypeStream(level).filter(configuredCowType -> configuredCowType.getConfiguration() instanceof MushroomCowConfiguration).toList()) {
            if (!(cowType.getConfiguration() instanceof MushroomCowConfiguration configuration)) continue;

            if (configuration.getNaturalSpawnWeight() > 0 && configuration.getBiomeTagKey().isPresent()) {
                TagKey<Biome> tag = configuration.getBiomeTagKey().get();
                var optionalList = registry.getTag(tag);
                if(optionalList.isPresent()) {
                    entryList = optionalList.get();
                }
                if (entryList != null && entryList.contains(level.getBiome(pos))) {
                    totalWeight += configuration.getNaturalSpawnWeight();
                }
            }
        }
        return totalWeight;
    }

    public static ResourceLocation getMooshroomSpawnType(LevelAccessor level, RandomSource random) {
        int totalWeight = 0;

        List<ConfiguredCowType<MushroomCowConfiguration, CowType<MushroomCowConfiguration>>> mooshroomList = new ArrayList<>();

        for (ConfiguredCowType<?, ?> cowType : BovineRegistryUtil.configuredCowTypeStream(level).filter(configuredCowType -> configuredCowType.getConfiguration() instanceof MushroomCowConfiguration).toList()) {
            if (!(cowType.getConfiguration() instanceof MushroomCowConfiguration mushroomCowConfiguration)) continue;

            if (mushroomCowConfiguration.getNaturalSpawnWeight() > 0) {
                mooshroomList.add((ConfiguredCowType<MushroomCowConfiguration, CowType<MushroomCowConfiguration>>) cowType);
            }
        }

        int index = 0;
        for (double r = random.nextDouble() * totalWeight; index < mooshroomList.size() - 1; ++index) {
            r -= mooshroomList.get(index).getConfiguration().getNaturalSpawnWeight();
            if (r <= 0.0) break;
        }
        if (!mooshroomList.isEmpty()) {
            return BovineRegistryUtil.getConfiguredCowTypeKey(level, mooshroomList.get(index));
        } else {
            return BovinesAndButtercups.asResource("missing_mooshroom");
        }
    }

    public static ResourceLocation getMooshroomSpawnTypeDependingOnBiome(LevelAccessor level, BlockPos pos, RandomSource random) {
        List<ConfiguredCowType<MushroomCowConfiguration, CowType<MushroomCowConfiguration>>> mooshroomList = new ArrayList<>();

        Registry<Biome> registry = level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY);

        HolderSet<Biome> entryList = null;

        for (ConfiguredCowType<?, ?> cowType : BovineRegistryUtil.configuredCowTypeStream(level).filter(configuredCowType -> configuredCowType.getConfiguration() instanceof MushroomCowConfiguration).toList()) {
            if (!(cowType.getConfiguration() instanceof MushroomCowConfiguration mushroomCowConfiguration)) continue;

            if (mushroomCowConfiguration.getNaturalSpawnWeight() > 0 && mushroomCowConfiguration.getBiomeTagKey().isPresent()) {
                TagKey<Biome> tag = mushroomCowConfiguration.getBiomeTagKey().get();
                var optionalList = registry.getTag(tag);
                if(optionalList.isPresent()) {
                    entryList = optionalList.get();
                }
                if (entryList != null && entryList.contains(level.getBiome(pos))) {
                    mooshroomList.add((ConfiguredCowType<MushroomCowConfiguration, CowType<MushroomCowConfiguration>>) cowType);
                }
            }
        }

        int index = 0;
        for (double r = random.nextDouble() * getTotalSpawnWeight(level, pos); index < mooshroomList.size() - 1; ++index) {
            r -= mooshroomList.get(index).getConfiguration().getNaturalSpawnWeight();
            if (r <= 0.0) break;
        }
        if (!mooshroomList.isEmpty()) {
            return BovineRegistryUtil.getConfiguredCowTypeKey(level, mooshroomList.get(index));
        } else {
            return BovinesAndButtercups.asResource("missing_mooshroom");
        }
    }
}
