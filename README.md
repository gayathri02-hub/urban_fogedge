# Fog-Cloud Simulation using CloudSim Plus

This project simulates a simplified Fog-Cloud architecture for processing urban IoT sensor data. It is built using **Java 1.8** and **CloudSim Plus v4.3.1**.

## Project Structure

Before running the simulation, make sure you have created a org.edge.urban package. Inside that package, place the following Java classes:

- `UrbanFogSimulationMain.java` – Main class to run the simulation
- `FogAndCloudBuilder.java` – Sets up the datacenters (Fog zones and Cloud)
- `SensorCloudletMaker.java` – Generates sensor tasks (cloudlets)
- `UrbanMetricReporter.java` – Calculates and prints performance metrics

All the simulation logic is organized into these classes.

## How to Import the Project (Maven)

1. Download or Save this modified version of cloudplus which it containing simulation code.
2. Open Eclipse IDE.
3. Go to `File → Import`.
4. Select `Maven → Existing Maven Projects`.
5. Browse to the folder where you cloned the project.
6. Click **Finish** to import it.
## How to Run the Simulation

1. Inside Eclipse, navigate to the package where simulation code is placed.
2. Open the `UrbanFogSimulationMain.java` file.
3. Right-click the file and choose `Run As → Java Application`.

The simulation will start, and the console will display:

- Task execution times for both Fog and Cloud layers
- Average latency comparison between Fog and Cloud
- Simulated data size reduction at the edge

This output helps demonstrate the performance benefits of offloading smaller, latency-sensitive tasks to Fog nodes while larger tasks are handled in the Cloud.

## Requirements

- Java 1.8
- Maven
- Eclipse IDE 2020-12
