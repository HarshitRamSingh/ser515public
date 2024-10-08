package edu.asu.ser515.threads;

class Transaction extends Thread {
    BankAccount account;
    int id;

    public Transaction(int id, BankAccount account) {
        super("Transaction #" + id);
        this.account = account;
        this.id = id;
    }

    public void run() {
        System.out.println("Transaction started #" + id);
        for (int i = 0; i < 3; i++) {
            account.deposit(id*i);
        }
    }
}

public class BankAccount {
    int     numTransactions = 0;
    int     balance = 0;

    public synchronized void deposit(int amount) {
        balance += amount;
        numTransactions++;
    }

    public synchronized int getBalance() {
        return balance;
    }
    
    public static void main(String args[]) {
        BankAccount account = new BankAccount();

        for (int i=1; i <= 3; i++) {
            Transaction trans = new Transaction(i, account);
            trans.start();
        }
	try {
	    Thread.sleep(10000);
	    System.out.println("Balance is " + account.getBalance());
	} catch (InterruptedException ie) {
	    // To test the interrupt, send an interrupt signal to the java process within 10 seconds, e.g. kill -s INT <pid>
	    System.out.println("Thread interrupted");
	    ie.printStackTrace();
	}
    }
}
