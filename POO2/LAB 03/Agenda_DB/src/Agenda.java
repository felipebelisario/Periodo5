import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.*;

class Agenda extends JFrame {

    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    // the database name
    private static String dbName="agendaJDBC";
    // define the Derby connection URL to use
    private static String connectionURL = "jdbc:derby:" + dbName + ";create=true";
    private static Connection conn = null;
    private static Statement stmt = null;

    private static void createConnection()
    {
        try
        {
            conn = DriverManager.getConnection(connectionURL);

            stmt = conn.createStatement();
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }
    }

    private static void shutdown()
    {

        try {
            conn.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Closed connection");

        //   ## DATABASE SHUTDOWN SECTION ##
        /*** In embedded mode, an application should shut down Derby.
         Shutdown throws the XJ015 exception to confirm success. ***/
        if (driver.equals("org.apache.derby.jdbc.EmbeddedDriver")) {
            boolean gotSQLExc = false;
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException se)  {
                if ( se.getSQLState().equals("XJ015") ) {
                    gotSQLExc = true;
                }
            }
            if (!gotSQLExc) {
                System.out.println("Database did not shut down normally");
            }  else  {
                System.out.println("Database shut down normally");
            }
        }

    }

    public static boolean wwdChk4Table (Connection conTst ) throws SQLException {
        boolean chk = true;
        boolean doCreate = false;
        try {
            Statement s = conTst.createStatement();
            s.execute("update CADASTRO set NOME = 'TEST ENTRY', ENDERECO = 'TEST ENTRY', TEL = '1234' where 1=3");
        }  catch (SQLException sqle) {
            String theError = (sqle).getSQLState();
            //   System.out.println("  Utils GOT:  " + theError);
            /** If table exists will get -  WARNING 02000: No row was found **/
            if (theError.equals("42X05"))   // Table does not exist
            {  return false;
            }  else if (theError.equals("42X14") || theError.equals("42821"))  {
                System.out.println("WwdChk4Table: Incorrect table definition. Drop table WISH_LIST and rerun this program");
                throw sqle;
            } else {
                System.out.println("WwdChk4Table: Unhandled SQLException" );
                throw sqle;
            }
        }
        //  System.out.println("Just got the warning - table exists OK ");
        return true;
    }  /*** END wwdInitTable  **/



    JPanel tit;
    JPanel principal;
    JLabel titulo;
    JPanel principal2;
    JPanel nomes;
    JPanel fones;
    JPanel ends;
    JLabel nome;
    JTextField nome1;
    JLabel fone;
    JTextField fone1;
    JLabel end;
    JTextField end1;
    JButton insere;
    JButton deleta;
    JButton procura;
    JButton lista;
    JButton limpa;

    public Agenda() {
        super("AGENDA TELEFÔNICA");

        tit = new JPanel();
        principal = new JPanel();
        principal2 = new JPanel();
        titulo = new JLabel("INSIRA OS DADOS");
        nomes = new JPanel();
        fones = new JPanel();
        ends = new JPanel();
        nome = new JLabel("Nome:    ");
        nome1 = new JTextField(15);
        fone = new JLabel("Telefone:");
        fone1 = new JTextField(15);
        end = new JLabel("Endereço:");
        end1 = new JTextField(15);
        insere = new JButton("Cadastrar");
        deleta = new JButton("Apagar");
        procura = new JButton("Procurar");
        limpa = new JButton("Limpar");
        lista = new JButton("Listar");

        nome1.setDocument(new JTextFieldLimit(100));
        end1.setDocument(new JTextFieldLimit(100));
        fone1.setDocument(new JTextFieldLimit(20));

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(tit, BorderLayout.NORTH);
        c.add(principal, BorderLayout.CENTER);
        c.add(principal2, BorderLayout.SOUTH);
        tit.add(titulo, BorderLayout.NORTH);
        nomes.add(nome, BorderLayout.WEST);
        nomes.add(nome1, BorderLayout.EAST);
        fones.add(fone, BorderLayout.WEST);
        fones.add(fone1, BorderLayout.EAST);
        ends.add(end, BorderLayout.WEST);
        ends.add(end1, BorderLayout.EAST);
        principal.add(nomes, BorderLayout.NORTH);
        principal.add(fones, BorderLayout.CENTER);
        principal.add(ends, BorderLayout.SOUTH);
        principal2.add(procura);
        principal2.add(deleta);
        principal2.add(insere);
        principal2.add(lista);
        principal2.add(limpa);
        this.setSize(275, 250);
        this.setVisible(true);
        //show();
        deleta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletar();
            }
        });
        insere.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrar();
            }
        });
        procura.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                procurar();
            }
        });
        lista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listar();
            }
        });
        limpa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nome1.setText("");
                fone1.setText("");
                end1.setText("");
            }
        });

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                shutdown();
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });

    }

    public static void main(String args[]) throws SQLException {

        String createString = "CREATE TABLE CADASTRO  "
                + "( NOME VARCHAR(100) NOT NULL,"
                + " ENDERECO VARCHAR(100) NOT NULL, "
                + " TEL VARCHAR(20) NOT NULL, "
                + "PRIMARY KEY (NOME,ENDERECO,TEL))";

        createConnection();

        new Agenda();
        try {

            if (!wwdChk4Table(conn)) {
                System.out.println(" . . . . creating table CADASTRO");
                stmt.execute(createString);
            }

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();

        }

    }

    public void cadastrar() {

        if(nome1.getText().equals("") || end1.getText().equals("") || fone1.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Existem campo(s) vazio(s)!");
        }
        else {
            try {
                stmt.execute("INSERT INTO CADASTRO VALUES ('" + nome1.getText() + "', '"
                        + end1.getText() + "', '" + fone1.getText() + "')");

                JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            }

            nome1.setText("");
            end1.setText("");
            fone1.setText("");
        }

    }

    public void procurar() {

        boolean achou = false;

        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM CADASTRO WHERE lower(NOME) = lower('"
                    + nome1.getText() + "') and lower(ENDERECO) = lower('" + end1.getText() + "') and lower(TEL) = lower('"
                    + fone1.getText() + "')");


            while(rs.next()) {
                if (nome1.getText().equalsIgnoreCase(rs.getString("NOME"))
                        && fone1.getText().equalsIgnoreCase(rs.getString("TEL"))
                        && end1.getText().equalsIgnoreCase(rs.getString("ENDERECO"))) {

                    JOptionPane.showMessageDialog(null, "Cadastro existente:\n\nNome = "
                            + rs.getString("NOME") + "\nTelefone = " + rs.getString("TEL")
                            + "\nEndereço = " + rs.getString("ENDERECO") + "\n");

                    achou = true;


                }
            }
            if(achou == false){
                JOptionPane.showMessageDialog(null, "Cadastro inexistente", "ERRO", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }


    }

    public void deletar() {

        boolean achou = false;

        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM CADASTRO WHERE lower(NOME) = lower('"
                    + nome1.getText() + "') and lower(ENDERECO) = lower('" + end1.getText() + "') and lower(TEL) = lower('"
                    + fone1.getText() + "')");


            while(rs.next()){
                if (nome1.getText().equalsIgnoreCase(rs.getString("NOME"))
                        && fone1.getText().equalsIgnoreCase(rs.getString("TEL"))
                        && end1.getText().equalsIgnoreCase(rs.getString("ENDERECO"))) {

                    stmt.executeUpdate("DELETE FROM CADASTRO WHERE lower(NOME) = lower('"
                            + nome1.getText() + "') and lower(ENDERECO) = lower('" + end1.getText() + "') and lower(TEL) = lower('"
                            + fone1.getText() + "')");

                    JOptionPane.showMessageDialog(null, "Removido com sucesso!");

                    achou = true;
                }
            }

            if(achou == false){
                JOptionPane.showMessageDialog(null, "Cadastro inexistente", "ERRO", JOptionPane.ERROR_MESSAGE);
            }

        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }

        nome1.setText("");
        end1.setText("");
        fone1.setText("");

    }

    public void listar () {
        String lista="";

        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM CADASTRO");

            while(rs.next()) {
                lista += rs.getString("NOME") + " mora na " + rs.getString("ENDERECO")
                        + " Tel: " + rs.getString("TEL") + "\n";
                }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }

        JOptionPane.showMessageDialog(null, lista);

    }
}

