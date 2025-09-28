package k_means;

import java.util.ArrayList; // For Array list
import java.util.Scanner; // For scanner
import java.util.Random; // For initial centroid computation
import java.io.File; // For file reading
import java.io.PrintWriter; // For file writing
import java.lang.Math; // For power / sqrt functions

/* Basic K-means clustering algorithm; takes 2 dimensional data points
and puts them into numbered clusters based on certain params */ 

public class clustering {

    // Stores the data points
    private static ArrayList<DataPoint> points = new ArrayList<DataPoint>(),
                                        centroids = new ArrayList<DataPoint>(),
                                        prevCentroids = new ArrayList<DataPoint>();
    private static int K, // Specified K value (number of clusters)
                        numSimC; // Checks num of similarities in centroids vs prev centroids

    // Find euclidian distance between a centroid (c) and a destination point (d)
    public static double euclidDFinder(DataPoint c, DataPoint d) {
        double dXdist, dYdist; // x and y distances

        // Calculate (x2 - x1) and (y2 - y1)
        dXdist = Math.pow(d.getData('x') - c.getData('x'), 2);
        dYdist = Math.pow(d.getData('y') - c.getData('y'), 2);
 
        // Return the sqrt of the sum
        return Math.sqrt(dXdist + dYdist);
    }
    
    public static void setKGroups() {
        int closeC = 0, // Stores destination index with the smallest minDist
            pointInK; // Stores number of points currently in a K group
        double minDist = -1, // Stores smallest distance a data point has to a destination point
                dXsum,
                dYsum; // Stores the sum x and y of K groups

        for (DataPoint d : points) {
            for (DataPoint c : centroids) {
                /* If 2 points' euclidian distance is smaller than minDist \
                OR minDist is uninitialized, set minDist to the new distance
                and set closeC to the index of the centroid c */
                if (euclidDFinder(c, d) < minDist || minDist == -1) {
                    minDist = euclidDFinder(c, d);
                    closeC = centroids.indexOf(c);
                }
            }
            // Assign the destination point the K group of the centroid
            d.setK(centroids.get(closeC).getK());
            // Reset minDist for another iteration
            minDist = -1;
        }

        // Compute new centroids from point mean
        for (int k = 1; k <= K; k++) {
            dXsum = 0; dYsum = 0; pointInK = 0;
            for (DataPoint d : points) {
                if (d.getK() == k) {
                    dXsum += d.getData('x');
                    dYsum += d.getData('y');
                    pointInK++;
                }
            }
            // DEBUG: Ensure pointInK is not 0 -----------------------------------
            // assert pointInK != 0 : "WARNING : NaN detected\n\n";
           
            centroids.set(k-1, new DataPoint(dXsum / pointInK, dYsum / pointInK));
            centroids.get(k-1).setK(k);
            // DEBUG: print the new centroids ------------------------------------
            // centroids.get(k-1).printData();
        }

        // Check similarity between new centroids and prev centroids
        for (int i = 0; i < centroids.size(); i++) {
            /* If a centroid is dissimilar, replace the index in prevCentroid,
            otherwise increment numSimC */
            if (centroids.get(i).isDissimilar(prevCentroids.get(i))) {
                prevCentroids.set(i, centroids.get(i));
            } else {
                numSimC++;
            }
        }
    }

    // Main
    public static void main(String[] args) {

        Random iC = new Random(); // Generates seed for current iteration
        DataPoint tmpCnt; // Temp storage for centroids
        File writeTo; // File to eventually be written to
        PrintWriter dataPrint; // Data writer to writeTo file

        K = Integer.parseInt(args[0]); // Grab the K set in command line
        
        try {
            Scanner scanner = new Scanner(new File(args[1])); // Scan file
            // Take all numbers and convert them into a point
            while (scanner.hasNextInt()) {
                points.add(new DataPoint(scanner.nextInt(), scanner.nextInt())); // Add to the main array
            }
            // Close scanner
            scanner.close();
        // Anything goes wrong, catch the error
        } catch(Exception e) { System.err.println(e.getMessage()); }

        // Randomly select initial centroids, assign them a K value, and place them in centroids
        for (int k = 1; k <= K; k++) {
            tmpCnt = points.get(iC.nextInt(points.size()));
            // If the point is already a centroid, ignore
            if (tmpCnt.centroid()) {
                k--;
                continue;
            }
            tmpCnt.setK(k);
            tmpCnt.setCentroid(); // Set the point as a centroid to prevent duplicates
            centroids.add(tmpCnt);
        }
        
        // Populate prevCentroids with c from centroids
        for (DataPoint c : centroids) {
            prevCentroids.add(c);
        }

        // While prevCentroids and centroids are even partially dissimilar, run setKGroups()
        do {
            System.out.println("Processing..."); // Simple string to ensure something is happening
            numSimC = 0;
            setKGroups();
            // DEBUG: check if loop properly ends at numSimC == centroids.size() ---------------------
            // System.out.println("numSimC : " + numSimC + " -- centroids.size() : " + centroids.size());
        } while (numSimC != centroids.size());

        // DEBUG: print the finalized points in order ------------------------------------------------
        /* for (DataPoint d : points) {
            d.printData();
        } */

        // Write data to a file
        try {
            writeTo = new File("output.txt");
            // If file does not exist, create it
            if (!writeTo.exists()) { writeTo.createNewFile(); }

            dataPrint = new PrintWriter("output.txt");
            for (DataPoint d : points) {
                d.printData(dataPrint);
            }
            dataPrint.close();
        // Anything goes wrong, catch the error
        } catch (Exception e) { System.err.println(e.getMessage()); }

        System.out.println("Done!"); // Simple string to ensure the program exited properly
    }
}