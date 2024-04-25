package models;

public class Facture {
    private int id,id_transaction_id;
    private double tax,montant_ttc;

    public Facture(int id, int id_transaction_id, double tax, double montant_ttc) {
        this.id = id;
        this.id_transaction_id = id_transaction_id;
        this.tax = tax;
        this.montant_ttc = montant_ttc;
    }

    public Facture() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_transaction_id() {
        return id_transaction_id;
    }

    public void setId_transaction_id(int id_transaction_id) {
        this.id_transaction_id = id_transaction_id;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getMontant_ttc() {
        return montant_ttc;
    }

    public void setMontant_ttc(double montant_ttc) {
        this.montant_ttc = montant_ttc;
    }

    public Facture(int id_transaction_id, double tax, double montant_ttc) {
        this.id_transaction_id = id_transaction_id;
        this.tax = tax;
        this.montant_ttc = montant_ttc;
    }
}
