package terra.world.consumers;

import arc.struct.ObjectFloatMap;
import mindustry.type.Item;
import mindustry.world.consumers.*;

public class ConsumeItemEfficiencyList extends ConsumeItemEfficiency {
    public final ObjectFloatMap<Item> multipliers;

    public ConsumeItemEfficiencyList(ObjectFloatMap<Item> multipliers) {
        super(item -> multipliers.containsKey(item) && multipliers.get(item, 0f) > 0f);
        this.multipliers = multipliers;
        this.itemDurationMultipliers = multipliers;
    }

    @Override
    public float itemEfficiencyMultiplier(Item item) {
        return multipliers.get(item, 0f);
    }
}
