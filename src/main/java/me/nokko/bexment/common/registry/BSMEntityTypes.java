package me.nokko.bexment.common.registry;

import me.nokko.bexment.common.entity.*;
import me.nokko.bexment.common.Besmirchment;
import me.nokko.bexment.common.entity.*;
import moriyashiine.bewitchment.common.entity.living.WerewolfEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

@SuppressWarnings("ConstantConditions")
public class BSMEntityTypes {
    public static final EntityType<FinalBroomEntity> FINAL_BROOM = FabricEntityTypeBuilder.create(SpawnGroup.MISC, FinalBroomEntity::new).dimensions(EntityType.ARROW.getDimensions()).build();
    public static final EntityType<WitchyDyeEntity> WITCHY_DYE = FabricEntityTypeBuilder.<WitchyDyeEntity>create(SpawnGroup.MISC, WitchyDyeEntity::new).dimensions(EntityType.POTION.getDimensions()).trackable(4, 10).build();
    public static final EntityType<InfectiousSpitEntity> INFECTIOUS_SPIT = FabricEntityTypeBuilder.<InfectiousSpitEntity>create(SpawnGroup.MISC, InfectiousSpitEntity::new).dimensions(EntityType.LLAMA_SPIT.getDimensions()).trackable(4, 10).build();
    public static final EntityType<WerepyreEntity> WEREPYRE = FabricEntityTypeBuilder.<WerepyreEntity>create(SpawnGroup.MONSTER, WerepyreEntity::new).dimensions(EntityDimensions.fixed(0.8F, 2.8F)).build();
    public static final EntityType<BeelzebubEntity> BEELZEBUB = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BeelzebubEntity::new).dimensions(EntityDimensions.fixed(1F, 4.2F)).build();

    public static final String BEELZEBUB_PLEDGE = "pledge.bexment.beelzebub";
    public static void init(){
        BSMUtil.register(Registry.ENTITY_TYPE, "final_broom", FINAL_BROOM);
        BSMUtil.register(Registry.ENTITY_TYPE, "witchy_dye", WITCHY_DYE);
        BSMUtil.register(Registry.ENTITY_TYPE, "infectious_spit", INFECTIOUS_SPIT);
        BSMUtil.register(Registry.ENTITY_TYPE, "werepyre", WEREPYRE);
        FabricDefaultAttributeRegistry.register(WEREPYRE, WerewolfEntity.createAttributes());
        BSMUtil.register(Registry.ENTITY_TYPE, "beelzebub", BEELZEBUB);
        FabricDefaultAttributeRegistry.register(BEELZEBUB, BeelzebubEntity.createAttributes());
        if (Besmirchment.config.mobs.werepyreWeight > 0) {
            BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld().and(context -> !context.getBiome().getSpawnSettings().getSpawnEntry(BSMEntityTypes.WEREPYRE.getSpawnGroup()).isEmpty() && context.getBiome().getCategory() != Biome.Category.OCEAN && (context.getBiome().getCategory() == Biome.Category.TAIGA || context.getBiome().getCategory() == Biome.Category.FOREST || context.getBiome().getCategory() == Biome.Category.ICY)), BSMEntityTypes.WEREPYRE.getSpawnGroup(), BSMEntityTypes.WEREPYRE, Besmirchment.config.mobs.werepyreWeight, Besmirchment.config.mobs.werepyreMinGroupCount, Besmirchment.config.mobs.werepyreMaxGroupCount);
            SpawnRestrictionAccessor.callRegister(BSMEntityTypes.WEREPYRE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.WORLD_SURFACE_WG, WerepyreEntity::canSpawn);
        }
    }
}
