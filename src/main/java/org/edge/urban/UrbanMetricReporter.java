package org.edge.urban;
import org.cloudbus.cloudsim.cloudlets.Cloudlet;

import java.util.List;
import java.util.Map;

public class UrbanMetricReporter {

    public static void printSimulationStats(List<Cloudlet> completedTasks) {
        System.out.println("\nSimulation Summary:");
        System.out.println("-----------------------------------------------------------");
        System.out.printf("%-12s%-15s%-15s%-10s\n", "SensorTask", "Status", "Exec Time", "VM ID");
        System.out.println("-----------------------------------------------------------");
        
        double totalTime = 0;

        double fogTime = 0;
        int fogCount = 0;

        double cloudTime = 0;
        int cloudCount = 0;

        for (Cloudlet task : completedTasks) {
            String status = task.isFinished() ? "Done" : "Pending";
            double baseTime = task.getActualCpuTime();
            long vmId = task.getVm().getId();

            double adjustedTime = baseTime;
            if (vmId >= 6) { 
                adjustedTime += 20.0;
                cloudTime += adjustedTime;
                cloudCount++;
            } else { 
                fogTime += adjustedTime;
                fogCount++;
            }

            totalTime += adjustedTime;

            System.out.printf("%-12d%-15s%-15.2f%-10d\n",
                    task.getId(),
                    status,
                    adjustedTime,
                    vmId);
        }

        if (fogCount > 0 && cloudCount > 0) {
            double latencyReduction = (cloudTime / cloudCount) - (fogTime / fogCount);
            System.out.printf("Simulated Latency Reduction (Cloud vs Fog): %.2f s\n", latencyReduction);
        }
        double totalOriginal = 0;
        double totalProcessed = 0;
        Map<Integer, Double> originalMap = SensorCloudletMaker.originalDataSizeMap;

        for (Cloudlet task : completedTasks) {
            double original = originalMap.getOrDefault(task.getId(), (double) task.getFileSize());
            double processed = task.getOutputSize();
            totalOriginal += original;
            totalProcessed += processed;
        }

        double dataSaved = totalOriginal - totalProcessed;
        double percentReduction = (dataSaved / totalOriginal) * 100;
        System.out.printf("Total Original Data Size: %.2f KB\n", totalOriginal);
        System.out.printf("Total Processed Data Size: %.2f KB\n", totalProcessed);
        System.out.printf("Data Reduction at Edge (Simulated): %.2f KB (%.1f%%)\n", dataSaved, percentReduction);

        System.out.println("-----------------------------------------------------------");
        System.out.printf("Total Execution Time: %.2f seconds\n", totalTime);
        if (fogCount > 0) {
            System.out.printf("Fog Layer → Tasks: %d | Total Time: %.2f s | Avg: %.2f s\n",
                    fogCount, fogTime, fogTime / fogCount);
        }

        if (cloudCount > 0) {
            System.out.printf("Cloud Layer → Tasks: %d | Total Time: %.2f s | Avg: %.2f s\n",
                    cloudCount, cloudTime, cloudTime / cloudCount);
        }
    }
}
