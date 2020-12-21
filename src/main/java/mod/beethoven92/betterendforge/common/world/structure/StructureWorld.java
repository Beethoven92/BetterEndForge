package mod.beethoven92.betterendforge.common.world.structure;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;

public class StructureWorld 
{
	private Map<ChunkPos, Part> parts = Maps.newHashMap();
	private ChunkPos lastPos;
	private Part lastPart;
	private int minX = Integer.MAX_VALUE;
	private int minY = Integer.MAX_VALUE;
	private int minZ = Integer.MAX_VALUE;
	private int maxX = Integer.MIN_VALUE;
	private int maxY = Integer.MIN_VALUE;
	private int maxZ = Integer.MIN_VALUE;
	
	public StructureWorld() {}
	
	public StructureWorld(CompoundNBT tag) 
	{
		minX = tag.getInt("minX");
		maxX = tag.getInt("maxX");
		minY = tag.getInt("minY");
		maxY = tag.getInt("maxY");
		minZ = tag.getInt("minZ");
		maxZ = tag.getInt("maxZ");
		
		ListNBT map = tag.getList("parts", 10);
		map.forEach((element) -> {
			CompoundNBT compound = (CompoundNBT) element;
			Part part = new Part(compound);
			int x = compound.getInt("x");
			int z = compound.getInt("z");
			parts.put(new ChunkPos(x, z), part);
		});
	}
	
	public void setBlock(BlockPos pos, BlockState state) 
	{
		ChunkPos cPos = new ChunkPos(pos);
		
		if (cPos.equals(lastPos)) 
		{
			lastPart.addBlock(pos, state);
			return;
		}
		
		Part part = parts.get(cPos);
		if (part == null) 
		{
			part = new Part();
			parts.put(cPos, part);
			
			if (cPos.x < minX) minX = cPos.x;
			if (cPos.x > maxX) maxX = cPos.x;
			if (cPos.z < minZ) minZ = cPos.z;
			if (cPos.z > maxZ) maxZ = cPos.z;
		}
		
		if (pos.getY() < minY) minY = pos.getY();
		if (pos.getY() > maxY) maxY = pos.getY();
		part.addBlock(pos, state);
		
		lastPos = cPos;
		lastPart = part;
	}
	
	public boolean placeChunk(ISeedReader world, ChunkPos chunkPos) 
	{
		Part part = parts.get(chunkPos);
		if (part != null) {
			IChunk chunk = world.getChunk(chunkPos.x, chunkPos.z);
			part.placeChunk(chunk);
			return true;
		}
		return false;
	}
	
	public CompoundNBT toNBT() 
	{
		CompoundNBT tag = new CompoundNBT();
		tag.putInt("minX", minX);
		tag.putInt("maxX", maxX);
		tag.putInt("minY", minY);
		tag.putInt("maxY", maxY);
		tag.putInt("minZ", minZ);
		tag.putInt("maxZ", maxZ);
		ListNBT map = new ListNBT();
		tag.put("parts", map);
		parts.forEach((pos, part) -> {
			map.add(part.toNBT(pos.x, pos.z));
		});
		return tag;
	}
	
	public MutableBoundingBox getBounds() 
	{
		if (minX == Integer.MAX_VALUE || maxX == Integer.MIN_VALUE || minZ == Integer.MAX_VALUE || maxZ == Integer.MIN_VALUE)
		{
			return MutableBoundingBox.getNewBoundingBox();
			//return new MutableBoundingBox(0 ,0, 0, 0, 0, 0);
		}
		return new MutableBoundingBox(minX << 4, minY, minZ << 4, (maxX << 4) | 15, maxY, (maxZ << 4) | 15);
	}
	
	private static final class Part 
	{
		Map<BlockPos, BlockState> blocks = Maps.newHashMap();
		
		public Part() {}
		
		public Part(CompoundNBT tag) 
		{
			ListNBT map = tag.getList("blocks", 10);
			ListNBT map2 = tag.getList("states", 10);
			BlockState[] states = new BlockState[map2.size()];
			for (int i = 0; i < states.length; i++) 
			{
				states[i] = NBTUtil.readBlockState((CompoundNBT) map2.get(i));
			}
			
			map.forEach((element) -> {
				CompoundNBT block = (CompoundNBT) element;
				BlockPos pos = NBTUtil.readBlockPos(block.getCompound("pos"));
				int stateID = block.getInt("state");
				BlockState state = stateID < states.length ? states[stateID] : Block.getStateById(stateID);
				blocks.put(pos, state);
			});
		}
	
		void addBlock(BlockPos pos, BlockState state)
		{
			BlockPos inner = new BlockPos(pos.getX() & 15, pos.getY(), pos.getZ() & 15);
			blocks.put(inner, state);
		}
		
		void placeChunk(IChunk chunk) 
		{
			blocks.forEach((pos, state) -> {
				chunk.setBlockState(pos, state, false);
			});
		}
		
		CompoundNBT toNBT(int x, int z) 
		{
			CompoundNBT tag = new CompoundNBT();
			tag.putInt("x", x);
			tag.putInt("z", z);
			ListNBT map = new ListNBT();
			tag.put("blocks", map);
			ListNBT stateMap = new ListNBT();
			tag.put("states", stateMap);
			
			int[] id = new int[1];
			Map<BlockState, Integer> states = Maps.newHashMap();
			
			blocks.forEach((pos, state) -> {
				int stateID = states.getOrDefault(states, -1);
				if (stateID < 0) {
					stateID = id[0] ++;
					states.put(state, stateID);
					stateMap.add(NBTUtil.writeBlockState(state));
				}
				
				CompoundNBT block = new CompoundNBT();
				block.put("pos", NBTUtil.writeBlockPos(pos));
				block.putInt("state", stateID);
				map.add(block);
			});
			
			return tag;
		}
	}
}
