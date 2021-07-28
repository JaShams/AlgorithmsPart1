import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] segments;
    private Point[] p;
    private int size;
    private Point[] collinear;
    private int collinearSize;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points){
        if(points == null) throw new NullPointerException();
        Point[] ptsNullCheck = points.clone();
        Arrays.sort(ptsNullCheck);
        for (int i = 0; i < ptsNullCheck.length - 1; i++) {
            if (ptsNullCheck[i] == null) throw new java.lang.NullPointerException();
            if (ptsNullCheck[i].compareTo(ptsNullCheck[i + 1]) == 0)
                throw new java.lang.IllegalArgumentException();
        }

        segments = new LineSegment[1];
        size=0;

        p= ptsNullCheck.clone();

        for(int i=0 ; i<p.length ; i++){
            Arrays.sort(p,ptsNullCheck[i].slopeOrder());

            for(int j=1 ; j<p.length ; j++){
                
                collinear = new Point[4];
                collinearSize = 0;

                double slopeA = p[0].slopeTo(p[j]);
                addPoint(p[0]);
                addPoint(p[j]);

                while (++j < p.length && slopeA == p[0].slopeTo(p[j])) addPoint(p[j]);
                j--;

                if(collinearSize >= 4){
                    Point[] toAdd = new Point[collinearSize];
                    for (int k = 0; k < collinearSize; k++) {
                        toAdd[k] = collinear[k];
                    }
                    Arrays.sort(toAdd);

                    if (anyAbove(j)) addSegment(new LineSegment(toAdd[0], toAdd[collinearSize-1]));


                }

            }
        }
    }

    private boolean anyAbove(int j) {
        for (int k = 0; k < collinearSize - 1; k++)
            if (p[0].compareTo(p[j - k]) < 0)
                return false;
        return true;
    }

    private void addSegment(LineSegment l){
        if(l==null) throw new IllegalArgumentException();
        if(size==segments.length) resize(2*size,segments);
        segments[size++]=l;
    }

    private void addPoint(Point p){
        if(p==null) throw new IllegalArgumentException();
        if(collinearSize==collinear.length) resize(2*collinearSize,collinear);
        collinear[collinearSize++]=p;
    }

    private void resize(int cap,LineSegment[] l){
        LineSegment[] copy = new LineSegment[cap];
        for(int i=0 ; i<size ; i++) copy[i]=l[i];
        segments=copy;
    }

    private void resize(int cap,Point[] arr){
        Point[] copy = new Point[cap];
        for(int i=0 ; i<collinearSize ; i++) copy[i]=arr[i];
        collinear=copy;
    }

    public int numberOfSegments(){
        return size;
    }        // the number of line segments


    public LineSegment[] segments(){
        LineSegment[] shrunk = new LineSegment[size];
        for (int i = 0; i < size; i++) shrunk[i] = segments[i];
        return shrunk;
    }                // the line segments
 }
