package org.edge.urban;

import org.cloudbus.cloudsim.cloudlets.Cloudlet;
import org.cloudbus.cloudsim.cloudlets.CloudletSimple;
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

public class SensorCloudletMaker {
	public static Map<Integer, Double> originalDataSizeMap = new HashMap<>();
    public static List<Cloudlet> createMobileSensorTasks() {
        List<Cloudlet> mobileTasks = new ArrayList<>();
        int sensorId = 1001;
        int peCount = 1;
        UtilizationModelDynamic utilization = new UtilizationModelDynamic(0.6);
        Random taskNoise = new Random();

        for (int i = 0; i < 10; i++) {
            long length = 9000 + taskNoise.nextInt(13000); 
            long fileSize = 200 + taskNoise.nextInt(600);  
            long outputSize = fileSize;

            Cloudlet sensorProbe = new CloudletSimple(sensorId++, length, peCount);
            sensorProbe.setUtilizationModel(utilization);
            sensorProbe.setFileSize(fileSize);
            sensorProbe.setOutputSize(outputSize);
            originalDataSizeMap.put((int) sensorProbe.getId(), (double) fileSize);


            mobileTasks.add(sensorProbe);
        }


        return mobileTasks;
    }
}
