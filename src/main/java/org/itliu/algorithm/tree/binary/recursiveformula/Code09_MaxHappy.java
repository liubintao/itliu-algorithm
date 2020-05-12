package org.itliu.algorithm.tree.binary.recursiveformula;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc: 派对的最大快乐值
 * <p>
 * 员工信息的定义如下:
 * class Employee {
 * public int happy; // 这名员工可以带来的快乐值
 * List<Employee> subordinates; // 这名员工有哪些直接下级
 * }
 * <p>
 * <p>
 * 公司的每个员工都符合 Employee 类的描述。整个公司的人员结构可以看作是一棵标准的、 没有环的多叉树。
 * 树的头节点是公司唯一的老板。除老板之外的每个员工都有唯一的直接上级。
 * 叶节点是没有任何下属的基层员工(subordinates列表为空)，除基层员工外，每个员工都有一个或多个直接下级。
 * @author: itliu
 * @date: 2020/4/30 10:56
 * @version: 1.0
 */
public class Code09_MaxHappy {

    public static class Employee {
        public int happy; // 这名员工可以带来的快乐值
        List<Employee> nexts; // 这名员工有哪些直接下级

        public Employee(int happy) {
            this.happy = happy;
            this.nexts = new ArrayList<>();
        }
    }

    public static int maxHappy1(Employee boss) {
        if (boss == null) {
            return 0;
        }
        int max = process1(boss, false);
        return max;
    }

    public static int process1(Employee boss, boolean up) {

        //上级来的最大值
        if (up) {
            int ans = 0;
            for (Employee e : boss.nexts) {
                ans += process1(e, false);
            }
            return ans;
        } else {//上级不来的最大值
            int p1 = boss.happy;//上级不来我来
            int p2 = 0;//上级不来我也不来
            for (Employee e : boss.nexts) {
                //每个下级员工都来
                p1 += process1(e, true);
                //每个下级员工都不来
                p2 += process1(e, false);
            }
            return Math.max(p1, p2);
        }
    }

    public static class Info {
        public int yes;//来的最大快乐值
        public int no;//不来的最大快乐值

        public Info(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }

    public static int maxHappy2(Employee boss) {
        if (boss == null) {
            return 0;
        }
        Info info = process2(boss);
        int max = Math.max(info.yes, info.no);
        return max;
    }

    private static Info process2(Employee x) {
        if (x.nexts.isEmpty()) {
            return new Info(x.happy, 0);
        }

        int yes = x.happy;
        int no = 0;
        for (Employee next : x.nexts) {
            Info info = process2(next);
            yes += info.no;
            no += Math.max(info.yes, info.no);
        }
        return new Info(yes, no);
    }

    // for test
    public static Employee genarateBoss(int maxLevel, int maxNexts, int maxHappy) {
        if (Math.random() < 0.02) {
            return null;
        }
        Employee boss = new Employee((int) (Math.random() * (maxHappy + 1)));
        genarateNexts(boss, 1, maxLevel, maxNexts, maxHappy);
        return boss;
    }

    // for test
    public static void genarateNexts(Employee e, int level, int maxLevel, int maxNexts, int maxHappy) {
        if (level > maxLevel) {
            return;
        }
        int nextsSize = (int) (Math.random() * (maxNexts + 1));
        for (int i = 0; i < nextsSize; i++) {
            Employee next = new Employee((int) (Math.random() * (maxHappy + 1)));
            e.nexts.add(next);
            genarateNexts(next, level + 1, maxLevel, maxNexts, maxHappy);
        }
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxNexts = 7;
        int maxHappy = 100;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            Employee boss = genarateBoss(maxLevel, maxNexts, maxHappy);
            if (maxHappy1(boss) != maxHappy2(boss)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
