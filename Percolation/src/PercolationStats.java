package Percolation.src;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats{
    private int totalExps;
    private double results[];

    private void runExperiments(int n){
        for(int i=0;i<totalExps;i++){
            Percolation exp = new Percolation(n);
            while(!exp.percolates()){
                int row = StdRandom.uniform(1,n+1);
                int col = StdRandom.uniform(1,n+1);
                if(!exp.isOpen(row, col)) exp.open(row, col);
            }
            results[i]= (double) (exp.numberOfOpenSites())/(double) (n*n);
        }
    }

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if(n<=0 || trials<=0) throw new IllegalArgumentException();
        totalExps=trials;
        results = new double[trials];
        runExperiments(n);
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean() - ((1.96*stddev())/Math.sqrt(totalExps));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean() + ((1.96*stddev())/Math.sqrt(totalExps));
    }

   // test client (see below)
   public static void main(String[] args){
        //int N = Integer.parseInt(args[0]);
        //int T = Integer.parseInt(args[1]);
        int N=200;
        int T=100;
        PercolationStats statObj = new PercolationStats(N, T);
        System.out.println("Mean = " + statObj.mean());
        System.out.println("Standard Deviation = " + statObj.stddev());
        System.out.println("Confidence = " + statObj.confidenceLo() + " , " + statObj.confidenceHi());
   }

}
