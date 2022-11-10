package Entities;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * This entity maps to an order in the database (table: vxz7784orders)
 * The table this entity maps to has 2 foreign key relationships:
 * 1. username, with vxz7784users
 * 2. ProdID, with vxz7784products
 * @author Tamati Rudd 18045626
 */
@Entity
@Table(name = "vxz7784orders")
public class Vxz7784orders implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "OrderID")
    private String orderID;
    @Basic(optional = false)
    @Column(name = "username")
    @ManyToOne private String username;
    @Basic(optional = false)
    @Column(name = "ProdID")
    @ManyToOne private String prodID;
    @Column(name = "Quantity")
    private Integer quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Total")
    private Float total;

    public Vxz7784orders() {
    }

    public Vxz7784orders(String orderID) {
        this.orderID = orderID;
    }

    public Vxz7784orders(String orderID, String username, String prodID) {
        this.orderID = orderID;
        this.username = username;
        this.prodID = prodID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProdID() {
        return prodID;
    }

    public void setProdID(String prodID) {
        this.prodID = prodID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderID != null ? orderID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vxz7784orders)) {
            return false;
        }
        Vxz7784orders other = (Vxz7784orders) object;
        if ((this.orderID == null && other.orderID != null) || (this.orderID != null && !this.orderID.equals(other.orderID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Vxz7784orders[ orderID=" + orderID + " ]";
    }
    
}
