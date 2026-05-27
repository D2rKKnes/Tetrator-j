package terra.world.blocks;

import arc.Core;
import arc.math.Mathf;
import arc.util.Strings;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.power.PowerGraph;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.*;

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
                    (state.rules.lighting ? 1f - state.rules.ambientLight.a : 1f))
                : 0f;

            if (productionEfficiency > 0.001f && power != null) {
                try {
                    java.lang.reflect.Method method = power.graph.getClass()
                        .getMethod("addProduction", float.class);
                    method.invoke(power.graph, productionEfficiency * powerProduction);
                } catch (Exception ignored) {
                }
            }
        }
    }
}
