package org.edge.urban;

import org.cloudbus.cloudsim.datacenters.Datacenter;
import org.cloudbus.cloudsim.datacenters.DatacenterSimple;
import org.cloudbus.cloudsim.resources.Pe;
import org.cloudbus.cloudsim.resources.PeSimple;
import org.cloudbus.cloudsim.vms.Vm;
import org.cloudbus.cloudsim.vms.VmSimple;
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.hosts.Host;
import org.cloudbus.cloudsim.hosts.HostSimple;
import org.cloudbus.cloudsim.core.CloudSim;

import java.util.List;
import java.util.ArrayList;

public class FogAndCloudBuilder {

    public static List<Datacenter> setupFogAndCloudLayers(CloudSim sim) {
        List<Datacenter> zones = new ArrayList<>();
        zones.add(createFogZone(sim, 0));
        zones.add(createFogZone(sim, 1));
        zones.add(createFogZone(sim, 2));
        zones.add(createUrbanCloud(sim));
        return zones;
    }

    public static List<Vm> createVirtualWorkers() {
        List<Vm> workers = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            Vm fogWorker = new VmSimple(2000, 1);
            fogWorker.setRam(768).setBw(1200).setSize(10000);
            workers.add(fogWorker);
        }

        Vm cloudMaster = new VmSimple(5000, 2);
        cloudMaster.setRam(2048).setBw(2000).setSize(20000);
        workers.add(cloudMaster);

        return workers;
    }


    private static Datacenter createFogZone(CloudSim sim, int zoneId) {
        List<Host> edgeHosts = new ArrayList<>();
        List<Pe> processors = new ArrayList<>();
        processors.add(new PeSimple(2000));
        processors.add(new PeSimple(2000));
        processors.add(new PeSimple(2000));
        Host zoneHost = new HostSimple(4096, 20000, 1000000, processors);
        zoneHost.setVmScheduler(new org.cloudbus.cloudsim.schedulers.vm.VmSchedulerTimeShared());
        edgeHosts.add(zoneHost);
        return new DatacenterSimple(sim, edgeHosts, new VmAllocationPolicySimple());
    }

    private static Datacenter createUrbanCloud(CloudSim sim) {
        List<Host> mainHosts = new ArrayList<>();
        List<Pe> powerCores = new ArrayList<>();
        powerCores.add(new PeSimple(5000));
        powerCores.add(new PeSimple(5000));
        powerCores.add(new PeSimple(5000));
        Host cloudHub = new HostSimple(8192, 100000, 1000000, powerCores);
        cloudHub.setVmScheduler(new org.cloudbus.cloudsim.schedulers.vm.VmSchedulerTimeShared());
        mainHosts.add(cloudHub);
        return new DatacenterSimple(sim, mainHosts, new VmAllocationPolicySimple());
    }
}
