//package main.java.com.shyslav.selectCommands;
//
//import appmodels.*;
//import com.shyslav.database.DBConnector;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//
///**
// * Created by Shyshkin Vladyslav on 23.05.2016.
// */
//public class ChartAction {
//    /**
//     * Фунция формирования отчетности
//     * @param chart какой график строить
//     * @param dateStart - дата начала формирования отчета
//     * @param dateEnd - дата конца формирования отчета
//     * @return - обьект типа _GraphReport который содержит описание и кол-во продуктов
//     */
//    public static ArrayList<_GraphReport> selectChart(String chart, String dateStart, String dateEnd) {
//        String query = " ";
//        //генерировать отчет по текущему месяцу если на вход поступают пустые значения
//        if(dateStart==null||dateEnd==null)
//        {
//            switch (chart){
//                case "bar":
//                    query = "    select di.name as name,sum( orl.amount ) as amount from orders ors join orderlist orl on ors.id = orl.orderID join dish di on di.id = orl.dishID\n" +
//                            "        where date(ors.odate) between(LAST_DAY(now()) + interval 1 day - interval 1 month) and now()group by di.name";
//                    break;
//                case "pie":
//                    query = "    select di.name as name,sum( orl.amount ) as amount from orders ors join orderlist orl on ors.id = orl.orderID join dish di on di.id = orl.dishID\n" +
//                            "        where date(ors.odate) between(LAST_DAY(now()) + interval 1 day - interval 1 month) and now()group by di.name";
//                    break;
//                case "line":
//                    query = "    select odate as name,sum(orl.amount) amount from orders ors join orderlist orl on ors.id = orl.orderID \n" +
//                            "        where date(ors.odate) between(LAST_DAY(now()) + interval 1 day - interval 1 month) and now() group by date(ors.odate)";
//                    break;
//            }
//        }
//        //генерировать отчет по заданой дате
//        else
//        {
//            switch (chart){
//                case "bar":
//                    query = "select di.name as name,sum( orl.amount ) as amount from orders ors join orderlist orl on ors.id = orl.orderID join dish di on di.id = orl.dishID \n" +
//                            "where date(ors.odate) between '"+dateStart+"' and '"+dateEnd+"' group by di.name;";
//                    break;
//                case "pie":
//                    query = "select di.name as name,sum( orl.amount ) as amount from orders ors join orderlist orl on ors.id = orl.orderID join dish di on di.id = orl.dishID \n" +
//                            "where date(ors.odate) between '"+dateStart+"' and '"+dateEnd+"' group by di.name;";
//                    break;
//                case "line":
//                    query = "select odate as name,sum(orl.amount) amount from orders ors join orderlist orl on ors.id = orl.orderID\n" +
//                            "where date(ors.odate) between '"+dateStart+"' and '"+dateEnd+"' group by date(ors.odate);";
//                    break;
//            }
//        }
//        //Лист содержащий элементы графика
//        ArrayList<_GraphReport> charts = new ArrayList<>();
//        try (Connection conn = DBConnector.connect()) {
//            Statement statement = conn.createStatement();
//            try (ResultSet resultSet = statement.executeQuery(query)) {
//                while (resultSet.next()) {
//                    charts.add(new _GraphReport(
//                            resultSet.getString("name"),
//                            resultSet.getInt("amount")));
//                }
//                if(charts.size()!=0)
//                {
//                    return charts;
//                }
//                else
//                {
//                    return null;
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//            return null;
//        }
//    }
//}
