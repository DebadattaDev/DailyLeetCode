public class NumberOfDisConnectedIsland {
    static int deltaRow[]={0,0,1,-1};
    static int deltaCol[]={1,-1,0,0};
    public static void main(String[] args) {
        int grid[][]=new int[4][4];
        minDays(grid);
    }

    public static int minDays(int[][] grid) {
        int rows=grid.length;
        int cols=grid[0].length;
        int initialCount=0;
        initialCount=countIsland(grid);
        if(initialCount!=1) return 0;
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(grid[i][j]==0) continue;
                grid[i][j]=0;
                int curCount=countIsland(grid);
                if(curCount!=1) return 1;
                grid[i][j]=1;

            }
        }
        return 2;
    }
    public static int  countIsland(int [][]grid){
        int rows=grid.length;
        int cols=grid[0].length;
        int count=0;
        boolean visited[][]=new boolean[rows][cols];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                System.out.println(rows+" "+cols+" "+i+" "+j);
                if(!visited[i][j] && grid[i][j]==1){
                    exploreIsland(grid,i,j,visited);
                    count++;
                }
            }
        }
        return count;
    }
    public static void exploreIsland(int [][]grid,int i,int j,boolean [][]visited){
        visited[i][j]=true;
        for(int m=0;m<4;m++){
            int newRow=i+deltaRow[m];
            int newCol=j+deltaCol[m];
            if(isValid(grid,newRow,newCol,visited)){
                exploreIsland(grid,newRow,newCol,visited);
            }
        }

    }
    public static boolean isValid(int [][]grid,int i,int j,boolean [][]visited){
        int row=grid.length;
        int col=grid[0].length;
        if(i<row && i>=0 && j>=0 && j<col && !visited[i][j] && grid[i][j]==1) return true;
        else return false;
    }
}

//    The key to understanding this lies in how the flood fill algorithm is implemented and how the visited state is managed across different calls to the countIslands function.
//
//        Key Points:
//        Visited Array:
//
//        In each call to countIslands, a new visited array is created. This ensures that the function starts fresh each time it is called.
//        The visited array tracks which cells have been visited during that particular call to avoid revisiting cells.
//        Recounting Islands After Each Cell Removal:
//
//        When a cell is temporarily turned into water (i.e., grid[row][col] = 0), the countIslands function is called again with a new visited array to recount the number of islands.
//        The previous state of the visited array from earlier calls does not affect the new call because a new visited array is used each time.
//        Detailed Steps:
//        Initial Island Count:
//
//        The first call to countIslands counts how many islands exist in the original grid.
//        It uses its own visited array to mark which cells have been visited during this process.
//        Simulating Cell Removal:
//
//        For each land cell in the grid, the algorithm temporarily changes that cell to water (i.e., grid[row][col] = 0).
//        After making this change, countIslands is called again. This call uses a new visited array, so it does not "remember" any previous visits. It starts fresh and examines the modified grid to count how many islands there are after the cell has been removed.
//        Restoring the Grid:
//
//        After counting islands with the modified grid, the algorithm restores the cell back to its original state (i.e., grid[row][col] = 1).
//        This allows the algorithm to try removing other cells in a similar fashion.

//using Tarjans Algo to find Articulation Point

//class Solution {
//    int deltaRow[] = {0, 0, 1, -1};
//    int deltaCol[] = {1, -1, 0, 0};
//    int time = 0;
//
//    public int minDays(int[][] grid) {
//        int rows = grid.length;
//        int cols = grid[0].length;
//        int initialCount = countIsland(grid);
//
//        if (initialCount != 1) return 0;
//
//        // Find articulation points
//        boolean[][] visited = new boolean[rows][cols];
//        int[][] disc = new int[rows][cols];  // Discovery time of visited vertices
//        int[][] low = new int[rows][cols];   // Earliest visited vertex reachable
//        int parent[][] = new int[rows][cols];
//        boolean[][] ap = new boolean[rows][cols];  // To store articulation points
//
//        // Initialize parent array
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                parent[i][j] = -1;
//            }
//        }
//
//        // Apply Tarjan's Algorithm
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                if (grid[i][j] == 1 && !visited[i][j]) {
//                    tarjansDFS(grid, i, j, visited, disc, low, parent, ap);
//                }
//            }
//        }
//
//        // If there's any articulation point, return 1
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                if (ap[i][j]) return 1;
//            }
//        }
//
//        // If no articulation point, return 2
//        return 2;
//    }
//
//    void tarjansDFS(int[][] grid, int u, int v, boolean[][] visited, int[][] disc, int[][] low, int[][] parent, boolean[][] ap) {
//        visited[u][v] = true;
//        disc[u][v] = low[u][v] = ++time;
//        int children = 0;
//
//        for (int k = 0; k < 4; k++) {
//            int newRow = u + deltaRow[k];
//            int newCol = v + deltaCol[k];
//
//            if (isValidCell(grid, newRow, newCol)) {
//                if (!visited[newRow][newCol]) {
//                    children++;
//                    parent[newRow][newCol] = u * grid[0].length + v;
//                    tarjansDFS(grid, newRow, newCol, visited, disc, low, parent, ap);
//
//                    // Check if the subtree rooted at newRow, newCol has a connection back to one of the ancestors of u,v
//                    low[u][v] = Math.min(low[u][v], low[newRow][newCol]);
//
//                    // u, v is an articulation point in following cases:
//                    // 1. u, v is root of DFS tree and has two or more children.
//                    // 2. u, v is not root and low value of one of its child is more than discovery value of u, v.
//                    if (parent[u][v] == -1 && children > 1) ap[u][v] = true;
//                    if (parent[u][v] != -1 && low[newRow][newCol] >= disc[u][v]) ap[u][v] = true;
//
//                } else if (newRow != parent[u][v] / grid[0].length || newCol != parent[u][v] % grid[0].length) {
//                    // Update low[u,v] to the minimum of its own value and discovery value of its adjacent vertex
//                    low[u][v] = Math.min(low[u][v], disc[newRow][newCol]);
//                }
//            }
//        }
//    }
//
//    boolean isValidCell(int[][] grid, int i, int j) {
//        return i >= 0 && i < grid.length && j >= 0 && j < grid[0].length && grid[i][j] == 1;
//    }
//
//    int countIsland(int[][] grid) {
//        // Implement this as per your original logic to count the number of islands.
//    }
//}

