import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

public class KdTree {

    private class Node {
        private Point2D p;
        private Node lc;
        private Node rc;
        private boolean vertical;

        public Node(Point2D p) {
            this.p = p;
            lc = rc = null;
        }
    }

    private Node root;
    private int size;

    public KdTree() {
        size = 0;
        root = null;
    } // construct an empty set of points

    public boolean isEmpty() {
        return root == null;
    } // is the set empty?

    public int size() {
        return size;
    } // number of points in the set

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        root = insert(root, p, true);

    } // add the point to the set (if it is not already in the set)

    private Node insert(Node cur, Point2D p, boolean isVertical) {
        if (cur == null) {
            Node temp = new Node(p);
            size++;
            temp.vertical=isVertical;
            return temp;
        }

        double x = p.x();
        double y = p.y();
        double curx = cur.p.x();
        double cury = cur.p.y();

        if (cur.vertical == true) {
            if (x > curx)
                cur.rc = insert(cur.rc, p, false);
            else if (x < curx)
                cur.lc = insert(cur.lc, p, false);
            else if (y != cury)
                cur.rc = insert(cur.rc, p, false);
        }
        else {
            if (y > cury)
                cur.rc = insert(cur.rc, p, true);
            else if (y < cury)
                cur.lc = insert(cur.lc, p, true);
            else if (x != curx)
                cur.rc = insert(cur.rc, p, true);
        }

        return cur;
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return contains(root, p);
    } // does the set contain point p?

    private boolean contains(Node cur, Point2D p) {
        if (cur == null) {
            return false;
        }

        double x = p.x();
        double y = p.y();
        double curx = cur.p.x();
        double cury = cur.p.y();

        if (x == curx && y == cury)
            return true;

        if (cur.vertical == true) {
            if (x > curx)
                return contains(cur.rc, p);
            else if (x < curx)
                return contains(cur.lc, p);
            else if (y != cury)
                return contains(cur.rc, p);
        }

        if (cur.vertical == false) {
            if (y > cury)
                return contains(cur.rc, p);
            else if (y < cury)
                return contains(cur.lc, p);
            else if (x != curx)
                return contains(cur.rc, p);
        }

        return false;
    }

    public void draw() {
        for (Point2D p : pointsIterable(root)) {
            p.draw();
        }
    } // draw all points to standard draw

    private Iterable<Point2D> pointsIterable(Node root) {
        Queue<Point2D> q = new Queue<>();
        dfs(q, root);
        return q;
    }

    private void dfs(Queue<Point2D> q, Node cur) {
        if (cur == null)
            return;
        q.enqueue(cur.p);
        dfs(q, cur.lc);
        dfs(q, cur.rc);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();

        double xmin = rect.xmin();
        double ymin = rect.ymin();
        double xmax = rect.xmax();
        double ymax = rect.ymax();

        Stack<Point2D> pInRect = new Stack<>();

        for (Point2D p : pointsIterable(root)) {
            double x = p.x();
            double y = p.y();

            if (x >= xmin && x <= xmax && y >= ymin && y <= ymax) {
                pInRect.push(p);
            }
        }

        return pInRect;
    } // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (this.size() == 0)
            return null;

        double dist = Double.MAX_VALUE;
        Point2D min = null;

        for (Point2D temp : pointsIterable(root)) {
            if (temp.distanceSquaredTo(p) < dist) {
                dist = temp.distanceSquaredTo(p);
                min = temp;
            }
        }

        return min;
    } // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        
    } // unit testing of the methods (optional)
}
