package terra.graphics.g3d;

import arc.math.geom.Mat3D;
import mindustry.graphics.g3d.GenericMesh;
import mindustry.graphics.g3d.PlanetParams;

public class PulsarBeamsMesh implements GenericMesh{
    public final PulsarMesh pulsar;

    public PulsarBeamsMesh(PulsarMesh pulsar){
        this.pulsar = pulsar;
    }

    @Override
    public void render(PlanetParams params, Mat3D projection, Mat3D transform){
        pulsar.renderBeams(params, projection, transform);
    }

    @Override
    public void dispose(){
    }
}