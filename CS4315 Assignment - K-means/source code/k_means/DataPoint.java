package k_means;

import java.io.PrintWriter;

// Class for 2 dimensional data points
public class DataPoint {
    private final double dX, // x coord
                        dY; // y coord
    private int K = 0; // K group
    private boolean isCentroid = false;

    public DataPoint() // Default constructor
    { dX = 0; dY = 0; }

    public DataPoint(double dX, double dY) // Main constructor
    { this.dX = dX; this.dY = dY; }

    // Fetch data points
    public double getData(char d) {
        // Fetch corresponding coord data
        if (d == 'x') return dX;
        if (d == 'y') return dY;
        // If invalid coord, return -1
        return -1;
    }

    // Fetch group
    public int getK() {
        return K;
    }

    // Set group
    public void setK(int cluster) {
        this.K = cluster;
    }

    // Determine if it has already been selected as a centroid
    public boolean centroid() {
        return isCentroid;
    }

    // set centroid status
    public void setCentroid() {
        isCentroid = true;
    }

    // Check similarity with another data point
    public boolean isDissimilar(DataPoint newPoint) {
        // If dX, dY, and K are identical, return false
        if (dX == newPoint.getData('x') &&
            dY == newPoint.getData('y') &&
            K == newPoint.getK()) {
            return false;
        }
        // By default, retur true
        return true;
    }

    // Print coord data in format [ dX dY cluster]
    public void printData() 
    { System.out.printf("%.0f   %.0f    %d%n", dX, dY, K); }

    // Overload : used for printing to text file 
    public void printData(PrintWriter printer) 
    { printer.printf("%.0f   %.0f    %d%n", dX, dY, K); }
}
