package com.company;


import java.awt.*;

import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;
import java.util.Calendar;




public class Menu extends JFrame {


    private final ArrayList<Customer> customerList = new ArrayList<>();
    private int position = 0;
    private String password;
    private Customer customer = null;
    private CustomerAccount acc = new CustomerAccount();
    JFrame MainFrame, secondFrame;
    JLabel firstNameLabel, surnameLabel, pPPSLabel, dOBLabel;
    JTextField firstNameTextField, surnameTextField, pPSTextField, dOBTextField;
    JLabel customerIDLabel, passwordLabel;
    JTextField customerIDTextField, passwordTextField;
    Container content;
    Customer e;


    JPanel panel2;
    JButton add;
    String PPS, firstName, surname, DOB, CustomerID;


    JButton returnButton11 = new JButton("Return");
    JButton returnButton12 = new JButton("Return");
    JButton returnButton13 = new JButton("Return");
    JTextArea textArea = new JTextArea(40, 20);
    JPanel textPanel = new JPanel();
    JPanel boxPanel = new JPanel();
    String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());


    public static void main(String[] args) {
        Menu driver = new Menu();
        driver.menuStart();
    }

    public void menuStart() {
		   /*The menuStart method asks the user if they are a new customer, an existing customer or an admin. It will then start the create customer process
		  if they are a new customer, or will ask them to log in if they are an existing customer or admin.*/


        MainFrame = new JFrame("User Type");
        MainFrame.setSize(400, 300);
        MainFrame.setLocation(200, 200);
        MainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        JPanel userTypePanel = new JPanel();
        final ButtonGroup userType = new ButtonGroup();
        JRadioButton radioButton;
        userTypePanel.add(radioButton = new JRadioButton("Existing Customer"));
        radioButton.setActionCommand("Customer");
        userType.add(radioButton);

        userTypePanel.add(radioButton = new JRadioButton("Administrator"));
        radioButton.setActionCommand("Administrator");
        userType.add(radioButton);

        userTypePanel.add(radioButton = new JRadioButton("New Customer"));
        radioButton.setActionCommand("New Customer");
        userType.add(radioButton);

        JPanel continuePanel = new JPanel();
        JButton continueButton = new JButton("Continue");
        continuePanel.add(continueButton);

        Container content = MainFrame.getContentPane();
        content.setLayout(new GridLayout(2, 1));
        content.add(userTypePanel);
        content.add(continuePanel);



        continueButton.addActionListener(ae -> {
            String user = userType.getSelection().getActionCommand();

            //if user selects NEW CUSTOMER--------------------------------------------------------------------------------------
            if (user.equals("New Customer")) {
                MainFrame.dispose();
                secondFrame = new JFrame("Create New Customer");
                secondFrame.setSize(400, 300);
                secondFrame.setLocation(200, 200);
                secondFrame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent we) {
                        System.exit(0);
                    }
                });
                Container content1 = secondFrame.getContentPane();
                content1.setLayout(new BorderLayout());

                firstNameLabel = new JLabel("First Name:", SwingConstants.RIGHT);
                surnameLabel = new JLabel("Surname:", SwingConstants.RIGHT);
                pPPSLabel = new JLabel("PPS Number:", SwingConstants.RIGHT);
                dOBLabel = new JLabel("Date of birth", SwingConstants.RIGHT);
                firstNameTextField = new JTextField(20);
                surnameTextField = new JTextField(20);
                pPSTextField = new JTextField(20);
                dOBTextField = new JTextField(20);
                JPanel panel = new JPanel(new GridLayout(6, 2));
                add(panel);

                panel2 = new JPanel();
                add = new JButton("Add");

                add.addActionListener(e -> {


                    PPS = pPSTextField.getText();
                    firstName = firstNameTextField.getText();
                    surname = surnameTextField.getText();
                    DOB = dOBTextField.getText();
                    password = "";

                    CustomerID = "ID" + PPS;


                    add.addActionListener(e1 -> {
                        secondFrame.dispose();

                        boolean executeAgain = true;
                        while (executeAgain) {
                            password = JOptionPane.showInputDialog(MainFrame, "Enter 7 character Password;");

                            if (password.length() != 7)//Making sure password is 7 characters
                                JOptionPane.showMessageDialog(null, null, "Password must be 7 charatcers long", JOptionPane.ERROR_MESSAGE);
                            else {
                                executeAgain = false;
                            }
                        }


//Removed Customer Account
                        ArrayList<CustomerAccount> accounts = new ArrayList<>();
                        Customer customer = new Customer(PPS, surname, firstName, DOB, CustomerID, password, accounts);

                        customerList.add(customer);

                        JOptionPane.showMessageDialog(MainFrame, "CustomerID = " + CustomerID + "\n Password = " + password, "Customer created.", JOptionPane.INFORMATION_MESSAGE);
                        menuStart();
                        MainFrame.dispose();
                    });
                });
                JButton cancel = new JButton("Cancel");
                cancel.addActionListener(e -> {
                    secondFrame.dispose();
                    menuStart();
                });

                panel2.add(add);
                panel2.add(cancel);

                content1.add(panel, BorderLayout.CENTER);
                content1.add(panel2, BorderLayout.SOUTH);

                secondFrame.setVisible(true);

            }


            //------------------------------------------------------------------------------------------------------------------

            //if user select ADMIN----------------------------------------------------------------------------------------------
            if (user.equals("Administrator")) {
                boolean executeAgain = true, userAuthenticated = true;
                boolean cont = false;
                while (executeAgain) {
                    Object adminUsername = JOptionPane.showInputDialog(MainFrame, "Enter Administrator Username:");

                    if (!adminUsername.equals("admin"))//search admin list for admin with matching admin username
                    {
                        int usersReply = JOptionPane.showConfirmDialog(null, null, "Incorrect Username. Try again?", JOptionPane.YES_NO_OPTION);

                        if (usersReply == JOptionPane.NO_OPTION) {
                            secondFrame.dispose();
                            executeAgain = false;
                            userAuthenticated = false;
                            menuStart();
                        }
                    } else {
                        executeAgain = false;
                    }
                }

                while (userAuthenticated) {
                    Object adminPassword = JOptionPane.showInputDialog(MainFrame, "Enter Administrator Password;");

                    if (!adminPassword.equals("admin11"))//search admin list for admin with matching admin password
                    {
                        int usersReply = JOptionPane.showConfirmDialog(null, null, "Incorrect Password. Try again?", JOptionPane.YES_NO_OPTION);

                        if (usersReply == JOptionPane.NO_OPTION) {
                            secondFrame.dispose();
                            userAuthenticated = false;
                            menuStart();
                        }
                    } else {
                        userAuthenticated = false;
                        cont = true;
                    }
                }

                if (cont) {
                    secondFrame.dispose();

                    admin();
                }
            }
            //----------------------------------------------------------------------------------------------------------------


            //if user selects CUSTOMER ----------------------------------------------------------------------------------------
            if (user.equals("Customer")) {
                boolean executeAgain = true, userAuthenticated = true;
                boolean cont = false;
                boolean exists = false;
                Customer customer = null;
                while (executeAgain) {
                    Object customerID = JOptionPane.showInputDialog(MainFrame, "Enter Customer ID:");

                    for (Customer aCustomer : customerList) {

                        if (aCustomer.getCustomerID().equals(customerID))//search customer list for matching customer ID
                        {
                            exists = true;
                            customer = aCustomer;
                        }
                    }

                    //If statement simplified
                    if (!exists) {
                        int usersReply = JOptionPane.showConfirmDialog(null, null, "User not exists. Try again?", JOptionPane.YES_NO_OPTION);

                        if (usersReply == JOptionPane.NO_OPTION) {
                            MainFrame.dispose();
                            executeAgain = false;
                            userAuthenticated = false;
                            menuStart();
                        }
                    } else {
                        executeAgain = false;
                    }

                }

                while (userAuthenticated) {
                    Object customerPassword = JOptionPane.showInputDialog(MainFrame, "Enter Customer Password;");

                    if (!customer.getPassword().equals(customerPassword))//check if custoemr password is correct
                    {
                        int usersReply = JOptionPane.showConfirmDialog(null, null, "Incorrect password. Try again?", JOptionPane.YES_NO_OPTION);

                        if (usersReply == JOptionPane.NO_OPTION) {
                            MainFrame.dispose();
                            userAuthenticated = false;
                            menuStart();
                        }
                    } else {
                        userAuthenticated = false;
                        cont = true;
                    }
                }

                if (cont) {
                    MainFrame.dispose();
                    customer();
                }
            }
            //-----------------------------------------------------------------------------------------------------------------------
        });
        MainFrame.setVisible(true);
    }

    public void add(JPanel panel) {
        initJPanel(panel);
    }

    public void initJPanel(JPanel panel) {
        panel.add(firstNameLabel);
        panel.add(firstNameTextField);
        panel.add(surnameLabel);
        panel.add(surnameTextField);
        panel.add(pPPSLabel);
        panel.add(pPSTextField);
        panel.add(dOBLabel);
        panel.add(dOBTextField);
    }


    public void admin() {
        dispose();

        MainFrame = new JFrame("Administrator Menu");
        MainFrame.setSize(400, 400);
        MainFrame.setLocation(200, 200);
        MainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        MainFrame.setVisible(true);

        JPanel deleteCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton deleteCustomer = new JButton("Delete Customer");
        deleteCustomer.setPreferredSize(new Dimension(250, 20));
        deleteCustomerPanel.add(deleteCustomer);

        JPanel deleteAccountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton deleteAccount = new JButton("Delete Account");
        deleteAccount.setPreferredSize(new Dimension(250, 20));
        deleteAccountPanel.add(deleteAccount);

        JPanel bankChargesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton bankChargesButton = new JButton("Apply Bank Charges");
        bankChargesButton.setPreferredSize(new Dimension(250, 20));
        bankChargesPanel.add(bankChargesButton);

        JPanel interestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton interestButton = new JButton("Apply Interest");
        interestPanel.add(interestButton);
        interestButton.setPreferredSize(new Dimension(250, 20));

        JPanel editCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editCustomerButton = new JButton("Edit existing Customer");
        editCustomerPanel.add(editCustomerButton);
        editCustomerButton.setPreferredSize(new Dimension(250, 20));

        JPanel navigatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton navigateButton = new JButton("Navigate Customer Collection");
        navigatePanel.add(navigateButton);
        navigateButton.setPreferredSize(new Dimension(250, 20));

        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton summaryButton = new JButton("Display Summary Of All Accounts");
        summaryPanel.add(summaryButton);
        summaryButton.setPreferredSize(new Dimension(250, 20));

        JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton accountButton = new JButton("Add an Account to a Customer");
        accountPanel.add(accountButton);
        accountButton.setPreferredSize(new Dimension(250, 20));

        JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton returnButton = new JButton("Exit Admin Menu");
        returnPanel.add(returnButton);

        JLabel label1 = new JLabel("Please select an option");

        content = MainFrame.getContentPane();
        content.setLayout(new GridLayout(9, 1));
        content.add(label1);
        content.add(accountPanel);
        content.add(bankChargesPanel);
        content.add(interestPanel);
        content.add(editCustomerPanel);
        content.add(navigatePanel);
        content.add(summaryPanel);
        content.add(deleteCustomerPanel);
        //	content.add(deleteAccountPanel);
        content.add(returnPanel);


        bankChargesButton.addActionListener(ae -> {

            boolean executeAgain = true;

            boolean exists = false;

            if (customerList.isEmpty()) {
                JOptionPane.showMessageDialog(MainFrame, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                MainFrame.dispose();
                admin();

            } else {
                while (executeAgain) {
                    Object customerID = JOptionPane.showInputDialog(MainFrame, "Customer ID of Customer You Wish to Apply Charges to:");

                    for (Customer aCustomer : customerList) {

                        if (aCustomer.getCustomerID().equals(customerID)) {
                            exists = true;
                            customer = aCustomer;
                            executeAgain = false;
                        }
                    }

                    //Simplified to If not exists
                    if (!exists) {
                        int usersReply = JOptionPane.showConfirmDialog(null, null, "User not exists. Try again?", JOptionPane.YES_NO_OPTION);

                        if (usersReply == JOptionPane.NO_OPTION) {
                            MainFrame.dispose();
                            executeAgain = false;

                            admin();
                        }
                    } else {
                        JComboBox<String> box = new JComboBox<>();
                        adminMenu(box);


                        boxPanel.add(box);

                        JPanel buttonPanel = new JPanel();
                        JButton continueButton = new JButton("Apply Charge");

                        addReturnButton(buttonPanel, continueButton, returnButton);


                        if (customer.getAccounts().isEmpty()) {
                            JOptionPane.showMessageDialog(MainFrame, "This customer has no accounts! \n The admin must add acounts to this customer.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                            MainFrame.dispose();
                            admin();
                        } else {

                            for (int i = 0; i < customer.getAccounts().size(); i++) {
                                if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
                                    acc = customer.getAccounts().get(i);
                                }
                            }


                            continueButton.addActionListener(ae1 -> {
                                String euro = "\u20ac";


                                if (acc instanceof CustomerDepositAccount) {


                                    JOptionPane.showMessageDialog(MainFrame, "25" + euro + " deposit account fee applied.", "", JOptionPane.INFORMATION_MESSAGE);
                                    acc.setBalance(acc.getBalance() - 25);
                                    JOptionPane.showMessageDialog(MainFrame, "New balance = " + acc.getBalance(), "Success!", JOptionPane.INFORMATION_MESSAGE);
                                }

                                if (acc instanceof CustomerCurrentAccount) {


                                    JOptionPane.showMessageDialog(MainFrame, "15" + euro + " current account fee aplied.", "", JOptionPane.INFORMATION_MESSAGE);
                                    acc.setBalance(acc.getBalance() - 25);
                                    JOptionPane.showMessageDialog(MainFrame, "New balance = " + acc.getBalance(), "Success!", JOptionPane.INFORMATION_MESSAGE);
                                }


                                MainFrame.dispose();
                                admin();
                            });

                            //Replaced with lambda
                            returnButton11.addActionListener(ae12 -> {
                                MainFrame.dispose();
                                menuStart();
                            });

                        }
                    }
                }
            }


        });


        interestButton.addActionListener(ae -> {

            boolean executeAgain = true;

            boolean exists = false;

            if (customerList.isEmpty()) {
                JOptionPane.showMessageDialog(MainFrame, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                MainFrame.dispose();
                admin();

            } else {
                while (executeAgain) {
                    Object customerID = JOptionPane.showInputDialog(MainFrame, "Customer ID of Customer You Wish to Apply Interest to:");

                    for (Customer aCustomer : customerList) {

                        if (aCustomer.getCustomerID().equals(customerID)) {
                            exists = true;
                            customer = aCustomer;
                            executeAgain = false;
                        }
                    }

                    if (!exists) {
                        int usersReply = JOptionPane.showConfirmDialog(null, null, "User not exists. Try again?", JOptionPane.YES_NO_OPTION);

                        if (usersReply == JOptionPane.NO_OPTION) {
                            MainFrame.dispose();
                            executeAgain = false;

                            admin();
                        }
                    } else {
                        JComboBox<String> box = new JComboBox<>();
                        adminMenu(box);


                        JPanel boxPanel = new JPanel();

                        JLabel label = new JLabel("Select an account to apply interest to:");
                        boxPanel.add(label);
                        boxPanel.add(box);

                        JPanel buttonPanel = new JPanel();
                        JButton continueButton = new JButton("Apply Interest");

                        addReturnButton(buttonPanel, continueButton, returnButton12);


                        if (customer.getAccounts().isEmpty()) {
                            JOptionPane.showMessageDialog(MainFrame, "This customer has no accounts! \n The admin must add acounts to this customer.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                            MainFrame.dispose();
                            admin();
                        } else {

                            for (int i = 0; i < customer.getAccounts().size(); i++) {
                                if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
                                    acc = customer.getAccounts().get(i);
                                }
                            }


                            continueButton.addActionListener(ae13 -> {
                                String euro = "\u20ac";


                                double interest;
                                boolean executeAgain1 = true;

                                while (executeAgain1) {
                                    String interestString = JOptionPane.showInputDialog(MainFrame, "Enter interest percentage you wish to apply: \n NOTE: Please enter a numerical value. (with no percentage sign) \n E.g: If you wish to apply 8% interest, enter '8'");
                                    if (isNumeric(interestString)) {

                                        interest = Double.parseDouble(interestString);
                                        executeAgain1 = false;

                                        acc.setBalance(acc.getBalance() + (acc.getBalance() * (interest / 100)));

                                        JOptionPane.showMessageDialog(MainFrame, interest + "% interest applied. \n new balance = " + acc.getBalance() + euro, "Success!", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(MainFrame, "You must enter a numerical value!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                                    }


                                }

                                MainFrame.dispose();
                                admin();
                            });


                            returnButton12.addActionListener(ae14 -> {
                                MainFrame.dispose();
                                menuStart();
                            });

                        }
                    }
                }
            }

        });


        editCustomerButton.addActionListener(ae -> {

            boolean executeAgain = true;

            boolean exists = false;

            if (customerList.isEmpty()) {
                JOptionPane.showMessageDialog(MainFrame, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                MainFrame.dispose();
                admin();

            } else {

                while (executeAgain) {
                    Object customerID = JOptionPane.showInputDialog(MainFrame, "Enter Customer ID:");

                    for (Customer aCustomer : customerList) {

                        if (aCustomer.getCustomerID().equals(customerID)) {
                            exists = true;
                            customer = aCustomer;
                        }
                    }

                    if (!exists) {

                        int usersReply = JOptionPane.showConfirmDialog(null, null, "User not exists. Try again?", JOptionPane.YES_NO_OPTION);

                        if (usersReply == JOptionPane.NO_OPTION) {
                            MainFrame.dispose();
                            executeAgain = false;

                            admin();
                        }
                    } else {
                        executeAgain = false;
                    }

                }

                MainFrame.dispose();

                MainFrame.dispose();
                MainFrame = new JFrame("Administrator Menu");
                MainFrame.setSize(400, 300);
                MainFrame.setLocation(200, 200);
                MainFrame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent we) {
                        System.exit(0);
                    }
                });

                initLabels();

                JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

                JPanel cancelPanel = new JPanel();

                add(textPanel);
                textPanel.add(customerIDLabel);
                textPanel.add(customerIDTextField);
                textPanel.add(passwordLabel);
                textPanel.add(passwordTextField);

                firstNameTextField.setText(customer.getFirstName());
                surnameTextField.setText(customer.getSurname());
                pPSTextField.setText(customer.getPPS());
                dOBTextField.setText(customer.getDOB());
                customerIDTextField.setText(customer.getCustomerID());
                passwordTextField.setText(customer.getPassword());




                JButton saveButton = new JButton("Save");
                JButton cancelButton = new JButton("Exit");

                cancelPanel.add(cancelButton, BorderLayout.SOUTH);
                cancelPanel.add(saveButton, BorderLayout.SOUTH);
                	content.removeAll();
                Container content = MainFrame.getContentPane();
                content.setLayout(new GridLayout(2, 1));
                content.add(textPanel, BorderLayout.NORTH);
                content.add(cancelPanel, BorderLayout.SOUTH);

                MainFrame.setContentPane(content);
                MainFrame.setSize(340, 350);
                MainFrame.setLocation(200, 200);
                MainFrame.setVisible(true);
                MainFrame.setResizable(false);


                saveButton.addActionListener(ae15 -> {

                    customer.setFirstName(firstNameTextField.getText());
                    customer.setSurname(surnameTextField.getText());
                    customer.setPPS(pPSTextField.getText());
                    customer.setDOB(dOBTextField.getText());
                    customer.setCustomerID(customerIDTextField.getText());
                    customer.setPassword(passwordTextField.getText());

                    JOptionPane.showMessageDialog(null, "Changes Saved.");
                });


                cancelButton.addActionListener(ae16 -> {
                    MainFrame.dispose();

                    admin();
                });
            }
        });


        summaryButton.addActionListener(ae -> {
            MainFrame.dispose();


            MainFrame = new JFrame("Summary of Transactions");
            MainFrame.setSize(400, 700);
            MainFrame.setLocation(200, 200);
            MainFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    System.exit(0);
                }
            });
            MainFrame.setVisible(true);

            JLabel label11 = new JLabel("Summary of all transactions: ");


            returnPanel.add(returnButton13);

            initJpanel(returnButton13, label11, textArea, textPanel);

            for (Customer value : customerList) {
                for (int b = 0; b < value.getAccounts().size(); b++) {
                    acc = value.getAccounts().get(b);
                    for (int c = 0; c < value.getAccounts().get(b).getTransactionList().size(); c++) {

                        textArea.append(acc.getTransactionList().get(c).toString());


                    }
                }
            }


            textPanel.add(textArea);
            content.removeAll();


            Container content = MainFrame.getContentPane();
            content.setLayout(new GridLayout(1, 1));
            content.add(label1);
            content.add(textPanel);
            content.add(returnPanel);


            returnButton13.addActionListener(ae17 -> {
                MainFrame.dispose();
                admin();
            });
        });


        navigateButton.addActionListener(this::addTextFields);

        accountButton.addActionListener(ae -> {
            MainFrame.dispose();

            if (customerList.isEmpty()) {
                JOptionPane.showMessageDialog(MainFrame, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                MainFrame.dispose();
                admin();
            } else {
                boolean executeAgain = true;


                boolean exists = false;

                while (executeAgain) {
                    Object customerID = JOptionPane.showInputDialog(MainFrame, "Customer ID of Customer You Wish to Add an Account to:");

                    for (Customer aCustomer : customerList) {

                        if (aCustomer.getCustomerID().equals(customerID)) {
                            exists = true;
                            customer = aCustomer;
                        }
                    }

                    if (!exists) {
                        int usersReply = JOptionPane.showConfirmDialog(null, null, "User not exists. Try again?", JOptionPane.YES_NO_OPTION);

                        if (usersReply == JOptionPane.NO_OPTION) {
                            MainFrame.dispose();
                            executeAgain = false;

                            admin();
                        }
                    } else {
                        executeAgain = false;

                        String[] choices = {"Current Account", "Deposit Account"};
                        String account = (String) JOptionPane.showInputDialog(null, "Please choose account type",
                                "Account Type", JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);

                        if (account.equals("Current Account")) {

                            boolean valid = true;
                            double balance = 0;


                            String number = "C" + (customerList.indexOf(customer) + 1) * 10 + (customer.getAccounts().size() + 1);//this simple algorithm generates the account number
                            ArrayList<AccountTransaction> transactionList = new ArrayList<>();
                            int randomPIN = (int) (Math.random() * 9000) + 1000;
                            String pin = String.valueOf(randomPIN);

                            ATMCard atm = new ATMCard(randomPIN, valid);

                            CustomerCurrentAccount current = new CustomerCurrentAccount(atm, number, balance, transactionList);

                            customer.getAccounts().add(current);
                            JOptionPane.showMessageDialog(MainFrame, "Account number = " + number + "\n PIN = " + pin, "Account created.", JOptionPane.INFORMATION_MESSAGE);

                            MainFrame.dispose();
                            admin();
                        }

                        if (account.equals("Deposit Account")) {
                            //create deposit account

                            double balance = 0, interest = 0;
                            String number = ("D" + (customerList.indexOf(customer) + 1) * 10 + (customer.getAccounts().size() + 1));//this simple algorithm generates the account number
                            ArrayList<AccountTransaction> transactionList = new ArrayList<>();

                            CustomerDepositAccount deposit = new CustomerDepositAccount(interest, number, balance, transactionList);

                            customer.getAccounts().add(deposit);
                            JOptionPane.showMessageDialog(MainFrame, "Account number = " + number, "Account created.", JOptionPane.INFORMATION_MESSAGE);

                            MainFrame.dispose();
                            admin();
                        }

                    }
                }
            }
        });

        deleteCustomer.addActionListener(ae -> {


            if (customerList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
                dispose();
                admin();
            } else {
                {
                    Object customerID = JOptionPane.showInputDialog(MainFrame, "Customer ID of Customer You Wish to Delete:");

                    for (Customer aCustomer : customerList) {

                        if (aCustomer.getCustomerID().equals(customerID)) {

                            customer = aCustomer;

                        }
                    }

                    if (customer.getAccounts().size() > 0) {
                        JOptionPane.showMessageDialog(MainFrame, "This customer has accounts. \n You must delete a customer's accounts before deleting a customer ", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        customerList.remove(customer);
                        JOptionPane.showMessageDialog(MainFrame, "Customer Deleted ", "Success.", JOptionPane.INFORMATION_MESSAGE);
                    }


                }
            }
        });

        deleteAccount.addActionListener(ae -> {
            boolean exists = true;


            {
                Object customerID = JOptionPane.showInputDialog(MainFrame, "Customer ID of Customer from which you wish to delete an account");

                for (Customer aCustomer : customerList) {

                    if (aCustomer.getCustomerID().equals(customerID)) {

                        customer = aCustomer;
                    }
                }

                if (!exists) {
                    int usersReply = JOptionPane.showConfirmDialog(null, null, "User not exists. Try again?", JOptionPane.YES_NO_OPTION);

                    if (usersReply == JOptionPane.NO_OPTION) {
                        MainFrame.dispose();

                        admin();
                    }
                } else {

                    //ADD CODE HERE

                    //Here I would make the user select an account to delete from a combo box. If the account had a balance of 0 then it would be deleted. (I do not have time to do this)
                }


            }
        });
        returnButton.addActionListener(ae -> {
            MainFrame.dispose();
            menuStart();
        });
    }

    private void setText() {
        firstNameTextField.setText(customerList.get(position).getFirstName());
        surnameTextField.setText(customerList.get(position).getSurname());
        pPSTextField.setText(customerList.get(position).getPPS());
        dOBTextField.setText(customerList.get(position).getDOB());
        customerIDTextField.setText(customerList.get(position).getCustomerID());
        passwordTextField.setText(customerList.get(position).getPassword());
    }

    private void initLabels() {
        firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
        surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
        pPPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
        dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
        customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
        passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
        firstNameTextField = new JTextField(20);
        surnameTextField = new JTextField(20);
        pPSTextField = new JTextField(20);
        dOBTextField = new JTextField(20);
        customerIDTextField = new JTextField(20);
        passwordTextField = new JTextField(20);
    }


    public void customer() {
        initJframe();


        if (e.getAccounts().size() == 0) {
            JOptionPane.showMessageDialog(MainFrame, "This customer does not have any accounts yet. \n An admin must create an account for this customer \n for them to be able to use customer functionality. ", "Oops!", JOptionPane.INFORMATION_MESSAGE);
            MainFrame.dispose();
            menuStart();
        } else {
            JPanel buttonPanel = new JPanel();
            JPanel boxPanel = new JPanel();
            JPanel labelPanel = new JPanel();

            JLabel label = new JLabel("Select Account:");
            labelPanel.add(label);

            JButton returnButton = new JButton("Return");
            buttonPanel.add(returnButton);
            JButton continueButton = new JButton("Continue");
            buttonPanel.add(continueButton);

            JComboBox<String> box = new JComboBox<>();
            for (int i = 0; i < e.getAccounts().size(); i++) {
                box.addItem(e.getAccounts().get(i).getNumber());
            }


            for (int i = 0; i < e.getAccounts().size(); i++) {
                if (e.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
                    acc = e.getAccounts().get(i);
                }
            }


            boxPanel.add(box);
            content = MainFrame.getContentPane();
            content.setLayout(new GridLayout(3, 1));
            content.add(labelPanel);
            content.add(boxPanel);
            content.add(buttonPanel);

            returnButton.addActionListener(ae -> {
                MainFrame.dispose();
                menuStart();
            });

            continueButton.addActionListener(ae -> {

                MainFrame.dispose();
                initJframe();


                JPanel statementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JButton statementButton = new JButton("Display Bank Statement");
                statementButton.setPreferredSize(new Dimension(250, 20));

                statementPanel.add(statementButton);

                JPanel lodgementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JButton lodgementButton = new JButton("Lodge money into account");
                lodgementPanel.add(lodgementButton);
                lodgementButton.setPreferredSize(new Dimension(250, 20));

                JPanel withdrawalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JButton withdrawButton = new JButton("Withdraw money from account");
                withdrawalPanel.add(withdrawButton);
                withdrawButton.setPreferredSize(new Dimension(250, 20));

                JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                JButton returnButton1 = new JButton("Exit Customer Menu");
                returnPanel.add(returnButton1);

                JLabel label1 = new JLabel("Please select an option");

                content = MainFrame.getContentPane();
                content.setLayout(new GridLayout(5, 1));
                content.add(label1);
                content.add(statementPanel);
                content.add(lodgementPanel);
                content.add(withdrawalPanel);
                content.add(returnPanel);

                statementButton.addActionListener(ae1 -> {
                    MainFrame.dispose();
                    MainFrame = new JFrame("Customer Menu");
                    MainFrame.setSize(400, 600);
                    MainFrame.setLocation(200, 200);
                    MainFrame.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent we) {
                            System.exit(0);
                        }
                    });
                    MainFrame.setVisible(true);

                    JLabel label11 = new JLabel("Summary of account transactions: ");


                    returnPanel.add(returnButton11);

                    initJpanel(returnButton11, label11, textArea, textPanel);

                    for (int i = 0; i < acc.getTransactionList().size(); i++) {
                        textArea.append(acc.getTransactionList().get(i).toString());

                    }

                    textPanel.add(textArea);
                    content.removeAll();


                    Container content = MainFrame.getContentPane();
                    content.setLayout(new GridLayout(1, 1));

                    content.add(textPanel);


                    returnButton11.addActionListener(ae11 -> {
                        MainFrame.dispose();
                        customer();
                    });
                });

                lodgementButton.addActionListener(ae12 -> {
                    double balance = 0;
                    boolean on = true;
                    changeBalance(balance, on);


                    {
                        String balanceTest = JOptionPane.showInputDialog(MainFrame, "Enter amount you wish to lodge:");//the isNumeric method tests to see if the string entered was numeric.
                        if (isNumeric(balanceTest)) {

                            balance = Double.parseDouble(balanceTest);


                        } else {
                            JOptionPane.showMessageDialog(MainFrame, "You must enter a numerical value!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                        }


                        String euro = "\u20ac";
                        acc.setBalance(acc.getBalance() + balance);


                        String type = "Lodgement";
                        double amount = balance;


                        AccountTransaction transaction = new AccountTransaction(date, type, amount);
                        acc.getTransactionList().add(transaction);

                        JOptionPane.showMessageDialog(MainFrame, balance + euro + " added do you account!", "Lodgement", JOptionPane.INFORMATION_MESSAGE);
                        JOptionPane.showMessageDialog(MainFrame, "New balance = " + acc.getBalance() + euro, "Lodgement", JOptionPane.INFORMATION_MESSAGE);
                    }

                });

                withdrawButton.addActionListener(ae13 -> {
                    double withdraw = 0;
                    boolean on = true;
                    changeBalance(withdraw, on);
                    //Extract method

                    if (on) {
                        String balanceTest = JOptionPane.showInputDialog(MainFrame, "Enter amount you wish to withdraw (max 500):");//the isNumeric method tests to see if the string entered was numeric.
                        if (isNumeric(balanceTest)) {

                            withdraw = Double.parseDouble(balanceTest);


                        } else {
                            JOptionPane.showMessageDialog(MainFrame, "You must enter a numerical value!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                        }
                        if (withdraw > 500) {
                            JOptionPane.showMessageDialog(MainFrame, "500 is the maximum you can withdraw at a time.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                            withdraw = 0;
                        }
                        if (withdraw > acc.getBalance()) {
                            JOptionPane.showMessageDialog(MainFrame, "Insufficient funds.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                            withdraw = 0;
                        }

                        String euro = "\u20ac";
                        acc.setBalance(acc.getBalance() - withdraw);





                        String type = "Withdraw";
                        double amount = withdraw;


                        AccountTransaction transaction = new AccountTransaction(date, type, amount);
                        acc.getTransactionList().add(transaction);


                        JOptionPane.showMessageDialog(MainFrame, withdraw + euro + " withdrawn.", "Withdraw", JOptionPane.INFORMATION_MESSAGE);
                        JOptionPane.showMessageDialog(MainFrame, "New balance = " + acc.getBalance() + euro, "Withdraw", JOptionPane.INFORMATION_MESSAGE);
                    }


                });

                returnButton1.addActionListener(ae14 -> {
                    MainFrame.dispose();
                    menuStart();
                });
            });
        }
    }

    public static boolean isNumeric(String str)  // a method that tests if a string is numeric
     {
        if (str == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    public boolean changeBalance(double val, boolean on) {
        boolean executeAgain;


        if (acc instanceof CustomerCurrentAccount) {
            int count = 3;
            int checkPin = ((CustomerCurrentAccount) acc).getAtm().getPin();
            executeAgain = true;

            while (executeAgain) {
                if (count == 0) {
                    JOptionPane.showMessageDialog(MainFrame, "Pin entered incorrectly 3 times. ATM card locked.", "Pin", JOptionPane.INFORMATION_MESSAGE);
                    ((CustomerCurrentAccount) acc).getAtm().setValid(false);
                    customer();
                    executeAgain = false;
                    on = false;
                }

                String Pin = JOptionPane.showInputDialog(MainFrame, "Enter 4 digit PIN;");
                int i = Integer.parseInt(Pin);

                if (on) {
                    if (checkPin == i) {
                        executeAgain = false;
                        JOptionPane.showMessageDialog(MainFrame, "Pin entry successful", "Pin", JOptionPane.INFORMATION_MESSAGE);

                    } else {
                        count--;
                        JOptionPane.showMessageDialog(MainFrame, "Incorrect pin. " + count + " attempts remaining.", "Pin", JOptionPane.INFORMATION_MESSAGE);

                    }

                }
                on = true;

            }


        }

        return on;
    }

    public void initJframe() {

        MainFrame = new JFrame("Customer Menu");
        MainFrame.setSize(400, 300);
        MainFrame.setLocation(200, 200);
        MainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        MainFrame.setVisible(true);

    }

    public void initJpanel(JButton returnButton, JLabel label, JTextArea textArea, JPanel textPanel) {


        textPanel.setLayout(new BorderLayout());

        textArea.setEditable(false);
        textPanel.add(label, BorderLayout.NORTH);
        textPanel.add(textArea, BorderLayout.CENTER);
        textPanel.add(returnButton, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(textArea);
        textPanel.add(scrollPane);


    }

    public void adminMenu(JComboBox<String> box) {

        MainFrame.dispose();
        MainFrame = new JFrame("Administrator Menu");
        MainFrame.setSize(400, 300);
        MainFrame.setLocation(200, 200);
        MainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        MainFrame.setVisible(true);


        for (int i = 0; i < customer.getAccounts().size(); i++) {


            box.addItem(customer.getAccounts().get(i).getNumber());
        }


        box.getSelectedItem();


    }

    public void addReturnButton(JPanel buttonPanel, JButton continueButton, JButton returnButton) {

        buttonPanel.add(continueButton);
        buttonPanel.add(returnButton);
        Container content = MainFrame.getContentPane();
        content.setLayout(new GridLayout(2, 1));

        content.add(boxPanel);
        content.add(buttonPanel);


    }

    private void addTextFields(ActionEvent ae) {
        MainFrame.dispose();

        if (customerList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
            admin();
        } else {

            JButton first, previous, next, last, cancel;
            JPanel gridPanel, buttonPanel, cancelPanel;

            Container content = getContentPane();

            content.setLayout(new BorderLayout());

            buttonPanel = new JPanel();
            gridPanel = new JPanel(new GridLayout(8, 2));
            cancelPanel = new JPanel();

            initLabels();

            first = new JButton("First");
            previous = new JButton("Previous");
            next = new JButton("Next");
            last = new JButton("Last");
            cancel = new JButton("Cancel");

            showCustomerDetails();

            firstNameTextField.setEditable(false);
            surnameTextField.setEditable(false);
            pPSTextField.setEditable(false);
            dOBTextField.setEditable(false);
            customerIDTextField.setEditable(false);
            passwordTextField.setEditable(false);

            initJPanel(gridPanel);
            gridPanel.add(customerIDLabel);
            gridPanel.add(customerIDTextField);
            gridPanel.add(passwordLabel);
            gridPanel.add(passwordTextField);

            buttonPanel.add(first);
            buttonPanel.add(previous);
            buttonPanel.add(next);
            buttonPanel.add(last);

            cancelPanel.add(cancel);

            content.add(gridPanel, BorderLayout.NORTH);
            content.add(buttonPanel, BorderLayout.CENTER);
            content.add(cancelPanel, BorderLayout.AFTER_LAST_LINE);
            first.addActionListener(ae18 -> {
                position = 0;
                showCustomerDetails();
            });

            previous.addActionListener(ae19 -> {

                if (!(position < 1)) {

                    position = position - 1;

                    setText();
                }
            });

            next.addActionListener(ae110 -> {

                if (!(position == customerList.size() - 1)) {

                    position = position + 1;

                    setText();
                }


            });

            last.addActionListener(ae111 -> {

                position = customerList.size() - 1;

                setText();
            });

            cancel.addActionListener(ae112 -> {
                dispose();
                admin();
            });
            setContentPane(content);
            setSize(400, 300);
            setVisible(true);
        }
    }

    private void showCustomerDetails() {
        firstNameTextField.setText(customerList.get(0).getFirstName());
        surnameTextField.setText(customerList.get(0).getSurname());
        pPSTextField.setText(customerList.get(0).getPPS());
        dOBTextField.setText(customerList.get(0).getDOB());
        customerIDTextField.setText(customerList.get(0).getCustomerID());
        passwordTextField.setText(customerList.get(0).getPassword());
    }
}

