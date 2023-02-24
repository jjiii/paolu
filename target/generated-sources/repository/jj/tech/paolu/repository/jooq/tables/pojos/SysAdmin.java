/*
 * This file is generated by jOOQ.
 */
package jj.tech.paolu.repository.jooq.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SysAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String realname;
    private String username;
    private String password;
    private Byte isLocked;

    public SysAdmin() {}

    public SysAdmin(SysAdmin value) {
        this.id = value.id;
        this.realname = value.realname;
        this.username = value.username;
        this.password = value.password;
        this.isLocked = value.isLocked;
    }

    public SysAdmin(
        Long id,
        String realname,
        String username,
        String password,
        Byte isLocked
    ) {
        this.id = id;
        this.realname = realname;
        this.username = username;
        this.password = password;
        this.isLocked = isLocked;
    }

    /**
     * Getter for <code>PUBLIC.SYS_ADMIN.ID</code>.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>PUBLIC.SYS_ADMIN.ID</code>.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>PUBLIC.SYS_ADMIN.REALNAME</code>.
     */
    public String getRealname() {
        return this.realname;
    }

    /**
     * Setter for <code>PUBLIC.SYS_ADMIN.REALNAME</code>.
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * Getter for <code>PUBLIC.SYS_ADMIN.USERNAME</code>.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Setter for <code>PUBLIC.SYS_ADMIN.USERNAME</code>.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for <code>PUBLIC.SYS_ADMIN.PASSWORD</code>.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Setter for <code>PUBLIC.SYS_ADMIN.PASSWORD</code>.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for <code>PUBLIC.SYS_ADMIN.IS_LOCKED</code>.
     */
    public Byte getIsLocked() {
        return this.isLocked;
    }

    /**
     * Setter for <code>PUBLIC.SYS_ADMIN.IS_LOCKED</code>.
     */
    public void setIsLocked(Byte isLocked) {
        this.isLocked = isLocked;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final SysAdmin other = (SysAdmin) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.realname == null) {
            if (other.realname != null)
                return false;
        }
        else if (!this.realname.equals(other.realname))
            return false;
        if (this.username == null) {
            if (other.username != null)
                return false;
        }
        else if (!this.username.equals(other.username))
            return false;
        if (this.password == null) {
            if (other.password != null)
                return false;
        }
        else if (!this.password.equals(other.password))
            return false;
        if (this.isLocked == null) {
            if (other.isLocked != null)
                return false;
        }
        else if (!this.isLocked.equals(other.isLocked))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.realname == null) ? 0 : this.realname.hashCode());
        result = prime * result + ((this.username == null) ? 0 : this.username.hashCode());
        result = prime * result + ((this.password == null) ? 0 : this.password.hashCode());
        result = prime * result + ((this.isLocked == null) ? 0 : this.isLocked.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SysAdmin (");

        sb.append(id);
        sb.append(", ").append(realname);
        sb.append(", ").append(username);
        sb.append(", ").append(password);
        sb.append(", ").append(isLocked);

        sb.append(")");
        return sb.toString();
    }
}