package terrra.world.blocks;

import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.power.*;
import mindustry.world.meta.*;
import mindustry.world.modules.*;

import static mindustry.Vars.*;

public class SolarGeneratorCore extends CoreBlock {
    public float powerProduction;

    public SolarGeneratorCore(String name) {
        super(name);
        hasPower = true;
        outputsPower = true;
        envEnabled = Env.any;
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.generationType, powerProduction * 60f, StatUnit.powerSecond);
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("power", (SolarGeneratorCoreBuild entity) -> new Bar(
            () -> Core.bundle.format("bar.poweroutput", Strings.fixed(entity.productionEfficiency * powerProduction * 60f, 1)),
            () -> Pal.powerBar,
            () -> entity.productionEfficiency
        ));
    }

    public class SolarGeneratorCoreBuild extends CoreBuild {
        public float productionEfficiency = 0f;

        @Override
        public void updateTile() {
            super.updateTile();
            productionEfficiency = enabled
                ? state.rules.solarMultiplier * Mathf.maxZero(Attribute.light.env() +
                    (state.rules.lighting
                        ? 1f - state.rules.ambientLight.a
                        : 1f))
                : 0f;

            if (productionEfficiency > 0.1f && power != null) {
                power.graph.addProduction(new PowerGraph.Generator(productionEfficiency * powerProduction));
            }
        }
    }
}
