
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Projeto001 extends JFrame implements ActionListener {

    JLabel Lra, Lnome, Lende, Lemail, Ltel, L0;
    JButton b1, b2, b3, b4, b5;
    static JTextField tfra, tfnome, tfende, tfemail, tftel;
    JPanel p1 = new JPanel();
    ResultSet rs;
    Statement MeuState;

    public static void main(String args[]) {
        JFrame Janela = new Projeto001();
        Janela.show();
        WindowListener x = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        Janela.addWindowListener(x);
    }

    Projeto001() {

        p1.setLayout(new FlowLayout(FlowLayout.LEFT));
        Lra = new JLabel("RA");
        Lnome = new JLabel("Nome");
        Lende = new JLabel("Endereco");
        Lemail = new JLabel("Email");
        Ltel = new JLabel("Telefone");

        L0 = new JLabel(" ");
        for (int i = 0; i < 40; i++) {
            L0.setText(L0.getText() + " ");
        }
        tfra = new JTextField(60);
        tfra.addActionListener(this);

        tfnome = new JTextField(60);
        tfnome.addActionListener(this);

        tfende = new JTextField(60);
        tfende.addActionListener(this);

        tfemail = new JTextField(60);
        tfemail.addActionListener(this);

        tftel = new JTextField(60);
        tftel.addActionListener(this);

        b1 = new JButton("Cadastrar");
        b2 = new JButton("Alterar");
        b3 = new JButton("Excluir");
        b4 = new JButton("Consultar");
        b5 = new JButton("Limpar");

        b1.setBackground(new Color(180, 180, 250));
        b2.setBackground(new Color(180, 180, 250));
        b3.setBackground(new Color(180, 180, 250));
        b4.setBackground(new Color(180, 180, 250));
        b5.setBackground(new Color(180, 180, 250));

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);

        p1.add(Lra);
        p1.add(tfra);

        p1.add(Lnome);
        p1.add(tfnome);

        p1.add(Lende);
        p1.add(tfende);

        p1.add(Lemail);
        p1.add(tfemail);

        p1.add(Ltel);
        p1.add(tftel);

        p1.add(L0);
        p1.add(b1);
        p1.add(b2);
        p1.add(b3);
        p1.add(b4);
        p1.add(b5);

        getContentPane().add(p1);
        setTitle("Registro de Alunos");
        setSize(700, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        String URL = "jdbc:mysql://localhost:3306/registroaluno";
        try {
            final String DRIVER = "com.mysql.jdbc.Driver";
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(URL, "root", "bigo");
            MeuState = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = MeuState.executeQuery("SELECT * FROM registroaluno.aluno");
            rs.first();
            atualizaCampos();
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver JDBC nao encontrado!");
        } catch (SQLException ex) {
            System.out.println("Problemas na conexao com a fonte de dados");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b5) // Limpa
        {
            limpaCampos();
            return;
        }
// ------------------ Final do Limpador de Campos -------------------

        if (e.getSource() == b1) //Cadastrar
        {
            try {
                String SQL = "INSERT INTO aluno (ra,name,endereco,telefone,email) Values ('"
                        + tfra.getText() + "','"
                        + tfnome.getText() + "','"
                        + tfende.getText() + "','"
                        + tftel.getText() + "','"
                        + tfemail.getText() + "')";
                MeuState.executeUpdate(SQL);
                JOptionPane.showMessageDialog(null, "Gravacao realizada com sucesso!");  
                limpaCampos();
                } 
            catch (SQLException ex) {
                if (ex.getMessage().equals("General error")) {
                    JOptionPane.showMessageDialog(null, "Aluno ja cadastrado");
                } else {
                    JOptionPane.showMessageDialog(null, "Dados invalidos ou repetidos");
                }
            }

        }
// ------------------ Final do Cadastro  -------------------

        if (e.getSource() == b2) //Alteracao
        {
            try {
                String SQL = "UPDATE aluno SET "
                        + "ra='" + tfra.getText() + "',"
                        + "name='" + tfnome.getText() + "',"
                        + "endereco='" + tfende.getText() + "',"
                        + "telefone='" + tftel.getText() + "',"
                        + "email='" + tfemail.getText() + "' "
                        + "WHERE Ra= '" + tfra.getText() + "'"; //Precisa somente do RA igual para mudar os outros
                int r = MeuState.executeUpdate(SQL);
                if (r == 1) {
                    JOptionPane.showMessageDialog(null, "Alteracao realizada com sucesso");
                } else {
                    JOptionPane.showMessageDialog(null, "Aluno sem cadastro\n Por favor, pressione Cadastro");
                }
            } catch (SQLException ex) {
            }
        }
// ------------------ Final da Atualizacao -------------------

        if (e.getSource() == b3) // Excluir
        {
            try {
                String SQL = "SELECT ra, name FROM aluno Where ra = '" + tfra.getText() + "'";
                rs = MeuState.executeQuery(SQL);
                String nome = "";
                try {
                    rs.next();
                    nome = "Deletar o Aluno: " + rs.getString("name");
                } catch (SQLException ex1) {
                    JOptionPane.showMessageDialog(null, "Aluno nao cadastrado!");
                    return;
                }
                int n = JOptionPane.showConfirmDialog(null, nome, " ", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    SQL = "DELETE FROM aluno Where ra = '" + tfra.getText() + "'";
                    int r = MeuState.executeUpdate(SQL);
                    if (r == 1) {
                        JOptionPane.showMessageDialog(null, "Exclusao realizada com sucesso");
                    } else {
                        JOptionPane.showMessageDialog(null, "Nao foi possivel excluir o aluno");
                    }
                } else {
                    return;
                }
            } catch (SQLException ex) {
            }
            limpaCampos();
        }
// ------------------ Final da Exclusao -------------------

        if (e.getSource() == b4 || e.getSource() == tfra) { //localizar
            try {
                String SQL = "SELECT * FROM aluno Where ra = '" + tfra.getText() + "'";
                rs = MeuState.executeQuery(SQL);
                rs.next();
                tfra.setText(rs.getString("ra"));
                tfnome.setText(rs.getString("name"));
                tfende.setText(rs.getString("endereco"));
                tfemail.setText(rs.getString("email"));
                tftel.setText(rs.getString("telefone"));

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Aluno nao encontrado!");
                return;
            }
        }
// ------------------ Final da Localizacao -------------------
    }

    public static void limpaCampos() {
        tfra.setText("");
        tfnome.setText("");
        tfende.setText("");
        tfemail.setText("");
        tftel.setText("");

    }

    public void atualizaCampos() {
        try {
            tfra.setText(rs.getString("ra"));
            tfnome.setText(rs.getString("name"));
            tfende.setText(rs.getString("endereco"));
            tfemail.setText(rs.getString("email"));
            tftel.setText(rs.getString("telefone"));
            limpaCampos();

        } catch (SQLException ex) {
        }
    }
}
