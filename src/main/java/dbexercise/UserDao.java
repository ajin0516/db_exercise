package dbexercise;

import dbexercise.domain.User;

import java.sql.*;
import java.util.Map;
//5. 종료는 역순으로
public class UserDao {
    public void add() throws SQLException, ClassNotFoundException {
        Map<String, String> env = System.getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver"); // 1. driver loading
        Connection conn = DriverManager.getConnection(dbHost,dbUser,dbPassword);  // 2. 1번을 해야 연결됨. db 연결
        PreparedStatement ps = conn.prepareStatement("INSERT INTO users(id, name, password) VALUES(?, ?, ?)"); // 3-1. sql실행 준비 완료/문장 작성
        ps.setString(1,"2"); // 3-2. 어떤 값이 들어갈지 알려줘야함
        ps.setString(2,"ajin2");
        ps.setString(3,"1234");

        ps.executeUpdate(); // 4. SQL 실행하라

        // 5. DB 연결 종료 : 역순으로 종료
        ps.close();
        conn.close();
    }
    public User get(String id) throws  SQLException, ClassNotFoundException{
        Map<String, String> env = System.getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection  conn = DriverManager.getConnection(dbHost,dbUser,dbPassword);
        PreparedStatement ps = conn.prepareStatement("SELECT id, name, password FROM users WHERE id = ?");
        ps.setString(1,id);
        ResultSet rs = ps.executeQuery(); // 4. (select문에 resultset, executeQuery 사용 - 주소값을 참조하고 있다고 생각하기) SQL 실행한 값 넘겨주기
        System.out.println(rs + " 삽입 성공");
        rs.next();  // 첫번째는 간 다음 다음으로 내려가라(row)
        User user = new User(rs.getString("id"), rs.getString("name"),
                rs.getString("password"));
        rs.close();
        ps.close();
        conn.close();
        return user;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
//        userDao.add();
        User user = userDao.get("1");
        System.out.println("name = " + user.getName());
    }
}
