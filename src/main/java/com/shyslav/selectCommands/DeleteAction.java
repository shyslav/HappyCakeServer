//package main.java.com.shyslav.selectCommands;
//
//import com.shyslav.database.DBConnector;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//
///**
// * Created by Shyshkin Vladyslav on 21.05.2016.
// */
//public class DeleteAction {
//    /**
//     * Функция удаления из таблицы
//     * @param tableName - имя таблицы
//     * @param id - ид элемента который нужно удалить
//     * @return результат выполнения
//     */
//    public static String delete(String tableName, String id)
//    {
//        String query  = "delete from  "+tableName +" where id =" +id;
//        try (Connection conn = DBConnector.connect()) {
//            Statement statement = conn.createStatement();
//            //System.out.println(query);
//            statement.execute(query);
//            } catch (SQLException e1) {
//            System.out.println("Ошибка запроса"+e1);
//            return "not found";
//        }catch (Exception ex)
//        {
//            System.out.println(ex);
//            return "not found";
//        }
//        return "done";
//    }
//}
