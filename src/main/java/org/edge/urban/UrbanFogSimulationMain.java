package org.edge.urban;

import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple;
import org.cloudbus.cloudsim.cloudlets.Cloudlet;
import org.cloudbus.cloudsim.vms.Vm;
import org.cloudbus.cloudsim.datacenters.Datacenter;

import java.util.ArrayList;
import java.util.List;

public class UrbanFogSimulationMain {

    public static void main(String[] args) {
        System.out.println("Starting Urban Fog-Cloud Simulation...");

        CloudSim beneventoSim = new CloudSim();
        DatacenterBrokerSimple fogCityBroker = new DatacenterBrokerSimple(beneventoSim);

        List<Cloudlet> sensorTaskList = SensorCloudletMaker.createMobileSensorTasks();
        List<Datacenter> allProcessingZones = FogAndCloudBuilder.setupFogAndCloudLayers(beneventoSim);
        List<Vm> allVms = FogAndCloudBuilder.createVirtualWorkers();

        List<Vm> fogVms = new ArrayList<>();
        List<Vm> cloudVms = new ArrayList<>();

        int half = allVms.size() / 2;
        for (int i = 0; i < allVms.size(); i++) {
            if (i < half) fogVms.add(allVms.get(i));
            else cloudVms.add(allVms.get(i));
        }
        fogCityBroker.submitVmList(allVms);

        List<Cloudlet> fogTasks = new ArrayList<>();
        List<Cloudlet> cloudTasks = new ArrayList<>();

        int fogLimit = 6;

        for (Cloudlet cl : sensorTaskList) {
            boolean latencySensitive = cl.getLength() < 18000;
            boolean smallData = cl.getFileSize() < 600;
            if (latencySensitive && smallData && fogTasks.size() < fogLimit) {
                Vm targetFog = fogVms.get(fogTasks.size() % fogVms.size());
                cl.setVm(targetFog);
                cl.setOutputSize((long)(cl.getFileSize() * 0.4));
                fogTasks.add(cl);
            } else {
                Vm targetCloud = cloudVms.get(cloudTasks.size() % cloudVms.size());
                cl.setVm(targetCloud);
                cloudTasks.add(cl);
            }
        }

        List<Cloudlet> finalTaskList = new ArrayList<>();
        finalTaskList.addAll(fogTasks);
        finalTaskList.addAll(cloudTasks);

        fogCityBroker.submitCloudletList(finalTaskList);

        beneventoSim.start();
        UrbanMetricReporter.printSimulationStats(finalTaskList);
    }
}
