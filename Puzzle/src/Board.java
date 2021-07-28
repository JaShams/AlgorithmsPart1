import java.util.LinkedList;

public class Board {

    private int[][] maze;
    private final int size;
    private static final int space=0;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        size = tiles[0].length;

        maze = new int[size][size]; 

        for(int i=0 ; i<size ; i++) for(int j=0 ; j<size ; j++) maze[i][j]=tiles[i][j];

    }
                                           
    // string representation of this board
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(dimension() + "\n");
        for(int i=0 ; i<size ; i++) {
            for(int j=0 ; j<size ; j++)
                str.append(String.format("%2d ", maze[i][j]));
            str.append("\n");
        }

        return str.toString();
    }


    // board dimension n
    public int dimension(){
        return size;
    }

    // number of tiles out of place
    public int hamming(){
        int count=0;
        for(int i=0 ; i<size ; i++) 
            for(int j=0 ; j<size ; j++)
                if(blockNotInPlace(i,j)) count++;
        return count;
    }

    private boolean blockNotInPlace(int row,int col){
        int block = maze[row][col];
        
        if(block!=space && block!=finalPlace(row,col)) return true;
        else return false;
    }

    private int finalPlace(int row,int col){
        return (col+1)+(row*size);
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int sum = 0;
        for(int i=0 ; i<size ; i++) 
            for(int j=0 ; j<size ; j++)
                sum += distance(i,j);
        return sum;
    }

    private int distance(int i,int j){
        if(maze[i][j]==space) return 0;
        return Math.abs(i-(maze[i][j]-1)/size) + Math.abs(j-(maze[i][j]-1)%size);
    }

    // is this board the goal board?
    public boolean isGoal(){
        for(int i=0 ; i<size ; i++) 
            for(int j=0 ; j<size ; j++)
                if(blockNotInPlace(i,j)) return false;
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if(y==this) return true;
        if(y==null || !(y instanceof Board) || (((Board) y).dimension() != this.dimension())) return false;

        for(int i=0 ; i<size ; i++) 
            for(int j=0 ; j<size ; j++)
                if(((Board)y).maze[i][j] != this.maze[i][j]) return false;
        
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        LinkedList<Board> neighbors = new LinkedList<>();
        int spaceRow=-1,spaceCol=-1;

        for (int i=0 ; i<size ; i++){
            for (int j=0 ; j<size ; j++){
                if(maze[i][j]==space) {
                    spaceRow=i;
                    spaceCol=j;
                    break;
                }
            }
            if(spaceRow!=-1) break;
        }

        if(spaceRow>0)      neighbors.add(new Board(swap(spaceRow,spaceCol,spaceRow-1,spaceCol)));
        if(spaceRow<size-1) neighbors.add(new Board(swap(spaceRow,spaceCol,spaceRow+1,spaceCol)));
        if(spaceCol>0)      neighbors.add(new Board(swap(spaceRow,spaceCol,spaceRow,spaceCol-1)));
        if(spaceCol<size-1) neighbors.add(new Board(swap(spaceRow,spaceCol,spaceRow,spaceCol+1)));


        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        for (int i=0 ; i<size ; i++)
            for (int j=0 ; j<size-1 ; j++)
                if (maze[i][j]!=space && maze[i][j+1]!=space)
                    return new Board(swap(i, j, i, j + 1));
        throw new RuntimeException();
    }

    private int[][] swap(int row1,int col1,int row2, int col2){
        int[][] copy = new int[size][size];
        for(int i=0 ; i<size ; i++) for(int j=0 ; j<size ; j++) copy[i][j]=maze[i][j];

        int temp=copy[row1][col1];
        copy[row1][col1]=copy[row2][col2];
        copy[row2][col2]=temp;

        return copy;
    }

    // unit testing (not graded)
    public static void main(String[] args){
        int test[][] = new int[10][10];
        for(int i=0 ; i<10 ; i++) for(int j=0 ; j<10 ; j++) test[i][j]=(j+1)+(i*10);
        test[9][9]=0;
        Board testobj = new Board(test);
        System.out.println(testobj.toString());
        for(Board x : testobj.neighbors()) System.out.println(x.toString());
    }

}