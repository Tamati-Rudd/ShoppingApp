package Entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * This entity maps to a user/account in the database (table: vxz7784users)
 * Also used as a request bean to confirm successful account creation
 * @author Tamati Rudd 18045626
 */
@Entity
@Table(name = "vxz7784users")
public class Vxz7784users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "balance")
    private Float balance;

    public Vxz7784users() {
    }

    public Vxz7784users(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vxz7784users)) {
            return false;
        }
        Vxz7784users other = (Vxz7784users) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "$"+ String.format("%.2f", getBalance());
    }
    
}
