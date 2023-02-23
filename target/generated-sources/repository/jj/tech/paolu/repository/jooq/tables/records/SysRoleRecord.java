/*
 * This file is generated by jOOQ.
 */
package jj.tech.paolu.repository.jooq.tables.records;


import jj.tech.paolu.repository.jooq.tables.SysRole;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SysRoleRecord extends UpdatableRecordImpl<SysRoleRecord> implements Record5<Long, Long, String, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>PUBLIC.SYS_ROLE.ID</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.SYS_ROLE.ID</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>PUBLIC.SYS_ROLE.PARENTID</code>.
     */
    public void setParentid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.SYS_ROLE.PARENTID</code>.
     */
    public Long getParentid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>PUBLIC.SYS_ROLE.NAME</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.SYS_ROLE.NAME</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>PUBLIC.SYS_ROLE.STATE</code>.
     */
    public void setState(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.SYS_ROLE.STATE</code>.
     */
    public String getState() {
        return (String) get(3);
    }

    /**
     * Setter for <code>PUBLIC.SYS_ROLE.DEPARTMENT</code>.
     */
    public void setDepartment(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>PUBLIC.SYS_ROLE.DEPARTMENT</code>.
     */
    public String getDepartment() {
        return (String) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, Long, String, String, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Long, Long, String, String, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return SysRole.SYS_ROLE.ID;
    }

    @Override
    public Field<Long> field2() {
        return SysRole.SYS_ROLE.PARENTID;
    }

    @Override
    public Field<String> field3() {
        return SysRole.SYS_ROLE.NAME;
    }

    @Override
    public Field<String> field4() {
        return SysRole.SYS_ROLE.STATE;
    }

    @Override
    public Field<String> field5() {
        return SysRole.SYS_ROLE.DEPARTMENT;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Long component2() {
        return getParentid();
    }

    @Override
    public String component3() {
        return getName();
    }

    @Override
    public String component4() {
        return getState();
    }

    @Override
    public String component5() {
        return getDepartment();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Long value2() {
        return getParentid();
    }

    @Override
    public String value3() {
        return getName();
    }

    @Override
    public String value4() {
        return getState();
    }

    @Override
    public String value5() {
        return getDepartment();
    }

    @Override
    public SysRoleRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public SysRoleRecord value2(Long value) {
        setParentid(value);
        return this;
    }

    @Override
    public SysRoleRecord value3(String value) {
        setName(value);
        return this;
    }

    @Override
    public SysRoleRecord value4(String value) {
        setState(value);
        return this;
    }

    @Override
    public SysRoleRecord value5(String value) {
        setDepartment(value);
        return this;
    }

    @Override
    public SysRoleRecord values(Long value1, Long value2, String value3, String value4, String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SysRoleRecord
     */
    public SysRoleRecord() {
        super(SysRole.SYS_ROLE);
    }

    /**
     * Create a detached, initialised SysRoleRecord
     */
    public SysRoleRecord(Long id, Long parentid, String name, String state, String department) {
        super(SysRole.SYS_ROLE);

        setId(id);
        setParentid(parentid);
        setName(name);
        setState(state);
        setDepartment(department);
    }

    /**
     * Create a detached, initialised SysRoleRecord
     */
    public SysRoleRecord(jj.tech.paolu.repository.jooq.tables.pojos.SysRole value) {
        super(SysRole.SYS_ROLE);

        if (value != null) {
            setId(value.getId());
            setParentid(value.getParentid());
            setName(value.getName());
            setState(value.getState());
            setDepartment(value.getDepartment());
        }
    }
}
