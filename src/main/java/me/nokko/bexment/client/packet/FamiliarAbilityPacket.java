package me.nokko.bexment.client.packet;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import me.nokko.bexment.common.Besmirchment;
import me.nokko.bexment.common.entity.InfectiousSpitEntity;
import me.nokko.bexment.common.registry.BSMEntityTypes;
import me.nokko.bexment.common.registry.BSMSounds;
import io.netty.buffer.Unpooled;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.api.interfaces.entity.MagicAccessor;
import moriyashiine.bewitchment.common.entity.interfaces.PolymorphAccessor;
import moriyashiine.bewitchment.common.registry.BWStatusEffects;
import moriyashiine.bewitchment.mixin.StatusEffectAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.stream.Collectors;

public class FamiliarAbilityPacket {
    public static final Identifier ID = Besmirchment.id("familiar_ability");

    @Environment(EnvType.CLIENT)
    public static void send(){
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
        server.execute(() -> {
            if (canUseAbility(player)) {
                useAbility(player);
            }
        });
    }

    private static boolean canUseAbility(PlayerEntity player) {
        EntityType<?> familiar = BewitchmentAPI.getFamiliar(player);
        return familiar == EntityType.SHEEP || familiar == EntityType.PARROT || familiar == EntityType.COW || familiar == EntityType.LLAMA || familiar == EntityType.TRADER_LLAMA;
    }

    public static void useAbility(PlayerEntity player){
        EntityType<?> familiar = BewitchmentAPI.getFamiliar(player);
        World world = player.world;
        if (EntityType.PARROT.equals(familiar) && !player.hasStatusEffect(BWStatusEffects.POLYMORPH)){
            Vec3d vec3d = player.getCameraPosVec(1);
            Vec3d vec3d2 = player.getRotationVec(1);
            Vec3d vec3d3 = vec3d.add(vec3d2.x * 16, vec3d2.y * 16, vec3d2.z * 16);
            double distance = Math.pow(16, 2);
            EntityHitResult hit = ProjectileUtil.getEntityCollision(world, player, vec3d, vec3d3, player.getBoundingBox().stretch(vec3d2.multiply(distance)).expand(1.0D, 1.0D, 1.0D), (target) -> target instanceof PlayerEntity && !target.isSpectator() && player.canSee(target));
            if (hit != null && hit.getEntity() instanceof PlayerEntity && BewitchmentAPI.drainMagic(player,50, true)){
                PlayerEntity polyMorphPlayer = (PlayerEntity) hit.getEntity();
                ((PolymorphAccessor) player).setPolymorphUUID(polyMorphPlayer.getUuid());
                ((PolymorphAccessor) player).setPolymorphName(polyMorphPlayer.getDisplayName().getString());
                player.addStatusEffect(new StatusEffectInstance(BWStatusEffects.POLYMORPH, 2400, 0, true, false, false));
                BewitchmentAPI.drainMagic(player,50, false);
            }
        }else if (EntityType.COW.equals(familiar) && player.isHolding(Items.BUCKET)){
            Hand hand = player.getMainHandStack().getItem() == Items.BUCKET ? Hand.MAIN_HAND : Hand.OFF_HAND;
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_COW_MILK, SoundCategory.PLAYERS, 1, 1);
            ItemStack milk = ItemUsage.method_30012(player.getStackInHand(hand), player, new ItemStack(Items.MILK_BUCKET));
            player.setStackInHand(hand, milk);
            player.swingHand(hand);
        }else if (EntityType.LLAMA.equals(familiar) || EntityType.TRADER_LLAMA.equals(familiar)){
            InfectiousSpitEntity spit = BSMEntityTypes.INFECTIOUS_SPIT.create(world);
            spit.init(player, null, player.getStatusEffects().stream().filter(instance -> ((StatusEffectAccessor) instance.getEffectType()).bw_getType() == StatusEffectType.HARMFUL).map(instance -> new StatusEffectInstance(instance.getEffectType(), 200, instance.getAmplifier())).collect(Collectors.toSet()));
            spit.setProperties(player, player.pitch, player.headYaw, 0, 2, 0);
            if (!player.isSilent()) {
                player.world.playSound(null, player.getX(), player.getY(), player.getZ(), BSMSounds.ENTITY_GENERIC_SPIT, player.getSoundCategory(), 1.0F, 1.0F + (player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.2F);
            }

            player.world.spawnEntity(spit);
        }
        if (player.canModifyBlocks()) {
            if (EntityType.SHEEP.equals(familiar) && (player.getHungerManager().isNotFull() || player.isCreative())) {
                Vec3d startVec = player.getCameraPosVec(1);
                Vec3d rotationVec = player.getRotationVec(1);
                double range = ReachEntityAttributes.getReachDistance(player, 4);
                Vec3d endVec = startVec.add(rotationVec.x * range, rotationVec.y * range, rotationVec.z * range);
                BlockHitResult blockHit = world.raycast(new RaycastContext(startVec, endVec, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player));
                if (blockHit.getBlockPos() != null){
                    BlockPos grassPos = blockHit.getBlockPos();
                    if (world.getBlockState(grassPos).isOf(Blocks.GRASS_BLOCK)){
                        world.syncWorldEvent(2001,grassPos, Block.getRawIdFromState(Blocks.GRASS_BLOCK.getDefaultState()));
                        world.setBlockState(grassPos, Blocks.DIRT.getDefaultState(), 2);
                        player.heal(0.5F);
                        player.getHungerManager().add(2, 0.5F);
                        ((MagicAccessor) player).fillMagic(5, false);
                        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_COW_MILK, SoundCategory.PLAYERS, 0.7F, 0.6F + player.getRandom().nextFloat() * 0.5F);
                    }
                }
            }
        }
    }
}
