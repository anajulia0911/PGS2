import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class App {
    public static void main(String[] args) {
        try {
            
            Class.forName("org.postgresql.Driver");

            System.out.println("Proprietarios!");

            String url = "jdbc:postgresql://aws-1-sa-east-1.pooler.supabase.com:6543/postgres?user=postgres.tbwabrzwqykefwfwblgn&password=Papaivce10!";

            // CONEXÃO COM BANCO DE DADOS
            Connection con = DriverManager.getConnection(url);
            System.out.println("Conexão ok!");

            // INSERT
            inserirProprietario(con, "Ana", "235656565-65");
            inserirProprietario(con, "Carlos", "123456789-00");

            // SELECT
            listarProprietarios(con);

            // UPDATE
            atualizarProprietario(con, 1, "Ana Maria", "987654321-00");

            listarProprietarios(con);

            // DELETE
            removerProprietario(con, 2);

            listarProprietarios(con);

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CREATE
    public static void inserirProprietario(Connection con, String nome, String cpf) throws SQLException {
        String sql = "INSERT INTO PROPRIETARIOS (nome, cpf) VALUES (?, ?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, nome);
        pstmt.setString(2, cpf);
        int qte = pstmt.executeUpdate();
        if (qte > 0) {
            System.out.println("Proprietário inserido com sucesso!");
        }
    }

    // READ
    public static void listarProprietarios(Connection con) throws SQLException {
        System.out.println("\n--- Lista de Proprietários ---");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM PROPRIETARIOS ORDER BY id");
        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String cpf = rs.getString("cpf");
            System.out.println("ID: " + id + " | Nome: " + nome + " | CPF: " + cpf);
        }
        System.out.println("-------------------------------\n");
    }

    // UPDATE
    public static void atualizarProprietario(Connection con, int id, String novoNome, String novoCpf) throws SQLException {
        String sql = "UPDATE PROPRIETARIOS SET nome = ?, cpf = ? WHERE id = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, novoNome);
        pstmt.setString(2, novoCpf);
        pstmt.setInt(3, id);
        int qte = pstmt.executeUpdate();
        if (qte > 0) {
            System.out.println("Proprietário atualizado com sucesso!");
        } else {
            System.out.println("Proprietário com ID " + id + " não encontrado!");
        }
    }

    // DELETE
    public static void removerProprietario(Connection con, int id) throws SQLException {
        String sql = "DELETE FROM PROPRIETARIOS WHERE id = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, id);
        int qte = pstmt.executeUpdate();
        if (qte > 0) {
            System.out.println("Proprietário removido com sucesso!");
        } else {
            System.out.println("Proprietário com ID " + id + " não encontrado!");
        }
    }
}
