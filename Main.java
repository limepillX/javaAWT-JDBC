import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Main extends Frame implements ActionListener {

    String selectedlist;

    List list1, list2;
    CheckboxGroup checkboxes;
    TextField searchfield, crudfield;
    Button createbtn, updatebtn, deletebtn, searchbtn, exitbtn;

    public Main() {
        list1 = new List();
        list1.setMultipleMode(true);

        list2 = new List();
        list2.setMultipleMode(true);
        checkboxes = new CheckboxGroup();
        Checkbox firstcheck = new Checkbox("First list",true,checkboxes);
        Checkbox secondcheck = new Checkbox("Second list",false,checkboxes);
        searchfield =  new TextField();
        crudfield = new TextField();
        createbtn = new Button("Add");
        updatebtn = new Button("Change");
        deletebtn = new Button("Remove");
        searchbtn = new Button("Search");
        exitbtn = new Button("Exit");

        setLayout( new GridBagLayout());
        GridBagLayout g = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setLayout(g);

        gbc.gridy = 0;
        gbc.gridx = 0;
        add(firstcheck, gbc);
        gbc.gridx = 1;
        add(secondcheck, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        add(searchfield, gbc);
        gbc.gridx = 3;
        add(searchbtn, gbc);

        gbc.gridwidth = 2;
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.ipadx = 10;
        add(list1, gbc);

        gbc.gridx = 2;
        add(list2, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        add(crudfield, gbc);

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

        list1.add("Test1");
        list2.add("Test2");

        createbtn.addActionListener(this);
        updatebtn.addActionListener(this);
        searchbtn.addActionListener(this);
        deletebtn.addActionListener(this);
        exitbtn.addActionListener(this);

    }

    public void deselectAll(){
        List[] lists = {list1, list2};

        for(List list : lists){
            for (int i = 0; i < list.getItemCount(); i++) {
                list.deselect(i);
            }
        }

    }

    public List getSelectedChoice(){
        switch (checkboxes.getSelectedCheckbox().getLabel()) {
            case "First list" -> {return list1;}
            case "Second list" -> {return list2;}
        }
        return list1;
    }

    public void createOption(){
        if (!crudfield.getText().trim().equals("")){
            getSelectedChoice().add(crudfield.getText());
        }
    }

    public void deleteOption(){
        List currentlist = getSelectedChoice();
        currentlist.remove(currentlist.getSelectedIndex());
    }
    public void updateOption(){
        List currentlist = getSelectedChoice();
        int[] selected = currentlist.getSelectedIndexes();

        for (int i : selected){
            currentlist.remove(i);
            currentlist.add(crudfield.getText(), i);
            currentlist.select(i);
        }
    }
    public void searchOption(){
        List currentlist = getSelectedChoice();

        deselectAll();
        for (int i = 0; i < currentlist.getItemCount(); i++){
            if (Objects.equals(currentlist.getItem(i), searchfield.getText())){
                currentlist.select(i);
            }
        }
    }


    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == createbtn){
            createOption();
        }
        if (ae.getSource() == updatebtn){
            updateOption();
        }
        if (ae.getSource() == deletebtn){
            deleteOption();
        }
        if (ae.getSource() == searchbtn){
            searchOption();
        }
        if (ae.getSource() == exitbtn){
            System.exit(0);
        }
    }


    public static void main(String[] args) {
    Main app = new Main();
    app.setSize(500, 250);
    app.setVisible(true);
    app.setLocation(300,300);

    }

}