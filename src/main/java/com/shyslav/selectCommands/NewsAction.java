//package main.java.com.shyslav.selectCommands;
//
//import com.shyslav.database.DBConnector;
//import appmodels.*;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//
///**
// * Created by Shyshkin Vladyslav on 16.05.2016.
// */
//public class NewsAction {
//    /**
//     * Функция получения всех новостей ресторана которые отображаются на сайте
//     * @param id - ид новости
//     * @return лист новостей
//     */
//    public static ArrayList<_News> selectNews(int id)
//    {
//        ArrayList<_News> news = new ArrayList<>();
//        String query = " ";
//        switch (id)
//        {
//            case 0:
//                query = "select * from news";
//                break;
//
//            default:
//                query = "select * from news where id = "+id;
//                break;
//        }
//        try (Connection conn = DBConnector.connect()) {
//            Statement statement = conn.createStatement();
//            try (ResultSet resultSet = statement.executeQuery(query)) {
//                while (resultSet.next()) {
//                    news.add(new _News(
//                            resultSet.getInt("id"),
//                            resultSet.getInt("authorID"),
//                            resultSet.getString("name"),
//                            resultSet.getString("nText"),
//                            resultSet.getDate("nDate"),
//                            resultSet.getString("tegs"),
//                            resultSet.getInt("views"),
//                            resultSet.getString("imageLink")));
//                }
//                if(news.size()!=0)
//                {
//                    return news;
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
