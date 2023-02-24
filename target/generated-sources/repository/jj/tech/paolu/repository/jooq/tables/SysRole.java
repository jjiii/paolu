/*
 * This file is generated by jOOQ.
 */
package jj.tech.paolu.repository.jooq.tables;


import java.util.function.Function;

import jj.tech.paolu.repository.jooq.Keys;
import jj.tech.paolu.repository.jooq.Public;
import jj.tech.paolu.repository.jooq.tables.records.SysRoleRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function5;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SysRole extends TableImpl<SysRoleRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>PUBLIC.SYS_ROLE</code>
     */
    public static final SysRole SYS_ROLE = new SysRole();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SysRoleRecord> getRecordType() {
        return SysRoleRecord.class;
    }

    /**
     * The column <code>PUBLIC.SYS_ROLE.ID</code>.
     */
    public final TableField<SysRoleRecord, Long> ID = createField(DSL.name("ID"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>PUBLIC.SYS_ROLE.PARENTID</code>.
     */
    public final TableField<SysRoleRecord, Long> PARENTID = createField(DSL.name("PARENTID"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>PUBLIC.SYS_ROLE.NAME</code>.
     */
    public final TableField<SysRoleRecord, String> NAME = createField(DSL.name("NAME"), SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.SYS_ROLE.STATE</code>.
     */
    public final TableField<SysRoleRecord, String> STATE = createField(DSL.name("STATE"), SQLDataType.VARCHAR(3).defaultValue(DSL.field("NULL", SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.SYS_ROLE.DEPARTMENT</code>.
     */
    public final TableField<SysRoleRecord, String> DEPARTMENT = createField(DSL.name("DEPARTMENT"), SQLDataType.VARCHAR(50).nullable(false), this, "");

    private SysRole(Name alias, Table<SysRoleRecord> aliased) {
        this(alias, aliased, null);
    }

    private SysRole(Name alias, Table<SysRoleRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>PUBLIC.SYS_ROLE</code> table reference
     */
    public SysRole(String alias) {
        this(DSL.name(alias), SYS_ROLE);
    }

    /**
     * Create an aliased <code>PUBLIC.SYS_ROLE</code> table reference
     */
    public SysRole(Name alias) {
        this(alias, SYS_ROLE);
    }

    /**
     * Create a <code>PUBLIC.SYS_ROLE</code> table reference
     */
    public SysRole() {
        this(DSL.name("SYS_ROLE"), null);
    }

    public <O extends Record> SysRole(Table<O> child, ForeignKey<O, SysRoleRecord> key) {
        super(child, key, SYS_ROLE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<SysRoleRecord, Long> getIdentity() {
        return (Identity<SysRoleRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<SysRoleRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_A;
    }

    @Override
    public SysRole as(String alias) {
        return new SysRole(DSL.name(alias), this);
    }

    @Override
    public SysRole as(Name alias) {
        return new SysRole(alias, this);
    }

    @Override
    public SysRole as(Table<?> alias) {
        return new SysRole(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public SysRole rename(String name) {
        return new SysRole(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SysRole rename(Name name) {
        return new SysRole(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public SysRole rename(Table<?> name) {
        return new SysRole(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, Long, String, String, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function5<? super Long, ? super Long, ? super String, ? super String, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function5<? super Long, ? super Long, ? super String, ? super String, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}