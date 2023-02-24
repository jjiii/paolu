package jj.tech.paolu.repository.jpa;
// Generated 2023年2月24日 上午9:45:43 by Hibernate Tools 6.1.5.Final


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * SysRole generated by hbm2java
 */
@Entity
@Table(name="SYS_ROLE"
    ,schema="PUBLIC"
    ,catalog="TEST"
)
public class SysRole  implements java.io.Serializable {


     private Long id;
     private Long parentid;
     private String name;
     private String state;
     private String department;

    public SysRole() {
    }

	
    public SysRole(String name, String department) {
        this.name = name;
        this.department = department;
    }
    public SysRole(Long parentid, String name, String state, String department) {
       this.parentid = parentid;
       this.name = name;
       this.state = state;
       this.department = department;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="ID", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    
    @Column(name="PARENTID")
    public Long getParentid() {
        return this.parentid;
    }
    
    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    
    @Column(name="NAME", nullable=false, length=50)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="STATE", length=3)
    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }

    
    @Column(name="DEPARTMENT", nullable=false, length=50)
    public String getDepartment() {
        return this.department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }




}

