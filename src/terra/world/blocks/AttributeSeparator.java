package terra.world.blocks;

import arc.math.*;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import mindustry.world.consumers.*;
import mindustry.world.blocks.production.AttributeCrafter;

public class AttributeSeparator extends AttributeCrafter {
    public ItemStack[] results;

    public AttributeSeparator(String name){
        super(name);
        hasItems = true;
        hasLiquids = true;
    }

    @Override
    public void setStats(){
        super.setStats();
        
        if(results != null){
            int sum = 0;
            for(var r : results) sum += r.amount;
            final int total = sum;

            stats.add(Stat.output, table -> {
                for(ItemStack stack : results){
                    table.add(StatValues.displayItemPercent(stack.item, (int)((float)stack.amount / total * 100), true)).padRight(5);
                }
            });
        }
    }

    @Override
    public void init(){
        super.init();
        consItems = findConsumer(c -> c instanceof ConsumeItems);
    }

    public class AttributeSeparatorBuild extends AttributeCrafterBuild {
        public int seed;

        @Override
        public void created(){
            super.created();
            seed = Mathf.randomSeed(tile.pos(), 0, Integer.MAX_VALUE - 1);
        }

        @Override
        public boolean shouldConsume(){
            int total = items.total();
            if(consItems != null){
                for(ItemStack stack : consItems.items) total -= items.get(stack.item);
            }
            return total < itemCapacity && enabled;
        }

        @Override
        public void craft(){
            int sum = 0;
            for(ItemStack stack : results) sum += stack.amount;

            int i = Mathf.randomSeed(seed++, 0, sum - 1);
            int count = 0;
            Item item = null;

            for(ItemStack stack : results){
                if(i >= count && i < count + stack.amount){
                    item = stack.item;
                    break;
                }
                count += stack.amount;
            }

            consume();

            if(item != null && items.get(item) < itemCapacity){
                offload(item);
            }

            if(craftEffect != null) craftEffect.at(x, y);
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.i(seed);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            seed = read.i();
        }
    }
}
