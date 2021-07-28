import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

public class PointSET {

    private SET<Point2D> points;
    
    public PointSET() { // construct an empty set of points
        points = new SET<Point2D>();
    }

    public boolean isEmpty() { // is the set empty?
        return points.isEmpty();
    }

    public int size() { // number of points in the set
        return points.size();
    }

    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
        if(p==null) throw new IllegalArgumentException();
        if(!points.contains(p)) points.add(p);
    }

    public boolean contains(Point2D p) { // does the set contain point p?
        if(p==null) throw new IllegalArgumentException();
        return points.contains(p);
    }

    public void draw() { // draw all points to standard draw
        for(Point2D p : points){
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle (or on the boundary)
        if(rect == null) throw new IllegalArgumentException();
        
        double xmin = rect.xmin();
        double ymin = rect.ymin();
        double xmax = rect.xmax();
        double ymax = rect.ymax();

        Stack<Point2D> pInRect = new Stack<>();

        for(Point2D p : points) {
            double x = p.x();
            double y = p.y();

            if(x>=xmin && x<=xmax && y>=ymin && y<=ymax){
                pInRect.push(p);
            }
        }

        return pInRect;
    }

    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty
        if(p==null) throw new IllegalArgumentException();
        if(points.isEmpty()) return null;

        double dist = Double.MAX_VALUE;
        Point2D min = null;

        for(Point2D temp : points){
            if(temp.distanceSquaredTo(p) < dist){
                dist = temp.distanceSquaredTo(p);
                min=temp;
            }
        }

        return min;
    }

    public static void main(String[] args) { // unit testing of the methods (optional)
        

    }
}
