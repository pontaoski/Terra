package com.dfsek.terra.population;

import com.dfsek.terra.TerraWorld;
import com.dfsek.terra.config.base.ConfigPack;
import com.dfsek.terra.config.genconfig.biome.BiomeOreConfig;
import org.bukkit.util.Vector;
import org.polydev.gaea.math.Range;
import com.dfsek.terra.TerraProfiler;
import com.dfsek.terra.biome.UserDefinedBiome;
import com.dfsek.terra.config.genconfig.OreConfig;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.polydev.gaea.biome.Biome;
import org.polydev.gaea.generation.GenerationPhase;
import org.polydev.gaea.population.GaeaBlockPopulator;
import org.polydev.gaea.profiler.ProfileFuture;

import java.util.Map;
import java.util.Random;

public class OrePopulator extends GaeaBlockPopulator {
    @Override
    public void populate(@NotNull World world, @NotNull Random random, @NotNull Chunk chunk) {
        try (ProfileFuture ignored = TerraProfiler.fromWorld(world).measure("OreTime")) {
            TerraWorld tw = TerraWorld.getWorld(world);
            if(!tw.isSafe()) return;
            ConfigPack config = tw.getConfig();
            Biome b = TerraWorld.getWorld(world).getGrid().getBiome((chunk.getX() << 4)+8, (chunk.getZ() << 4) + 8, GenerationPhase.POPULATE);
            BiomeOreConfig ores = config.getBiome((UserDefinedBiome) b).getOres();
            for(Map.Entry<OreConfig, Range> e : ores.getOres().entrySet()) {
                int num = e.getValue().get(random);
                for(int i = 0; i < num; i++) {
                    int x = random.nextInt(16);
                    int z = random.nextInt(16);
                    int y = ores.getOreHeights().get(e.getKey()).get(random);
                    if(e.getKey().crossChunks()) e.getKey().doVein(new Vector(x, y, z), chunk, random);
                    else e.getKey().doVeinSingle(new Vector(x, y, z), chunk, random);
                }
            }
        }
    }
}
