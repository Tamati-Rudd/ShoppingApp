package Entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * This entity maps to a product in the database (table: vxz7784products)
 * @author Tamati Rudd 18045626
 */
@Entity
@Table(name = "vxz7784products")
public class Vxz7784products implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ProdID")
    private String prodID;
    @Column(name = "ProdName")
    private String prodName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Price")
    private Float price;
    @Column(name = "InStock")
    private Integer inStock;

    public Vxz7784products() {
    }

    public Vxz7784products(String prodID) {
        this.prodID = prodID;
    }

    public String getProdID() {
        return prodID;
    }

    public void setProdID(String prodID) {
        this.prodID = prodID;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prodID != null ? prodID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vxz7784products)) {
            return false;
        }
        Vxz7784products other = (Vxz7784products) object;
        if ((this.prodID == null && other.prodID != null) || (this.prodID != null && !this.prodID.equals(other.prodID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Vxz7784products[ prodID=" + prodID + " ]";
    }
    
}
