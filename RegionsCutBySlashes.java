import java.util.LinkedList;
import java.util.Queue;

public class RegionsCutBySlashes {
    public static void main(String[] args) {
        String arg[]={" /","/ "};
        int deltaRow[]={0,0,1,-1};
        int deltaCol[]={1,-1,0,0};
        int ans=regionsBySlashes(arg,deltaRow,deltaCol);
        System.out.println(ans);
    }
    public static int regionsBySlashes(String[] grid,int []deltaRow,int []deltaCol) {
        int gridSize=grid.length;
        int [][]expandedGrid=new int [gridSize*3][gridSize*3];
        for(int i=0;i<gridSize;i++){
            for(int j=0;j<gridSize;j++){
                int baseRow=i*3;
                int baseCol=j*3;
                if(grid[i].charAt(j)=='\\'){
                    expandedGrid[baseRow][baseCol]=1;
                    expandedGrid[baseRow+1][baseCol+1]=1;
                    expandedGrid[baseRow+2][baseCol+2]=1;
                }else if(grid[i].charAt(j)=='/'){
                    expandedGrid[baseRow][baseCol+2]=1;
                    expandedGrid[baseRow+1][baseCol+1]=1;
                    expandedGrid[baseRow+2][baseCol]=1;
                }
            }
        }
        int regionCount=0;
        for(int i=0;i<gridSize*3;i++){
            for(int j=0;j<gridSize*3;j++){
                if(expandedGrid[i][j]==0){
                    floyd(expandedGrid,i,j,deltaRow,deltaCol);
                    regionCount++;
                }
            }
        }
        return regionCount;
    }
    public static void floyd(int [][]expandedGrid,int currRow,int currCol,int []deltaRow,int []deltaCol){
        Queue<int[]> q=new LinkedList<>();
        q.add(new int[] {currRow,currCol});
        while(!q.isEmpty()){
            int []arr=q.poll();
            for(int i=0;i<4;i++){
                int tRow=arr[0]+deltaRow[i];
                int tCol=arr[1]+deltaCol[i];
                if(isValid(expandedGrid,tRow,tCol)){
                    q.add(new int[] {tRow,tCol});
                    expandedGrid[tRow][tCol]=1;
                }
            }

        }
    }
    public static boolean isValid(int [][]expandedGrid,int currRow,int currCol){
        if(currRow>=0 && currRow < expandedGrid.length && currCol>=0 && currCol<expandedGrid.length && expandedGrid[currRow][currCol]==0) return true;
        else return false;
    }
}

//    When a cell in the grid contains a slash, it effectively divides it into two parts. A forward slash divides the cell into top-left and bottom-right sections, while a backslash divides it into top-right and bottom-left sections. As you can see in Example 2 of the problem, counting the regions directly is challenging since a divided cell does not always lead to an additional region.
//
//        To address this, we can magnify the grid by expanding each cell into a 3×3 sub-grid, with slashes represented by diagonal cells marked as barriers:
//
//
//
//        This transformation simplifies our task. If we treat the slashes and grid boundaries as water, and the remaining cells as land, the problem becomes analogous to the Number of Islands.
//
//        We can solve this using the flood-fill algorithm to visit each connected region in the grid. We iterate over each cell of the grid and invoke floodfill whenever we encounter an unvisited land cell. The floodfill function explores all reachable land cells from the current cell and marks them as visited. Then, we continue to iterate over each cell in the grid until we reach the next unvisited cell, which signifies the next land region. The total number of floodfill calls corresponds to the number of regions in the grid, which is our desired answer.
