package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        final String URL = "jdbc:mysql://localhost:3306/test_database";
        final String USER = "root";
        final String PASS = "rootp@ssw0rd";
        final String SQL = "insert into test_table values (?,?,?,?)";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            // データベース接続
            conn = DriverManager.getConnection(
                    // ホスト名、データベース名
                    URL,
                    // ユーザー名
                    USER,
                    // パスワード
                    PASS);


            //データの追加
            //参考：https://itsakura.com/java-mysql-insert

            //自動コミットをオフ
            conn.setAutoCommit(false);

            //第一引数追加する位置、第二引数追加する値
            stmt = conn.prepareStatement(SQL);
            stmt.setInt(1, 3);
            stmt.setString(2, "橋詰風花");
            stmt.setInt(3, 23);
            stmt.setString(4, "青森");

            stmt.executeUpdate();
            
            //コミット
            conn.commit();

            //取り消し
            //conn.rollback()
            // SQL文をセット
            stmt = conn.prepareStatement("SELECT * FROM test_table");
            // SQL文を実行
            rs = stmt.executeQuery();
            
            // ループして1レコードずつ取得
            while (rs.next()) {
                int id      = rs.getInt("id");
                String name    = rs.getString("name");
                int age     = rs.getInt("age");
                String address = rs.getString("addres");
                
 
                System.out.println(id + "、" + name + "、" + address + "、" + age);
            }
 
        } catch (SQLException e) {
            System.out.println("データベース接続エラー。");
 
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("データベース接続エラー。");
            }
        }
    }
}
