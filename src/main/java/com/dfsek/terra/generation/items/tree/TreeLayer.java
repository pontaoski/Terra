package com.dfsek.terra.generation.items.tree;

import com.dfsek.terra.Terra;
import com.dfsek.terra.generation.items.PlaceableLayer;
import com.dfsek.terra.procgen.math.Vector2;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.polydev.gaea.math.FastNoiseLite;
import org.polydev.gaea.math.ProbabilityCollection;
import org.polydev.gaea.math.Range;
import org.polydev.gaea.tree.Tree;

import java.util.Random;

public class TreeLayer extends PlaceableLayer<Tree> {

    public TreeLayer(double density, Range level, ProbabilityCollection<Tree> layer, FastNoiseLite noise) {
        super(density, level, layer, noise);
    }

    @Override
    public void place(Chunk chunk, Vector2 coords, Random random) {
        Tree item = layer.get(random);
        Block current = chunk.getBlock((int) coords.getX(), level.getMax(), (int) coords.getZ());
        for(int ignored : level) {
            current = current.getRelative(BlockFace.DOWN);
            if(item.getSpawnable().contains(current.getType())) item.plant(current.getLocation(), random, Terra.getInstance());
        }
    }
}
