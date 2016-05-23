package com.shyslav.selectCommands;

import com.shyslav.database.connector;
import com.shyslav.models.ReportsGraph;
import com.shyslav.models.orderList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Shyshkin Vladyslav on 23.05.2016.
 */
public class ChartAction {
    public static ArrayList<ReportsGraph> selectChart(String chart,String dateStart,String dateEnd) {
        String query = " ";
        if(dateStart==null||dateEnd==null)
        {
            switch (chart){
                case "bar":
                    query = "    select di.name as name,sum( orl.amount ) as amount from orders ors join orderlist orl on ors.id = orl.orderID join dish di on di.id = orl.dishID\n" +
                            "        where date(ors.odate) between(LAST_DAY(now()) + interval 1 day - interval 1 month) and now()group by di.name";
                    break;
                case "pie":
                    query = "    select di.name as name,sum( orl.amount ) as amount from orders ors join orderlist orl on ors.id = orl.orderID join dish di on di.id = orl.dishID\n" +
                            "        where date(ors.odate) between(LAST_DAY(now()) + interval 1 day - interval 1 month) and now()group by di.name";
                    break;
                case "line":
                    query = "    select odate as name,sum(orl.amount) amount from orders ors join orderlist orl on ors.id = orl.orderID \n" +
                            "        where date(ors.odate) between(LAST_DAY(now()) + interval 1 day - interval 1 month) and now() group by date(ors.odate)";
                    break;
            }
        }
        else
        {
            switch (chart){
                case "bar":
                    query = "select di.name as name,sum( orl.amount ) as amount from orders ors join orderlist orl on ors.id = orl.orderID join dish di on di.id = orl.dishID \n" +
                            "where date(ors.odate) between '"+dateStart+"' and '"+dateEnd+"' group by di.name;";
                    break;
                case "pie":
                    query = "select di.name as name,sum( orl.amount ) as amount from orders ors join orderlist orl on ors.id = orl.orderID join dish di on di.id = orl.dishID \n" +
                            "where date(ors.odate) between '"+dateStart+"' and '"+dateEnd+"' group by di.name;";
                    break;
                case "line":
                    query = "select odate as name,sum(orl.amount) amount from orders ors join orderlist orl on ors.id = orl.orderID\n" +
                            "where date(ors.odate) between '"+dateStart+"' and '"+dateEnd+"' group by date(ors.odate);";
                    break;
            }
        }
        ArrayList<ReportsGraph> charts = new ArrayList<>();

        try (Connection conn = connector.connect()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    charts.add(new ReportsGraph(
                            resultSet.getString("name"),
                            resultSet.getInt("amount")));
                }
                if(charts.size()!=0)
                {
                    return charts;
                }
                else
                {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
