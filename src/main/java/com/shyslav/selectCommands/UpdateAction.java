//package main.java.com.shyslav.selectCommands;
//
//
//import com.shyslav.database.DBConnector;
//
//import java.sql.*;
//import java.util.Arrays;
//
///**
// * Created by Shyshkin Vladyslav on 22.05.2016.
// */
//public class UpdateAction {
//    /**
//     * Функция селекта для редактирования данных в админ панели
//     *
//     * @param tableName - имя таблицы
//     * @param what      - поле для селекта
//     * @param id        - ид записи для селекта
//     * @return - значение поля
//     */
//    public static String selectToUpdate(String tableName, String what, String id) {
//        String result = "";
//        String query = "select " + what + " from " + tableName + " where id = " + id;
//        try (Connection conn = DBConnector.connect()) {
//            Statement statement = conn.createStatement();
//            try (ResultSet resultSet = statement.executeQuery(query)) {
//                while (resultSet.next()) {
//                    //значение поля
//                    result = resultSet.getString(1);
//                }
//                if (result.isEmpty() && result == null && result.length() == 0) {
//                    return null;
//                } else {
//                    return result;
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//            return null;
//        }
//    }
//
//    /**
//     * Функция обновления данных таблицы
//     *
//     * @param date - команда которая пришла от клиента
//     * @return - результат выполнения запроса
//     */
//    public static String update(String[] date) {
//        //Создать дополнительный масив который вмещает в себя ТОЛЬКО значения всех "ключ = значение"
//        String[] tmp = Arrays.copyOfRange(date, 3, date.length);
//        //Заменить символ | на :
//        //В сплит команды входит : по этому : на клиенте заменяется на | и мы обратно заменяем зжесб для апдейта
//        for (int i = 0; i < tmp.length; i++) {
//            if (tmp[i].contains("|")) {
//                tmp[i] = tmp[i].replace("|", ":");
//            }
//        }
//        // Запрос, date[1] - имя таблицы, date[2] - ид для замены, с 3 элемента параметры "ключ = значение"
//        String query = "update " + date[1] + " SET " + String.join(",", tmp) + " where id = " + date[2];
//        System.out.println(query);
//        try (Connection conn = DBConnector.connect()) {
//            Statement statement = conn.createStatement();
//            statement.execute(query);
//        } catch (SQLException ex) {
//            System.out.println("Ошибка запроса" + ex);
//            return "not found";
//        }
//        return "done";
//    }
//
//    /**
//     * Функция вставки в таблицу
//     *
//     * @param date - команда которая пришла от клиента
//     * @return результат выполнения запроса
//     */
//    public static String insert(String[] date) {
//        // создать дополнительный массив с 2 элемента запроса клиента для отделеня значений
//        String insertValues[] = Arrays.copyOfRange(date, 2, date.length);
//        //Заменить символ | на :
//        //В сплит команды входит : по этому : на клиенте заменяется на | и мы обратно заменяем зжесб для апдейта
//        for (int i = 0; i < insertValues.length; i++) {
//            if (insertValues[i].contains("|")) {
//                insertValues[i] = insertValues[i].replace("|", ":");
//            }
//        }
//        //Запрос date[1] - имя таблицы в которую произвести вставку
//        String query = "insert into " + date[1] + " values( LAST_INSERT_ID()," + String.join(",", insertValues) + ")";
//        System.out.println(query);
//        try (Connection conn = DBConnector.connect()) {
//            Statement statement = conn.createStatement();
//            statement.execute(query);
//        } catch (SQLException ex) {
//            System.out.println("Ошибка запроса" + ex);
//            return "not found";
//        }
//        return "done";
//    }
//}
