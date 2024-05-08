package models;

public class Contact {
    private int id;
    private int ownerId;
    private String contactName;
    private String contactAccount;

    public Contact() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactAccount() {
        return contactAccount;
    }

    public void setContactAccount(String contactAccount) {
        this.contactAccount = contactAccount;
    }

    public int getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(int isArchived) {
        this.isArchived = isArchived;
    }

    public Contact(int ownerAccount, String contactName, String contactAccount, int isArchived) {
        this.ownerId = ownerAccount;
        this.contactName = contactName;
        this.contactAccount = contactAccount;
        this.isArchived = isArchived;
    }

    public Contact(int id, int ownerAccount, String contactName, String contactAccount, int isArchived) {
        this.id = id;
        this.ownerId = ownerAccount;
        this.contactName = contactName;
        this.contactAccount = contactAccount;
        this.isArchived = isArchived;
    }

    private int isArchived;
}
