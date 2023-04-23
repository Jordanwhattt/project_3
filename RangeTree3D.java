package prj3;

import java.util.Arrays;

public class RangeTree3D {
    
    
    public Node root;
    public String type;
    
    public RangeTree3D() {
        root = null;
        this.type = "y";
    }
    
    
    public RangeTree3D(String type) {
        root = null;
        this.type = type;
    }
    
    
    public Node buildTree(Point[] points_x, Point[] points_y, Point[] points_z) {
        int n = points_x.length;
        
        //1. Build a binary search tree for based on P_y
        BST tree_x = new BST("x");
        RangeTree2D tree_2d = new RangeTree2D("y");
        
        //2. if P contains only one point
        if(n == 1) {
            Node v = new Node(points_x[0], tree_x); //Creates leaf node storing this point and make it 
            Point[] pyArray = new Point[1];
            pyArray[0] = points_x[0];
            v.setPy(pyArray);
            return v;
        }
        
        Point[] px_left;
        Point[] px_right;
        Point[] py_left;
        Point[] py_right;
        Point[] pz_left;
        Point[] pz_right;
         
        
    
        // Find median
        int median_index = (n/2);


        // Split P into P_left and P_right.
        
        if(n % 2 == 0) { // This is 
            px_left = Arrays.copyOfRange(points_x, 0, median_index);
            px_right = Arrays.copyOfRange(points_x, median_index, n);
        }
        else {
            px_left = Arrays.copyOfRange(points_x, 0, median_index);
            px_right = Arrays.copyOfRange(points_x, median_index, n);
        }
        

        py_left = new Point[px_left.length];
        py_right = new Point[px_right.length];
        //pz_left = new Point[px_left.length];
        //pz_right = new Point[px_right.length];
            

        int left_index = 0, right_index = 0;
        // Then subsets <= and > the median x-coordinate x_mid
        for (Point p : points_y) {
            if (p.getX() <= points_x[median_index-1].getX()) {
                py_left[left_index] = p;
                left_index++;
            }
            else {
                py_right[right_index] = p;
                right_index++;
            }
        }
        
        for (Point p : points_z) {
            if (p.getX() <= points_x[median_index-1].getX()) {
                py_left[left_index] = p;
                left_index++;
            }
            else {
                py_right[right_index] = p;
                right_index++;
            }
        }


        
        Node v = new Node(points_x[median_index-1], tree);
        v.setPy(points_y);

        //5. build v_left
        Node v_left = this.buildTree(px_left, py_left);
        //6. build v_right
        Node v_right = this.buildTree(px_right, py_right);
        
        //Make v_left the left child of v
        v.left = v_left;
        //Make v_right the right child of v
        v.right = v_right;    
        return v;
        
    }

}