package jj.tech.paolu.repository.jpa;
// Generated 2023年2月23日 下午3:40:58 by Hibernate Tools 6.1.5.Final


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * UserInfo generated by hbm2java
 */
@Entity
@Table(name="USER_INFO"
    ,schema="PUBLIC"
    ,catalog="TEST"
)
public class UserInfo  implements java.io.Serializable {


     private Long id;
     private String realName;
     private String userName;
     private String password;
     private String headImg;

    public UserInfo() {
    }

	
    public UserInfo(String realName, String userName, String password) {
        this.realName = realName;
        this.userName = userName;
        this.password = password;
    }
    public UserInfo(String realName, String userName, String password, String headImg) {
       this.realName = realName;
       this.userName = userName;
       this.password = password;
       this.headImg = headImg;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="ID", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    
    @Column(name="REAL_NAME", nullable=false, length=50)
    public String getRealName() {
        return this.realName;
    }
    
    public void setRealName(String realName) {
        this.realName = realName;
    }

    
    @Column(name="USER_NAME", nullable=false, length=50)
    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    
    @Column(name="PASSWORD", nullable=false, length=50)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    
    @Column(name="HEAD_IMG", length=50)
    public String getHeadImg() {
        return this.headImg;
    }
    
    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }




}


