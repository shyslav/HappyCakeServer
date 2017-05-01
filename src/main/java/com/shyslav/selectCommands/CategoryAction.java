//package main.java.com.shyslav.selectCommands;
//
//import com.shyslav.database.DBConnector;
//import appmodels.*;
//import org.apache.commons.io.IOUtils;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//
///**
// * Created by Shyshkin Vladyslav on 16.05.2016.
// */
//public class CategoryAction {
//    /**
//     * Функция получения данных из категории
//     *
//     * @param id ид категории
//     * @return лист категорий
//     */
//    public static ArrayList<_Category> selectCategory(int id) {
//        ArrayList<_Category> category = new ArrayList<>();
//        String query = " ";
//        switch (id) {
//            //Получить все категоии из базы
//            case 0:
//                query = "select * from category";
//                break;
//            //Получить категорию по id
//            default:
//                query = "select * from category where id = " + id;
//                break;
//        }
//        try (Connection conn = DBConnector.connect()) {
//            Statement statement = conn.createStatement();
//            try (ResultSet resultSet = statement.executeQuery(query)) {
//                while (resultSet.next()) {
//                    category.add(new _Category(
//                            resultSet.getInt("id"),
//                            resultSet.getString("name"),
//                            resultSet.getString("description"),
//                            IOUtils.toByteArray(resultSet.getBinaryStream("image"))));
//                }
//                if (category.size() != 0) {
//                    return category;
//                } else {
//                    return null;
//                }
//            }
//        } catch (SQLException | IOException e) {
//            System.out.println(e);
//            return null;
//        }
//    }
//
//    /**
//     * Функция получения блюда из базы
//     *
//     * @param id ид блюда для селекта
//     * @return лист блюд
//     */
//    public static ArrayList<_Dish> selectDish(int id) {
//        ArrayList<_Dish> dish = new ArrayList<>();
//        //В поле sell записывается скидка с таблицы hotprice
//        String query = " ";
//        switch (id) {
//            case 0:
//                //Все блюда
//                query = "select id as idfromdish, categoryID, name, description, amount, price, image, readyORnot, (select percent from hotprice where dishID = idfromdish and dateEnd>=curdate() ) as sell from dish";
//                break;
//
//            default:
//                //Блюда по категории
//                query = "select id as idfromdish, categoryID, name, description, amount, price, image, readyORnot, (select percent from hotprice where dishID = idfromdish and dateEnd>=curdate() ) as sell from dish  where categoryID = " + id;
//                break;
//        }
//        try (Connection conn = DBConnector.connect()) {
//            Statement statement = conn.createStatement();
//            try (ResultSet resultSet = statement.executeQuery(query)) {
//                while (resultSet.next()) {
//                    dish.add(new _Dish(
//                            resultSet.getInt("idfromdish"),
//                            resultSet.getInt("categoryID"),
//                            resultSet.getString("name"),
//                            resultSet.getString("description"),
//                            resultSet.getInt("amount"),
//                            resultSet.getDouble("price"),
//                            IOUtils.toByteArray(resultSet.getBinaryStream("image")),
//                            resultSet.getString("readyORnot").trim(),
//                            resultSet.getString("sell")));
//                }
//                if (dish.size() != 0)
//                    return dish;
//                else
//                    return null;
//            }
//        } catch (SQLException | IOException e) {
//            System.out.println(e);
//            return null;
//        }
//    }
//}