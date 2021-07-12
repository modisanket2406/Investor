package main.java.investor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvestorGraphTest {

    public static void main(String args[]) {
        InvestorGraph graph = new InvestorGraph();
        InvestorGraphTest investorGraphTest = new InvestorGraphTest();

        //Run tests
        investorGraphTest.testGraphLevel1(graph);
        investorGraphTest.testGraphLevel2(graph);
        investorGraphTest.testGraphLevel3(graph);
        investorGraphTest.testGetMarketValue(graph);
        investorGraphTest.testLeafWithoutReqValues(graph);


        System.out.println("Market Value of Fund" + graph.getMarketValue("Test-Fund1"));

    }

    public void testGraphLevel1(InvestorGraph graph) {
        Map<Integer, Map<Node, List<Node>>> parentChildLevelMap = new HashMap<>();

        List<Node> childs = new ArrayList<>();
        childs.add(getFund("Test-Fund1"));
        childs.add(getFund("Test-Fund2"));

        Map<Node, List<Node>> parentChildMap = new HashMap<>();
        parentChildMap.put(getInvestor("Test-Investor1"), childs);
        parentChildMap.put(getInvestor("Test-Investor2"), childs);

        parentChildLevelMap.put(1, parentChildMap);

        graph.addNode(parentChildLevelMap);
        graph.printGraph();
    }

    public void testGraphLevel2(InvestorGraph graph) {

        Map<Integer, Map<Node, List<Node>>> parentChildLevelMap = new HashMap<>();

        List<Node> childs2 = new ArrayList<>();

        Map<String, Integer> fundNameToQtyMap1 = new HashMap<>();
        fundNameToQtyMap1.put("Test-Fund1", 100);

        Map<String, Integer> fundNameToQtyMap2 = new HashMap<>();
        fundNameToQtyMap2.put("Test-Fund1", 1000);

        childs2.add(getHolding("Test-Holding1", fundNameToQtyMap1, 20d));
        childs2.add(getHolding("Test-Holding2", fundNameToQtyMap2, 10d));

        Map<Node, List<Node>> parentChildMap = new HashMap<>();
        parentChildMap.put(getFund("Test-Fund1"), childs2);
        parentChildLevelMap.put(2, parentChildMap);
        graph.addNode(parentChildLevelMap);
        graph.printGraph();
    }

    public void testGetMarketValue(InvestorGraph graph) {
        Double marketValue = graph.getMarketValue("Test-Fund1");
        if (marketValue == 12000) {
            System.out.println("Market Value Test Passed");
        } else {
            throw new RuntimeException("Market Value is :" + marketValue + " which doesn't match with the expected value.");
        }
    }

    public void testGraphLevel3(InvestorGraph graph) {
        try {
            Map<Integer, Map<Node, List<Node>>> parentChildLevelMap = new HashMap<>();

            List<Node> childs2 = new ArrayList<>();

            Map<String, Integer> fundNameToQtyMap1 = new HashMap<>();
            fundNameToQtyMap1.put("Test-Fund1", 100);

            childs2.add(getHolding("Test-Holding1", fundNameToQtyMap1, 20d));

            Map<Node, List<Node>> parentChildMap = new HashMap<>();
            parentChildMap.put(getFund("Test-Fund1"), childs2);
            parentChildLevelMap.put(3, parentChildMap);
            graph.addNode(parentChildLevelMap);
        } catch (Exception e) {
            System.out.println("Exception occurred with Level 3 which is expected.");
        }

    }

    public void testLeafWithoutReqValues(InvestorGraph graph) {

        Map<Integer, Map<Node, List<Node>>> parentChildLevelMap = new HashMap<>();

        List<Node> childs2 = new ArrayList<>();

        Map<String, Integer> fundNameToQtyMap1 = new HashMap<>();
        fundNameToQtyMap1.put("Test-Fund1", 100);

        childs2.add(getHolding("Test-Holding1", fundNameToQtyMap1, 20d));
        childs2.add(getHolding("Test-Holding2", null, 10d));

        Map<Node, List<Node>> parentChildMap = new HashMap<>();
        parentChildMap.put(getFund("Test-Fund1"), childs2);
        parentChildLevelMap.put(2, parentChildMap);
        graph.addNode(parentChildLevelMap);
        graph.printGraph();
    }

    private Investor getInvestor(String investorName) {
        Investor investor = new Investor();
        investor.setName(investorName);

        return investor;
    }

    private Fund getFund(String fundName) {
        Fund fund = new Fund();
        fund.setName(fundName);

        return fund;
    }

    private Holding getHolding(String holdingName, Map<String, Integer> fundNameToQtyMap, Double marketValue) {
        Holding holding = new Holding();
        holding.setName(holdingName);
        holding.setMarketValue(marketValue);
        holding.setFundNameToQtyMap(fundNameToQtyMap);

        return holding;
    }
}




