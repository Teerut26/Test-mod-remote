
package net.mcreator.testmod.world.inventory;

import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.testmod.init.TestModModMenus;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

public class AssemblerMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
	public final static HashMap<String, Object> guistate = new HashMap<>();
	public final Level world;
	public final Player entity;
	public int x, y, z;
	private IItemHandler internal;
	private final Map<Integer, Slot> customSlots = new HashMap<>();
	private boolean bound = false;

	public AssemblerMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		super(TestModModMenus.ASSEMBLER, id);
		this.entity = inv.player;
		this.world = inv.player.level;
		this.internal = new ItemStackHandler(26);
		BlockPos pos = null;
		if (extraData != null) {
			pos = extraData.readBlockPos();
			this.x = pos.getX();
			this.y = pos.getY();
			this.z = pos.getZ();
		}
		if (pos != null) {
			if (extraData.readableBytes() == 1) { // bound to item
				byte hand = extraData.readByte();
				ItemStack itemstack;
				if (hand == 0)
					itemstack = this.entity.getMainHandItem();
				else
					itemstack = this.entity.getOffhandItem();
				itemstack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
					this.internal = capability;
					this.bound = true;
				});
			} else if (extraData.readableBytes() > 1) {
				extraData.readByte(); // drop padding
				Entity entity = world.getEntity(extraData.readVarInt());
				if (entity != null)
					entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						this.internal = capability;
						this.bound = true;
					});
			} else { // might be bound to block
				BlockEntity ent = inv.player != null ? inv.player.level.getBlockEntity(pos) : null;
				if (ent != null) {
					ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						this.internal = capability;
						this.bound = true;
					});
				}
			}
		}
		this.customSlots.put(0, this.addSlot(new SlotItemHandler(internal, 0, 7, 24) {
		}));
		this.customSlots.put(1, this.addSlot(new SlotItemHandler(internal, 1, 25, 24) {
		}));
		this.customSlots.put(2, this.addSlot(new SlotItemHandler(internal, 2, 43, 24) {
		}));
		this.customSlots.put(3, this.addSlot(new SlotItemHandler(internal, 3, 61, 24) {
		}));
		this.customSlots.put(4, this.addSlot(new SlotItemHandler(internal, 4, 79, 24) {
		}));
		this.customSlots.put(5, this.addSlot(new SlotItemHandler(internal, 5, 7, 42) {
		}));
		this.customSlots.put(6, this.addSlot(new SlotItemHandler(internal, 6, 25, 42) {
		}));
		this.customSlots.put(7, this.addSlot(new SlotItemHandler(internal, 7, 43, 42) {
		}));
		this.customSlots.put(8, this.addSlot(new SlotItemHandler(internal, 8, 61, 42) {
		}));
		this.customSlots.put(9, this.addSlot(new SlotItemHandler(internal, 9, 79, 42) {
		}));
		this.customSlots.put(10, this.addSlot(new SlotItemHandler(internal, 10, 7, 60) {
		}));
		this.customSlots.put(11, this.addSlot(new SlotItemHandler(internal, 11, 25, 60) {
		}));
		this.customSlots.put(12, this.addSlot(new SlotItemHandler(internal, 12, 43, 60) {
		}));
		this.customSlots.put(13, this.addSlot(new SlotItemHandler(internal, 13, 61, 60) {
		}));
		this.customSlots.put(14, this.addSlot(new SlotItemHandler(internal, 14, 79, 60) {
		}));
		this.customSlots.put(15, this.addSlot(new SlotItemHandler(internal, 15, 7, 78) {
		}));
		this.customSlots.put(16, this.addSlot(new SlotItemHandler(internal, 16, 25, 78) {
		}));
		this.customSlots.put(17, this.addSlot(new SlotItemHandler(internal, 17, 43, 78) {
		}));
		this.customSlots.put(18, this.addSlot(new SlotItemHandler(internal, 18, 61, 78) {
		}));
		this.customSlots.put(19, this.addSlot(new SlotItemHandler(internal, 19, 79, 78) {
		}));
		this.customSlots.put(20, this.addSlot(new SlotItemHandler(internal, 20, 7, 96) {
		}));
		this.customSlots.put(21, this.addSlot(new SlotItemHandler(internal, 21, 25, 96) {
		}));
		this.customSlots.put(22, this.addSlot(new SlotItemHandler(internal, 22, 43, 96) {
		}));
		this.customSlots.put(23, this.addSlot(new SlotItemHandler(internal, 23, 61, 96) {
		}));
		this.customSlots.put(24, this.addSlot(new SlotItemHandler(internal, 24, 79, 96) {
		}));
		this.customSlots.put(25, this.addSlot(new SlotItemHandler(internal, 25, 124, 60) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}
		}));
		for (int si = 0; si < 3; ++si)
			for (int sj = 0; sj < 9; ++sj)
				this.addSlot(new Slot(inv, sj + (si + 1) * 9, 0 + 8 + sj * 18, 51 + 84 + si * 18));
		for (int si = 0; si < 9; ++si)
			this.addSlot(new Slot(inv, si, 0 + 8 + si * 18, 51 + 142));
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index < 26) {
				if (!this.moveItemStackTo(itemstack1, 26, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
				slot.onQuickCraft(itemstack1, itemstack);
			} else if (!this.moveItemStackTo(itemstack1, 0, 26, false)) {
				if (index < 26 + 27) {
					if (!this.moveItemStackTo(itemstack1, 26 + 27, this.slots.size(), true)) {
						return ItemStack.EMPTY;
					}
				} else {
					if (!this.moveItemStackTo(itemstack1, 26, 26 + 27, false)) {
						return ItemStack.EMPTY;
					}
				}
				return ItemStack.EMPTY;
			}
			if (itemstack1.getCount() == 0) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(playerIn, itemstack1);
		}
		return itemstack;
	}

	@Override
	protected boolean moveItemStackTo(ItemStack p_38904_, int p_38905_, int p_38906_, boolean p_38907_) {
		boolean flag = false;
		int i = p_38905_;
		if (p_38907_) {
			i = p_38906_ - 1;
		}
		if (p_38904_.isStackable()) {
			while (!p_38904_.isEmpty()) {
				if (p_38907_) {
					if (i < p_38905_) {
						break;
					}
				} else if (i >= p_38906_) {
					break;
				}
				Slot slot = this.slots.get(i);
				ItemStack itemstack = slot.getItem();
				if (slot.mayPlace(itemstack) && !itemstack.isEmpty() && ItemStack.isSameItemSameTags(p_38904_, itemstack)) {
					int j = itemstack.getCount() + p_38904_.getCount();
					int maxSize = Math.min(slot.getMaxStackSize(), p_38904_.getMaxStackSize());
					if (j <= maxSize) {
						p_38904_.setCount(0);
						itemstack.setCount(j);
						slot.set(itemstack);
						flag = true;
					} else if (itemstack.getCount() < maxSize) {
						p_38904_.shrink(maxSize - itemstack.getCount());
						itemstack.setCount(maxSize);
						slot.set(itemstack);
						flag = true;
					}
				}
				if (p_38907_) {
					--i;
				} else {
					++i;
				}
			}
		}
		if (!p_38904_.isEmpty()) {
			if (p_38907_) {
				i = p_38906_ - 1;
			} else {
				i = p_38905_;
			}
			while (true) {
				if (p_38907_) {
					if (i < p_38905_) {
						break;
					}
				} else if (i >= p_38906_) {
					break;
				}
				Slot slot1 = this.slots.get(i);
				ItemStack itemstack1 = slot1.getItem();
				if (itemstack1.isEmpty() && slot1.mayPlace(p_38904_)) {
					if (p_38904_.getCount() > slot1.getMaxStackSize()) {
						slot1.set(p_38904_.split(slot1.getMaxStackSize()));
					} else {
						slot1.set(p_38904_.split(p_38904_.getCount()));
					}
					slot1.setChanged();
					flag = true;
					break;
				}
				if (p_38907_) {
					--i;
				} else {
					++i;
				}
			}
		}
		return flag;
	}

	@Override
	public void removed(Player playerIn) {
		super.removed(playerIn);
		if (!bound && playerIn instanceof ServerPlayer serverPlayer) {
			if (!serverPlayer.isAlive() || serverPlayer.hasDisconnected()) {
				for (int j = 0; j < internal.getSlots(); ++j) {
					playerIn.drop(internal.extractItem(j, internal.getStackInSlot(j).getCount(), false), false);
				}
			} else {
				for (int i = 0; i < internal.getSlots(); ++i) {
					playerIn.getInventory().placeItemBackInInventory(internal.extractItem(i, internal.getStackInSlot(i).getCount(), false));
				}
			}
		}
	}

	public Map<Integer, Slot> get() {
		return customSlots;
	}
}
