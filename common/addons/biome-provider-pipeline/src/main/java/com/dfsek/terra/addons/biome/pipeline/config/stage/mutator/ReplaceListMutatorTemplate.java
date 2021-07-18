package com.dfsek.terra.addons.biome.pipeline.config.stage.mutator;

import com.dfsek.tectonic.annotations.Value;
import com.dfsek.terra.addons.biome.pipeline.api.BiomeMutator;
import com.dfsek.terra.addons.biome.pipeline.mutator.ReplaceListMutator;
import com.dfsek.terra.api.util.collection.ProbabilityCollection;
import com.dfsek.terra.api.util.seeded.SeededTerraBiome;
import com.dfsek.terra.api.world.biome.TerraBiome;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ReplaceListMutatorTemplate extends MutatorStageTemplate {
    @Value("default-from")
    private String defaultFrom;

    @Value("default-to")
    private ProbabilityCollection<SeededTerraBiome> defaultTo;

    @Value("to")
    private Map<SeededTerraBiome, ProbabilityCollection<SeededTerraBiome>> replace;

    @Override
    public BiomeMutator build(long seed) {
        Map<TerraBiome, ProbabilityCollection<TerraBiome>> replaceMap = new HashMap<>();

        replace.forEach((biomeBuilder, biomeBuilders) -> replaceMap.put(biomeBuilder.apply(seed), biomeBuilders.map(builder -> builder.apply(seed), true)));

        return new ReplaceListMutator(replaceMap, defaultFrom, defaultTo.map(biomeBuilder -> biomeBuilder.apply(seed), true), noise.apply(seed));
    }
}
