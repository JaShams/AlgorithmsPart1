package Percolation.src;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean grid[][];
    private int count;
    private WeightedQuickUnionUF uf;
    private int size;
    private int vt;
    private int vb;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if(n<=0) throw new IllegalArgumentException();
        grid = new boolean[n][n];
        count=0;
        size=n;
        vt=0;
        vb=n*n+1;
        uf= new WeightedQuickUnionUF(n*n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if(row<1 || row>size || col<1 || col>size) throw new IllegalArgumentException();
        if(grid[row-1][col-1]==false) grid[row-1][col-1]=true;
        else return;
        
        count++;

        if(row==1) uf.union(indexOf(row, col), vt);
        if(row==size) uf.union(indexOf(row, col), vb);

        if(row>1 && isOpen(row-1,col)) uf.union(indexOf(row-1,col),indexOf(row,col));
        if(row<size && isOpen(row+1,col)) uf.union(indexOf(row+1,col),indexOf(row,col));
        if(col>1 && isOpen(row,col-1)) uf.union(indexOf(row,col-1),indexOf(row,col));
        if(col<size && isOpen(row,col+1)) uf.union(indexOf(row,col+1),indexOf(row,col));
    }

    private int indexOf(int row, int col){
        return col + (row-1)*size;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if(row<1 || row>size || col<1 || col>size) return false;
        return grid[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if(row<1 || row>size || col<1 || col>size) throw new IllegalArgumentException();
        return (uf.find(indexOf(row, col))==uf.find(vt));
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return count;
    }

    // does the system percolate?
    public boolean percolates(){
        return (uf.find(vt)==uf.find(vb));
    }

    public void display(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(grid[i][j]==false) System.out.print("0 ");
                else System.out.print("1 ");
            }
            System.out.println();
        }
        System.out.println(uf.count());
    }
}