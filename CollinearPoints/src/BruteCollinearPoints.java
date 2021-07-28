import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] segments;
    private Point[] p;
    private int count;


    public BruteCollinearPoints(Point[] points){
        if(points == null) throw new IllegalArgumentException();
        p = new Point[points.length];
        for(int i=0 ; i<points.length ; i++) p[i]=points[i];

        Arrays.sort(p);
        for (int i = 0; i < points.length - 1; i++) {
            if (p[i] == null) throw new IllegalArgumentException();
            if (p[i].compareTo(p[i + 1]) == 0)
                throw new IllegalArgumentException();
        }

        segments = new LineSegment[1];
        count=0;

        Point[] test = new Point[4];

        for(int i=0 ; i<points.length-3 ; i++){
            test[0]=p[i];
            for(int j=i+1 ; j<points.length-2 ; j++){
                test[1]=p[j];
                for(int k=j+1 ; k<points.length-1 ; k++){
                    test[2]=p[k];
                    for(int l=k+1 ; l<points.length-0 ; l++){
                        test[3]=p[l];
                        double slopeA = test[0].slopeTo(test[1]);
                        double slopeB = test[0].slopeTo(test[2]);
                        double slopeC = test[0].slopeTo(test[3]);
                        if (slopeA == slopeB && slopeB == slopeC) {
                            addSegment(new LineSegment(test[0],test[3]));
                        }
                    }
                }
            }
        }
    }    // finds all line segments containing 4 points

    private void addSegment(LineSegment l){
        if(l==null) throw new IllegalArgumentException();
        if(count==segments.length) resize(2*count);
        segments[count++]=l;
    }

    private void resize(int cap){
        LineSegment[] copy = new LineSegment[cap];
        for(int i=0 ; i<count ; i++) copy[i]=segments[i];
        segments=copy;
    }


    public int numberOfSegments(){
        return count;
    }        // the number of line segments


    public LineSegment[] segments(){
        LineSegment[] shrunk = new LineSegment[count];
        for (int i = 0; i < count; i++) shrunk[i] = segments[i];
        return shrunk;
    }                // the line segments
 }