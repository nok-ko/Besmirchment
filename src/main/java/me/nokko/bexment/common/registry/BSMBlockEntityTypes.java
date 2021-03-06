package me.nokko.bexment.common.registry;

import me.nokko.bexment.common.block.entity.PhylacteryBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class BSMBlockEntityTypes {
    public static final BlockEntityType<PhylacteryBlockEntity> PHYLACTERY =  BlockEntityType.Builder.create(PhylacteryBlockEntity::new, BSMObjects.PHYLACTERY).build(null);
    public static void init(){
        BSMUtil.register(Registry.BLOCK_ENTITY_TYPE, "phylactery", PHYLACTERY);
    }
}
