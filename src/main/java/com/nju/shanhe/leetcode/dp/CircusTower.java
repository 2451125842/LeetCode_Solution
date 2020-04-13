package com.nju.shanhe.leetcode.dp;

import java.util.Arrays;

/**
 * @author shanhe
 * @className CircusTower(马戏团人塔)
 * @date 2020-04-13 14:37
 **/
public class CircusTower {

    public int bestSeqAtIndex(int[] height, int[] weight) {
        //边界处理
        if(height == null || height.length < 1) {
            return 0;
        }
        //数据处理,将该问题转化为最长严格增序子序列问题
        int person[][] = new int[height.length][];
        for(int i = 0; i < height.length; i++) {
            person[i] = new int[] {height[i], weight[i]};
        }
        Arrays.sort(person, (a, b) -> a[0] == b[0] ? b[1]-a[1] : a[0]-b[0]);
        /*for (int i = 0; i < person.length; i++) {
             System.out.println(person[i][0] + " " + person[i][1]);
        }*/
        //dp求解，dp中存储的是前i个的最长子序列
        int[] dp = new int[height.length];
        dp[0] = person[0][1];
        int tail = 0;
        for(int i = 1; i < person.length; i++) {
            if(person[i][1] > dp[tail]) {
                dp[++tail] = person[i][1];
            } else {
                int index = binarySearch(dp, person[i][1], tail);
                if(index != -1)
                    dp[index] = person[i][1];
            }
        }
        return tail+1;

    }

    private int binarySearch(int[] dp, int target, int end) {
        int start = 0;
        while (start <= end) {
            int mid = start + (end-start)/2;
            if(dp[mid] == target) {
                return mid;
            } else if (dp[mid] > target) {
                end = mid-1;
            } else {
                if(dp[mid+1] >= target) {
                    return mid+1;
                }
                start = mid+1;
            }
        }
        //当需要需要查找的对象为0时,上面的二分查找无法检查,所以
        return dp[0] > target ? 0 : -1;
    }

}
