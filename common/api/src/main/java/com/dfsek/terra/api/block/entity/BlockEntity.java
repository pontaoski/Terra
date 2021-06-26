package com.dfsek.terra.api.block.entity;

import com.dfsek.terra.api.Handle;
import com.dfsek.terra.api.block.state.BlockState;
import com.dfsek.terra.api.vector.Vector3;

public interface BlockEntity extends Handle {
    Vector3 getPosition();

    int getX();

    int getY();

    int getZ();

    BlockState getBlockData();

    boolean update(boolean applyPhysics);

    default void applyState(String state) {
        // Do nothing by default.
    }
}
