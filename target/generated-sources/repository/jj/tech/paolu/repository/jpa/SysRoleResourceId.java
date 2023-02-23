package jj.tech.paolu.repository.jpa;
// Generated 2023年2月23日 下午3:40:58 by Hibernate Tools 6.1.5.Final


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * SysRoleResourceId generated by hbm2java
 */
@Embeddable
public class SysRoleResourceId  implements java.io.Serializable {


     private long resId;
     private long roleId;

    public SysRoleResourceId() {
    }

    public SysRoleResourceId(long resId, long roleId) {
       this.resId = resId;
       this.roleId = roleId;
    }
   


    @Column(name="RES_ID", nullable=false)
    public long getResId() {
        return this.resId;
    }
    
    public void setResId(long resId) {
        this.resId = resId;
    }


    @Column(name="ROLE_ID", nullable=false)
    public long getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SysRoleResourceId) ) return false;
		 SysRoleResourceId castOther = ( SysRoleResourceId ) other; 
         
		 return (this.getResId()==castOther.getResId())
 && (this.getRoleId()==castOther.getRoleId());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + (int) this.getResId();
         result = 37 * result + (int) this.getRoleId();
         return result;
   }   


}


