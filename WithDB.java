import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;


public class WithDB extends Frame implements ActionListener {
    Connection connection;
    List list;
    TextField idfield, AuthorField, NameField, YearField;
    Button createbtn, updatebtn, deletebtn, exitbtn;

    public WithDB() {
        list = new List();
        this.connection = initSQL();
        idfield = new TextField();
        AuthorField = new TextField();
        NameField = new TextField();
        YearField = new TextField();
        createbtn = new Button("Add");
        updatebtn = new Button("Change");
        deletebtn = new Button("Remove");
        exitbtn = new Button("Exit");

        setLayout( new GridBagLayout());
        GridBagLayout g = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setLayout(g);


        gbc.gridwidth = 4;
        gbc.weightx = 100;
        gbc.weighty = 50;
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.ipadx = 10;
        add(list, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 1;

        add(idfield, gbc);
        gbc.gridx = 1;
        add(AuthorField, gbc);
        gbc.gridx = 2;
        add(NameField, gbc);
        gbc.gridx = 3;
        add(YearField, gbc);

        gbc.gridwidth = 2;
        gbc.gridy = 4;
        gbc.gridx = 0;
        add(createbtn, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 2;
        add(updatebtn, gbc);

        gbc.gridx = 3;
        add(deletebtn, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        add(exitbtn, gbc);

        createbtn.addActionListener(this);
        updatebtn.addActionListener(this);
        deletebtn.addActionListener(this);
        exitbtn.addActionListener(this);
        list.addActionListener(this);
    }

    public ArrayList<String> serializeAnswer(ResultSet resultSet) throws SQLException {
        ArrayList<String> answers = new ArrayList<>();
        String[] cols = {"id", "Author", "Name", "Year"};
        while(resultSet.next()){
            StringBuilder answer = new StringBuilder();
            for (String col : cols){
                answer.append(resultSet.getString(col) + " / ");
            }
           answers.add(answer.toString());
        }
        return answers;
    }

    public ResultSet runSQL(String sql, boolean query) throws SQLException {
        Statement statement = connection.createStatement();
        if (query){
            return statement.executeQuery(sql);
        }
        else{
            statement.execute(sql);
            fillList();
            return null;
        }
    }

    public ArrayList<String> fillList () throws SQLException {
        ResultSet resultSet =  runSQL("SELECT * FROM Books", true);
        ArrayList<String> records = serializeAnswer(resultSet);
        list.removeAll();
        for (String record: records){
            list.add(record);
        }
        return records;
    }
    public Connection initSQL(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection co = DriverManager.getConnection("jdbc:sqlite:db.sqlite3");
            System.out.println("Connected");
            return co;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void createOption() throws SQLException {
        String sql = "INSERT INTO Books VALUES (" + idfield.getText() + ", \"" + AuthorField.getText() + "\", \"" + NameField.getText() + "\", " + YearField.getText() + ");";
        System.out.println(sql);
        runSQL(sql, false);
    }

    public void deleteOption() throws SQLException {
        runSQL("DELETE FROM Books WHERE id = " + list.getSelectedItem().charAt(0), false);
    }
    public void updateOption() throws SQLException {
        list.getSelectedItem().charAt(0);
        String sql = "UPDATE Books Set id = " + idfield.getText() + ", Author = \"" + AuthorField.getText() + "\", Name = \"" + NameField.getText() + "\", Year = " + YearField.getText() + " WHERE id= " + list.getSelectedItem().charAt(0);
        System.out.println(sql);
        runSQL(sql, false);
    }


    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == createbtn){
            try {
                createOption();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (ae.getSource() == updatebtn){
            try {
                updateOption();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (ae.getSource() == deletebtn){
            try {
                deleteOption();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (ae.getSource() == exitbtn){
            System.exit(0);
        }
        if (ae.getSource() == list){
            String[] selected = list.getSelectedItem().split(" / ");
            idfield.setText(selected[0]);
            AuthorField.setText(selected[1]);
            NameField.setText(selected[2]);
            YearField.setText(selected[3]);
        }
    }

    public static void main(String[] args) throws SQLException {
        WithDB app = new WithDB();
        app.setSize(500, 200);
        app.setVisible(true);
        app.setLocation(300,300);


        ArrayList<String> allrecords = app.fillList();

        System.out.println(allrecords);

    }

}
