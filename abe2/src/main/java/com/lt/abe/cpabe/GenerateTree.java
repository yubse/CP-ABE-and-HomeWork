package com.lt.abe.cpabe;

import org.json.JSONArray;
import org.json.JSONObject;

public class GenerateTree {
    public static String RelHwTree(String college, String classs, String teacherId) {
        int randomName = generateRandomNumber();
        JSONObject rootNode = new JSONObject();

        rootNode.put("name", 0);
        rootNode.put("value", "(1,2)");

        JSONArray childrenArray = new JSONArray();

        JSONObject child1 = new JSONObject();
        child1.put("name", generateRandomNumber());
        child1.put("value", "(2,2)");

        JSONArray child1Children = new JSONArray();

        JSONObject child1Child1 = new JSONObject();
        child1Child1.put("name", generateRandomNumber());
        child1Child1.put("value", college);
        child1Children.put(child1Child1);

        JSONObject child1Child2 = new JSONObject();
        child1Child2.put("name", generateRandomNumber());
        child1Child2.put("value", classs);
        child1Children.put(child1Child2);

        child1.put("children", child1Children);
        childrenArray.put(child1);

        JSONObject child2 = new JSONObject();
        child2.put("name", generateRandomNumber());
        child2.put("value", teacherId);
        childrenArray.put(child2);

        rootNode.put("children", childrenArray);

        return rootNode.toString();
    }

    public static String SubHwTree(String StudentId, String TeacherRole) {
        int randomName = generateRandomNumber();
        JSONObject rootNode = new JSONObject();
        rootNode.put("name", 0);
        rootNode.put("value", "(1,2)");

        JSONArray childrenArray = new JSONArray();
        JSONObject collegeNode = new JSONObject();
        int randomCollegeName = generateRandomNumber();
        collegeNode.put("name", randomCollegeName);
        collegeNode.put("value", StudentId);
        childrenArray.put(collegeNode);

        JSONObject classNode = new JSONObject();
        int randomClassName = generateRandomNumber();
        classNode.put("name", randomClassName);
        classNode.put("value", TeacherRole);
        childrenArray.put(classNode);

        rootNode.put("children", childrenArray);

        return rootNode.toString();
    }

    private static int generateRandomNumber() {
        // 生成随机数的逻辑，根据您的要求进行实现
        // 这里简单地返回一个 0 到 100 之间的随机数
        return (int) (Math.random() * 101);
    }
}