package com.nju.shanhe.leetcode.dp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author shanhe
 * @className LostRobot（迷路的机器人）
 * @date 2020-04-14 15:22
 **/
public class LostRobot {

    private LinkedList<List<Integer>> res = new LinkedList<>();

    private final int LEFT = 3;
    private final int UP = 4;

    public List<List<Integer>> pathWithObstacles(int[][] obstacleGrid) {
        //边界判断
        if(obstacleGrid == null || obstacleGrid.length < 1 || obstacleGrid[0].length < 1) {
            return res;
        }
        if(obstacleGrid[0][0] == 1 || obstacleGrid[obstacleGrid.length-1][obstacleGrid[0].length-1] == 1) {
            return res;
        }
        //数据处理，dp存储的是上一个节点，即dp存储路径
        int[][] dp = new int[obstacleGrid.length][obstacleGrid[0].length];
        //首行处理,从左边来用8表示，从上面来用16表示,起点为1,不可达用0表示
        dp[0][0] = 1 << LEFT;
        for(int i = 1; i < obstacleGrid[0].length; i++) {
            if(obstacleGrid[0][i] == 1) {
                dp[0][i] = 0;
                continue;
            }
            dp[0][i] = dp[0][i-1];
        }
        //首列处理
        dp[0][0] = 1 << UP;
        for(int i = 1; i < obstacleGrid.length; i++) {
            if(obstacleGrid[i][0] == 1) {
                dp[i][0] = 0;
                continue;
            }
            dp[i][0] = dp[i-1][0];
        }
        //起点重置,存储可达路径
        dp[0][0] = 1;
        for (int i = 1; i < obstacleGrid.length; i++) {
            for (int j = 1; j < obstacleGrid[i].length; j++) {
                if(obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                    continue;
                }
                if(dp[i][j-1] != 0) {
                    dp[i][j] = 1 << LEFT;
                } else if (dp[i-1][j] != 0) {
                    dp[i][j] = 1 << UP;
                }
            }
        }
        //判断是否可达，并从终点往回走生成结果集
        int i = obstacleGrid.length-1, j = obstacleGrid[i].length-1;
        //System.out.println("dp[i][j]=" + dp[i][j]);
        if (dp[i][j] != 0) {
            while (dp[i][j] != 1) {
                ArrayList<Integer> arr = new ArrayList<>();
                arr.add(i); arr.add(j);
                res.addFirst(arr);
                if(dp[i][j] == 1 << LEFT) {
                    j--;
                } else if(dp[i][j] == 1 << UP) {
                    i--;
                }
            }
            //返回集中加入初始点
            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(0);arrayList.add(0);
            res.addFirst(arrayList);
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] o = new int[][] {{0,0}, {1,1}, {0,0}};
        List<List<Integer>> res = new LostRobot().pathWithObstacles(o);
        System.out.println(res);
    }
}
